package com.example.cocowords

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Composable
fun PasswordScreen() {
    var generatedPassword by remember { mutableStateOf("") }
    var isButtonEnabled by remember { mutableStateOf(true) } // Track button state
    var isLoading by remember { mutableStateOf(false) } // Track loading state

    suspend fun getRandomWord(): String {
        val httpClient = HttpClient(OkHttp)

        val response: HttpResponse = httpClient.get("https://api.wordnik.com/v4/words.json/randomWord?api_key=API_KEY_HERE")

        if (response.status.isSuccess()) {
            val responseBody = response.bodyAsText()

            try {
                val jsonObject = kotlinx.serialization.json.Json.parseToJsonElement(responseBody).jsonObject
                return jsonObject["word"]?.jsonPrimitive?.content ?: ""
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return ""
    }

    suspend fun generateRandomPassword() {
        if (!isButtonEnabled) {
            return // Exit if the button is locked
        }

        isButtonEnabled = false // Lock the button
        isLoading = true // Show loading animation

        try {
            val apiWord = getRandomWord()
            generatedPassword = apiWord

            delay(2000) // Delay for 2 seconds

        } finally {
            isLoading = false // Hide loading animation
            isButtonEnabled = true // Unlock the button after the delay
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("Password Generator", fontSize = 24.sp)

            Spacer(modifier = Modifier.padding(16.dp))

            Box(
                modifier = Modifier
                    .width(200.dp)
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(10.dp)
            ) {
                Text(
                    text = generatedPassword,
                    fontSize = 18.sp,
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    CoroutineScope(Dispatchers.Default).launch {
                        generateRandomPassword()
                    }
                },
                enabled = isButtonEnabled // Enable/disable the button based on the state
            ) {
                Text("Generate Password")
            }
        }

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 200.dp)
                    .align(Alignment.BottomCenter), // Align the loading indicator at the bottom
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.Gray)
            }
        }
    }
}