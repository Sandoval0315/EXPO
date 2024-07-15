package HealthSync.healthsync

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_alimentacion : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_alimentacion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //ocultar barra de arriba
        supportActionBar?.hide()

        val btnsalidealimen = findViewById<ImageButton>(R.id.btnsalirdealimen)
        val btnmenudesayuno = findViewById<Button>(R.id.btnmenudesayuno)

        btnsalidealimen.setOnClickListener {
            val Intent = Intent(this, activity_Hidra_y_Alimen::class.java)
        }

        btnmenudesayuno.setOnClickListener {
            val Intent = Intent(this, activity_desayunar::class.java)
        }

    }
}