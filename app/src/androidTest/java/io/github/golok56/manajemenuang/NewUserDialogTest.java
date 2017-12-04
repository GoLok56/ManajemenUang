package io.github.golok56.manajemenuang;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.github.golok56.manajemenuang.views.activity.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * @author Satria Adi Putra
 */
@RunWith(AndroidJUnit4.class)
public class NewUserDialogTest {

    private static final String NAME = "Satria";
    private static final String WALLET = "100000";

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void shouldCreateUser() {
        onView(withId(R.id.et_user_name_dialog_create_user)).perform(typeText(NAME));
        onView(withId(R.id.et_user_wallet_dialog_create_user)).perform(typeText(WALLET));
        onView(withText("OK")).perform(click());
    }

}
