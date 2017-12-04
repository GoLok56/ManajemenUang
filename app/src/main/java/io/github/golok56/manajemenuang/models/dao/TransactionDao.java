package io.github.golok56.manajemenuang.models.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.github.golok56.manajemenuang.models.Report;
import io.github.golok56.manajemenuang.models.Transaction;
import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * @author Satria Adi Putra
 */
@Dao
public interface TransactionDao {

    @Query("SELECT * FROM `Transaction` ORDER BY `id` DESC")
    Flowable<List<Transaction>> getTransactions();

    @Query("SELECT SUM(`amount`) AS `amount`, TRIM(SUBSTR(`date`,3)) AS `month`, `type` FROM `Transaction` GROUP BY TRIM(SUBSTR(`date`,3)), `type`")
    Flowable<List<Report>> getReports();

    @Query("SELECT * FROM `Transaction` WHERE `id`=:id")
    Single<Transaction> getTransaction(long id);

    @Delete
    void delete(Transaction transaction);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Transaction transaction);

}
