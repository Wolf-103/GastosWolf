package data

import domain.ExpenseRepository
import model.Expense
import model.ExpenseCategory

/**
 * @author Cristian Manuel Orozco - Orozcocristian860@gmail.com
 * @created 26/07/2024 11:36
 */
class ExpenseRepoImpl(private val expenseManager: ExpenseManager) : ExpenseRepository {

    override fun getAllExpenses(): List<Expense> {
        return expenseManager.fakeExpenseList
    }

    override fun addExpense(expense: Expense) {
        expenseManager.addExpense(expense)
    }

    override fun updateExpense(expense: Expense) {
        expenseManager.updateExpense(expense)
    }

    override fun getCategories(): List<ExpenseCategory> {
        return expenseManager.getCategories()
    }

    override fun deleteExpense(expense: Expense): Boolean {
        return expenseManager.deleteExpense(expense)
    }

}