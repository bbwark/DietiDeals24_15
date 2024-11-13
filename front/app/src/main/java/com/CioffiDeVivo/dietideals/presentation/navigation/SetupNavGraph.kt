package com.CioffiDeVivo.dietideals.presentation.navigation

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.CioffiDeVivo.dietideals.presentation.ui.auction.components.AuctionTopBar
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.BottomNavigationBar
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.DetailsViewTopBar
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.presentation.ui.account.AccountView
import com.CioffiDeVivo.dietideals.presentation.ui.addCard.AddCardView
import com.CioffiDeVivo.dietideals.presentation.ui.auction.AuctionView
import com.CioffiDeVivo.dietideals.presentation.ui.bidHistory.BidHistoryView
import com.CioffiDeVivo.dietideals.presentation.ui.createAuction.CreateAuction
import com.CioffiDeVivo.dietideals.presentation.ui.editContactInfo.EditContactInfoView
import com.CioffiDeVivo.dietideals.presentation.ui.editProfile.EditProfile
import com.CioffiDeVivo.dietideals.presentation.ui.favourites.FavouritesView
import com.CioffiDeVivo.dietideals.presentation.ui.home.HomeView
import com.CioffiDeVivo.dietideals.presentation.ui.loginCredentials.LogInCredentialsView
import com.CioffiDeVivo.dietideals.presentation.ui.login.LoginView
import com.CioffiDeVivo.dietideals.presentation.ui.makeBid.MakeABid
import com.CioffiDeVivo.dietideals.presentation.ui.manageCards.ManageCardsView
import com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials.RegisterCredentialsView
import com.CioffiDeVivo.dietideals.presentation.ui.register.RegisterView
import com.CioffiDeVivo.dietideals.presentation.ui.search.SearchView
import com.CioffiDeVivo.dietideals.presentation.ui.sell.SellView
import com.CioffiDeVivo.dietideals.utils.GenericViewModelFactory
import com.CioffiDeVivo.dietideals.presentation.ui.account.AccountViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.addCard.AddCardViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.createAuction.CreateAuctionViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.editContactInfo.EditContactInfoViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.editProfile.EditProfileViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.favourites.FavouritesViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.home.HomeViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.loginCredentials.LogInCredentialsViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.manageCards.ManageCardsViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.registerCredentials.RegisterCredentialsViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.search.SearchViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.sell.SellViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.auction.AuctionViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.becomeSeller.BecomeSellerView
import com.CioffiDeVivo.dietideals.presentation.ui.becomeSeller.BecomeSellerViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.bidHistory.BidHistoryViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.login.LogInViewModel
import com.CioffiDeVivo.dietideals.presentation.ui.makeBid.MakeABidViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SetupNavGraph() {
    val viewModelFactory = GenericViewModelFactory(LocalContext.current.applicationContext as Application)
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(
            route = Screen.Account.route
        ) {
            Scaffold(bottomBar = {
                BottomNavigationBar(navController = navController)
            }) {
                Box(modifier = Modifier.padding(it)) {
                    val viewModel: AccountViewModel = viewModel(factory = viewModelFactory)
                    AccountView(viewModel = viewModel, navController = navController)
                }
            }
        }
        composable(
            route = Screen.EditProfile.route
        ) {
            Scaffold(topBar = {
                DetailsViewTopBar(
                    caption = stringResource(id = R.string.editProfile),
                    navController = navController
                )
            },
                bottomBar = {
                    BottomNavigationBar(navController = navController)
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
                    navController = navController
                )
            },
                bottomBar = {
                    BottomNavigationBar(navController = navController)
                }) {
                Box(modifier = Modifier.padding(it)) {
                    val viewModel: EditContactInfoViewModel = viewModel(factory = viewModelFactory)
                    EditContactInfoView(viewModel = viewModel, navController = navController)
                }
            }
        }
        composable(
            route = Screen.ManageCards.route
        ) {
            Scaffold(topBar = {
                DetailsViewTopBar(
                    caption = stringResource(id = R.string.manageCards),
                    navController = navController
                )
            },
                bottomBar = {
                    BottomNavigationBar(navController = navController)
                }) {
                Box(modifier = Modifier.padding(it)) {
                    val viewModel : ManageCardsViewModel = viewModel(factory = viewModelFactory)
                    ManageCardsView(viewModel = viewModel, navController = navController)
                }
            }
        }
        composable(
            route = Screen.Favourites.route
        ) {
            Scaffold(bottomBar = {
                BottomNavigationBar(navController = navController)
            }) {
                Box(modifier = Modifier.padding(it)) {
                    val viewModel: FavouritesViewModel = viewModel(factory = viewModelFactory)
                    FavouritesView(viewModel = viewModel, navController = navController)
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
                    BottomNavigationBar(navController = navController)
                }
            ) {
                Box(modifier = Modifier.padding(it)) {
                    val viewModel: SellViewModel = viewModel(factory = viewModelFactory)
                    SellView(viewModel = viewModel, navController = navController)
                }
            }
        }
        composable(
            route = Screen.CreateAuction.route
        ) {
            Scaffold(topBar = {
                DetailsViewTopBar(
                    caption = stringResource(id = R.string.createAuction),
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
            Scaffold(topBar = {
                DetailsViewTopBar(
                    caption = stringResource(id = R.string.logIn),
                    navController = navController
                )
            }) {
                Box(modifier = Modifier.padding(it)) {
                    val viewModel: LogInCredentialsViewModel = viewModel(factory = viewModelFactory)
                    LogInCredentialsView(viewModel = viewModel, navController = navController)
                }
            }
        }
        composable(
            route = Screen.Home.route
        ) {
            Scaffold(bottomBar = {
                BottomNavigationBar(navController = navController)
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
            val viewModel : LogInViewModel = viewModel(factory = viewModelFactory)
            LoginView(viewModel = viewModel ,navController = navController)
        }
        composable(
            route = Screen.Register.route
        ) {
            RegisterView(navController = navController)
        }
        composable(
            route = Screen.Search.route
        ) {
            Scaffold(bottomBar = {
                BottomNavigationBar(navController = navController)
            }) {
                Box(modifier = Modifier.padding(it)) {
                    val viewModel: SearchViewModel = viewModel(factory = viewModelFactory)
                    SearchView(viewModel = viewModel, navController = navController)
                }
            }
        }
        composable(
            route = Screen.Auction.route + "/{auctionId}",
            arguments = listOf(
                navArgument("auctionId"){
                    type = NavType.StringType
                }
            )
        ) { entry ->
            val auctionId = entry.arguments?.getString("auctionId") ?: ""
            val viewModel: AuctionViewModel = viewModel(factory = viewModelFactory)
            Scaffold(topBar = {
                AuctionTopBar(
                    navController = navController,
                    viewModel = viewModel
                )
            },
                bottomBar = {
                    BottomNavigationBar(navController = navController)
                }) {
                Box(modifier = Modifier.padding(it)) {
                    AuctionView(
                        auctionId = auctionId,
                        viewModel = viewModel,
                        navController = navController
                    )
                }
            }
        }
        composable(
            route = Screen.MakeABid.route + "/{auctionId}",
            arguments = listOf(
                navArgument("auctionId"){
                    type = NavType.StringType
                }
            )
        ) { entry ->
            val auctionId = entry.arguments?.getString("auctionId") ?: ""
            val viewModel: MakeABidViewModel = viewModel(factory = viewModelFactory)
            MakeABid(
                auctionId = auctionId,
                viewModel = viewModel,
                navController = navController
            )
        }
        composable(
            route = Screen.BidHistory.route + "/{auctionId}",
            arguments = listOf(
                navArgument("auctionId"){
                    type = NavType.StringType
                }
            )
        ) {entry ->
            val auctionId = entry.arguments?.getString("auctionId") ?: ""
            val viewModel: BidHistoryViewModel = viewModel(factory = viewModelFactory)
            Scaffold(topBar = {
                DetailsViewTopBar(
                    caption = stringResource(id = R.string.bidHistory),
                    navController = navController
                )
            },
                bottomBar = {
                    BottomNavigationBar(navController = navController)
                }) {
                Box(modifier = Modifier.padding(it)) {
                    BidHistoryView(auctionId = auctionId ,viewModel = viewModel, navController = navController)
                }
            }
        }
        composable(
            route = Screen.AddCard.route
        ){
            Scaffold(topBar = {
                DetailsViewTopBar(
                    caption = stringResource(R.string.addCard),
                    navController = navController
                )
            },
                bottomBar = {
                    BottomNavigationBar(navController = navController)
                }
            ){
                Box(modifier = Modifier.padding(it)){
                    val viewModel : AddCardViewModel = viewModel(factory = viewModelFactory)
                    AddCardView(viewModel = viewModel, navController = navController)
                }

            }
        }
        composable(
            route = Screen.BecomeSeller.route
        ) {
            Scaffold(
                topBar = {
                    DetailsViewTopBar(
                        caption = stringResource(R.string.becomeSeller),
                        navController = navController
                    )
                }
            ) {
                Box(modifier = Modifier.padding(it)){
                    val viewModel : BecomeSellerViewModel = viewModel(factory = viewModelFactory)
                    BecomeSellerView(viewModel = viewModel, navController = navController)
                }
            }
        }
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavHostController): T{
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this) { navController.getBackStackEntry(navGraphRoute) }
    return viewModel(parentEntry)
}