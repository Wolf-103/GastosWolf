package presentation

import domain.ExpenseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.Expense
import model.ExpenseCategory
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

/**
 * @author Cristian Manuel Orozco - Orozcocristian860@gmail.com
 * @created 26/07/2024 11:59
 */
data class ExpensesUiState(
    val expenses: List<Expense> = emptyList(),
    val total: Double = 0.0
)

class ExpensesViewModel(private val repo: ExpenseRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(ExpensesUiState())
    val uiState = _uiState.asStateFlow()
    private val allExpense = repo.getAllExpenses()

    init {
        getAllExpense()
    }

    private fun getAllExpense() {
        viewModelScope.launch {
            _uiState.update { state ->
                updateState(state)
            }
        }
    }

    private fun updateState(state: ExpensesUiState) = state.copy(
        expenses = allExpense,
        total = allExpense.sumOf { it.amount }
    )

    fun addExpense(expense: Expense) {
        viewModelScope.launch {
            repo.addExpense(expense)
            getAllExpense()
        }
    }

    fun updateExpense(expense: Expense) {
        viewModelScope.launch {
            repo.updateExpense(expense)
            getAllExpense()
        }
    }

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            repo.deleteExpense(expense)
            getAllExpense()
        }
    }

    fun getExpenseById(id: Long): Expense {
        return allExpense.first {
            it.id == id
        }
    }

    fun getCategories(): List<ExpenseCategory>{
        return repo.getCategories()
    }
}