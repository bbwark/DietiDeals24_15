package com.CioffiDeVivo.dietideals.Views.Navigation

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.CioffiDeVivo.dietideals.Components.AuctionTopBar
import com.CioffiDeVivo.dietideals.Components.BottomNavBar
import com.CioffiDeVivo.dietideals.Components.DetailsViewTopBar
import com.CioffiDeVivo.dietideals.viewmodel.MainViewModel
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.Views.AccountView
import com.CioffiDeVivo.dietideals.Views.AddCardView
import com.CioffiDeVivo.dietideals.Views.AuctionView
import com.CioffiDeVivo.dietideals.Views.BidHistoryView
import com.CioffiDeVivo.dietideals.Views.CreateAuction
import com.CioffiDeVivo.dietideals.Views.EditContactInfoView
import com.CioffiDeVivo.dietideals.Views.EditProfile
import com.CioffiDeVivo.dietideals.Views.FavouritesView
import com.CioffiDeVivo.dietideals.Views.HomeView
import com.CioffiDeVivo.dietideals.Views.LogInCredentialsView
import com.CioffiDeVivo.dietideals.Views.LoginView
import com.CioffiDeVivo.dietideals.Views.MakeABid
import com.CioffiDeVivo.dietideals.Views.ManageCardsView
import com.CioffiDeVivo.dietideals.Views.RegisterCredentialsView
import com.CioffiDeVivo.dietideals.Views.RegisterView
import com.CioffiDeVivo.dietideals.Views.SearchView
import com.CioffiDeVivo.dietideals.Views.SellView
import com.CioffiDeVivo.dietideals.utils.GenericViewModelFactory
import com.CioffiDeVivo.dietideals.viewmodel.AccountViewModel
import com.CioffiDeVivo.dietideals.viewmodel.AddCardViewModel
import com.CioffiDeVivo.dietideals.viewmodel.AuctionViewModel
import com.CioffiDeVivo.dietideals.viewmodel.BidHistoryViewModel
import com.CioffiDeVivo.dietideals.viewmodel.CreateAuctionViewModel
import com.CioffiDeVivo.dietideals.viewmodel.EditContactInfoViewModel
import com.CioffiDeVivo.dietideals.viewmodel.EditProfileViewModel
import com.CioffiDeVivo.dietideals.viewmodel.FavouritesViewModel
import com.CioffiDeVivo.dietideals.viewmodel.HomeViewModel
import com.CioffiDeVivo.dietideals.viewmodel.LogInCredentialsViewModel
import com.CioffiDeVivo.dietideals.viewmodel.MakeABidViewModel
import com.CioffiDeVivo.dietideals.viewmodel.ManageCardsViewModel
import com.CioffiDeVivo.dietideals.viewmodel.RegisterCredentialsViewModel
import com.CioffiDeVivo.dietideals.viewmodel.SearchViewModel
import com.CioffiDeVivo.dietideals.viewmodel.SellViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SetupNavGraph(navController: NavHostController, mainViewModel: MainViewModel) {
    val viewModelFactory = GenericViewModelFactory(LocalContext.current.applicationContext as Application)
    NavHost(
        navController = navController,
        startDestination = Screen.BidHistory.route
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
                        selectedNavBarItem = mainViewModel.selectedNavBarItem,
                        navController = navController
                    )
                }) {
                Box(modifier = Modifier.padding(it)) {
                    val viewModel: EditProfileViewModel = viewModel(factory = viewModelFactory)
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
                        selectedNavBarItem = mainViewModel.selectedNavBarItem,
                        navController = navController
                    )
                }) {
                Box(modifier = Modifier.padding(it)) {
                    val viewModel: EditContactInfoViewModel = viewModel(factory = viewModelFactory)
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
                    val viewModel: CreateAuctionViewModel = viewModel(factory = viewModelFactory)
                    CreateAuction(viewModel = viewModel, navController = navController)
                }
            }
        }
        composable(
            route = Screen.RegisterCredentials.route
        ) {
            val viewModel: RegisterCredentialsViewModel = viewModel(factory = viewModelFactory)
            RegisterCredentialsView(viewModel = viewModel, navController = navController)
        }
        composable(
            route = Screen.LogInCredentials.route
        ) {
            val viewModel: LogInCredentialsViewModel = viewModel(factory = viewModelFactory)
            LogInCredentialsView(viewModel = viewModel, navController = navController)
        }
        composable(
            route = Screen.MakeABid.route
        ) {
            val viewModel : MakeABidViewModel = viewModel(factory = viewModelFactory)
            MakeABid(viewModel = viewModel, navController = navController)
        }
        composable(
            route = Screen.Home.route
        ) {
            Scaffold(bottomBar = {
                BottomNavBar(
                    selectedNavBarItem = mainViewModel.selectedNavBarItem,
                    navController = navController
                )
            }) {
                Box(modifier = Modifier.padding(it)) {
                    val viewModel : HomeViewModel = viewModel(factory = viewModelFactory)
                    HomeView(viewModel = viewModel, navController = navController)
                }
            }
        }
        composable(
            route = Screen.Login.route
        ) {
            LoginView(navController = navController)
        }
        composable(
            route = Screen.Register.route
        ) {
            RegisterView(navController = navController)
        }
        composable(
            route = Screen.Favourites.route
        ) {
            Scaffold(bottomBar = {
                BottomNavBar(
                    selectedNavBarItem = mainViewModel.selectedNavBarItem,
                    navController = navController
                )
            }) {
                Box(modifier = Modifier.padding(it)) {
                    val viewModel: FavouritesViewModel = viewModel(factory = viewModelFactory)
                    FavouritesView(viewModel = viewModel, navController = navController)
                }
            }
        }
        composable(
            route = Screen.Search.route
        ) {
            Scaffold(bottomBar = {
                BottomNavBar(
                    selectedNavBarItem = mainViewModel.selectedNavBarItem,
                    navController = navController
                )
            }) {
                Box(modifier = Modifier.padding(it)) {
                    SearchView(viewModel = SearchViewModel(), navController = navController)
                }
            }
        }
        composable(
            route = Screen.Auction.route
        ) {
            Scaffold(topBar = {
                AuctionTopBar(
                    navController = navController,
                    viewModel = mainViewModel
                )
            },
                bottomBar = {
                    BottomNavBar(
                        selectedNavBarItem = mainViewModel.selectedNavBarItem,
                        navController = navController
                    )
                }) {
                Box(modifier = Modifier.padding(it)) {
                    val viewModel: AuctionViewModel = viewModel(factory = viewModelFactory)
                    AuctionView(viewModel = viewModel)
                }
            }
        }
        composable(
            route = Screen.Account.route
        ) {
            Scaffold(bottomBar = {
                BottomNavBar(
                    selectedNavBarItem = mainViewModel.selectedNavBarItem,
                    navController = navController
                )
            }) {
                Box(modifier = Modifier.padding(it)) {
                    AccountView(viewModel = AccountViewModel(), navController = navController)
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
                        selectedNavBarItem = mainViewModel.selectedNavBarItem,
                        navController = navController
                    )
                }) {
                Box(modifier = Modifier.padding(it)) {
                    val viewModel : ManageCardsViewModel = viewModel(factory = viewModelFactory)
                    ManageCardsView(viewModel = viewModel, navController = navController)
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
                        selectedNavBarItem = mainViewModel.selectedNavBarItem,
                        navController = navController
                    )
                }
            ) {
                Box(modifier = Modifier.padding(it)) {
                    SellView(viewModel = SellViewModel(), navController = navController)
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
                        selectedNavBarItem = mainViewModel.selectedNavBarItem,
                        navController = navController
                    )
                }) {
                Box(modifier = Modifier.padding(it)) {
                    val viewModel : BidHistoryViewModel = viewModel(factory = viewModelFactory)
                    BidHistoryView(viewModel = viewModel, navController = navController)
                }
            }
        }
        composable(
            route = Screen.AddCard.route
        ){
            Scaffold(topBar = {
                DetailsViewTopBar(
                    caption = stringResource(R.string.addCard),
                    destinationRoute = Screen.ManageCards.route,
                    navController = navController
                )
            },
                bottomBar = {
                    BottomNavBar(
                        selectedNavBarItem = mainViewModel.selectedNavBarItem,
                        navController = navController
                    )
                }
            ){
                Box(modifier = Modifier.padding(it)){
                    val viewModel : AddCardViewModel = viewModel(factory = viewModelFactory)
                    AddCardView(viewModel = viewModel, navController = navController)
                }

            }
        }
    }
}