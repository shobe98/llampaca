package com.ait.alpaca

import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_challenge.*
import android.util.Size
import android.graphics.Matrix
import android.util.Log
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.*
import androidx.core.graphics.scale
import androidx.lifecycle.LifecycleOwner
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import java.io.File
import java.util.concurrent.Executors
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel
import java.util.*
import kotlin.concurrent.schedule


class ChallengeActivity : AppCompatActivity(), LifecycleOwner {
    private lateinit var uid: String
    private var challengeNumber: Long = 0L
    private lateinit var challengeWord: String
    private lateinit var progressionDocument: DocumentSnapshot

    fun handleSuccess(labels: MutableList<FirebaseVisionImageLabel>) {
        var successful = false
        for (label in labels) {
            if (label.text == challengeWord) {
                successful = true
                CameraX.unbindAll()

                //TODO(astanciu): Dialog with new llama

                Toast.makeText(
                    this,
                    "You finished challenge %d".format(challengeNumber),
                    Toast.LENGTH_LONG
                ).show()


            }
        }

        if (!successful) {
            CameraX.unbindAll()
            Toast.makeText(
                this,
                "That is not a %s but you get a llama anyway!".format(challengeWord.toLowerCase()),
                Toast.LENGTH_LONG
            ).show()
        }

        successfullChallenge()
        CameraX.unbindAll()
        // TODO: Change layout or redirect!
    }

    private fun successfullChallenge() {

        val db = FirebaseFirestore.getInstance()

        db.collection("progression").whereEqualTo("uid", uid).get().addOnSuccessListener {
            it.documents[0].reference.update(mapOf("challenges_solved" to (challengeNumber + 1)))
                .addOnCompleteListener {

                    Toast.makeText(
                        this@ChallengeActivity,
                        "We just recorded your progress",
                        Toast.LENGTH_LONG
                    ).show()

                }
        }.addOnFailureListener {
            Toast.makeText(
                this@ChallengeActivity,
                "Error: ${it.message}", Toast.LENGTH_LONG
            ).show()

        }
    }

    fun handleFailure(e: Exception) {
        Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
    }

    private val executor = Executors.newSingleThreadExecutor()

    private var cameraPermissionGranted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge)

        requestNeededPermission()


        if (cameraPermissionGranted) {
            startCamera()
        }

        initializeChallenge()

    }

    private fun initializeChallenge() {
        uid = "e18flAKNdjQktKemQOHjF0HUo9C3" // Peekler for now

        if (FirebaseAuth.getInstance().currentUser != null) {
            uid = FirebaseAuth.getInstance().currentUser!!.uid
        }

        val db = FirebaseFirestore.getInstance()

        db.collection("progression").whereEqualTo("uid", uid).get().addOnCompleteListener {
            val doc = it.result!!.documents[0]

            challengeNumber = doc.get("challenges_solved") as Long


            challengeWord = resources.getStringArray(R.array.words)[challengeNumber.toInt() + 1]

            runOnUiThread {
                challenge_placeholder.text = challengeWord.toUpperCase() + " " + challengeNumber.toString()
            }
        }

    }


    // Camera stuff
    private fun updateTransform() {
        val matrix = Matrix()

        // Compute the center of the view finder
        val centerX = cameraView.width / 2f
        val centerY = cameraView.height / 2f

        // Correct preview output to account for display rotation
        val rotationDegrees = when (cameraView.display.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> return
        }
        matrix.postRotate(-rotationDegrees.toFloat(), centerX, centerY)

        // Finally, apply transformations to our TextureView
        cameraView.setTransform(matrix)
    }

    private fun startCamera() {
        // Create configuration object for the cameraView use case
        val previewConfig = PreviewConfig.Builder().apply {
            setTargetResolution(Size(640, 480))
        }.build()


        // Build the cameraView use case
        val preview = Preview(previewConfig)


        // Every time the cameraView is updated, recompute layout
        preview.setOnPreviewOutputUpdateListener {

            // To update the SurfaceTexture, we have to remove it and re-add it
            val parent = cameraView.parent as ViewGroup
            parent.removeView(cameraView)
            parent.addView(cameraView, 0)

            cameraView.surfaceTexture = it.surfaceTexture
            updateTransform()
        }


        // Add this before CameraX.bindToLifecycle

        // Create configuration object for the image capture use case
        val imageCaptureConfig = ImageCaptureConfig.Builder()
            .apply {
                // We don't set a resolution for image capture; instead, we
                // select a capture mode which will infer the appropriate
                // resolution based on aspect ration and requested mode
                setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
            }.build()

        // Build the image capture use case and attach button click listener
        val imageCapture = ImageCapture(imageCaptureConfig)
        btnCapture.setOnClickListener {
            val file = File(
                externalMediaDirs.first(),
                "last_capture.jpg"
            )

            imageCapture.takePicture(file, executor,
                object : ImageCapture.OnImageSavedListener {
                    override fun onError(
                        imageCaptureError: ImageCapture.ImageCaptureError,
                        message: String,
                        exc: Throwable?
                    ) {
                        val msg = "Photo capture failed: $message"
                        Log.e("CameraXApp", msg, exc)
                        cameraView.post {
                            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onImageSaved(file: File) {
                        val msg = "Photo capture succeeded: ${file.absolutePath}"
                        Log.d("CameraXApp", msg)
                        cameraView.post {
                            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                        }
                        runOnUiThread {
                            ivTest.setImageBitmap(
                                BitmapFactory.decodeFile(file.absolutePath).scale(
                                    40,
                                    40
                                )
                            )
                        }

                        val image =
                            FirebaseVisionImage.fromBitmap(BitmapFactory.decodeFile(file.absolutePath))

                        val labeler = FirebaseVision.getInstance().onDeviceImageLabeler

                        labeler.processImage(image)
                            .addOnSuccessListener { labels ->
                                handleSuccess(labels)
                            }
                            .addOnFailureListener { e ->
                                handleFailure(e)
                            }
                    }
                })

            btnCapture.visibility = View.GONE
            btnRetry.visibility = View.VISIBLE

        }

        btnRetry.setOnClickListener {
            btnCapture.visibility = View.VISIBLE
            btnRetry.visibility = View.GONE
            btnCapture.isEnabled = false

        }


        // Bind use cases to lifecycle
        // If Android Studio complains about "this" being not a LifecycleOwner
        // try rebuilding the project or updating the appcompat dependency to
        // version 1.1.0 or higher.
        //CameraX.bindToLifecycle(this, preview, imageCapture)
        CameraX.bindToLifecycle(this, preview, imageCapture)
    }


    private fun requestNeededPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    android.Manifest.permission.CAMERA
                )
            ) {
                Toast.makeText(
                    this,
                    "I need it for camera", Toast.LENGTH_SHORT
                ).show()
            }

            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.CAMERA),
                101
            )
        } else {
            // we already have permission
            cameraPermissionGranted = true
            btnCapture.isEnabled = true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            101 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "CAMERA perm granted", Toast.LENGTH_SHORT).show()

                    cameraPermissionGranted = true
                    btnCapture.isEnabled = true
                } else {
                    cameraPermissionGranted = false
                    Toast.makeText(this, "CAMERA perm NOT granted", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
