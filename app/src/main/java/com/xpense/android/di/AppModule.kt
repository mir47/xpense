package com.xpense.android.di

import android.content.Context
import androidx.room.Room
import com.xpense.android.data.source.local.TxnDataSourceLocal
import com.xpense.android.data.source.local.TxnDatabase
import com.xpense.android.data.source.remote.TxnDataSourceRemote
import com.xpense.android.domain.repository.TxnRepository
import com.xpense.android.domain.repository.TxnRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideTxnDatabase(@ApplicationContext appContext: Context): TxnDatabase =
        Room.databaseBuilder(
            appContext,
            TxnDatabase::class.java,
            "transaction_database"
        )
            // migration strategy - use destructive to recreate a new db
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideTxnRepository(db: TxnDatabase): TxnRepository =
        TxnRepositoryImpl(
            TxnDataSourceLocal(db.txnDao()),
            TxnDataSourceRemote
        )
}
