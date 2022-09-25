package com.xpense.android.di

import com.xpense.android.ui.txn.add_edit.TxnAddEditViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelFactoryProvider {

    fun txnAddEditViewModelFactory(): TxnAddEditViewModel.Factory
}
