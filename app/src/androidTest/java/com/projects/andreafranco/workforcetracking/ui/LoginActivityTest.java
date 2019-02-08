package com.projects.andreafranco.workforcetracking.ui;

import android.arch.core.executor.testing.CountingTaskExecutorRule;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.projects.andreafranco.workforcetracking.AppExecutors;
import com.projects.andreafranco.workforcetracking.R;
import com.projects.andreafranco.workforcetracking.local.AppDatabase;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class LoginActivityTest {

    private static final String USERNAME = "Andrea";
    private static final String PASSWORD = "andrea";

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class);

    @Rule
    public CountingTaskExecutorRule mCountingTaskExecutorRule = new CountingTaskExecutorRule();

    public LoginActivityTest() {
        //Delete DB
        InstrumentationRegistry.getTargetContext().deleteDatabase(AppDatabase.DATABASE_NAME);
    }

    @Before
    public void waitForDbCreation() throws Throwable {
        final CountDownLatch latch = new CountDownLatch(1);
        final LiveData<Boolean> dataBaseCreated = AppDatabase.getInstance(
                InstrumentationRegistry.getTargetContext(), new AppExecutors())
                .getDatabaseCreated();
        mActivityRule.runOnUiThread(()->{
            dataBaseCreated.observeForever(new Observer<Boolean>() {
                @Override
                public void onChanged(@Nullable Boolean aBoolean) {
                    if (Boolean.TRUE.equals(aBoolean)) {
                        dataBaseCreated.removeObserver(this);
                        latch.countDown();
                    }
                }
            });
        });
        MatcherAssert.assertThat("DB should have been inizialized",
                latch.await(1, TimeUnit.MINUTES), CoreMatchers.is(true));
    }

    @Test
    public void clickOnLoginButton() throws Throwable{
        drain();
        onView(withId(R.id.username_edittext)).perform(typeText(USERNAME));
        onView(withId(R.id.password_edittext)).perform(typeText(PASSWORD)).perform(closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());
    }


    private void drain() throws TimeoutException, InterruptedException {
        mCountingTaskExecutorRule.drainTasks(1, TimeUnit.MINUTES);
    }
}
