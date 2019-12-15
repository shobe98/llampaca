package com.ait.alpaca.utils

import com.google.firebase.firestore.FirebaseFirestore

object RegistrationUtils {
    interface RegistrationHandler {
        fun registrationCallback()
    }

    fun firstTimeUser(uid: String, handler: RegistrationHandler) {
        val db = FirebaseFirestore.getInstance()
        db.collection("progression").add(mapOf("uid" to uid, "challenges_solved" to 0 )).addOnSuccessListener {
            handler.registrationCallback()
        }.addOnFailureListener {
            throw(Throwable("Something went wrong with innit progression after registration"))
        }
    }
}