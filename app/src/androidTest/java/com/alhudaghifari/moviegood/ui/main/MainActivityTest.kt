package com.alhudaghifari.moviegood.ui.main

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.alhudaghifari.moviegood.R
import com.alhudaghifari.moviegood.ui.movies.MoviesFragment
import com.alhudaghifari.moviegood.ui.tvshows.TvShowsFragment
import com.alhudaghifari.moviegood.utils.DummyGenerator
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test

class MainActivityTest {
    private val dummyMovie = DummyGenerator.generateDummyMovies()
    private val dummyMoviePosition = 3
    private val dummyMovieRecommendation = DummyGenerator.generateDummyMovieRecommendation(dummyMovie[dummyMoviePosition].moviesId)
    private val dummyTv = DummyGenerator.generateDummyTvShows()
    private val dummyTvPosition = 4
    private val dummyTvRecommendation = DummyGenerator.generateDummyTvRecommendation(dummyTv[dummyTvPosition].moviesId)

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun loadMovieku() {
        onView(withId(R.id.rv_data)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_data)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyMovie.size))
    }

    @Test
    fun testShowNoDataMovie() {
        val scenario = launchFragmentInContainer<MoviesFragment>()
        scenario.onFragment { fragment ->
            fragment.showNoData()
        }
        onView(withId(R.id.tvNoData)).check(matches(isDisplayed()))
    }

    @Test
    fun loadDetailMovieku() {
        val position = 2
        onView(withId(R.id.rv_data)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(position, click()))
        onView(withId(R.id.tvTitleMovie)).check(matches(isDisplayed()))
        onView(withId(R.id.tvTitleMovie)).check(matches(withText(dummyMovie[position].title)))
        onView(withId(R.id.tv_score)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_score)).check(matches(withText("Score : ${dummyMovie[position].score}")))
    }

    @Test
    fun loadMovieRecommendation() {
        onView(withId(R.id.rv_data)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(dummyMoviePosition, click()))
        onView(withId(R.id.rvRecommendation)).check(matches(isDisplayed()))
        onView(withId(R.id.rvRecommendation)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyMovieRecommendation.size))
    }

    @Test
    fun performClickMovieRecommendation() {
        onView(withId(R.id.rv_data)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(dummyMoviePosition, click()))
        onView(withId(R.id.rvRecommendation)).check(matches(isDisplayed()))
        onView(withId(R.id.rvRecommendation)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(dummyMoviePosition, click()))
        onView(withId(R.id.tvTitleMovie)).check(matches(isDisplayed()))
        onView(withId(R.id.tvTitleMovie)).check(matches(not(withText(dummyMovie[dummyMoviePosition].title))))
    }

    @Test
    fun loadTvmu() {
        onView(withText(R.string.tv_shows)).perform(click())
        onView(withId(R.id.rv_tv_shows)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_shows)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyTv.size))
    }

    @Test
    fun testNoDataTv() {
        onView(withText(R.string.tv_shows)).perform(click())
        onView(withId(R.id.rv_tv_shows)).check(matches(isDisplayed()))

        val scenario = launchFragmentInContainer<TvShowsFragment>()
        scenario.onFragment { fragment ->
            fragment.showNoData()
        }
        onView(withId(R.id.tvNoData)).check(matches(isDisplayed()))
    }

    @Test
    fun loadDetailTvmu() {
        onView(withText(R.string.tv_shows)).perform(click())
        onView(withId(R.id.rv_tv_shows)).check(matches(isDisplayed()))

        onView(withId(R.id.rv_tv_shows)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(dummyTvPosition, click()))
        onView(withId(R.id.tvTitleMovie)).check(matches(isDisplayed()))
        onView(withId(R.id.tvTitleMovie)).check(matches(withText(dummyTv[dummyTvPosition].title)))
        onView(withId(R.id.tv_score)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_score)).check(matches(withText("Score : ${dummyTv[dummyTvPosition].score}")))
    }

    @Test
    fun loadTvRecommendation() {
        onView(withText(R.string.tv_shows)).perform(click())
        onView(withId(R.id.rv_tv_shows)).check(matches(isDisplayed()))

        onView(withId(R.id.rv_tv_shows)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(dummyTvPosition, click()))
        onView(withId(R.id.rvRecommendation)).check(matches(isDisplayed()))
        onView(withId(R.id.rvRecommendation)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyTvRecommendation.size))
    }

    @Test
    fun performClickTvRecommendation() {
        onView(withText(R.string.tv_shows)).perform(click())
        onView(withId(R.id.rv_tv_shows)).check(matches(isDisplayed()))

        onView(withId(R.id.rv_tv_shows)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(dummyTvPosition, click()))
        onView(withId(R.id.rvRecommendation)).check(matches(isDisplayed()))
        onView(withId(R.id.rvRecommendation)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(dummyTvPosition, click()))
        onView(withId(R.id.tvTitleMovie)).check(matches(isDisplayed()))
        onView(withId(R.id.tvTitleMovie)).check(matches(not(withText(dummyTv[dummyTvPosition].title))))
    }

}