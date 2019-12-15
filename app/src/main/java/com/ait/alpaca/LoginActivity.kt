package com.ait.alpaca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ait.alpaca.utils.ProgressUtils
import com.ait.alpaca.utils.RegistrationUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), RegistrationUtils.RegistrationHandler {
    override fun registrationCallback() {
        btnLogin.isEnabled = true
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun loginClick(v: View) {
        if (!isFormValid()) {
            return
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(
            etEmail.text.toString(), etPassword.text.toString()
        ).addOnSuccessListener {
            Toast.makeText(
                this@LoginActivity, "Login OK",
                Toast.LENGTH_SHORT).show()

            // open Scrolling Activity
            startActivity(
                Intent(this@LoginActivity,
                    MenuActivity::class.java)
            )
            finish()

        }.addOnFailureListener {
            Toast.makeText(
                this@LoginActivity, "Error: ${it.message}",
                Toast.LENGTH_LONG).show()
        }

    }

    fun registerClick(v: View) {
        if (!isFormValid()) {
            return
        }

        btnLogin.isEnabled = false

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
            etEmail.text.toString(), etPassword.text.toString()
        ).addOnSuccessListener {
            val user = it.user

            user!!.updateProfile(
                UserProfileChangeRequest.Builder()
                    .setDisplayName(userNameFromEMail(etEmail.text.toString()))
                    .build()
            )

            RegistrationUtils.firstTimeUser(user.uid.toString(), this)

            Toast.makeText(
                this@LoginActivity, "Registration OK",
                Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            btnLogin.isEnabled = true
            Toast.makeText(
                this@LoginActivity, "Error: ${it.message}",
                Toast.LENGTH_LONG).show()
        }

    }


    fun userNameFromEMail(email: String) = email.substringBefore("@")

    private fun isFormValid(): Boolean {
        return when {
            etEmail.text.isEmpty() -> {
                etEmail.error = "This field can not be empty"
                false
            }
            etPassword.text.isEmpty() -> {
                etPassword.error = "This field can not be empty"
                false
            }
            else -> true
        }
    }

}
