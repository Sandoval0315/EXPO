package HealthSync.healthsync

import HealthSync.healthsync.ui.dashboard.DashboardFragment
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController

class activity_rutina_cardiovascular : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_rutina_cardiovascular)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //ocultar barra de arriba
        supportActionBar?.hide()

        val imgregresarRCardiovascular = findViewById<ImageView>(R.id.imgregresarRCardiovascular)
        val btnempezarRC = findViewById<Button>(R.id.btnempezarRC)


        imgregresarRCardiovascular.setOnClickListener{
            val intent = Intent(this, navigatioPrincipal::class.java)
            intent.putExtra("ir_a_agregar_rutinas", true)
            startActivity(intent)
        }

        btnempezarRC.setOnClickListener{
            val intent = Intent(this, activity_tiempovascular::class.java)
            startActivity(intent)


        }

    }
}