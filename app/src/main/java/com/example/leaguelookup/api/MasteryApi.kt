package com.example.leaguelookup.api


import com.example.leaguelookup.MasteryObj
import com.example.leaguelookup.MasteryResponse
import retrofit2.http.GET
import retrofit2.http.Path

private const val API_KEY = "RGAPI-2ed19305-8567-48db-b5d6-6d583dc0e678"

interface MasteryApi {

    @GET(
        "lol/champion-mastery/v4/champion-masteries/by-summoner/" +
                "{summonerId}" +
                "?api_key=$API_KEY"
    )
    suspend fun fetchMastery(@Path("summonerId") summonerId: String): List<MasteryObj>
}