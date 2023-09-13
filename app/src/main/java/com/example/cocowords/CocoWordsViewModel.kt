package com.example.cocowords

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CocoWordsViewModel : ViewModel() {
    // Define the mutable state variable for the generated password
    val generatedPassword = mutableStateOf("")

    // Function to generate a random password
    suspend fun generateRandomPassword() {
        val apiWord = CocoWordsModel().getRandomWord()
        generatedPassword.value = apiWord
    }
}
