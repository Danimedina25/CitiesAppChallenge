package com.danifitdev.citiesappchallenge.cities.presentation

import com.danifitdev.citiesappchallenge.cities.domain.model.CityModel
import com.danifitdev.citiesappchallenge.cities.domain.repository.CitiesRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CitiesViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var repository: CitiesRepository

    @InjectMocks
    private lateinit var viewModel: CitiesViewModel

    @Test
    fun `filterCities should return cities that start with the search query`() = runTest{
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

        val expectedFiltered = listOf(
            CityModel(_id = 2, name = "Madrid"),
            CityModel(_id = 5, name = "Medellín"),
            CityModel(_id = 3, name = "Miami"),
            CityModel(_id = 1, name = "México"),
        )
        assertEquals(expectedFiltered, viewModel.state.value.filteredCities)
    }

    @Test
    fun `filterCities should return empty list when no cities match the search query`() = runTest {
        val cities = listOf(
            CityModel(_id = 1, name = "México"),
            CityModel(_id = 2, name = "Madrid"),
            CityModel(_id = 3, name = "Miami"),
            CityModel(_id = 4, name = "Barcelona"),
            CityModel(_id = 5, name = "Medellín")
        )

        viewModel.setCities(cities)

        viewModel.setNewSearchQuery("Z")
        viewModel.filterCities()

        val expectedFiltered = emptyList<CityModel>()
        assertEquals(expectedFiltered, viewModel.state.value.filteredCities)
    }

    @Test
    fun `filterCities should return all cities when the search query is empty`() = runTest {
        val cities = listOf(
            CityModel(_id = 1, name = "México"),
            CityModel(_id = 2, name = "Madrid"),
            CityModel(_id = 3, name = "Miami"),
            CityModel(_id = 4, name = "Barcelona"),
            CityModel(_id = 5, name = "Medellín")
        )

        viewModel.setCities(cities)

        viewModel.setNewSearchQuery("")
        viewModel.filterCities()

        val expectedFiltered = cities.sortedBy { it.name }
        assertEquals(expectedFiltered, viewModel.state.value.filteredCities)
    }

    @Test
    fun `filterCities should return cities ignoring case in the search query`() = runTest {
        val cities = listOf(
            CityModel(_id = 1, name = "México"),
            CityModel(_id = 2, name = "Madrid"),
            CityModel(_id = 3, name = "Miami"),
            CityModel(_id = 4, name = "Barcelona"),
            CityModel(_id = 5, name = "Medellín")
        )

        viewModel.setCities(cities)

        viewModel.setNewSearchQuery("m")
        viewModel.filterCities()

        val expectedFiltered = listOf(
            CityModel(_id = 2, name = "Madrid"),
            CityModel(_id = 5, name = "Medellín"),
            CityModel(_id = 3, name = "Miami"),
            CityModel(_id = 1, name = "México")
        )
        assertEquals(expectedFiltered, viewModel.state.value.filteredCities)
    }
}
