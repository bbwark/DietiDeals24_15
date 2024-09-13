package com.CioffiDeVivo.dietideals.presentation.ui.search

import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.presentation.ui.search.components.SearchAuctionsList
import com.CioffiDeVivo.dietideals.presentation.ui.search.components.SearchViewBar

@Composable
fun SearchView(viewModel: SearchViewModel, navController: NavHostController) {
    val searchedAuctionState by viewModel.searchedAuctionState.collectAsState()
    val categoriesToHide by viewModel.categoriesToHide.collectAsState()

    Column(Modifier.fillMaxSize()) {
        SearchViewBar(
            categoriesToHide = categoriesToHide,
            updateCategories = { viewModel.setCategoriesToHide(it) },
            updateSearchWord = { viewModel.searchWordUpdate(it) },
            navController = navController)
        SearchAuctionsList(
            auctions = searchedAuctionState,
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