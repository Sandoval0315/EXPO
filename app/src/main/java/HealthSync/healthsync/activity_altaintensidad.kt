package HealthSync.healthsync

import HealthSync.healthsync.ui.dashboard.DashboardFragment
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_altaintensidad : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_altaintensidad)
        window.statusBarColor = resources.getColor(R.color.colorPrimaryVariant, theme)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.hide()

        val imgregresarRIntensidad = findViewById<ImageView>(R.id.imgregresarRIntensidad)

        imgregresarRIntensidad.setOnClickListener{
            val intent = Intent(this, navigatioPrincipal::class.java)
            intent.putExtra("ir_a_agregar_rutinas", true)
            startActivity(intent)
        }

        val button = findViewById<Button>(R.id.btn_rstintensidad)
        button.setOnClickListener {
            val intent = Intent(this, activity_intensisdad1::class.java)
            startActivity(intent)
        }
    }
}