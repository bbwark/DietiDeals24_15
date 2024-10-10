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
import com.CioffiDeVivo.dietideals.presentation.ui.loading.LoadingView
import com.CioffiDeVivo.dietideals.presentation.ui.retry.RetryView
import com.CioffiDeVivo.dietideals.presentation.ui.search.components.SearchAuctionsList
import com.CioffiDeVivo.dietideals.presentation.ui.search.components.SearchViewBar

@Composable
fun SearchView(viewModel: SearchViewModel, navController: NavHostController) {
    val searchUiState by viewModel.searchUiState.collectAsState()
    val categoriesToHide by viewModel.categoriesToHide.collectAsState()

    when(searchUiState){
        is SearchUiState.Loading -> {
            SearchViewBar(
                categoriesToHide = categoriesToHide,
                updateCategories = { viewModel.setCategoriesToHide(categoriesToHide) },
                updateSearchWord = { viewModel.searchWordUpdate(it) },
                navController = navController
            )
            LoadingView()
        }
        is SearchUiState.Success -> SearchAuctionsListView(
            auctions = (searchUiState as SearchUiState.Success).auctions,
            navController = navController,
            categoriesToHide = categoriesToHide,
            updateCategories = { viewModel.setCategoriesToHide(categoriesToHide) },
            updateSearchWord = { viewModel.searchWordUpdate(it) }
        )
        is SearchUiState.Error -> RetryView()
    }
}

@Composable
fun SearchAuctionsListView(
    auctions: ArrayList<Auction>,
    navController: NavController,
    categoriesToHide: Set<String>,
    updateCategories: (Set<String>) -> (Unit),
    updateSearchWord: (String) -> (Unit),
){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SearchViewBar(
            categoriesToHide = categoriesToHide,
            updateCategories = { updateCategories(it) },
            updateSearchWord = { updateSearchWord(it) },
            navController = navController)
        SearchAuctionsList(
            auctions = auctions,
            categoriesToHide = categoriesToHide,
            navController = navController
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchViewPreview() {
    SearchView(viewModel = SearchViewModel(Application()), navController = rememberNavController())
}