package com.example.registrosuperheroes

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.registrosuperheroes.databinding.ActivityDetailBinding
import com.example.registrosuperheroes.databinding.ActivityMainBinding

class DetailActivity : AppCompatActivity() {
    companion object{
        const val SUPERHERO_KEY = "superhero"
        const val BITMAP_KEY = "bitmap"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras!!
        val superhero = bundle.getParcelable<Superhero>(SUPERHERO_KEY)!!
        binding.superhero = superhero
        val bitmapDirectory = bundle.getString(BITMAP_KEY)!!

        val bitmap = BitmapFactory.decodeFile(bitmapDirectory)

        if (bitmap != null){
            binding.tomaImage.setImageBitmap(bitmap)
        }
    }
}