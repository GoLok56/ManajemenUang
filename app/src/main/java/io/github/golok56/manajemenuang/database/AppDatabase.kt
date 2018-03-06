package io.github.golok56.manajemenuang.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

import io.github.golok56.manajemenuang.models.Transaction
import io.github.golok56.manajemenuang.models.dao.TransactionDao

/**
 * @author Satria Adi Putra
 */
@Database(entities = [(Transaction::class)], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract val transactionTable: TransactionDao

    companion object {

        private var sInstance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (sInstance == null) {
                sInstance = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        "manajemen_uang"
                ).build()
            }

            return sInstance as AppDatabase
        }
    }

}
