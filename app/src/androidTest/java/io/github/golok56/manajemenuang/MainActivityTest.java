package io.github.golok56.manajemenuang;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.github.golok56.manajemenuang.ui.main.MainActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static io.github.golok56.manajemenuang.BaseAction.checkDisplay;
import static io.github.golok56.manajemenuang.BaseAction.clickOn;
import static io.github.golok56.manajemenuang.BaseAction.clickTab;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActtivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void shouldDisplayTabLayoutAndViewPager(){
        checkDisplay(R.id.tlMainActivity);
        checkDisplay(R.id.vpMainActivity);
    }

    @Test
    public void shouldDisplayUserInfo(){
        checkDisplay(R.id.tv_user_name_fragment_user_info);
        checkDisplay(R.id.tv_wallet_fragment_user_info);
        checkDisplay(R.id.sp_expense_fragment_user_info);
        checkDisplay(R.id.sp_income_fragment_user_info);
    }

    @Test
    public void canSwipeLeftAndSwipeRightToChangeFragment(){
        onView(withId(R.id.vpMainActivity)).perform(swipeLeft());
        checkDisplay(R.id.fabAddNewTransaction);

        onView(withId(R.id.vpMainActivity)).perform(swipeRight());
        checkDisplay(R.id.tv_user_name_fragment_user_info);
    }

    @Test
    public void canNavigateByTappingOnTab(){
        clickTab("History");
        checkDisplay(R.id.fabAddNewTransaction);

        clickTab("Info");
        checkDisplay(R.id.tv_user_name_fragment_user_info);
    }

    @Test
    public void shouldDisplayTransactionFormWhenFabIsClicked(){
        clickTab("History");
        clickOn(R.id.fabAddNewTransaction);
        checkDisplay(R.id.etAmount);
    }

    @Test
    public void shouldDisplayTransactionDetailWhenItemIsClicked(){
        clickTab("History");
        onData(anything())
                .inAdapterView(withId(R.id.lvRecord))
                .atPosition(1)
                .perform(click());
        checkDisplay(R.id.tvAmount);
    }
}
