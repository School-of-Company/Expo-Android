package com.school_of_company.datastore.serializer

interface KeystoreHelper {
    fun encryptData(data: String): String
    fun decryptData(encryptedData: String): String
}
