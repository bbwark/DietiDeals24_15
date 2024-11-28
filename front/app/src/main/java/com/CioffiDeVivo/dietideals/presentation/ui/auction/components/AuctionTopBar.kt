package com.CioffiDeVivo.dietideals.presentation.ui.auction.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.CioffiDeVivo.dietideals.presentation.ui.auction.AuctionUiState
import com.CioffiDeVivo.dietideals.presentation.ui.auction.AuctionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuctionTopBar(
    navController: NavController,
    viewModel: AuctionViewModel
) {
    val auctionUiState by viewModel.auctionUiState.collectAsState()
    if(auctionUiState is AuctionUiState.Success){
        TopAppBar(
            title = { Text(text = "") },
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = ""
                    )
                }
            },
            actions = {
                if ((auctionUiState as AuctionUiState.Success).isFavoured) {
                    IconButton(onClick = {
                        viewModel.removeFromFavourites((auctionUiState as AuctionUiState.Success).auction.id)
                    }) {
                        Icon(
                            Icons.Default.Bookmark,
                            contentDescription = ""
                        )
                    }
                } else {
                    IconButton(onClick = {
                        viewModel.addOnFavourites((auctionUiState as AuctionUiState.Success).auction.id)
                    }) {
                        Icon(
                            Icons.Default.BookmarkBorder,
                            contentDescription = ""
                        )
                    }
                }
            }
        )
    }
}