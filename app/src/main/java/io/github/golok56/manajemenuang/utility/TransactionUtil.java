package io.github.golok56.manajemenuang.utility;

import java.util.ArrayList;
import java.util.List;

import io.github.golok56.manajemenuang.models.Transaction;

/**
 * @author Satria Adi Putra
 */

public class TransactionUtil {
    private TransactionUtil() {
    }

    public static List<Object> preprocess(List<Transaction> transactions) {
        ArrayList<Object> objects = new ArrayList<>();
        String currentMonth = "";
        for (Transaction transaction : transactions) {
            String transactionMonth = transaction.getMonth() + " " + transaction.getYear();
            if (!currentMonth.equals(transactionMonth)) {
                objects.add(transactionMonth);
                currentMonth = transactionMonth;
            }

            objects.add(transaction);
        }

        return objects;
    }
}
