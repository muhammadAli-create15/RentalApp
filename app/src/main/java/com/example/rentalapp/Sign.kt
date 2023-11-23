package com.example.rentalapp

import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class Sign : AppCompatActivity() {
   private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)

       auth=Firebase.auth



        val signUp=findViewById<TextView>(R.id.btnSign)
        signUp.setOnClickListener {
            val intent=Intent(this,CreateAccount::class.java)
            startActivity(intent)
        }

          val  login=findViewById<Button>(R.id.edtLog)
          login.setOnClickListener {
              performlogin()
          }
         val forgotPassword=findViewById<TextView>(R.id.tvForgot)
         forgotPassword.setOnClickListener {
          val builder:AlertDialog.Builder=AlertDialog.Builder(this)
             builder.setTitle("Forgot Password")
             val view: View = layoutInflater.inflate(R.layout.dialog_forgot_password, null)
             builder.setView(view)
             val username:EditText=view.findViewById(R.id.edtUsername)


             builder.setPositiveButton("Reset",DialogInterface.OnClickListener { dialogInterface, i ->
                forgotPasword(username)
             })
             builder.setNegativeButton("Close",DialogInterface.OnClickListener { dialogInterface, i ->

             })
             builder.show()
             
         }



    }

    private fun forgotPasword(username:EditText) {
        if(username.text.toString().isEmpty()){
            username.error="Please enter the email"
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(username.text.toString()).matches()){
            return
        }
        Firebase.auth.sendPasswordResetEmail(username.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                 Toast.makeText(this,"Email sent",Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun performlogin() {
        val emailInput = findViewById<TextInputLayout>(R.id.tvEmail)
        val emailEditText = emailInput.editText

        val passwordInput = findViewById<TextInputLayout>(R.id.edtPassword)
        val passwordEditText = passwordInput.editText

        val email = emailEditText?.text.toString()
        val password = passwordEditText?.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(email, password)
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
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error occurred ${it.localizedMessage}", Toast.LENGTH_SHORT)
                    .show()
            }
    }

}