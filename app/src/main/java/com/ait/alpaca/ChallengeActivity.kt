package com.ait.alpaca

import android.content.Intent
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
import android.view.ViewGroup
import androidx.camera.core.*
import androidx.lifecycle.LifecycleOwner
import com.ait.alpaca.utils.ProgressUtils
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_challenge.ivClouds
import java.io.File
import java.util.concurrent.Executors
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel
import java.util.*


class ChallengeActivity : AppCompatActivity(), LifecycleOwner {
    private var challengeNumber: Long = 0L
    private lateinit var challengeWord: String

    fun handleMLLabelingSuccess(labels: MutableList<FirebaseVisionImageLabel>) {
        var successful = false
        for (label in labels) {
           if (label.text.toLowerCase(Locale.ENGLISH) == challengeWord.toLowerCase(Locale.ENGLISH)) {
                successful = true

                successfullChallenge() // This finishes the activity.
            }
        }

        if (!successful) {
            failedChallenge()
            tvLabels.setOnClickListener {
                failedChallenge()
            }
        }
    }

    private fun failedChallenge() {
        Log.i("FAILED_CHALLENGE", "You suck! Can't you find a picture of ${challengeWord}??")
        startActivity(Intent(this@ChallengeActivity, FailureActivity::class.java))
        finish()


    }

    private fun successfullChallenge() {
        ProgressUtils.updateAlpacas()
        Log.i("SUCCESSFUL_CHALLENGE","Updated your progress!")
        startActivity(Intent(this@ChallengeActivity, SuccessActivity::class.java))
        finish()
    }

    override fun onStop() {
        CameraX.unbindAll()
        super.onStop()
    }

    fun handleMLResponseFailure(e: Exception) {
        Log.e("ML_ERROR", e.message)
        throw(Throwable(e))
    }

    private val executor = Executors.newSingleThreadExecutor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge)

        Glide.with(this).asGif().load(R.drawable.clouds).into(ivClouds)

        btnQuit.setOnClickListener {
            finish()

        }




        requestNeededPermission()



        // Initialize challenge
        challengeNumber = ProgressUtils.getNumberOfAlpacas()

        challengeWord = resources.getStringArray(R.array.words)[challengeNumber.toInt() + 1]

        challenge_placeholder.text =
            challengeWord.toUpperCase(Locale.ENGLISH)

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

                        val image =
                            FirebaseVisionImage.fromBitmap(BitmapFactory.decodeFile(file.absolutePath))

                        val labeler = FirebaseVision.getInstance().cloudImageLabeler

                        labeler.processImage(image)
                            .addOnSuccessListener { labels ->
                                handleMLLabelingSuccess(labels)
                            }
                            .addOnFailureListener { e ->
                                handleMLResponseFailure(e)
                            }
                    }
                })

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
            btnCapture.isEnabled = true
            startCamera()
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
                    btnCapture.isEnabled = true
                    startCamera()
                } else {
                    Toast.makeText(this, "CAMERA perm NOT granted", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
