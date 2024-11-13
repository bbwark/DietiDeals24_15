package com.CioffiDeVivo.dietideals.presentation.ui.auction.components

import android.app.Application
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.presentation.ui.auction.AuctionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuctionTopBar(
    navController: NavController,
    viewModel: AuctionViewModel
) {
    TopAppBar(
        title = { Text(text = "") },
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = ""
                )
            }
        },
        actions = {
            if (true) {
                IconButton(onClick = {
                    viewModel.addOnFavourites()
                }) {
                    Icon(
                        Icons.Default.Bookmark,
                        contentDescription = ""
                    )
                }
            } else {
                IconButton(onClick = {
                    viewModel.removeFromFavourites()
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

@Preview
@Composable
fun AuctionTopBarPreview() {
    AuctionTopBar(rememberNavController(), AuctionViewModel(Application()))
}