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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cocowords.ui.theme.CocoCoolBrown
import com.example.cocowords.ui.theme.CocoCoolGreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CocoWordsPortrait(viewModel: CocoWordsViewModel) {
    var generatedPassword by remember { mutableStateOf("") }
    var isButtonEnabled by remember { mutableStateOf(true) }
    var isLoading by remember { mutableStateOf(false) }

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
            horizontalAlignment = CenterHorizontally
        ) {
            Text("Word Generator", fontSize = 24.sp)

            Spacer(modifier = Modifier.padding(16.dp))

            Box(
                modifier = Modifier
                    .width(200.dp)
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(8.dp)
            ) {
                Text(
                    text = generatedPassword,
                    fontSize = 18.sp,
                    color = Color.Gray,
                    modifier = Modifier.align(Center)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (isButtonEnabled) {
                        isButtonEnabled = false
                        isLoading = true // Show progress indicator
                        CoroutineScope(Dispatchers.Default).launch {
                            viewModel.generateRandomPassword()
                            delay(1500) //
                            isLoading = false // Hide progress indicator
                            isButtonEnabled = true
                            generatedPassword = viewModel.generatedPassword.value
                        }
                    }
                },
                enabled = isButtonEnabled,
                colors = ButtonDefaults.buttonColors(CocoCoolBrown)

            ) {
                Text("Generate Word")
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .align(Center)
                .padding(bottom = 260.dp),
            horizontalAlignment = CenterHorizontally,
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    color = CocoCoolGreen
                )
            }

            Spacer(modifier = Modifier.padding(bottom = 32.dp))
        }
    }
}
