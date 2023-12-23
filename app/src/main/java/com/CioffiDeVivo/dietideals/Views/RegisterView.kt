package com.CioffiDeVivo.dietideals.Views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.CioffiDeVivo.dietideals.R

@Composable
fun RegisterView() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(
            modifier = Modifier.height(40.dp))
        Text(
            "Create an Account",
            fontSize = 30.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )
        Spacer(
            modifier = Modifier.height(450.dp)
        )

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.size(width = 330.dp, height = 50.dp),
            content = {
                Text(stringResource(R.string.ContinuewithEmail), fontSize = 20.sp)
            }
        )
        Spacer(
            modifier = Modifier.height(5.dp)
        )
        Text(
            "or", fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Medium
        )
        Spacer(
            modifier = Modifier.height(5.dp)
        )
        OutlinedButton(
            onClick = { /*TODO*/ },
            modifier = Modifier.size(width = 330.dp, height = 50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            content = {
                Image(
                    painter = painterResource(id = R.drawable.logogoogle),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    stringResource(R.string.ContinuewithGoogle),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

        )
        Spacer(
            modifier = Modifier.height(5.dp)
        )
        OutlinedButton(
            onClick = { /*TODO*/ },
            modifier = Modifier.size(width = 330.dp, height = 50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            content = {
                Image(
                    painter = painterResource(id = R.drawable.logogit),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(

                    stringResource(R.string.ContinuewithGit),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

            }
        )
        Spacer(
            modifier = Modifier.height(5.dp)
        )
        OutlinedButton(
            onClick = { /*TODO*/ },
            modifier = Modifier.size(width = 330.dp, height = 50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            content = {
                Image(
                    painter = painterResource(id = R.drawable.logofacebook),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    stringResource(R.string.ContinuewithFacebook),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterPreview(){
    RegisterView()
}