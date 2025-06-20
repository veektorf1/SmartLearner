package com.example.learnsmarter.api

import com.example.learnsmarter.api.TranslateWord.translateWord

fun AddFlashcard(word: String){
        translateWord(word, onSuccess = {success -> println("Translated word: $success")},
        onError = { err -> println("Błąd: $err") } )
}