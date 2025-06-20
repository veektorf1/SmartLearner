package com.example.learnsmarter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnsmarter.models.Flashcard
import com.example.learnsmarter.repository.FlashcardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FlashcardViewModel : ViewModel() {
    private val repository = FlashcardRepository()

    private val _flashcards = MutableStateFlow<List<Flashcard>>(emptyList())
    val flashcards: StateFlow<List<Flashcard>> = _flashcards

    fun loadFlashcards() {
        viewModelScope.launch {
            _flashcards.value = repository.getUserFlashcards()
        }
    }

    fun addFlashcard(wordEng: String, wordPol: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            repository.addFlashcard(wordEng, wordPol)
            onSuccess()
            loadFlashcards()
        }
    }
}
