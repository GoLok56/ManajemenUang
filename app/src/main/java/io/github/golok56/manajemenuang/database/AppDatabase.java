package io.github.golok56.manajemenuang.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

import io.github.golok56.manajemenuang.models.Category;
import io.github.golok56.manajemenuang.models.Transaction;
import io.github.golok56.manajemenuang.models.dao.TransactionDao;

/**
 * @author Satria Adi Putra
 */
@Database(entities = {Transaction.class, Category.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE Category (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "name TEXT" +
                    ")");

            database.execSQL("ALTER TABLE `Transaction` ADD COLUMN month TEXT");
            database.execSQL("ALTER TABLE `Transaction` ADD COLUMN year TEXT");
            database.execSQL("ALTER TABLE `Transaction` ADD COLUMN category_id INTEGER");
            database.execSQL("ALTER TABLE `Transaction` RENAME TO `Transaction_old`");

            database.execSQL("CREATE TABLE `Transaction` (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "amount REAL NOT NULL, " +
                    "type INTEGER NOT NULL, " +
                    "date TEXT, " +
                    "month TEXT, " +
                    "year TEXT, " +
                    "description TEXT," +
                    "category_id INTEGER NOT NULL," +
                    "CONSTRAINT category_fk FOREIGN KEY(category_id) REFERENCES Category (id)" +
                    ")");

            database.execSQL("INSERT INTO `Transaction` SELECT * FROM Transaction_old");
            database.execSQL("DROP TABLE Transaction_old");
        }
    };

    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("INSERT INTO Category (`name`) VALUES ('Makan')");
            database.execSQL("INSERT INTO Category (`name`) VALUES ('Belanja Bulanan')");
        }
    };

    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room
                    .databaseBuilder(
                            context,
                            AppDatabase.class,
                            "manajemen_uang"
                    )
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .build();
        }

        return sInstance;
    }

    public abstract TransactionDao getTransactionTable();

}
