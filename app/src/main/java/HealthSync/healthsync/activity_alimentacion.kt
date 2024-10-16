package HealthSync.healthsync

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_alimentacion : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_alimentacion)
        window.statusBarColor = resources.getColor(R.color.colorOnSecondary, theme)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //ocultar barra de arriba
        supportActionBar?.hide()

        val btnsalidealimen = findViewById<ImageView>(R.id.imgBackk)
        val btnmenudesayuno = findViewById<Button>(R.id.btnmenudesayuno)

        btnsalidealimen.setOnClickListener {
            val intent = Intent(this, activity_Hidra_y_Alimen::class.java)
            startActivity(intent)
        }

        btnmenudesayuno.setOnClickListener {
            val intent = Intent(this, comiedasDieta::class.java)
            startActivity(intent)
        }

    }
}