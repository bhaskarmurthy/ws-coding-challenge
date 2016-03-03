package com.wealthsimple.wealthsimplecodingchallenge;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.wealthsimple.wealthsimplecodingchallenge.view.MainActivity;

import org.hamcrest.core.StringContains;
import org.hamcrest.core.StringStartsWith;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by bhaskar on 2016-03-01
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void sendMessage_emptyCommand() {
        onView(withId(R.id.button_submit))
                .perform(click());
        onView(withText("Command required"))
                .check(matches(isDisplayed()));
    }

    @Test
    public void sendMessage_errorMessage() {
        onView(withId(R.id.input_command))
                .perform(typeText("invalid command"));
        onView(withId(R.id.button_submit))
                .perform(click());
        onView(withText(new StringStartsWith("[invalid_command]")))
                .check(matches(isDisplayed()));
    }

    @Test
    public void sendMessage_validMessage() {
        onView(withId(R.id.input_command))
                .perform(typeText("marco"));
        onView(withId(R.id.button_submit))
                .perform(click());
        onView(withText(new StringContains("Polo")))
                .check(matches(isDisplayed()));
    }
}