package io.github.golok56.manajemenuang.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * This class store the information about transaction such as
 * amount, date, type, and description of transaction
 */
@Entity
public class Transaction {

    public static final int EXPENSE_TYPE = 0;
    public static final int INCOME_TYPE = 1;

    @PrimaryKey(autoGenerate = true)
    private long id;
    private double amount;
    private int type;
    private String date;
    private String description;

    /**
     * Create an instance of {@link Transaction}
     *
     * @param amount      the total amount of transaction
     * @param description the description of the transaction
     * @param date        the date of transaction
     * @param type        the type of transaction
     */
    public Transaction(long id, double amount, String description, String date, int type) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.type = type;
    }

    @Ignore
    public Transaction(double amount, String description, String date, int type) {
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.type = type;
    }

    /**
     * Return if of transaction
     */
    public long getId() {
        return id;
    }

    /**
     * Return the total amount of transaction
     */
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Return the type of transaction
     */
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeText() {
        switch (type) {
            case EXPENSE_TYPE:
                return "Pengeluaran";
            case INCOME_TYPE:
                return "Pemasukan";
        }
        return "";
    }

    /**
     * Return the description of transaction
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
