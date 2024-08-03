
import model.Expense
import model.ExpenseCategory
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

/**
 * @author Cristian Manuel Orozco - Orozcocristian860@gmail.com
 * @created 27/07/2024 21:27
 */
class ExempleTest {
    @Test
    fun expense_model_list_test() {
        //Give
        val expenseList = mutableListOf<Expense>()
        val expense = Expense(id = 1, amount = 50.0, category = ExpenseCategory.HOUSE, description = "Alimentos")
        //When
        expenseList.add(expense)

        //Then
        assertContains(expenseList, expense)
    }

    @Test
    fun expense_model_param_test() {
        //Give
        val expenseList = mutableListOf<Expense>()
        val expense = Expense(id = 1, amount = 50.0, category = ExpenseCategory.HOUSE, description = "Alimentos")
        val expense2 = Expense(id = 2, amount = 20.0, category = ExpenseCategory.PARTY, description = "joda")

        //When
        expenseList.add(expense)
        expenseList.add(expense2)

        //Then
        assertContains(expenseList, expense)
        assertNotEquals(expense.category, expense2.category)
        assertEquals(expenseList[0].category,expense.category)
        assertNotEquals(expenseList[0].category,expense2.category)
        assertEquals(expenseList[1].category,expense2.category)
        assertNotEquals(expenseList[1].category,expense.category)
    }
}