package com.example.lister
import android.content.SharedPreferences
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.example.lister.data.model.LoggedInUser
import com.example.lister.ui.login.LoginActivity
import org.junit.Assert
import org.junit.Rule
import org.junit.Test


class SharedPreferencesTest {
    private var preferenceManager: PreferenceManager?= null

    @Test
    fun savingPreferencesByPreferenceActivity() {
        val context = InstrumentationRegistry.getInstrumentation().context
        preferenceManager = PreferenceManager(context)
        val user = LoggedInUser(1, "user", "TestTestTestTestTest")
        preferenceManager!!.loginAccount(user)
        val preferences: SharedPreferences =
            preferenceManager!!.preferences
        Assert.assertEquals(1, preferences.getInt("userId", 0))
        Assert.assertEquals(true, preferences.getBoolean("logged", false))
        Assert.assertEquals("user", preferences.getString("userNick", ""))
        Assert.assertEquals("TestTestTestTestTest", preferences.getString("sessionKey", ""))
        preferenceManager!!.logoutAccount()
        Assert.assertEquals(null, preferences.getInt("userId", 0))
        Assert.assertEquals(null, preferences.getBoolean("logged", false))
        Assert.assertEquals(null, preferences.getString("userNick", ""))
        Assert.assertEquals(null, preferences.getString("sessionKey", ""))
    }

}

class LoginActivityTest {
    @get:Rule
    var activityRule: ActivityTestRule<LoginActivity> = ActivityTestRule(
        LoginActivity::class.java
    )

    @Test
    fun test() {
        onView(withId(R.id.username)).perform(typeText("qwe"))
        onView(withId(R.id.password)).perform(typeText("qweqweqwe"))
        onView(withId(R.id.login)).perform(click())
        onView(withId(R.id.drawer_layout)).check(matches(isDisplayed()))
    }
}
