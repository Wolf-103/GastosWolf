package domain

import model.Expense
import model.ExpenseCategory

/**
 * @author Cristian Manuel Orozco - Orozcocristian860@gmail.com
 * @created 26/07/2024 11:32
 */
interface ExpenseRepository {

    fun getAllExpenses(): List<Expense>

    fun addExpense(expense: Expense)

    fun updateExpense(expense: Expense)

    fun getCategories(): List<ExpenseCategory>

    fun deleteExpense(expense: Expense): Boolean
}