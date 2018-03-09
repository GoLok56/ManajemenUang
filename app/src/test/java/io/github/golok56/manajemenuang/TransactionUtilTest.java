package io.github.golok56.manajemenuang;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import io.github.golok56.manajemenuang.models.Transaction;
import io.github.golok56.manajemenuang.utility.TransactionUtil;

/**
 * @author Satria Adi Putra
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class TransactionUtilTest {
    @Test
    public void shouldReturnCorrectDataStructureOnPreProcessing() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(1, 10000, "Makan makan", "12", "Desember", "2018", 1, 1));
        transactions.add(new Transaction(2, 15000, "Makan minum", "13", "Desember", "2018", 1, 1));

        List<Object> objects = TransactionUtil.preprocess(transactions);
        Assert.assertEquals("Desember 2018", objects.get(0).toString());
        Assert.assertEquals("Makan makan", ( (Transaction) objects.get(1)).getDescription());
        Assert.assertEquals("Makan minum", ( (Transaction) objects.get(2)).getDescription());
    }
}
