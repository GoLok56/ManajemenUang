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
                    intent.putExtra(TransactionDetailActivity.Companion.getTRANSACTION_ID_EXTRA(), 3L);
                    return intent;
                }
            };

    @Test
    public void shouldDisplayTransactionDetail(){
        checkText(R.id.tvAmount, "Rp 50.000,00");
        checkText(R.id.tvDesc, "Ini lagi test");
        checkText(R.id.tvDate, "19 December 2017");
    }

}
