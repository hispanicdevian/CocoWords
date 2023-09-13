package com.example.cocowords

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class CocoWordsModel {
    suspend fun getRandomWord(): String {
        return withContext(Dispatchers.IO) {
            val httpClient = HttpClient()
            val response: HttpResponse = httpClient.get("https://api.wordnik.com/v4/words.json/randomWord?api_key=iqa3mhnt0yryz8gsk7j9heukupgcjg0m1p96x8ah2dn0r97mr")

            if (response.status.isSuccess()) {
                val responseBody = response.bodyAsText()

                try {
                    val jsonObject = Json.parseToJsonElement(responseBody).jsonObject
                    return@withContext jsonObject["word"]?.jsonPrimitive?.content ?: ""
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            return@withContext ""
        }
    }
}
