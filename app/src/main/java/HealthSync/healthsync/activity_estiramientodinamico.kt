package HealthSync.healthsync

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_estiramientodinamico : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_estiramientodinamico)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val regresarpausa = findViewById<ImageButton>(R.id.imgbuttonregresarpausa)
        val siguientepausa = findViewById<ImageView>(R.id.img_siguientePausa20)

        regresarpausa.setOnClickListener{
            val intent = Intent(this, activity_pausas::class.java)
            startActivity(intent)
        }

        siguientepausa.setOnClickListener{
            val intent = Intent(this, activity_pausa20::class.java)
            startActivity(intent)
        }

    }
}