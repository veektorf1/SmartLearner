package com.example.learnsmarter.repository

import com.example.learnsmarter.models.Flashcard
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await

class FlashcardRepository {
    private val db = FirebaseFirestore.getInstance()
    private val flashcardsRef = db.collection("flashcards")
    private val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    suspend fun addFlashcard(wordEng: String, wordPol: String) {
        val flashcard = hashMapOf(
            "userId" to userId,
            "wordEng" to wordEng,
            "wordPol" to wordPol,
            "updatedAt" to com.google.firebase.firestore.FieldValue.serverTimestamp()
        )

        flashcardsRef.add(flashcard).await()
    }

    suspend fun getUserFlashcards(): List<Flashcard> {
        val snapshot = flashcardsRef
            .whereEqualTo("userId", userId)
            .get()
            .await()

        return snapshot.documents.mapNotNull { doc ->
            val flashcard = doc.toObject<Flashcard>()
            flashcard?.copy(id = doc.id)
        }
    }
}
