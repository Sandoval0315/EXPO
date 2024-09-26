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

class activity_yoga : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_yoga)
        window.statusBarColor = resources.getColor(R.color.colorPrimaryVariant, theme)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.hide()

        val imgregresarRYoga = findViewById<ImageView>(R.id.imgBackk)
        val siguiente = findViewById<Button>(R.id.btnparamientras)

        imgregresarRYoga.setOnClickListener{
            val intent = Intent(this, navigatioPrincipal::class.java)
            intent.putExtra("ir_a_agregar_rutinas", true)
            startActivity(intent)
        }

        siguiente.setOnClickListener{
            val intent = Intent(this, activity_yoga1::class.java)
            startActivity(intent)
        }


    }
}