package com.CioffiDeVivo.dietideals.Views.Navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.CioffiDeVivo.dietideals.Components.AuctionTopBar
import com.CioffiDeVivo.dietideals.Components.BottomNavBar
import com.CioffiDeVivo.dietideals.Components.DetailsViewTopBar
import com.CioffiDeVivo.dietideals.viewmodel.MainViewModel
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.Views.AccountView
import com.CioffiDeVivo.dietideals.Views.AuctionView
import com.CioffiDeVivo.dietideals.Views.BidHistoryView
import com.CioffiDeVivo.dietideals.Views.CreateAuction
import com.CioffiDeVivo.dietideals.Views.EditContactInfoView
import com.CioffiDeVivo.dietideals.Views.EditProfile
import com.CioffiDeVivo.dietideals.Views.FavouritesView
import com.CioffiDeVivo.dietideals.Views.HomeView
import com.CioffiDeVivo.dietideals.Views.LogInCredentialsView
import com.CioffiDeVivo.dietideals.Views.LoginView
import com.CioffiDeVivo.dietideals.Views.MakeABidEnglish
import com.CioffiDeVivo.dietideals.Views.MakeABidSilent
import com.CioffiDeVivo.dietideals.Views.ManageCardsView
import com.CioffiDeVivo.dietideals.Views.RegisterCredentialsView
import com.CioffiDeVivo.dietideals.Views.RegisterView
import com.CioffiDeVivo.dietideals.Views.SearchView
import com.CioffiDeVivo.dietideals.Views.SellView
import com.CioffiDeVivo.dietideals.viewmodel.RegistrationViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetupNavGraph(navController: NavHostController, viewModel: MainViewModel, viewModel2: RegistrationViewModel) {
    NavHost(
        navController = navController,
        startDestination = Screen.Register.route
    ) {
        composable(
            route = Screen.EditProfile.route
        ) {
            Scaffold(topBar = {
                DetailsViewTopBar(
                    caption = stringResource(id = R.string.editProfile),
                    destinationRoute = Screen.Account.route,
                    navController = navController
                )
            },
                bottomBar = {
                    BottomNavBar(
                        selectedNavBarItem = viewModel.selectedNavBarItem,
                        navController = navController
                    )
                }) {
                Box(modifier = Modifier.padding(it)) {
                    EditProfile(viewModel = viewModel, navController = navController)
                }
            }
        }
        composable(
            route = Screen.EditContactInfo.route
        ) {
            Scaffold(topBar = {
                DetailsViewTopBar(
                    caption = stringResource(id = R.string.contactInfo),
                    destinationRoute = Screen.Account.route,
                    navController = navController
                )
            },
                bottomBar = {
                    BottomNavBar(
                        selectedNavBarItem = viewModel.selectedNavBarItem,
                        navController = navController
                    )
                }) {
                Box(modifier = Modifier.padding(it)) {
                    EditContactInfoView(viewModel = viewModel, navController = navController)
                }
            }
        }
        composable(
            route = Screen.CreateAuction.route
        ) {
            Scaffold(topBar = {
                DetailsViewTopBar(
                    caption = stringResource(id = R.string.createAuction),
                    destinationRoute = Screen.Sell.route,
                    navController = navController
                )
            }) {
                Box(modifier = Modifier.padding(it)) {
                    CreateAuction(viewModel = viewModel, navController = navController)
                }
            }
        }
        composable(
            route = Screen.RegisterCredentials.route
        ) {
            RegisterCredentialsView(viewModel = viewModel, viewModel2 = viewModel2)
        }
        composable(
            route = Screen.LogInCredentials.route
        ) {
            LogInCredentialsView(viewModel = viewModel, navController = navController)
        }
        composable(
            route = Screen.MakeABidEnglish.route
        ) {
            MakeABidEnglish(viewModel = viewModel)
        }
        composable(
            route = Screen.MakeABidSilent.route
        ) {
            MakeABidSilent(viewModel = viewModel)
        }
        composable(
            route = Screen.Home.route
        ) {
            Scaffold(bottomBar = {
                BottomNavBar(
                    selectedNavBarItem = viewModel.selectedNavBarItem,
                    navController = navController
                )
            }) {
                Box(modifier = Modifier.padding(it)) {
                    HomeView()
                }
            }
        }
        composable(
            route = Screen.Login.route
        ) {
            LoginView(viewModel = viewModel, navController = navController)
        }
        composable(
            route = Screen.Register.route
        ) {
            RegisterView(viewModel = viewModel, navController = navController)
        }
        composable(
            route = Screen.Favourites.route
        ) {
            Scaffold(bottomBar = {
                BottomNavBar(
                    selectedNavBarItem = viewModel.selectedNavBarItem,
                    navController = navController
                )
            }) {
                Box(modifier = Modifier.padding(it)) {
                    FavouritesView(viewModel = viewModel, navController = navController)
                }
            }
        }
        composable(
            route = Screen.Search.route
        ) {
            Scaffold(bottomBar = {
                BottomNavBar(
                    selectedNavBarItem = viewModel.selectedNavBarItem,
                    navController = navController
                )
            }) {
                Box(modifier = Modifier.padding(it)) {
                    SearchView(viewModel = viewModel, navController = navController)
                }
            }
        }
        composable(
            route = Screen.Auction.route
        ) {
            Scaffold(topBar = {
                AuctionTopBar(
                    navController = navController,
                    viewModel = viewModel
                )
            },
                bottomBar = {
                    BottomNavBar(
                        selectedNavBarItem = viewModel.selectedNavBarItem,
                        navController = navController
                    )
                }) {
                Box(modifier = Modifier.padding(it)) {
                    AuctionView(
                        auction = viewModel.selectedAuction,
                        viewModel.auctionOpenByOwner
                    )
                }
            }
        }
        composable(
            route = Screen.Account.route
        ) {
            Scaffold(bottomBar = {
                BottomNavBar(
                    selectedNavBarItem = viewModel.selectedNavBarItem,
                    navController = navController
                )
            }) {
                Box(modifier = Modifier.padding(it)) {
                    AccountView(viewModel = viewModel, navController = navController)
                }
            }
        }
        composable(
            route = Screen.ManageCards.route
        ) {
            Scaffold(topBar = {
                DetailsViewTopBar(
                    caption = stringResource(id = R.string.manageCards),
                    destinationRoute = Screen.Account.route,
                    navController = navController
                )
            },
                bottomBar = {
                    BottomNavBar(
                        selectedNavBarItem = viewModel.selectedNavBarItem,
                        navController = navController
                    )
                }) {
                Box(modifier = Modifier.padding(it)) {
                    ManageCardsView(viewModel = viewModel)
                }
            }
        }
        composable(
            route = Screen.Sell.route
        ) {
            Scaffold(
                topBar = {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.sellView),
                            fontSize = 30.sp,
                            fontWeight = FontWeight(600)
                        )
                    }
                },
                bottomBar = {
                    BottomNavBar(
                        selectedNavBarItem = viewModel.selectedNavBarItem,
                        navController = navController
                    )
                }
            ) {
                Box(modifier = Modifier.padding(it)) {
                    SellView(viewModel = viewModel, navController = navController)
                }
            }
        }
        composable(
            route = Screen.BidHistory.route
        ) {
            Scaffold(topBar = {
                DetailsViewTopBar(
                    caption = stringResource(id = R.string.bidHistory),
                    destinationRoute = Screen.Auction.route,
                    navController = navController
                )
            },
                bottomBar = {
                    BottomNavBar(
                        selectedNavBarItem = viewModel.selectedNavBarItem,
                        navController = navController
                    )
                }) {
                Box(modifier = Modifier.padding(it)) {
                    BidHistoryView(viewModel = viewModel)
                }
            }
        }
    }
}