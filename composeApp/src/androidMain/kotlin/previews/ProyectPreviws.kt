package previews

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import data.ExpenseManager
import presentation.ExpensesUiState
import ui.AllExpensesHeader
import ui.ExpensesItem
import ui.ExpensesScreen
import ui.ExpensesTotalHeader

/**
 * @author Cristian Manuel Orozco - Orozcocristian860@gmail.com
 * @created 18/07/2024 19:56
 */
@Preview(
    showBackground = true
)
@Composable
private fun ExpensesTotalHeaderPreview() {
    Box(Modifier.padding(16.dp)) {
        ExpensesTotalHeader(total = 2000.9);
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun AllExpensesHeaderPreview() {
    Box(Modifier.padding(16.dp)) {
        AllExpensesHeader()
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun ExpensesItemPreview() {
    Box(Modifier.padding(16.dp)) {
        ExpensesItem(expense = ExpenseManager.fakeExpenseList[0], onExpenseClick = {})
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun ExpensesScreenPreview() {
    Box(Modifier.padding(16.dp)) {
        ExpensesScreen(
            uiState = ExpensesUiState(
                expenses = ExpenseManager.fakeExpenseList,
                total = ExpenseManager.fakeExpenseList.sumOf { it.amount }
            ), onExpenseClick = {})
    }
}