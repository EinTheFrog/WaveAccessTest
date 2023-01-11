package com.example.waveaccesstest.api

import com.example.waveaccesstest.model.data.Candidate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import org.jsoup.Jsoup

private const val CANDIDATES_URL = "https://firebasestorage.googleapis.com/v0/b/candidates--questionnaire.appspot.com/o/users.json?alt=media&token=e3672c23-b1a5-4ca7-bb77-b6580d75810c"

class CandidatesAPI {
    suspend fun getCandidates(): List<Candidate> = withContext(Dispatchers.IO) {
        val candidates = mutableListOf<Candidate>()
        val doc = Jsoup.connect(CANDIDATES_URL).ignoreContentType(true).get()
        val json = doc.select("body").html()
        val candidateArray = JSONArray(json)
        for (i in 0 until candidateArray.length()) {
            val candidateJson = candidateArray.get(i) as JSONObject
            val friendsJsonArray = candidateJson.getJSONArray("friends")
            val friends = mutableListOf<Long>()
            for (j in 0 until friendsJsonArray.length()) {
                val friendJson = friendsJsonArray.get(j) as JSONObject
                val friendId = friendJson.getLong("id")
                friends.add(friendId)
            }
            val candidate = Candidate(
                id = candidateJson.getLong("id"),
                isActive = candidateJson.getBoolean("isActive"),
                age = candidateJson.getInt("age"),
                eyeColor = candidateJson.getString("eyeColor"),
                name = candidateJson.getString("name"),
                company = candidateJson.getString("company"),
                email = candidateJson.getString("email"),
                phone = candidateJson.getString("phone"),
                address = candidateJson.getString("address"),
                about = candidateJson.getString("about"),
                registered = candidateJson.getString("registered").split(" ")[0].trim(),
                latitude = candidateJson.getDouble("latitude"),
                longitude = candidateJson.getDouble("longitude"),
                friends = friends,
                favoriteFruit = candidateJson.getString("favoriteFruit")
            )
            candidates.add(candidate)
        }

        return@withContext candidates
    }
}