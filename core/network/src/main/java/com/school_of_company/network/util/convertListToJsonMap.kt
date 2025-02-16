package com.school_of_company.network.util

import com.squareup.moshi.Moshi

val moshi: Moshi = Moshi.Builder().build()
val jsonAdapter = moshi.adapter(Map::class.java)

fun List<String>.convertListToJsonMap(): String =
    jsonAdapter.toJson(this.mapIndexed { index, value -> "${index + 1}" to value }.toMap())
