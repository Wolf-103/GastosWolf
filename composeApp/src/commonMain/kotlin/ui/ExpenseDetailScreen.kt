package ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.TitleTopBarTypesEnum
import getColorsTheme
import kotlinx.coroutines.launch
import model.Expense
import model.ExpenseCategory

/**
 * @author Cristian Manuel Orozco - Orozcocristian860@gmail.com
 * @created 27/07/2024 09:14
 */

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExpenseDetailScreen(
    expenseToEdit: Expense? = null,
    categoryList: List<ExpenseCategory> = emptyList(),
    backToHome: (Expense) -> Unit
) {
    val colors = getColorsTheme()
    var price by remember { mutableStateOf(expenseToEdit?.amount ?: 0.0) }
    var description by remember { mutableStateOf(expenseToEdit?.description ?: "") }
    var category by remember { mutableStateOf(expenseToEdit?.category?.name ?: "") }
    var categorySelected by remember {
        mutableStateOf(
            expenseToEdit?.category?.name ?: "Select a category"
        )
    }
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()

    /**
     * Esta funcion solo se ejecuta cuando un valor cambia
     * En un composable cuando cambiamos valores, generamos que se recargue la pantalla
     * esto no es eficiente, por lo que debemos evitarlo
     * Usamos esto para mostrar el teclado
     * Ademas del teclado se selecciona las categorias, asi que para controlar quien se muestra,
     * vamos a ocular el teclado si el shet bottom esta activo
     */
    LaunchedEffect(sheetState.targetValue) {
        if (sheetState.targetValue == ModalBottomSheetValue.Expanded) {
            keyboardController?.hide()
        }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            CategoryBottomSheetContent(
                categories = categoryList
            ) {
                category = it.name
                categorySelected = it.name
                scope.launch {
                    sheetState.hide()
                }
            }
        }
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(vertical = 16.dp, horizontal = 16.dp)) {
            ExpenseAmount(
                price = price,
                onPriceChange = {
                    price = it
                },
                keyboardController = keyboardController
            )
            Spacer(modifier = Modifier.height(30.dp))
            ExpenseTypeSelector(
                categorySelected = categorySelected,
                openBottomSheet = {
                    scope.launch {
                        sheetState.show()
                    }
                }
            )
            Spacer(modifier = Modifier.height(30.dp))
            ExpenseDescription(
                description = description,
                onDescriptionChange = {
                    description = it
                },
                keyboardController = keyboardController
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(45)),
                onClick = {
                    val expense = Expense(
                        amount = price,
                        description = description,
                        category = ExpenseCategory.valueOf(category)
                    )
                    val expenseIfEdit = expenseToEdit?.id?.let { expense.copy(id = it) }
                    backToHome(expenseIfEdit ?: expense)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colors.purple,
                    contentColor = Color.White
                ),
                enabled = (price != 0.0
                        && description.isNotBlank()
                        && category.isNotBlank())
            ){
                expenseToEdit?.let {
                    Text(
                        text = TitleTopBarTypesEnum.EditExpense.value,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    return@Button
                }
                Text(
                    text = TitleTopBarTypesEnum.AddExpense.value,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}

@Composable
fun ExpenseAmount(
    price: Double,
    onPriceChange: (Double) -> Unit,
    keyboardController: SoftwareKeyboardController?
) {
    val colors = getColorsTheme()
    var text by remember { mutableStateOf("$price") }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Amount",
            color = Color.Gray,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = text,
                fontSize = 25.sp,
                fontWeight = FontWeight.ExtraBold,
                color = colors.textColor
            )
            TextField(
                modifier = Modifier.weight(1F),
                value = text,
                onValueChange = {
                    val numericText = it.filter { it.isDigit() || it == '.' }
                    text = if (numericText.isNotEmpty() && numericText.count { it == '.' } <= 1) {
                        try {
                            val newValue = numericText.toDouble()
                            onPriceChange(newValue)
                            numericText
                        } catch (e: NumberFormatException) {
                            ""
                        }
                    } else {
                        onPriceChange(0.0)
                        ""
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = colors.textColor,
                    backgroundColor = colors.BackgroundColor,
                    focusedIndicatorColor = Color.Transparent,
                    focusedLabelColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    unfocusedLabelColor = Color.Transparent
                ),
                textStyle = TextStyle(fontSize = 35.sp, fontWeight = FontWeight.ExtraBold)
            )
            Text(
                text = "USD",
                color = Color.Gray,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
        Divider(
            color = Color.Gray,
            thickness = 2.dp
        )
    }
}

@Composable
private fun CategoryBottomSheetContent(
    categories: List<ExpenseCategory>,
    onCategorySelected: (ExpenseCategory) -> Unit,
) {
    LazyVerticalGrid(
        modifier = Modifier.padding(16.dp),
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.Center
    ) {
        items(categories) {
            CategoryItem(category = it, onCategorySelected = onCategorySelected)
        }
    }
}

@Composable
private fun CategoryItem(
    category: ExpenseCategory,
    onCategorySelected: (ExpenseCategory) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(8.dp)
            .clickable { onCategorySelected(category) },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            //Recorte de imagen?
            modifier = Modifier.size(40.dp).clip(CircleShape),
            imageVector = category.icon,
            contentDescription = "Image category",
            contentScale = ContentScale.Crop
        )
        Text(text = category.name)
    }
}

@Composable
private fun ExpenseTypeSelector(
    categorySelected: String,
    openBottomSheet: () -> Unit
) {
    val colors = getColorsTheme()
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                text = "Expense Type",
                color = Color.Gray,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = categorySelected,
                color = colors.textColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        IconButton(
            modifier = Modifier.clip(RoundedCornerShape(35)).background(colors.colorArrowRound),
            onClick = {
                openBottomSheet.invoke()
            }
        ) {
            Icon(
                Icons.Default.KeyboardArrowUp,
                contentDescription = "Open category options",
                tint = colors.textColor
            )
        }
    }
}

@Composable
fun ExpenseDescription(
    description: String,
    onDescriptionChange: (String) -> Unit,
    keyboardController: SoftwareKeyboardController?
) {
    var text by remember { mutableStateOf(description) }
    val colors = getColorsTheme()
    Column(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Description",
            color = Color.Gray,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = {
                if (it.length <= 200) {
                    text = it
                    onDescriptionChange(it)
                }
            },
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                textColor = colors.textColor,
                backgroundColor = colors.BackgroundColor,
                focusedIndicatorColor = Color.Transparent,
                focusedLabelColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                unfocusedLabelColor = Color.Transparent
            ),
            textStyle = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            )
        )
        Divider(
            color = Color.Black,
            thickness = 2.dp
        )
    }
}
