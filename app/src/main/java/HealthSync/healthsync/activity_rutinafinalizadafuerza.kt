package HealthSync.healthsync

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_rutinafinalizadafuerza : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_rutinafinalizadafuerza)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.hide()

        val btnFinFuerza = findViewById<Button>(R.id.btnFinalFuerza)

        btnFinFuerza.setOnClickListener{
            val intent = Intent(this, navigatioPrincipal::class.java)
            intent.putExtra("ir_a_agregar_rutinas", true)
            startActivity(intent)
        }
    }
}