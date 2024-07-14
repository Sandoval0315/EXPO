package HealthSync.healthsync

import HealthSync.healthsync.ui.dashboard.DashboardFragment
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_altaintensidad : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_altaintensidad)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val imgregresarRIntensidad = findViewById<ImageView>(R.id.imgregresarRIntensidad)

        imgregresarRIntensidad.setOnClickListener{
            val intent = Intent(this, DashboardFragment::class.java)
            startActivity(intent)
        }
    }
}