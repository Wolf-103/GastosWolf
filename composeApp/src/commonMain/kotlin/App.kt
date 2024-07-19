
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import moe.tlaster.precompose.PreComposeApp
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * @author Cristian Manuel Orozco - Orozcocristian860@gmail.com
 * @created
*/

@Composable
@Preview
fun App() {
    PreComposeApp(){
        var colors = getColorsTheme()
        AppTheme {
            Column (modifier = Modifier.fillMaxSize()){
                Text("Ejemplo")
                Text("Ejemplo 2")
                Text("Ejemplo 2")

            }
        }
    }
}