package com.example.leaguelookup

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChampionObj(
    val key: String,
    val name: String,
    val title: String,
//    val image: Image,
    val stats: Stats
)

//@JsonClass(generateAdapter = true)
//data class Image(
//    val full: String,
//    val sprite: String,
//    val group: String,
//    val x: Int,
//    val y: Int,
//    val w: Int,
//    val h: Int
//)

@JsonClass(generateAdapter = true)
data class Stats(
    val hp: Double,
    val hpperlevel: Double,
    val mp: Double,
    val mpperlevel: Double,
    val movespeed: Double,
    val armor: Double,
    val armorperlevel: Double,
    val spellblock: Double,
    val spellblockperlevel: Double,
    val attackrange: Double,
    val hpregen: Double,
    val hpregenperlevel: Double,
    val mpregen: Double,
    val mpregenperlevel: Double,
    val crit: Double,
    val critperlevel: Double,
    val attackdamage: Double,
    val attackdamageperlevel: Double,
    val attackspeedperlevel: Double,
    val attackspeed: Double
)
