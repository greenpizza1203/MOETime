//package org.firstinspires.ftc.robotcontroller.moeglobal.sheets
//
//
//import android.content.Context
//import androidx.annotation.RestrictTo
//import com.google.android.gms.auth.api.signin.GoogleSignIn
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount
//import com.google.android.gms.auth.api.signin.GoogleSignInClient
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions
//import com.google.android.gms.common.api.Scope
//import com.google.android.gms.drive.Drive
//import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
//import com.google.api.services.sheets.v4.SheetsScopes
//
//class AuthenticationManager(private val context: Lazy<Context>,
//                            val googleSignInClient: GoogleSignInClient,
//                            val googleAccountCredential: GoogleAccountCredential?) {
//    val googleSignInClient = GoogleSignIn.getClient(context, )
//    fun getLastSignedAccount(): GoogleSignInAccount? {
//        return GoogleSignIn.getLastSignedInAccount(context.value)
//    }
//
//    fun setUpGoogleAccountCredential() {
//        googleAccountCredential?.selectedAccount = getLastSignedAccount()?.account
//    }
//
//    companion object {
//        val SCOPES = arrayOf(SheetsScopes.SPREADSHEETS_READONLY)
//    }
//
//}
//
//val signInOptions by lazy {
//    val signInOptions: GoogleSignInOptions =
//            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                    .requestScopes(Scope(SheetsScopes.SPREADSHEETS_READONLY))
//                    .requestScopes(Scope(SheetsScopes.SPREADSHEETS))
//                    .requestScopes(Drive.SCOPE_FILE)
//                    .requestEmail()
//                    .build()
//}