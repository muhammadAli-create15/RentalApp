package com.example.rentalapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Api
import com.google.android.gms.common.api.ApiException
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CreateAccount : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var client: GoogleSignInClient
    private lateinit var textview: TextView
    private lateinit var textview1: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        textview = findViewById(R.id.txtGoogle)

        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        client = GoogleSignIn.getClient(this, options)
        textview.setOnClickListener {
            val intent = client.signInIntent
            startActivityForResult(intent, 10001)

        }

        // Initialize Firebase Auth
        auth = Firebase.auth


        val login = findViewById<TextView>(R.id.editLogin)

        val signButton = findViewById<Button>(R.id.tvSign)
        signButton.setOnClickListener {
            createAccount()
        }
        login.setOnClickListener {
            val intent = Intent(this, Sign::class.java)
            startActivity(intent)
        }
    }

    //Lets get emails and passwords from the users
    private fun createAccount() {
        //Basing on the TextInputLayout,we have to do it like this
        val nameInputLayout = findViewById<TextInputLayout>(R.id.edtName)
        val nameEditText = nameInputLayout.editText

        val emailInputLayout = findViewById<TextInputLayout>(R.id.edtEmail)
        val emailEditText = emailInputLayout.editText


        // val  passwordEditText = findViewById<TextInputLayout>(R.id.tvPassword1)
        // val passwordEdit = findViewById<TextInputLayout>(R.id.edtPassword2)

        val password1InputLayout = findViewById<TextInputLayout>(R.id.tvPassword1)
        val passwordEditText = password1InputLayout.editText

        val password2InputLayout = findViewById<TextInputLayout>(R.id.edtPassword2)
        val passwordEdit = password2InputLayout.editText


        val fullName = nameEditText?.text.toString()
        val email = emailEditText?.text.toString()
        val password1 = passwordEditText?.text.toString()
        val password2 = passwordEdit?.text.toString()

        if (fullName.isEmpty() || email.isEmpty() || password1.isEmpty() || password2.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (password1 != password2) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, password1)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val intent = Intent(this, Welcome::class.java)
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()

                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error occurred ${it.localizedMessage}", Toast.LENGTH_SHORT)
                    .show()
            }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10001) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener {
                    if (task.isSuccessful) {
                        val i = Intent(this, Welcome::class.java)
                        startActivity(i)

                    } else {
                        Toast.makeText(this, "task.exception.message", Toast.LENGTH_SHORT).show()

                    }
                }

        }
    }
}


