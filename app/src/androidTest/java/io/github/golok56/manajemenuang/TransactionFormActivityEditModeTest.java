package io.github.golok56.manajemenuang;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.github.golok56.manajemenuang.ui.transaction.TransactionDetailActivity;
import io.github.golok56.manajemenuang.ui.transaction.TransactionFormActivity;

import static io.github.golok56.manajemenuang.BaseAction.clickOn;
import static io.github.golok56.manajemenuang.BaseAction.isShowingToast;
import static io.github.golok56.manajemenuang.BaseAction.type;

/**
 * @author Satria Adi Putra
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class TransactionFormActivityEditModeTest {

    @Rule
    public ActivityTestRule<TransactionFormActivity> mActivityTestRule =
            new ActivityTestRule<TransactionFormActivity>(TransactionFormActivity.class){
                @Override
                protected Intent getActivityIntent() {
                    Intent intent = new Intent(
                            InstrumentationRegistry.getInstrumentation().getTargetContext(),
                            TransactionFormActivity.class
                    );
                    intent.putExtra(TransactionDetailActivity.TRANSACTION_ID_EXTRA, 3L);
                    return intent;
                }
            };

    @Test
    public void shouldShowSuccessEditToast(){
        String successText = mActivityTestRule.getActivity().getString(R.string.success_edit_transaction);

        type("50000", R.id.et_amount_activity_transaction_form);
        clickOn(R.id.btn_save_activity_transaction_form);
        isShowingToast(successText, mActivityTestRule.getActivity());
    }

}
