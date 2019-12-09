package com.ait.alpaca.utils

import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

object ProgressUtils {
    private var uid: String

    private var alpacas: Long = -1
    private lateinit var progressionDocument: DocumentReference

    // TODO(astanciu): If enough time, make this class keep track of the progression locally, and leave syncing wiht the cloud in the background

    init {
        uid = "e18flAKNdjQktKemQOHjF0HUo9C3" // Peekler for now
        if (FirebaseAuth.getInstance().currentUser != null) {
            uid = FirebaseAuth.getInstance().currentUser!!.uid
        }

        val db = FirebaseFirestore.getInstance()

        val progression = db.collection("progression").whereEqualTo("uid", uid)

        var progressionDocumentId: String?

        progression.get().addOnSuccessListener {
            progressionDocumentId = it!!.documents[0].id
            progressionDocument = db.collection("progression").document(progressionDocumentId!!)
            setUpProgressionListeners()
        }.addOnFailureListener {
            Log.e("INIT_ERROR", "Alpaca progress init didn't work")
            throw(Throwable("Alpaca progress init didn't work"))
        }
    }

    private fun setUpProgressionListeners() {
        progressionDocument.addSnapshotListener { documentSnapshot: DocumentSnapshot?, firebaseFirestoreException: FirebaseFirestoreException? ->
            var cloud_alpacas = documentSnapshot!!.get("challenges_solved") as Long

            when {
                cloud_alpacas > alpacas -> {
                    alpacas = cloud_alpacas
                    Log.i("FIREBASE", "Updated from cloud: ${cloud_alpacas}")
                }

                cloud_alpacas < alpacas -> uploadAlpacas()
                else -> Log.w("FIREBASE", "Snapshot equals!")     // Do nothing if equal!

            }
        }
    }

    private fun uploadAlpacas() {
        Thread {
            progressionDocument.update(mapOf("challenges_solved" to (alpacas)))
                .addOnFailureListener {
                    Log.e("FIREBASE_ERROR", "Error: ${it.message}")
                    throw (Throwable("Error: ${it.message}"))

                }.addOnCompleteListener {
                    Log.i("FIREBASE", "Updated to cloud: ${alpacas}")
                }
        }.run()
    }

    fun getNumberOfAlpacas(): Long {
        if (this.alpacas == -1L) {
            Log.e("INIT_ERROR", "Alpaca progress init didn't work")
            throw(Throwable("Alpaca progress init didn't work"))
        }
        return alpacas
    }

    fun updateAlpacas() {
        alpacas += 1
        uploadAlpacas()
    }
}