package com.example.chengqi.mycoderepo;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;

import com.example.chengqi.mycoderepo.widgets.ButtonActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.action.ViewActions.*;

/**
 * Created by archermind on 1/25/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ApplicationTest {

    @Rule
    public ActivityTestRule<ButtonActivity> mActivityRule = new ActivityTestRule<ButtonActivity>(ButtonActivity.class);

    /*  onView(withId(R.id.my_view))


     */

    @Test
    public void testHelloWorldOnView() {
//        onView(withText("Widgets")).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.button68)).perform(click()).check(ViewAssertions.matches(isDisplayed()));
    }

}