package io.github.golok56.manajemenuang.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * This class store the information about transaction such as
 * amount, date, type, and description of transaction
 */
@Entity(foreignKeys = @ForeignKey(
        entity = Category.class,
        parentColumns = "id",
        childColumns = "category_id"
))
public class Transaction {

    public static final int EXPENSE_TYPE = 0;
    public static final int INCOME_TYPE = 1;

    @PrimaryKey(autoGenerate = true)
    private long id;

    private double amount;
    private int type;
    private String date;
    private String month;
    private String year;
    private String description;

    @ColumnInfo(name = "category_id")
    private long categoryId;

    public Transaction() {
    }

    public Transaction(long id, double amount, String description, String date, String month,
                       String year, int type, long categoryId) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.month = month;
        this.year = year;
        this.type = type;
        this.categoryId = categoryId;
    }

    @Ignore
    public Transaction(double amount, String description, String date, String month,
                       String year, int type, long categoryId) {
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.month = month;
        this.year = year;
        this.type = type;
        this.categoryId = categoryId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getType() {
        return type;
    }

    public String getStringType() {
        switch (type) {
            case EXPENSE_TYPE:
                return "Pengeluaran";
            case INCOME_TYPE:
                return "Pemasukan";
            default:
                return "";
        }
    }

    public void setType(int type) {
        this.type = type;
    }

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

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getFullDate() {
        return date + " " + month + " " + year;
    }

    public boolean isIncome() {
        return type == INCOME_TYPE;
    }
}
