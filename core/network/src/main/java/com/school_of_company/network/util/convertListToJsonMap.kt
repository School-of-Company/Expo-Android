package com.school_of_company.network.util

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

val moshi: Moshi = Moshi.Builder().build()

fun List<String>.convertListToJsonMap(): String {
    val jsonAdapter = moshi.adapter(Map::class.java)

    return jsonAdapter.toJson(this.mapIndexed { index, value -> "${index + 1}" to value }.toMap())
}

fun String.convertJsonMapToList(): List<String> {
    val type = Types.newParameterizedType(Map::class.java, String::class.java, String::class.java)
    val jsonAdapter = moshi.adapter<Map<String, String>>(type)

    val map = jsonAdapter.fromJson(this) ?: return emptyList()
    return map.values.toList()
}
