package com.CioffiDeVivo.dietideals.presentation.ui.search

import android.app.Application
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.data.models.Auction
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
            is SearchUiState.Empty -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "No Auctions Found!",
                        color = Color.Gray,
                        fontSize = 20.sp,
                        fontWeight = FontWeight(600),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(start = 25.dp, end = 25.dp)
                    )
                }
            }
            is SearchUiState.Idle -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "Search Auctions!",
                        color = Color.Gray,
                        fontSize = 20.sp,
                        fontWeight = FontWeight(600),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(start = 25.dp, end = 25.dp)
                    )
                }
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