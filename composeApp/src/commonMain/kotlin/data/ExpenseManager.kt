package data

import model.Expense
import model.ExpenseCategory

/**
 * @author Cristian Manuel Orozco - Orozcocristian860@gmail.com
 * @created 26/07/2024 11:32
 */
object ExpenseManager {
    private var currentId = 1L

    var fakeExpenseList = mutableListOf(
        Expense(
            id = currentId++,
            amount = 70.0,
            category = ExpenseCategory.CAR,
            description = "shell"
        ),
        Expense(
            id = currentId++,
            amount = 10.0,
            category = ExpenseCategory.HOUSE,
            description = "comida"
        ),
        Expense(
            id = currentId++,
            amount = 40.0,
            category = ExpenseCategory.SNACKS,
            description = "chango mas"
        ),
        Expense(
            id = currentId++,
            amount = 60.0,
            category = ExpenseCategory.COFFEE,
            description = "Salida con frasco"
        ),
        Expense(
            id = currentId++,
            amount = 70.0,
            category = ExpenseCategory.OTHER,
            description = "Frasco cosas"
        ),
        Expense(
            id = currentId++,
            amount = 80.0,
            category = ExpenseCategory.OTHER,
            description = "Kilian cosas"
        ),
        Expense(
            id = currentId++,
            amount = 30.0,
            category = ExpenseCategory.GROCERIES,
            description = "Chango mas"
        )
    )

    fun addExpense(expense: Expense) {
        fakeExpenseList.add(expense.copy(id = currentId++))
    }

    fun updateExpense(expense: Expense) {
        val index = getIndex(expense)
        if (index != -1) {
            fakeExpenseList[index] = fakeExpenseList[index].copy(
                amount = expense.amount,
                category = expense.category,
                description = expense.description
            )
        }
    }

    private fun getIndex(expense: Expense): Int {
        return fakeExpenseList.indexOfFirst { it ->
            it.id == expense.id
        }
    }

    fun deleteExpense(expense: Expense): Boolean {
        val index = getIndex(expense)
        if (index != -1) {
            fakeExpenseList.removeAt(index)
            return true
        }
        return false
    }

    fun getCategories(): List<ExpenseCategory> {
        return listOf(
            ExpenseCategory.CAR,
            ExpenseCategory.SNACKS,
            ExpenseCategory.COFFEE,
            ExpenseCategory.OTHER,
            ExpenseCategory.HOUSE,
            ExpenseCategory.GROCERIES,
            ExpenseCategory.PARTY
        )
    }
}