package di

import data.ExpenseManager
import data.ExpenseRepoImpl
import domain.ExpenseRepository
import org.koin.core.module.dsl.createdAtStart
import org.koin.core.module.dsl.withOptions
import org.koin.dsl.module
import presentation.ExpensesViewModel

/**
 * @author Cristian Manuel Orozco - Orozcocristian860@gmail.com
 * @created 23/08/2024 20:28
 */

fun appModule() = module {
    single { ExpenseManager }.withOptions { createdAtStart() }
    /**
     * Se genera un single de la interface ExpenseRepository, pero le decimos que usse
     * la implementacion ExpenseRepoImpl, esta necesita de ExpenseManager para crearse
     * por eso esta se crea debajo de ExpenseManager, get() es como decir, usa el ExpenseManager
     * que este creado
     */
    single<ExpenseRepository> { ExpenseRepoImpl(get()) }
    factory { ExpensesViewModel(get()) }
}