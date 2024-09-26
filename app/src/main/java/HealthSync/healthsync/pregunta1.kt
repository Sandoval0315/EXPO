package HealthSync.healthsync

import HealthSync.healthsync.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class pregunta1 : AppCompatActivity() {

    companion object {
        lateinit var generoSeleccionado: String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pregunta1)
        window.statusBarColor = resources.getColor(R.color.colorOnSecondary, theme)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //ocultar barra de arriba
        supportActionBar?.hide()
        val imgHombre = findViewById<ImageView>(R.id.imgHombre)
        val imgMujer = findViewById<ImageView>(R.id.imgMujer)
        val btnSiguiente = findViewById<Button>(R.id.btnSiguiente)
        btnSiguiente.visibility = View.GONE

        imgHombre.setOnClickListener {
            generoSeleccionado = "Hombre"
            btnSiguiente.visibility = View.VISIBLE
        }

        imgMujer.setOnClickListener {
            generoSeleccionado = "Mujer"
            btnSiguiente.visibility = View.VISIBLE
        }

        btnSiguiente.setOnClickListener {
            val intent = Intent(this, pregunta2::class.java)
            startActivity(intent)
        }
    }
}