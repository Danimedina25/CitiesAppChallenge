package com.danifitdev.citiesappchallenge.cities.presentation

import com.danifitdev.citiesappchallenge.cities.domain.model.CityModel
import com.danifitdev.citiesappchallenge.cities.domain.repository.CitiesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CitiesViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()

    @Mock
    private lateinit var repository: CitiesRepository

    @InjectMocks
    private lateinit var viewModel: CitiesViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = CitiesViewModel(repository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        testDispatcher.scheduler.advanceUntilIdle()
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `filterCities should return cities that start with the search query`() {
        val cities = listOf(
            CityModel(_id = 1, name = "México"),
            CityModel(_id = 2, name = "Madrid"),
            CityModel(_id = 3, name = "Miami"),
            CityModel(_id = 4, name = "Barcelona"),
            CityModel(_id = 5, name = "Medellín")
        )

        viewModel.setCities(cities)

        viewModel.setNewSearchQuery("M")
        viewModel.filterCities()

        testDispatcher.scheduler.advanceUntilIdle()

        val expectedFiltered = listOf(
            CityModel(_id = 2, name = "Madrid"),
            CityModel(_id = 5, name = "Medellín"),
            CityModel(_id = 3, name = "Miami"),
            CityModel(_id = 1, name = "México"),
        )
        assertEquals(expectedFiltered, viewModel.state.value.filteredCities)
    }
}
