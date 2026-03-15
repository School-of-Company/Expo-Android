#!/bin/bash

# Ensure scripts directory exists
mkdir -p "$(dirname "$0")"

# Function to exit if input is 'q'
exit_if_q() {
    if [[ "$1" == "q" || "$1" == "Q" ]]; then
        echo "배포 단계를 취소하고 종료합니다."
        exit 0
    fi
}

echo "--------------------------------"
echo "[1] 로컬 APK 추출 (Debug)"
echo "[2] 스토어 배포 (CD 트리거)"
echo "[q] 취소"
echo "--------------------------------"
read -p "선택하세요: " MENU_SELECTION
exit_if_q "$MENU_SELECTION"

if [ "$MENU_SELECTION" == "1" ]; then
    echo "로컬 APK 추출 중..."
    ./gradlew assembleDebug
    echo "완료: app/build/outputs/apk/debug/"
    exit 0
elif [ "$MENU_SELECTION" == "2" ]; then
    # Check current branch
    CURRENT_BRANCH=$(git branch --show-current)
    
    # Fetch latest master info
    echo "원격 master 브랜치 상태 확인 중..."
    git fetch origin master --quiet

    # Check for commits in master not in current branch
    MISSING_COMMITS=$(git log HEAD..origin/master --oneline)
    if [ -n "$MISSING_COMMITS" ]; then
        echo "------------------------------------------------"
        echo "⚠️  알림: 현재 브랜치에 반영되지 않은 master의 변경사항이 있습니다!"
        echo "누락된 커밋 목록:"
        echo "$MISSING_COMMITS" | head -n 5
        [ $(echo "$MISSING_COMMITS" | wc -l) -gt 5 ] && echo "...외 $(($(echo "$MISSING_COMMITS" | wc -l) - 5))개의 커밋 더 있음"
        echo "------------------------------------------------"
        read -p "master의 최신 변경사항 없이 배포를 진행하시겠습니까? (y/n/q): " MERGE_CONFIRM
        exit_if_q "$MERGE_CONFIRM"
        if [[ ! "$MERGE_CONFIRM" =~ ^[yY]$ ]]; then
            echo "배포를 중단합니다. 'git merge origin/master' 또는 브랜치 전환 후 다시 시도하세요."
            exit 0
        fi
    fi

    if [ "$CURRENT_BRANCH" != "master" ]; then
        echo "⚠️  경고: 현재 브랜치가 'master'가 아닙니다 ($CURRENT_BRANCH)."
        echo "스토어 배포는 일반적으로 'master' 브랜치에서 수행해야 합니다."
        read -p "정말로 진행하시겠습니까? (y/n/q): " CONFIRM
        exit_if_q "$CONFIRM"
        if [[ ! "$CONFIRM" =~ ^[yY]$ ]]; then
            echo "배포를 취소합니다."
            exit 0
        fi
    fi

    # Check if gh CLI is installed
    if ! command -v gh &> /dev/null; then
        echo "Error: GitHub CLI (gh)가 설치되어 있지 않습니다."
        echo "설치 후 다시 시도해주세요 (https://cli.github.com/)."
        exit 1
    fi

    echo "--------------------------------"
    echo "버전 업데이트 방식을 선택하세요:"
    echo "[1] None   (버전 유지)"
    echo "[2] Patch  (x.x.Y+1)"
    echo "[3] Minor  (x.Y+1.0)"
    echo "[4] Major  (X+1.0.0)"
    echo "[q] 취소"
    echo "--------------------------------"
    read -p "선택 (1-4/q): " BUMP_CHOICE
    exit_if_q "$BUMP_CHOICE"

    case "$BUMP_CHOICE" in
        1) BUMP_TYPE="None" ;;
        2) BUMP_TYPE="Patch" ;;
        3) BUMP_TYPE="Minor" ;;
        4) BUMP_TYPE="Major" ;;
        *) echo "잘못된 선택입니다."; exit 1 ;;
    esac

    echo "GitHub Action CD 트리거 중... (Track: internal, Bump: $BUMP_TYPE)"
    gh workflow run "Android CD" --ref master -f version_bump_type="$BUMP_TYPE" -f track="internal"
    
    if [ $? -eq 0 ]; then
        echo "성공적으로 트리거되었습니다. GitHub Actions 탭에서 확인하세요."
    else
        echo "트리거 실패. GitHub 로그인을 확인하세요 (gh auth login)."
    fi
else
    echo "잘못된 메뉴 선택입니다."
    exit 1
fi
