package com.school_of_company.datastore.serializer

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.inject.Inject

class KeystoreHelperImpl @Inject constructor() : KeystoreHelper {

    private val alias = "TokenKeyAlias"
    private val keyStore: KeyStore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }

    init {
        if (!keyStore.containsAlias(alias)) {
            generateKey()
        }
    }

    private fun generateKey() {
        val keyGenerator =
            KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
        keyGenerator.init(
            KeyGenParameterSpec.Builder(
                alias,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .build()
        )
        keyGenerator.generateKey()
    }

    private fun getSecretKey(): SecretKey {
        return keyStore.getKey(alias, null) as SecretKey
    }

    override fun encryptData(data: String): String {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey())
        val iv = cipher.iv
        val encryptedData = cipher.doFinal(data.toByteArray())

        // IV와 암호화된 데이터를 Base64로 결합
        return Base64.encodeToString(iv + encryptedData, Base64.DEFAULT)

    }

    override fun decryptData(encryptedData: String): String {
        val decodedData = Base64.decode(encryptedData, Base64.DEFAULT)
        val iv = decodedData.sliceArray(0 until 12)
        val encryptedBytes = decodedData.sliceArray(12 until decodedData.size)

        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), GCMParameterSpec(128, iv))
        val originalData = cipher.doFinal(encryptedBytes)
        return String(originalData)
    }
}

