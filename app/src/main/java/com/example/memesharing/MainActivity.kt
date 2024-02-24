package com.example.memesharing

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.memesharing.databinding.ActivityMainBinding
import android.view.View
import com.android.volley.Response.Listener
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import java.util.Objects


class MainActivity : AppCompatActivity() {
    var currentImageurl: String? = null
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        loadMeme()
    }


     private fun loadMeme() {
        binding.processing.visibility = View.VISIBLE
        currentImageurl  = "https://meme-api.com/gimme"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, currentImageurl , null,
            { response ->
                currentImageurl = response.getString("url")
                Glide.with(this).load(currentImageurl).listener(object : RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.processing.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.processing.visibility = View.GONE
                        return false
                    }

                }).into(binding.memeImageView)


            },
            { error ->
                Toast.makeText(this,"Something went wrong ", Toast.LENGTH_LONG).show()
            }
        )

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }

    fun shareMeme(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey , checkout this $currentImageurl")
        val chooser = Intent.createChooser(intent,"Share this meme using....")
        startActivity(chooser)

    }
    fun nextMeme(view: View) {
        loadMeme()

    }


}