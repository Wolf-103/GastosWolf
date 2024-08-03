
import data.ExpenseManager
import data.ExpenseRepoImpl
import model.Expense
import model.ExpenseCategory
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue


/**
 * @author Cristian Manuel Orozco - Orozcocristian860@gmail.com
 * @created 27/07/2024 21:27
 */
class ExpenseRepoTest {
    private val expenseManager = ExpenseManager
    private val repo = ExpenseRepoImpl(expenseManager)

    @Test
    fun expense_model_list_is_not_empty_test() {
        //Give
        val expenseList = mutableListOf<Expense>()
        //When
        expenseList.addAll(repo.getAllExpenses())

        //Then
        assertTrue(expenseList.isNotEmpty())
    }

    @Test
    fun add_new_expense_test() {
        //Give
        val expenseList = repo.getAllExpenses()
        //When
        repo.addExpense(Expense(amount = 50.0, category = ExpenseCategory.HOUSE, description = "Alimentos"))
        //Then
        assertContains(expenseList, expenseList.first { it.id == 7L })
    }

    @Test
    fun edit_expense_test() {
        //Give
        val expenseListBefore = repo.getAllExpenses()
        //When
        val newExpenseId = 7L;
        repo.addExpense(Expense(amount = 50.0, category = ExpenseCategory.HOUSE, description = "Alimentos"))

        assertNotNull(expenseListBefore.find { it.id == newExpenseId })

        val expenseUpdate =Expense(id = newExpenseId, amount = 44.0, category = ExpenseCategory.OTHER, description = "Kilian y sus huesitos")
        repo.updateExpense(expenseUpdate)

        //Then
        val expenseListAfter = repo.getAllExpenses()
        assertEquals(expenseUpdate, expenseListAfter.find { it.id == newExpenseId })
    }

    @Test
    fun get_all_expense_test() {
        //Give
        val categoryList = mutableListOf<ExpenseCategory> ()

        //When
        categoryList.addAll(repo.getCategories())

        //Then
        assertTrue(categoryList.isNotEmpty())
    }

    @Test
    fun get_all_category_test() {
        //Give
        val allCategory = listOf(
            ExpenseCategory.CAR,
            ExpenseCategory.SNACKS,
            ExpenseCategory.COFFEE,
            ExpenseCategory.OTHER,
            ExpenseCategory.HOUSE,
            ExpenseCategory.GROCERIES,
            ExpenseCategory.PARTY
        )

        val repoCategory = repo.getCategories()

        //Then
        assertEquals(repoCategory, allCategory)
    }
}