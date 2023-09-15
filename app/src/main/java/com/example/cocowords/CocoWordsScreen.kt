package com.example.cocowords

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun CocoWordsScreen(viewModel: CocoWordsViewModel) {
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    if (isPortrait) {
        CocoWordsPortrait(viewModel)
    } else {
        CocoWordsLandscape(viewModel)
    }
}