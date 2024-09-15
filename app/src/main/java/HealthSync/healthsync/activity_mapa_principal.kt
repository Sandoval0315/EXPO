package HealthSync.healthsync

import HealthSync.healthsync.ui.dashboard.DashboardFragment
import HealthSync.healthsync.ui.home.HomeFragment
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_mapa_principal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mapa_principal)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //ocultar barra de arriba
        supportActionBar?.hide()

        val img_RegresMap = findViewById<ImageView>(R.id.imgBackk)
        
        val img_IrMap = findViewById<ImageView>(R.id.img_IrMap)

        img_RegresMap.setOnClickListener(){
            val intent = Intent(this, navigatioPrincipal::class.java)
            startActivity(intent)
        }

        img_IrMap.setOnClickListener(){
            val intent = Intent(this, activity_mapa ::class.java)
            startActivity(intent)
        }
    }
}