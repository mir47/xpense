package com.xpense.android.ui.edit

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xpense.android.db.Transaction
import com.xpense.android.db.TransactionDatabaseDao
import kotlinx.coroutines.launch
import java.lang.Exception

class EditTransactionViewModel(private val database: TransactionDatabaseDao) : ViewModel() {

    val amount = ObservableField<String>()

    fun submit() {
        try {
            amount.get()?.toDouble()?.let {
                viewModelScope.launch {
                    database.insert(Transaction(amount = it))
                }
            }
        } catch (e: Exception) {
        }
    }
}