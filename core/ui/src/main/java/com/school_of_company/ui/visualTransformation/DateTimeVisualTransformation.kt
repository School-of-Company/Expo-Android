package com.school_of_company.ui.visualTransformation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.school_of_company.ui.util.autoFormatToDateTime

class DateTimeVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        // 입력된 텍스트에서 숫자만 추출
        val digitsOnly = text.text.filter { it.isDigit() }
        // 숫자를 날짜 및 시간 형식으로 변환
        val transformedText = digitsOnly.autoFormatToDateTime()

        // 원본과 변환된 텍스트 간의 오프셋 매핑 생성
        val offsetMapping = createOffsetMapping(digitsOnly, transformedText)

        // 변환된 텍스트와 오프셋 매핑을 사용해 TransformedText 반환
        return TransformedText(AnnotatedString(transformedText), offsetMapping)
    }

    /**
     * 원본 텍스트와 변환된 텍스트 간의 오프셋 매핑을 생성합니다.
     * @param original 변환 이전의 숫자 문자열
     * @param transformed 변환 이후의 날짜 및 시간 문자열
     * @return OffsetMapping 객체
     */
    private fun createOffsetMapping(original: String, transformed: String): OffsetMapping {
        // 원본 텍스트의 각 문자와 변환된 텍스트 간의 매핑을 저장
        val originalToTransformedMap = mutableListOf<Int>()

        // 변환된 텍스트의 위치를 원본 텍스트의 인덱스와 연결
        val reverseMap = mutableMapOf<Int, Int>()

        var digitIndex = 0 // 원본 텍스트에서 숫자의 현재 위치를 추적

        // 변환된 텍스트를 순회하며 매핑 생성
        for (i in transformed.indices) {
            // 변환된 텍스트에서 숫자를 찾고 매핑에 추가
            if (digitIndex < original.length && transformed[i].isDigit()) {
                originalToTransformedMap.add(i) // 원본 -> 변환된 위치
                reverseMap[i] = digitIndex      // 변환된 -> 원본 위치
                digitIndex++
            }
        }

        // OffsetMapping 객체 반환
        return object : OffsetMapping {
            /**
             * 원본 텍스트의 오프셋을 변환된 텍스트의 오프셋으로 변환합니다.
             * @param offset 원본 텍스트에서의 커서 위치
             * @return 변환된 텍스트에서의 커서 위치
             */
            override fun originalToTransformed(offset: Int): Int {
                // 원본 오프셋이 매핑된 값을 반환하거나 변환된 텍스트의 길이를 반환
                return originalToTransformedMap.getOrElse(offset) { transformed.length }
            }

            /**
             * 변환된 텍스트의 오프셋을 원본 텍스트의 오프셋으로 변환합니다.
             * @param offset 변환된 텍스트에서의 커서 위치
             * @return 원본 텍스트에서의 커서 위치
             */
            override fun transformedToOriginal(offset: Int): Int {
                // 변환된 텍스트의 오프셋을 원본 오프셋으로 매핑하거나 원본 길이를 반환
                return reverseMap[offset] ?: original.length
            }
        }
    }
}
