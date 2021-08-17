package com.alhudaghifari.moviegood.ui.main

import android.support.test.espresso.IdlingRegistry
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.alhudaghifari.moviegood.R
import com.alhudaghifari.moviegood.data.remote.*
import com.alhudaghifari.moviegood.utils.DummyGenerator
import com.alhudaghifari.moviegood.utils.EspressoIdlingResource
import com.alhudaghifari.moviegood.utils.MockResponseFileReader
import com.google.gson.Gson
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResourceForMainActivity())
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResourceForMainActivity())
    }

    @Test
    fun loadMovieku() {
        onView(withId(R.id.rv_data)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_data)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
    }

    @Test
    fun loadDetailMovieku() {
        val position = 2
        onView(withId(R.id.rv_data)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(position, click()))
        onView(withId(R.id.tvTitleMovie)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_score)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_tagline)).check(matches(isDisplayed()))
    }

    @Test
    fun loadMovieRecommendation() {
        onView(withId(R.id.rv_data)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(3, click()))
        onView(withId(R.id.rvRecommendation)).check(matches(isDisplayed()))
        onView(withId(R.id.rvRecommendation)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(3))
    }

    @Test
    fun performClickMovieRecommendation() {
        onView(withId(R.id.rv_data)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(3, click()))
        onView(withId(R.id.rvRecommendation)).check(matches(isDisplayed()))
        onView(withId(R.id.rvRecommendation)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(3, click()))
        onView(withId(R.id.tvTitleMovie)).check(matches(isDisplayed()))
    }

    @Test
    fun loadTvmu() {
        onView(withText(R.string.tv_shows)).perform(click())
        onView(withId(R.id.rv_tv_shows)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_shows)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(3))
    }

    @Test
    fun loadDetailTvmu() {
        onView(withText(R.string.tv_shows)).perform(click())
        onView(withId(R.id.rv_tv_shows)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_shows)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(3, click()))
        onView(withId(R.id.tvTitleMovie)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_score)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_tagline)).check(matches(isDisplayed()))
    }

    @Test
    fun loadTvRecommendation() {
        onView(withText(R.string.tv_shows)).perform(click())
        onView(withId(R.id.rv_tv_shows)).check(matches(isDisplayed()))

        onView(withId(R.id.rv_tv_shows)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(3, click()))
        onView(withId(R.id.rvRecommendation)).check(matches(isDisplayed()))
        onView(withId(R.id.rvRecommendation)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(3))
    }

    @Test
    fun performClickTvRecommendation() {
        onView(withText(R.string.tv_shows)).perform(click())
        onView(withId(R.id.rv_tv_shows)).check(matches(isDisplayed()))

        onView(withId(R.id.rv_tv_shows)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(4, click()))
        onView(withId(R.id.rvRecommendation)).check(matches(isDisplayed()))
        onView(withId(R.id.rvRecommendation)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(4, click()))
        onView(withId(R.id.tvTitleMovie)).check(matches(isDisplayed()))
        onView(withId(R.id.tvTitleMovie)).check(matches(withText("oke")))
    }

}