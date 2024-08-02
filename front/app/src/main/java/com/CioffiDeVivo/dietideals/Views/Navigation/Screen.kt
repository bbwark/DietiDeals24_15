package com.CioffiDeVivo.dietideals.Views.Navigation

sealed class Screen(val route: String) {
    object Home: Screen("home_view")
    object Sell: Screen("sell_view")
    object Account: Screen("account_view")
    object Login: Screen("login_view")
    object Register: Screen("register_view")
    object Favourites: Screen("favourites_view")
    object Search: Screen("search_view")
    object EditProfile: Screen("edit_profile_view")
    object EditContactInfo: Screen("edit_contact_info_view")
    object ManageCards: Screen("manage_cards_view")
    object Auction: Screen("auction_view")
    object CreateAuction: Screen("create_auction_view")
    object LogInCredentials: Screen("login_credentials_view")
    object RegisterCredentials: Screen("register_credentials_view")
    object BidHistory: Screen("bid_history_view")
    object MakeABid: Screen("make_bid_view")
    object AddCard: Screen("add_card_view")
}