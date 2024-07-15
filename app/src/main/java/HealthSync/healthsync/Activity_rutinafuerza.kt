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

class Activity_rutinafuerza : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_rutinafuerza)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //ocultar barra de arriba
        supportActionBar?.hide()


        val imgregresarRFuerza = findViewById<ImageView>(R.id.imgregresarRFuerza)

        imgregresarRFuerza.setOnClickListener{
            val intent = Intent(this, DashboardFragment::class.java)
            startActivity(intent)
        }

        //Nos manda a la pantalla cardiovascular (Saltos continuos)
        val button = findViewById<Button>(R.id.btn_rtsFuerza)
        button.setOnClickListener {
            val intent = Intent(this, activity_tiempovascular::class.java)
            startActivity(intent)
        }
    }
}