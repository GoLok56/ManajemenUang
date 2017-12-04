package io.github.golok56.manajemenuang.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import io.github.golok56.manajemenuang.models.Transaction;
import io.github.golok56.manajemenuang.models.dao.TransactionDao;

/**
 * @author Satria Adi Putra
 */
@Database(entities = {Transaction.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(
                    context,
                    AppDatabase.class,
                    "manajemen_uang"
            ).build();
        }

        return sInstance;
    }

    public abstract TransactionDao getTransactionTable();

}
