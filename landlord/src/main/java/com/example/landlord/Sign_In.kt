package com.example.landlord

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class Sign_In : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        auth= FirebaseAuth.getInstance()

       val signIn=findViewById<Button>(R.id.edtSignIn)
        signIn.setOnClickListener {
            login()
        }
        val recoverPassword=findViewById<TextView>(R.id.edtRecover)
        recoverPassword.setOnClickListener {
            val builder: AlertDialog.Builder= AlertDialog.Builder(this)
            builder.setTitle("Forgot Password")
            val view: View = layoutInflater.inflate(R.layout.dialog_forgot_password, null)
            builder.setView(view)
            val username:EditText=view.findViewById(R.id.edtUsername)


            builder.setPositiveButton("Reset", DialogInterface.OnClickListener { dialogInterface, i ->
                forgotPasword(username)
            })
            builder.setNegativeButton("Close", DialogInterface.OnClickListener { dialogInterface, i ->

            })
            builder.show()

        }


    }

    private fun forgotPasword(username: EditText) {


    }

    private fun login() {
        val emailInput = findViewById<EditText>(R.id.editEmail)
        val passwordInput = findViewById<EditText>(R.id.tvPassword)


        val email = emailInput?.text.toString()
        val password = passwordInput?.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val intent = Intent(this, Home::class.java)
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