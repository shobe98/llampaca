package com.ait.alpaca.utils

import android.util.Log
import com.ait.alpaca.data.Alpaca
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

object ProgressUtils {
    interface AlpacaHandler { //hahaha
        fun handleAlpacasFirstInnit()
    }

    private lateinit var uid: String

    private var alpacas: Long = -1
    private lateinit var progressionDocument: DocumentReference

    fun initializeSingleton(handler: AlpacaHandler) {
       if (FirebaseAuth.getInstance().currentUser != null) {
            uid = FirebaseAuth.getInstance().currentUser!!.uid
        }

        val db = FirebaseFirestore.getInstance()

        val progression = db.collection("progression").whereEqualTo("uid", uid)

        var progressionDocumentId: String?

        progression.get().addOnSuccessListener {
            progressionDocumentId = it!!.documents[0].id
            progressionDocument = db.collection("progression").document(progressionDocumentId!!)
            setUpProgressionListeners(handler)
        }.addOnFailureListener {
            Log.e("INIT_ERROR", "Alpaca progress init didn't work")
            throw(Throwable("Alpaca progress init didn't work"))
        }
    }

    fun resetProgression() {
        alpacas = 1
        uploadAlpacas(1)
    }

    fun isFinished():Boolean {
        return alpacas == Alpaca.LAST_CHALLENGE

    }

    private fun setUpProgressionListeners(handler: AlpacaHandler) {
        progressionDocument.addSnapshotListener { documentSnapshot: DocumentSnapshot?, firebaseFirestoreException: FirebaseFirestoreException? ->
            var cloudAlpacas = documentSnapshot!!.get("challenges_solved") as Long

            when {
                alpacas == -1L -> {

                    alpacas = cloudAlpacas
                    Log.i("FIREBASE", "First time fetched from cloud: ${cloudAlpacas}")
                    handler.handleAlpacasFirstInnit()
                }
                cloudAlpacas > alpacas -> {
                    alpacas = cloudAlpacas
                    Log.i("FIREBASE", "Updated from cloud: ${cloudAlpacas}")
                }

                cloudAlpacas < alpacas -> uploadAlpacas(alpacas)
                else -> Log.w("FIREBASE", "Snapshot equals!")     // Do nothing if equal!

            }
        }
    }

    private fun uploadAlpacas(alpacas: Long) {
            progressionDocument.update(mapOf("challenges_solved" to (alpacas)))
                .addOnFailureListener {
                    Log.e("FIREBASE_ERROR", "Error: ${it.message}")
                    throw (Throwable("Error: ${it.message}"))

                }.addOnCompleteListener {
                    Log.i("FIREBASE", "Updated to cloud: ${alpacas}")
                    this.alpacas = alpacas // just to make sure nothing gets fucked up
                }
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
        uploadAlpacas(alpacas)
    }
}