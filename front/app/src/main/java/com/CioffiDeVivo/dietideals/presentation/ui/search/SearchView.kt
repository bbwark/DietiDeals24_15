package com.CioffiDeVivo.dietideals.presentation.ui.search

import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.domain.models.Auction
import com.CioffiDeVivo.dietideals.presentation.navigation.Screen
import com.CioffiDeVivo.dietideals.presentation.ui.loading.LoadingView
import com.CioffiDeVivo.dietideals.presentation.ui.retry.RetryView
import com.CioffiDeVivo.dietideals.presentation.ui.search.components.SearchAuctionsList
import com.CioffiDeVivo.dietideals.presentation.ui.search.components.SearchViewBar

@Composable
fun SearchView(viewModel: SearchViewModel, navController: NavHostController) {

    val searchUiState by viewModel.searchUiState.collectAsState()
    val categoriesToHide by viewModel.categoriesToHide.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SearchViewBar(
            categoriesToHide = categoriesToHide,
            updateCategories = { viewModel.setCategoriesToHide(it) },
            updateSearchWord = { viewModel.searchWordUpdate(it) },
            navController = navController
        )
        when(searchUiState){
            is SearchUiState.Loading -> {
                LoadingView()
            }
            is SearchUiState.Success -> {
                SearchAuctionsListView(
                    auctions = (searchUiState as SearchUiState.Success).auctions,
                    navController = navController,
                    categoriesToHide = categoriesToHide
                )
            }
            is SearchUiState.Error -> RetryView(
                onClick = {
                    navController.popBackStack()
                    navController.navigate(Screen.Search.route)
                }
            )
            is SearchUiState.Idle -> {

            }
        }
    }
}

@Composable
fun SearchAuctionsListView(
    auctions: ArrayList<Auction>,
    navController: NavController,
    categoriesToHide: Set<String>
){
    SearchAuctionsList(
        auctions = auctions,
        categoriesToHide = categoriesToHide,
        navController = navController
    )
}

@Preview(showBackground = true)
@Composable
fun SearchViewPreview() {
    SearchView(viewModel = SearchViewModel(Application()), navController = rememberNavController())
}