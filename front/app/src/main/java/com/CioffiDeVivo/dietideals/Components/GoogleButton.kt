package com.CioffiDeVivo.dietideals.Components

import android.content.ContentValues.TAG
import android.credentials.GetCredentialException
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.navigation.NavController
import com.CioffiDeVivo.dietideals.R
import com.CioffiDeVivo.dietideals.Views.Navigation.Screen
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.util.UUID

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun GoogleButton(navController: NavController){

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val googleId = stringResource(id = R.string.googleId)

    val rawNonce = UUID.randomUUID().toString()
    val rawNonceToBytes = rawNonce.toByteArray()
    val messageDigest = MessageDigest.getInstance("SHA-256")
    val digest = messageDigest.digest(rawNonceToBytes)
    val hashedNonce = digest.fold(""){ str, it -> str + "%02x".format(it) }

    val onClick: () -> Unit = {
        val credentialManager = CredentialManager.create(context)

        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(googleId)
            .setNonce(hashedNonce)
            .build()

        val credentialRequest: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        coroutineScope.launch {
            try {
                val result = credentialManager.getCredential(
                    request = credentialRequest,
                    context = context
                )
                val credential = result.credential

                val googleIdTokenCredential = GoogleIdTokenCredential
                    .createFrom(credential.data)

                val googleIdToken = googleIdTokenCredential.idToken

                Log.i(TAG, googleIdToken)
                //Rest che prende il google Token


                Toast.makeText(context, "You are signed in", Toast.LENGTH_SHORT).show()
                navController.navigate(Screen.Home.route)
            } catch ( e: GetCredentialException ){
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            } catch ( e: GoogleIdTokenParsingException ){
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
            }
    }

    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .size(width = 330.dp, height = 50.dp)
            .pulsateClick(),
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
                stringResource(R.string.continuewithGoogle),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

    )
}
