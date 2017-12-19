package io.github.golok56.manajemenuang;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.github.golok56.manajemenuang.models.Transaction;
import io.github.golok56.manajemenuang.ui.transaction.TransactionFormActivity;

import static io.github.golok56.manajemenuang.BaseAction.clickOn;
import static io.github.golok56.manajemenuang.BaseAction.hasError;
import static io.github.golok56.manajemenuang.BaseAction.isShowingToast;
import static io.github.golok56.manajemenuang.BaseAction.type;

/**
 * @author Satria Adi Putra
 */
@RunWith(AndroidJUnit4.class)
public class TransactionFormActivityTest {

    @Rule
    public ActivityTestRule<TransactionFormActivity> mActivityTestRule =
            new ActivityTestRule<>(TransactionFormActivity.class);

    private static final String AMOUNT = "10000";
    private static final String DESCRIPTION = "Ini lagi test";
    private static final int TYPE = Transaction.INCOME_TYPE;

    @Test
    public void shouldDisplayErrorOnFormWhenLeaveEmpty(){
        String errorText = mActivityTestRule.getActivity().getString(R.string.validasi_form);

        clickOn(R.id.btnSave);
        hasError(errorText, R.id.etAmount);
    }

    @Test
    public void shouldShowSuccessCreateTransactionToast(){
        String successText = mActivityTestRule
                .getActivity()
                .getString(R.string.success_create_transaction);

        type(AMOUNT, R.id.etAmount);
        type(DESCRIPTION, R.id.etDesc);

        if(TYPE == Transaction.INCOME_TYPE){
            clickOn(R.id.rbIncome);
        }
        clickOn(R.id.btnSave);
        isShowingToast(successText, mActivityTestRule.getActivity());
    }
}
