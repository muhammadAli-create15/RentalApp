package com.example.landlord

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

   private lateinit var  databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sign = findViewById<Button>(R.id.edtLog)

        val nameEditText = findViewById<EditText>(R.id.editName)
        val emailEditText = findViewById<EditText>(R.id.editEmailAddress)
        val phoneEditText = findViewById<EditText>(R.id.editPhone)
        val passwordEditText = findViewById<EditText>(R.id.editPassword)

        val accoutSignIn=findViewById<TextView>(R.id.haveAccount)
        accoutSignIn.setOnClickListener {
            val intent=Intent(this,Sign_In::class.java)
            startActivity(intent)

        }



        sign.setOnClickListener {
                val name = nameEditText.text.toString()
                val email = emailEditText.text.toString()
                val phone = phoneEditText.text.toString()
                val password = passwordEditText.text.toString()


                if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                } else {

                    databaseReference = FirebaseDatabase.getInstance().getReference("Users")
                val userData = UserData(name, email, phone, password)
                databaseReference.child(password).setValue(userData).addOnSuccessListener {

                    nameEditText.text.clear()
                    emailEditText.text.clear()
                    phoneEditText.text.clear()
                    passwordEditText.text.clear()

                    showToast("Signed")

                    val intent = Intent(this, Phone::class.java)
                    startActivity(intent)
                   // finish()

                }.addOnFailureListener {

                    showToast("Failed")
                }

            }

        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()


    }
}