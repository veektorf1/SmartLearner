package com.example.learnsmarter.api

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import com.example.learnsmarter.api.API_lingvanex
import org.json.JSONObject
import java.io.IOException

object TranslateWord {

    private const val URL = "https://api-b2b.backenster.com/b1/api/v3/translate"
    private val client = OkHttpClient()

    fun translateWord(
        word: String,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        val jsonBody = JSONObject().apply {
            put("platform", "api")
            put("from", "en_GB")
            put("to", "pl_PL")
            put("data", word)
        }

        val body = jsonBody.toString().toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url(URL)
            .addHeader("Authorization", API_lingvanex)
            .addHeader("accept", "application/json")
            .addHeader("content-type", "application/json")
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                onError("Failed: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!it.isSuccessful) {
                        onError("HTTP error: ${it.code}")
                        return
                    }

                    val responseString = it.body?.string()
                    val json = JSONObject(responseString)
                    val translation = json.getString("result") // lub "translated" w innej wersji
                    onSuccess(translation)
                }
            }
        })
    }
}