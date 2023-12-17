package com.whalescale.binit.api

import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.whalescale.binit.App
import java.io.ByteArrayOutputStream


object FirebaseUtils {

    fun uploadImage(name: String, uri: Uri, callback: (String) -> Unit) {
        FirebaseAuth.getInstance()
            .signInAnonymously()
            .addOnCompleteListener {
                val storage = Firebase.storage("gs://binit-e50ef.appspot.com")

                val ref = storage.reference.child(name)

                val baos = ByteArrayOutputStream()

                val bitmap = MediaStore.Images.Media.getBitmap(App.context.contentResolver, uri)

                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos)

                val data = baos.toByteArray()

                val uploadTask = ref.putBytes(data)

                uploadTask.addOnSuccessListener {
                    ref.downloadUrl.addOnSuccessListener { uri ->
                        callback(uri.toString())
                    }

                }.addOnFailureListener {
                    callback(it.message ?: "ERROR_IMAGE_UPLOADING")
                }


            }

    }
}