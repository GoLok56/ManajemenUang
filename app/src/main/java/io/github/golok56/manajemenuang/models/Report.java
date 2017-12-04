package io.github.golok56.manajemenuang.models;

import java.util.HashSet;

public class Report {

    private static HashSet<String> Bulan;
    private int amount, type;
    private String month;

    public Report(int amount, int type, String month) {
        this.amount = amount;
        this.month = month;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public String getMonth() {
        return month;
    }
}
