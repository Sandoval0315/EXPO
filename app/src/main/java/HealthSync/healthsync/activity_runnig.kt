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

class activity_runnig : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_runnig)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.hide()

        val imgregresarRRunning = findViewById<ImageView>(R.id.imgregresarRRunning)

        imgregresarRRunning.setOnClickListener{
            val intent = Intent(this, navigatioPrincipal::class.java)
            intent.putExtra("ir_a_agregar_rutinas", true)
            startActivity(intent)
        }

        //Nos manda a la pantalla de ritmo lento (Caminar con ritmo lento)
        val button = findViewById<Button>(R.id.btn_runnigrts)
        button.setOnClickListener {
            val intent = Intent(this, activity_ritmolento::class.java)
            startActivity(intent)
        }
    }
}