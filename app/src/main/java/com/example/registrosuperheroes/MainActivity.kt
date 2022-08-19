package com.example.registrosuperheroes

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import com.example.registrosuperheroes.databinding.ActivityMainBinding
import java.io.File


class MainActivity : AppCompatActivity() {
    private lateinit var superheroImage: ImageView
    private var heroBitmap: Bitmap? = null
    private var picturePath = ""

    private val getContent = registerForActivityResult(ActivityResultContracts.TakePicture()){
        success ->
        if (success && picturePath.isNotEmpty()){
            heroBitmap = BitmapFactory.decodeFile(picturePath)
            superheroImage.setImageBitmap(heroBitmap)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.bioText)

        binding.saveButton.setOnClickListener {
            val superheroName = binding.heroNameEdit.text.toString()
            val alterEgo = binding.alterEdoEdit.text.toString()
            val bio = binding.bioEdit.text.toString()
            val power = binding.powerBar.rating

            val hero = Superhero(superheroName, alterEgo, bio, power)
            openDetailActivity(hero)
        }
        superheroImage = binding.heroImage
        superheroImage.setOnClickListener {
            openCamera()
        }
    }

    private fun openCamera() {
        val file = createImageFile()
        val uri = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            FileProvider.getUriForFile(this,"$packageName.provider", file as File)
        }else{
            Uri.fromFile(file as File?)
        }
        getContent.launch(uri)
    }

    private fun createImageFile(): Any {
        val fileName = "superhero_image"
        val fileDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File.createTempFile(fileName, ".jpg", fileDirectory)
        picturePath = file.absolutePath
        return file
    }

    private fun openDetailActivity(superhero: Superhero){
        //Esto es un explicit Intent
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.SUPERHERO_KEY, superhero)
        intent.putExtra(DetailActivity.BITMAP_KEY, picturePath)

        startActivity(intent)
    }
}