package com.CioffiDeVivo.dietideals.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.CioffiDeVivo.dietideals.presentation.ui.auction.components.AuctionTopBar
import com.CioffiDeVivo.dietideals.presentation.common.sharedComponents.BottomNavigationBar
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
import com.CioffiDeVivo.dietideals.presentation.ui.register.RegisterViewModel
import com.CioffiDeVivo.dietideals.utils.AppViewModelFactory
import com.CioffiDeVivo.dietideals.utils.trackScreen
import com.google.firebase.analytics.FirebaseAnalytics

@Composable
fun NavGraph(
    navController: NavHostController,
    appViewModelFactory: AppViewModelFactory,
    firebaseAnalytics: FirebaseAnalytics
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(
            route = Screen.Login.route
        ) {
            LaunchedEffect(Unit){
                trackScreen(firebaseAnalytics, "LogInView")
            }
            val viewModel : LogInViewModel = viewModel(factory = appViewModelFactory)
            LoginView(viewModel = viewModel ,navController = navController)
        }
        composable(
            route = Screen.LogInCredentials.route
        ) {
            LaunchedEffect(Unit){
                trackScreen(firebaseAnalytics, "LogInCredentialsView")
            }
            val viewModel: LogInCredentialsViewModel = viewModel(factory = appViewModelFactory)
            LogInCredentialsView(viewModel = viewModel, navController = navController)
        }
        composable(
            route = Screen.Register.route
        ) {
            LaunchedEffect(Unit){
                trackScreen(firebaseAnalytics, "RegisterView")
            }
            val viewModel: RegisterViewModel = viewModel(factory = appViewModelFactory)
            RegisterView(viewModel = viewModel, navController = navController)
        }
        composable(
            route = Screen.RegisterCredentials.route
        ) {
            LaunchedEffect(Unit){
                trackScreen(firebaseAnalytics, "RegisterCredentialsView")
            }
            val viewModel: RegisterCredentialsViewModel = viewModel(factory = appViewModelFactory)
            RegisterCredentialsView(viewModel = viewModel, navController = navController)
        }
        composable(
            route = Screen.Home.route
        ) {
            LaunchedEffect(Unit){
                trackScreen(firebaseAnalytics, "HomeView")
            }
            val viewModel : HomeViewModel = viewModel(factory = appViewModelFactory)
            HomeView(viewModel = viewModel, navController = navController)
        }
        composable(
            route = Screen.Search.route
        ) {
            LaunchedEffect(Unit){
                trackScreen(firebaseAnalytics, "SearchView")
            }
            val viewModel: SearchViewModel = viewModel(factory = appViewModelFactory)
            SearchView(viewModel = viewModel, navController = navController)
        }
        composable(
            route = Screen.Sell.route
        ) {
            LaunchedEffect(Unit){
                trackScreen(firebaseAnalytics, "SellView")
            }
            val viewModel: SellViewModel = viewModel(factory = appViewModelFactory)
            SellView(viewModel = viewModel, navController = navController)
        }
        composable(
            route = Screen.BecomeSeller.route
        ) {
            LaunchedEffect(Unit){
                trackScreen(firebaseAnalytics, "BecomeSellerView")
            }
            val viewModel : BecomeSellerViewModel = viewModel(factory = appViewModelFactory)
            BecomeSellerView(viewModel = viewModel, navController = navController)
        }
        composable(
            route = Screen.CreateAuction.route
        ) {
            LaunchedEffect(Unit){
                trackScreen(firebaseAnalytics, "CreateAuctionView")
            }
            val viewModel: CreateAuctionViewModel = viewModel(factory = appViewModelFactory)
            CreateAuction(viewModel = viewModel, navController = navController)
        }
        composable(
            route = Screen.Account.route
        ) {
            LaunchedEffect(Unit){
                trackScreen(firebaseAnalytics, "AccountView")
            }
            val viewModel: AccountViewModel = viewModel(factory = appViewModelFactory)
            AccountView(viewModel = viewModel, navController = navController)
        }
        composable(
            route = Screen.EditProfile.route
        ) {
            LaunchedEffect(Unit){
                trackScreen(firebaseAnalytics, "EditProfileView")
            }
            val viewModel: EditProfileViewModel = viewModel(factory = appViewModelFactory)
            EditProfile(viewModel = viewModel, navController = navController)
        }
        composable(
            route = Screen.EditContactInfo.route
        ) {
            LaunchedEffect(Unit){
                trackScreen(firebaseAnalytics, "EditContactInfoView")
            }
            val viewModel: EditContactInfoViewModel = viewModel(factory = appViewModelFactory)
            EditContactInfoView(viewModel = viewModel, navController = navController)
        }
        composable(
            route = Screen.ManageCards.route
        ) {
            LaunchedEffect(Unit){
                trackScreen(firebaseAnalytics, "ManageCardsView")
            }
            val viewModel: ManageCardsViewModel = viewModel(factory = appViewModelFactory)
            ManageCardsView(viewModel = viewModel, navController = navController)
        }
        composable(
            route = Screen.Favourites.route
        ) {
            LaunchedEffect(Unit){
                trackScreen(firebaseAnalytics, "FavouritesView")
            }
            val viewModel: FavouritesViewModel = viewModel(factory = appViewModelFactory)
            FavouritesView(viewModel = viewModel, navController = navController)
        }
        composable(
            route = Screen.Auction.route + "/{auctionId}",
            arguments = listOf(
                navArgument("auctionId"){
                    type = NavType.StringType
                }
            )
        ) { entry ->
            LaunchedEffect(Unit){
                trackScreen(firebaseAnalytics, "AuctionView")
            }
            val auctionId = entry.arguments?.getString("auctionId") ?: ""
            val viewModel: AuctionViewModel = viewModel(factory = appViewModelFactory)
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
            LaunchedEffect(Unit){
                trackScreen(firebaseAnalytics, "MakeABidView")
            }
            val auctionId = entry.arguments?.getString("auctionId") ?: ""
            val viewModel: MakeABidViewModel = viewModel(factory = appViewModelFactory)
            MakeABid(auctionId = auctionId, viewModel = viewModel, navController = navController)
        }
        composable(
            route = Screen.BidHistory.route + "/{auctionId}",
            arguments = listOf(
                navArgument("auctionId"){
                    type = NavType.StringType
                }
            )
        ) {entry ->
            LaunchedEffect(Unit){
                trackScreen(firebaseAnalytics, "BidHistoryView")
            }
            val auctionId = entry.arguments?.getString("auctionId") ?: ""
            val viewModel: BidHistoryViewModel = viewModel(factory = appViewModelFactory)
            BidHistoryView(auctionId = auctionId ,viewModel = viewModel, navController = navController)
        }
        composable(
            route = Screen.AddCard.route
        ){
            LaunchedEffect(Unit){
                trackScreen(firebaseAnalytics, "AddCardView")
            }
            val viewModel : AddCardViewModel = viewModel(factory = appViewModelFactory)
            AddCardView(viewModel = viewModel, navController = navController)
        }
    }
}