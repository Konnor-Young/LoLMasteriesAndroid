package com.example.leaguelookup

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MasteryObj(
    val championId: Long,
    @Json(name = "championLevel")
    val masteryLevel: Int,
    @Json(name = "championPoints")
    val masteryPoints: Int,
)
