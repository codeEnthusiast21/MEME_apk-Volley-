package com.example.volley_api

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar

import androidx.appcompat.app.AppCompatActivity

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest

import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {
    var currentImageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()

    }
    private fun loadMeme(){
        val memeImage= findViewById<ImageView>(R.id.memeimage)
        val pgbar = findViewById<ProgressBar>(R.id.pgbar)
        pgbar.visibility=View.VISIBLE

        // Instantiate the RequestQueue.
        val url = "https://meme-api.com/gimme"

// Request a string response from the provided URL.
        val JSONobjectreq= JsonObjectRequest(
            Request.Method.GET, url,null,
            Response.Listener{ response ->
                currentImageUrl= response.getString("url")

                Glide .with(this).load(currentImageUrl).listener(object:RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        pgbar.visibility=View.GONE
                    return false}

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>?,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        pgbar.visibility=View.GONE
                        return false

                    }
                }).into(memeImage)},
            Response.ErrorListener {  })

// Add the request to the RequestQueue.
MySingleton.getInstance(this).addToRequestQueue(JSONobjectreq)    }

    fun next(view: View) {
        loadMeme()
    }
    fun share(view: View) {
        val intent =Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"oye ye meme dekh!! \n $currentImageUrl")
        val chooser = Intent.createChooser(intent,"pkka share krna h?")
        startActivity(chooser)
    }
}