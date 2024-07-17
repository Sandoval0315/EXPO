package HealthSync.healthsync

import HealthSync.healthsync.ui.dashboard.DashboardFragment
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_pausas : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pausas)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val regresar = findViewById<ImageButton>(R.id.button_regresamos)
        val siguientePantalla = findViewById<ImageView>(R.id.img_siguientePantalla)

        regresar.setOnClickListener{
            val intent = Intent(this, activity_tiempovascular::class.java)
            startActivity(intent)
        }

        //Tengo que ver como arreglo esto lo mas seguro me toque crear otras pantallas :/

        siguientePantalla.setOnClickListener{
            val intent = Intent(this, activity_estiramientodinamico::class.java)
            startActivity(intent)
        }


    }
}