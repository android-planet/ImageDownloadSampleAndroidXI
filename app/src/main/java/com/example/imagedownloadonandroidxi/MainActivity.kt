package com.example.imagedownloadonandroidxi

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy

class MainActivity : AppCompatActivity() {
    var imagUrl: String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val imgSample: AppCompatImageView = findViewById(R.id.imgSample)
        val btnDownloadCertificates: AppCompatButton = findViewById(R.id.btnDownload)

        imagUrl = "https://sample-videos.com/img/Sample-jpg-image-50kb.jpg"

        Glide.with(this)
            .load(imagUrl)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .priority(Priority.IMMEDIATE)
            .into(imgSample);

        btnDownloadCertificates!!.setOnClickListener {
            //  downloadCertificates()
            downloadImageToDownloadFolder()
        }
    }

    private fun downloadImageToDownloadFolder() {

        var downloadFileName: String? = ""
        if (imagUrl!!.contains(".pdf")) {
            downloadFileName = "Sample-jpg-image" + ".pdf"
        } else if (imagUrl!!.contains(".jpg")) {
            downloadFileName = "Sample-jpg-image" + ".jpg"
        } else if (imagUrl!!.contains(".jpeg")) {
            downloadFileName = "Sample-jpg-image" + ".jpeg"
        }


        val mgr = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val downloadUri = Uri.parse(imagUrl)
        val request = DownloadManager.Request(downloadUri)
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setAllowedOverRoaming(false).setTitle(downloadFileName)
            .setDescription("Download Image")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                "certificate_" + downloadFileName
            )

        Toast.makeText(
            applicationContext,
            "Download successfully to Download folder",
            Toast.LENGTH_LONG
        ).show()

        mgr.enqueue(request)

    }
}