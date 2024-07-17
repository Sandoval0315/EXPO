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

class activity_pausa20 : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pausa20)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val regresar = findViewById<ImageButton>(R.id.img_siguientePausa20)
        val finRutina = findViewById<ImageView>(R.id.img_finRutina)

        regresar.setOnClickListener{
            val intent = Intent(this, activity_pausa20::class.java)
            startActivity(intent)
        }

        finRutina.setOnClickListener{
            val intent = Intent(this, activity_rutinafinalizada::class.java)
            startActivity(intent)
        }


    }
}