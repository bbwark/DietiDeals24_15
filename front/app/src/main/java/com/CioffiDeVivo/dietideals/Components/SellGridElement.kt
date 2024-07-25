package com.CioffiDeVivo.dietideals.Components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.CioffiDeVivo.dietideals.viewmodel.MainViewModel
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.viewmodel.SellViewModel

@Composable
fun SellGridElement(auctionItemName: String, modifier: Modifier = Modifier, viewModel: ViewModel, navController: NavHostController) { //it should receive also the compressed image
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 12.dp)
            .size(width = 160.dp, height = 170.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CardHeader(
                auctionItemName = auctionItemName,
                viewModel = viewModel,
                navController = navController
            )
            Image(
                painter = painterResource(id = R.drawable.placeholder),
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .clickable { /*navigate to auction*/ },
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Composable
fun CardHeader(auctionItemName: String, viewModel: ViewModel, navController: NavHostController) {
    Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = auctionItemName,
            modifier = Modifier
                .weight(1f)
                .clickable { /*navigate to auction*/ },
            fontSize = 14.sp,
            fontWeight = FontWeight(500),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun SellGridElementPreview() {
    SellGridElement(
        auctionItemName = "Auction Item Name Placeholder",
        viewModel = SellViewModel(),
        navController = rememberNavController()
    )
}