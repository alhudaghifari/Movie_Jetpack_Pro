package com.alhudaghifari.moviegood.ui.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.alhudaghifari.moviegood.R
import com.alhudaghifari.moviegood.data.remote.*
import com.alhudaghifari.moviegood.utils.EspressoIdlingResource
import com.alhudaghifari.moviegood.utils.MockResponseFileReader
import com.google.gson.Gson
import org.hamcrest.Matchers.any
import org.junit.After
import org.junit.Before
import org.junit.Test

class MainActivityTest {

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @Test
    fun loadMovieku() {
        onView(withId(R.id.rv_data)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_data)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
    }

    @Test
    fun loadDetailMovieku() {
        val position = 2
        onView(withId(R.id.rv_data)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_data)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
        onView(withId(R.id.rv_data)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(position, click()))
        onView(withId(R.id.tvTitleMovie)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_score)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_tagline)).check(matches(isDisplayed()))
    }

    @Test
    fun checkComponentInDetailMovie() {
        val position = 2
        onView(withId(R.id.rv_data)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_data)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
        onView(withId(R.id.rv_data)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(position, click()))
        onView(withId(R.id.tvTitleMovie)).check(matches(isDisplayed()))
        onView(withId(R.id.tvReleased)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_score)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_category)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_tagline)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_overview)).check(matches(isDisplayed()))
    }

    @Test
    fun loadMovieRecommendation() {
        onView(withId(R.id.rv_data)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_data)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
        onView(withId(R.id.rv_data)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(3, click()))
        onView(withId(R.id.rvRecommendation)).check(matches(isDisplayed()))
        onView(withId(R.id.rvRecommendation)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(3))
    }

    @Test
    fun performClickMovieRecommendation() {
        onView(withId(R.id.rv_data)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_data)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(2))
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
    fun checkComponentInDetailTv() {
        onView(withText(R.string.tv_shows)).perform(click())
        onView(withId(R.id.rv_tv_shows)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_shows)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(3, click()))
        onView(withId(R.id.tvTitleMovie)).check(matches(isDisplayed()))
        onView(withId(R.id.tvReleased)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_score)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_category)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_tagline)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_overview)).check(matches(isDisplayed()))
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
    }

    @Test
    fun loadFavoriteMovie() {
        onView(withId(R.id.rv_data)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_data)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.ibFavorite)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())

        onView(withId(R.id.rv_data)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        onView(withId(R.id.ibFavorite)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())

        onView(withId(R.id.rv_data)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))
        onView(withId(R.id.ibFavorite)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())

        onView(withId(R.id.action_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.action_favorite)).perform(click())
        onView(withId(R.id.rv_data)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_data)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.tvTitleMovie)).check(matches(isDisplayed()))
        onView(withId(R.id.tvReleased)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_score)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_category)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_tagline)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_overview)).check(matches(isDisplayed()))
    }

    @Test
    fun loadFavoriteTv() {
        onView(withText(R.string.tv_shows)).perform(click())
        onView(withId(R.id.rv_tv_shows)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_shows)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.ibFavorite)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())

        onView(withId(R.id.action_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.action_favorite)).perform(click())
        onView(withText(R.string.tv_shows)).perform(click())
        onView(withId(R.id.rv_data)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_data)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.tvTitleMovie)).check(matches(isDisplayed()))
        onView(withId(R.id.tvReleased)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_score)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_category)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_tagline)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_overview)).check(matches(isDisplayed()))
    }


}