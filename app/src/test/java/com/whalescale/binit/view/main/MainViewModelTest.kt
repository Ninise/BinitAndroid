package com.whalescale.binit.view.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.whalescale.binit.MainCoroutineRule
import com.whalescale.binit.repository.FakeBinitRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        viewModel = MainViewModel(FakeBinitRepository())
        viewModel.downloadData()
    }

    @Test
    fun `getSearchSuggestions must return all saved search attempts by user, no more than 7`() {
        assertThat(viewModel.suggestionState.value.suggestions!!.size).isLessThan(7)
    }

    @Test
    fun `getGarbageCategories must return 3 categories`() {
        assertThat(viewModel.garbageState.value.garbageList!!.size).isEqualTo(3)
    }
}