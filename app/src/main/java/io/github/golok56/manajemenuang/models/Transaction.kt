package io.github.golok56.manajemenuang.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

/**
 * This class store the information about transaction such as
 * amount, date, type, and description of transaction
 */
@Entity
class Transaction {

    /**
     * Return if of transaction
     */
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L

    /**
     * Return the total amount of transaction
     */
    var amount: Double = 0.toDouble()

    /**
     * Return the type of transaction
     */
    var type: Int = 0

    var date: String? = null

    /**
     * Return the description of transaction
     */
    var description: String? = null

    val typeText: String
        get() {
            return when (type) {
                EXPENSE_TYPE -> "Pengeluaran"
                INCOME_TYPE -> "Pemasukan"
                else -> ""
            }
        }

    /**
     * Create an instance of [Transaction]
     *
     * @param amount      the total amount of transaction
     * @param description the description of the transaction
     * @param date        the date of transaction
     * @param type        the type of transaction
     */
    constructor(id: Long, amount: Double, description: String, date: String, type: Int) {
        this.id = id
        this.amount = amount
        this.description = description
        this.date = date
        this.type = type
    }

    @Ignore
    constructor(amount: Double, description: String, date: String, type: Int) {
        this.amount = amount
        this.description = description
        this.date = date
        this.type = type
    }

    companion object {
        val EXPENSE_TYPE = 0
        val INCOME_TYPE = 1
    }
}
