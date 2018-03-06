package io.github.golok56.manajemenuang.models.dao

import android.arch.persistence.room.*
import io.github.golok56.manajemenuang.models.Report
import io.github.golok56.manajemenuang.models.Transaction
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * @author Satria Adi Putra
 */
@Dao
interface TransactionDao {

    @get:Query("SELECT * FROM `Transaction` ORDER BY `id` DESC")
    val transactions: Flowable<List<Transaction>>

    @get:Query("SELECT SUM(`amount`) AS `amount`, TRIM(SUBSTR(`date`,3)) AS `month`, `type` FROM `Transaction` GROUP BY TRIM(SUBSTR(`date`,3)), `type`")
    val reports: Flowable<List<Report>>

    @Query("SELECT * FROM `Transaction` WHERE `id`=:id")
    fun getTransaction(id: Long): Single<Transaction>

    @Delete
    fun delete(transaction: Transaction)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(transaction: Transaction)

}
