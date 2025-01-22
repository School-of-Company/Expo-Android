package com.school_of_company.datastore.serializer

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.school_of_company.datastore.AuthToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class AuthTokenSerializer @Inject constructor(
    private val keystoreHelper: KeystoreHelper
) : Serializer<AuthToken> {
    override val defaultValue: AuthToken = AuthToken.getDefaultInstance()

    // DataStore에서 데이터를 읽어와 "AuthToken" 객체로 변환하는 함수
    override suspend fun readFrom(input: InputStream): AuthToken =
        try {
            // 입력 스트림에서 AuthToken을 Protobuf 형식으로 읽어와 "AuthToken" 객체로 변환
            // 암호화된 데이터 복호화
            val decryptedData = keystoreHelper.decryptData(input.readBytes().toString())
            // 복호화된 데이터를 String으로 변환
            AuthToken.parseFrom(decryptedData.toByteArray()) // String을 다시 ByteArray로 변환하여 파싱
        } catch (e: InvalidProtocolBufferException) {
            // 형식 변환 중 오류가 발생하면 CorruptionException을 발생시킵니다.
            throw CorruptionException("Cannot read proto", e)
        }

    // DataStore에 데이터를 기록하기 위해 "AuthToken" 객체를 직렬화하는 함수
    override suspend fun writeTo(t: AuthToken, output: OutputStream) {
        withContext(Dispatchers.IO) {
            // 데이터를 암호화하고 출력 스트림에 기록
            val encryptedData = keystoreHelper.encryptData(t.accessToken) // 암호화된 데이터
            output.write(encryptedData.toByteArray()) // ByteArray로 출력
        }
    }
}
