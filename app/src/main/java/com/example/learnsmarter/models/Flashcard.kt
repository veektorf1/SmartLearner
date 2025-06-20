package com.example.learnsmarter.models

import com.google.firebase.Timestamp


data class Flashcard(
    val id: String = "",
    val userId: String = "",
    val wordEng: String = "",
    val wordPol: String = "",
    val updatedAt: Timestamp? = null
)