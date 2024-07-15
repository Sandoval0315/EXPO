package HealthSync.healthsync

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Recuperacion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recuperacion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //ocultar barra de arriba
        supportActionBar?.hide()

        val btnRecuperar = findViewById<Button>(R.id.btnRecuperarC)
        val imgBack = findViewById<ImageView>(R.id.imgBack)

        imgBack.setOnClickListener{
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }


        btnRecuperar.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                enviarCorreo("20200300@ricaldone.edu.sv", "Recuperación de contraseña", "Hola")

            }
        }
    }
}