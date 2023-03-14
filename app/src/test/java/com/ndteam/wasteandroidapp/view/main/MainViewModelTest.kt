package com.ndteam.wasteandroidapp.view.main

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class MainViewModelTest {

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        viewModel = MainViewModel()
    }

    @Test
    fun `getSearchSuggestions must return all saved search attempts by user, no more than 7`() {
        assertThat(viewModel.getSearchSuggestions().size).isLessThan(7)
    }

    @Test
    fun `getGarbageCategories must return 3 categories`() {
        assertThat(viewModel.getGarbageCategories().size).isEqualTo(3)
    }
}