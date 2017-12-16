package io.github.golok56.manajemenuang;

import android.app.Activity;
import android.view.View;

import org.hamcrest.Matcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

/**
 * @author Satria Adi Putra
 */
class BaseAction {

    private static Matcher<View> matchTitle(String title){
        return allOf(withText(title), isDescendantOfA(withId(R.id.tlMainActivity)));
    }

    static void clickOn(int id){
        onView(withId(id)).perform(click());
    }

    static void clickTab(String title){
        onView(matchTitle(title)).perform(click());
    }

    static void checkDisplay(int id){
        onView(withId(id)).check(matches(isDisplayed()));
    }

    static void checkText(int id, String text){
        onView(withId(id)).check(matches(withText(text)));
    }

    static void hasError(String msg, int id){
        onView(withId(id)).check(matches(hasErrorText(msg)));
    }

    static void type(String msg, int id){
        onView(withId(id)).perform(clearText(), typeText(msg), closeSoftKeyboard());
    }

    static void isShowingToast(String msg, Activity activity){
        onView(withText(msg))
                .inRoot(withDecorView(not(activity.getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }
}
