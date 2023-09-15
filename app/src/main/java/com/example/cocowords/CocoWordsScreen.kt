import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import com.example.cocowords.CocoWordsLandscape
import com.example.cocowords.CocoWordsPortrait
import com.example.cocowords.CocoWordsViewModel

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