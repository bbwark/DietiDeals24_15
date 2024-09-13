package com.CioffiDeVivo.dietideals.presentation.common.sharedComponents

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
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
import com.CioffiDeVivo.dietideals.animations.pulsateClick
import com.CioffiDeVivo.dietideals.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.launch

//private lateinit var auth: FirebaseAuth

@Composable
fun GoogleButton(navController: NavController){
    //auth = Firebase.auth
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val credentialManager = CredentialManager.create(context)


    val onClick: () -> Unit = {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId("754156422030-qiheipearrtvon541iab5ogtniefvnth.apps.googleusercontent.com")
            .build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        coroutineScope.launch {
            try {
                val result = credentialManager.getCredential(
                    context = context,
                    request = request
                )
                val credential = result.credential
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val googleIdToken = googleIdTokenCredential.idToken
                Log.i(TAG, googleIdToken)
                Toast.makeText(context, "You Signed In", Toast.LENGTH_SHORT).show()
                /*val firebaseCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
                auth.signInWithCredential(firebaseCredential)
                    .addOnCompleteListener{ task ->
                        if(task.isSuccessful){
                            Toast.makeText(context, "You Signed In", Toast.LENGTH_SHORT).show()
                        }
                    }*/
            } catch (e: Exception){
                Log.e(TAG, e.toString())
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
