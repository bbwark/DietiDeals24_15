package com.CioffiDeVivo.dietideals.Views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeView(){
    Column(horizontalAlignment = Alignment.End,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(18.dp))
        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Rounded.Favorite, contentDescription = null)
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {

        Spacer(
            modifier = Modifier.height(40.dp))
        Text(
            "DietiDeals",
            fontSize = 30.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )
        Spacer(modifier = Modifier.height(15.dp))
        ElevatedButton(
            onClick = { /*TODO*/ },
            modifier = Modifier.size(width = 330.dp, height = 50.dp),
            content = {

                Icon(Icons.Rounded.Search, contentDescription = null)
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    "Search on DietiDeals",
                    fontSize = 15.sp,
                    color = Color.Gray,
                )
                Spacer(modifier = Modifier.width(110.dp))
            }
        )

    }

}

@Preview(showBackground = true)
@Composable
fun HomeViewPreview(){
    HomeView()
}