package navigation

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import getColorsTheme
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import org.koin.core.parameter.parametersOf
import presentation.ExpensesViewModel
import ui.ExpenseDetailScreen
import ui.ExpensesScreen

/**
 * @author Cristian Manuel Orozco - Orozcocristian860@gmail.com
 * @created 26/07/2024 12:57
 */

@Composable
fun Navigation(navigator: Navigator, modifier: Modifier = Modifier) {
    var colors = getColorsTheme()
    val viewModel = koinViewModel(ExpensesViewModel::class){
        parametersOf()
    }
//    val viewModel = viewModel(modelClass = ExpensesViewModel::class) {
//        ExpensesViewModel(ExpenseRepoImpl(ExpenseManager))
//    }

    NavHost(
        modifier = Modifier.background(colors.BackgroundColor),
        navigator = navigator,
        initialRoute = "/home"
    ) {
        scene(route = "/home") {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            ExpensesScreen(uiState) { expense ->
                navigator.navigate("/addExpense/${expense.id}")
            }
        }

//        backStackEntry puede ser cualquier valor, cuando se llama a scena se trae la scena o vista anterior
//       ni siquiera es necesario colocar backStrak de manera expicita, podemos usar it, y se referiría a el backStrack
//        scene(route = "/addExpense/{id}") {
//            val idFromPath = it.path<Long>("id")
//        }
        scene(route = "/addExpense/{id}?") { backStackEntry ->
            val idFromPath = backStackEntry.path<Long>("id")
            // Verificamos que no sea null el id que estamos trayendo del backstrack
            // ?.lef se usa cuando queremos saber si una referencia es o no nula
            // es una combinacion de operador seguro (?) y funcion de ambito (lef)
            // sirve para ejecutar un bloque solo si el objeto no es nulo
            // ? verifica que la referencia no es nula en este caso, y como no se puede usar sola, se realiza lef
            // lef es una funcion de extension que ejecuta un bloque solo si el objeto al que se llama no es nulo,
            // dentro del codigo se pasa el objeto de referencia, generalmente es it, en nuestro caso colcoamos id
            val selectExpense = idFromPath?.let { id -> viewModel.getExpenseById(id)}
            //Si existe, entonces la editamos sino la agregamos
            // es como usar una misma funcion para dos acciones, si no me pasa id entonces estoy agregando
            // y si me pasa algo es editar
            // En expensedetail definimos una vista que devuelve un expense por callback,
            // por eso le pasamos parametros y despues tenemos un it con el que podemos trabajar
            ExpenseDetailScreen(
                expenseToEdit = selectExpense,
                categoryList = viewModel.getCategories()
            ){ expenseBack ->
                //Verificamos si seleccionamos un expense o si lo estamos editando
                if(selectExpense == null){
                    viewModel.addExpense(expenseBack)
                }else{
                    viewModel.updateExpense(expenseBack)
                }
                navigator.popBackStack()
            }
        }
    }
}

