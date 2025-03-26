package com.danifitdev.citiesappchallenge.cities.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.danifitdev.citiesappchallenge.R
import com.danifitdev.citiesappchallenge.cities.domain.model.CityModel
import com.danifitdev.citiesappchallenge.core.presentation.ObserveAsEvents
import com.danifitdev.citiesappchallenge.core.presentation.components.CitiesToolbar
import com.danifitdev.citiesappchallenge.core.presentation.designsystem.Black
import com.danifitdev.citiesappchallenge.core.presentation.designsystem.CitiesAppChallengeTheme
import com.danifitdev.citiesappchallenge.core.presentation.designsystem.DarkGray
import com.danifitdev.citiesappchallenge.core.presentation.designsystem.White

@Composable
fun CitiesScreenRoot(
    viewModel: CitiesViewModel = hiltViewModel(),
    onCityClick: () -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    ObserveAsEvents(viewModel.events) { event ->
        when(event) {
            is CitiesEvent.SearchError -> {
                Toast.makeText(
                    context,
                    R.string.error_search,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    CitiesScreen(state, onAction = { action ->
        when(action){
            is CitiesAction.OnClickCity ->{
                viewModel.setSelectedCity(action.city)
                onCityClick()
            }
            else -> Unit
        }
        viewModel.onAction(action)
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitiesScreen(
    state: CitiesState,
    onAction: (CitiesAction) -> Unit,
){
    if(state.loading){
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
        ) {
            CircularProgressIndicator()
        }
    }else if(state.cities.isNotEmpty()){
        Scaffold(
            topBar =
            {
                CitiesToolbar(
                    showBackButton = false,
                    title = stringResource(id = R.string.title_top_bar_cities),
                    modifier = Modifier,
                    onBackClick = {})
            }
            ,
            modifier = Modifier
        ) { padding ->
            CitiesList(padding, {}, {}, state,
                onFilterAction = {onAction(CitiesAction.OnFilterCities(it))},
                onAction
            )
        }
    }
}

@Composable
fun CitiesList(
    paddingValues: PaddingValues,
    onInfoClick: () -> Unit,
    onToggleFavorite: () -> Unit,
    state: CitiesState,
    onFilterAction: (queryString: String)->Unit,
    onAction: (CitiesAction) -> Unit,
    ) {
    //val sortedCities = state.cities.sortedBy { it.name }
    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            state = state,
            onSearchQueryChange = { onFilterAction(it) },
            paddingValues = paddingValues
        )
        LazyColumn {
            itemsIndexed(
                state.filteredCities.ifEmpty { if(state.searchQuery.text.isNotEmpty()) emptyList() else state.cities })
            { index, city ->
                val backgroundColor = if (index % 2 == 0) White else LightGray
                CityItem(
                    city = city,
                    onInfoClick = onInfoClick,
                    onToggleFavorite = onToggleFavorite,
                    backgroundColor = backgroundColor,
                    onCityClickAction = {onAction(CitiesAction.OnClickCity(it))}
                )
            }
        }
    }
}

@Composable
fun CityItem(
    city: CityModel,
    onInfoClick: () -> Unit,
    onToggleFavorite: () -> Unit,
    backgroundColor: Color,
    onCityClickAction: (city: CityModel)-> Unit,
) {
    Box(modifier = Modifier
        .background(backgroundColor)
        .clickable {
            onCityClickAction(city) }){
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(2f)
            ) {
                Text(
                    text = "${city.name}, ${city.country}",
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text =  "${city.coord.lat}, ${city.coord.lon}",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.weight(0.2f))

            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { onToggleFavorite() }
                ) {
                    Icon(
                        imageVector = if (city.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Toggle Favorite"
                    )
                }
                Button(
                    onClick = { onInfoClick() },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(text = stringResource(R.string.text_more_info_button))
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    state: CitiesState,
    onSearchQueryChange: (newText: String) -> Unit,
    paddingValues: PaddingValues
) {
    Row(Modifier.padding(paddingValues)){
        OutlinedTextField(
            value = state.searchQuery.text.toString(),
            onValueChange = { newQuery ->
                onSearchQueryChange(newQuery)
            },
            leadingIcon = {
               Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Ícono de búsqueda",
                    tint = Black
                )
            },
            label = { Text(text = "Buscar por nombre o género",
                style = MaterialTheme.typography.bodySmall) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp),
            shape = RoundedCornerShape(24.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = DarkGray,
                unfocusedBorderColor = DarkGray,
                backgroundColor = Color.Transparent,
                focusedLabelColor = LightGray,
                unfocusedLabelColor = LightGray,
                textColor = DarkGray
            ),
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview
@Composable
private fun CitiesScreenPreview() {
    CitiesAppChallengeTheme {
        CitiesScreen (
            state = CitiesState(cities = listOf(CityModel(name = "Prueba 1, US"),
                CityModel(name = "Prueba 2, US"),
                CityModel(name = "Prueba 3, US")
            )),
            onAction = {}
        )
    }
}