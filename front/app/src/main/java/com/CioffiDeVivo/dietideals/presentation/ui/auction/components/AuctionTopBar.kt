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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.presentation.common.sharedViewmodels.SharedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuctionTopBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: SharedViewModel
) {
    TopAppBar(
        title = { Text(text = "") },
        navigationIcon = {
            IconButton(onClick = {
                //pop navigation back to home
            }) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = ""
                )
            }
        },
        actions = {
            if (viewModel.user.favouriteAuctions.contains(viewModel.selectedAuction)) {
                IconButton(onClick = {
                    //Code to call the function that removes the auction from user's favourites
                }) {
                    Icon(
                        Icons.Default.Bookmark,
                        contentDescription = ""
                    )
                }
            } else {
                IconButton(onClick = {
                    //Code to call the function that adds the auction to user's favourites
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
    AuctionTopBar(navController = rememberNavController(), viewModel = SharedViewModel(application = Application()))
}