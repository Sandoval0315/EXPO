package HealthSync.healthsync

import HealthSync.healthsync.ConfirmarPreguntas
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import HealthSync.healthsync.R
import android.content.Intent
import android.widget.Button

class pregunta7 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pregunta7)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //ocultar barra de arriba
        supportActionBar?.hide()

        val btnSiguiente = findViewById<Button>(R.id.btnSiguiente)
        val btnAtras = findViewById<Button>(R.id.btnAtras)

        btnSiguiente.setOnClickListener {
            val intent = Intent(this, ConfirmarPreguntas::class.java)
            startActivity(intent)
        }

        btnAtras.setOnClickListener {
            val intent = Intent(this, pregunta6::class.java)
            startActivity(intent)
        }
    }
}