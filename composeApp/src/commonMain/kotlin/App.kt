import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.TitleTopBarTypesEnum
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator
import navigation.Navigation
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * @author Cristian Manuel Orozco - Orozcocristian860@gmail.com
 * @created
 */

@Composable
@Preview
fun App() {
    PreComposeApp() {
        val colors = getColorsTheme()
        AppTheme {
            val navigator = rememberNavigator()
            val titleTopBar = getTitleTopBar(navigator)
            val isEditOrAddExpenses = titleTopBar != TitleTopBarTypesEnum.Dashboard.value
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        elevation = 0.dp,
                        title = {
                            Text(
                                text = titleTopBar,
                                fontSize = 25.sp,
                                color = colors.textColor
                            )
                        },
                        navigationIcon = {
                            if (isEditOrAddExpenses) {
                                IconButton(onClick = {
                                    navigator.popBackStack()
                                }) {
                                    Icon(
                                        modifier = Modifier.padding(16.dp),
                                        imageVector = Icons.Default.ArrowBackIosNew,
                                        contentDescription = "Back incon",
                                        tint = colors.textColor
                                    )

                                }
                            } else {
                                Icon(
                                    modifier = Modifier.padding(16.dp),
                                    imageVector = Icons.Default.Apps,
                                    contentDescription = "Dashboard icon",
                                    tint = colors.textColor
                                )
                            }
                        },
                        backgroundColor = colors.BackgroundColor
                    )
                },
                floatingActionButton = {
                    if (!isEditOrAddExpenses) {
                        FloatingActionButton(
                            modifier = Modifier.padding(8.dp),
                            onClick = {
                                navigator.navigate("/addExpense")
                            },
                            backgroundColor = colors.addIconColor,
                            shape = RoundedCornerShape(50),
                            contentColor = Color.White
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                tint = Color.White,
                                contentDescription = "Add Expense"
                            )
                        }
                    }
                }
            ) {
                Navigation(navigator)
            }
        }
    }
}

@Composable
fun getTitleTopBar(navigator: Navigator): String {
    var titleTopBar = TitleTopBarTypesEnum.Dashboard

    val isOnAddExpenses =
        navigator.currentEntry.collectAsState(null).value?.route?.route.equals("/addExpense/{id}?")
    if (isOnAddExpenses) {
        titleTopBar = TitleTopBarTypesEnum.AddExpense
    }
    val isOnEditExpense = navigator.currentEntry.collectAsState(null).value?.path<Long>("id")
    isOnEditExpense?.let {
        titleTopBar = TitleTopBarTypesEnum.EditExpense
    }
    return titleTopBar.value
}