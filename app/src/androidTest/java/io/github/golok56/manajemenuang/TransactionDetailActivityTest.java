package io.github.golok56.manajemenuang;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.github.golok56.manajemenuang.ui.transaction.TransactionDetailActivity;

import static io.github.golok56.manajemenuang.BaseAction.checkText;

/**
 * @author Satria Adi Putra
 */
@RunWith(AndroidJUnit4.class)
public class TransactionDetailActivityTest {

    @Rule
    public ActivityTestRule<TransactionDetailActivity> mActivityTestRule =
            new ActivityTestRule<TransactionDetailActivity>(TransactionDetailActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Intent intent = new Intent(
                            InstrumentationRegistry.getInstrumentation().getTargetContext(),
                            TransactionDetailActivity.class
                    );
                    intent.putExtra(TransactionDetailActivity.TRANSACTION_ID_EXTRA, 3L);
                    return intent;
                }
            };

    @Test
    public void shouldDisplayTransactionDetail(){
        checkText(R.id.tv_amount_activity_transaction_detail, "Rp 50.000,00");
        checkText(R.id.tv_desc_activity_transaction_detail, "Ini lagi test");
        checkText(R.id.tv_date_activity_transaction_detail, "16 December 2017");
    }

}
