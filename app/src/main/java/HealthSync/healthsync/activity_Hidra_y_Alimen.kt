package HealthSync.healthsync

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_Hidra_y_Alimen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_hidra_yalimen)
        window.statusBarColor = resources.getColor(R.color.colorPrimaryVariant, theme)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //ocultar barra de arriba
        supportActionBar?.hide()

        val btn_hidratacion = findViewById<Button>(R.id.btnhidratacion)
        val btn_alimen = findViewById<Button>(R.id.btnalimentacion)
        val btn_regresar = findViewById<ImageView>(R.id.imgBackk)

        btn_hidratacion.setOnClickListener {
            val intent = Intent(this, activity_hidratacion::class.java)
            startActivity(intent)
        }

        btn_alimen.setOnClickListener {
            val intent = Intent(this, activity_alimentacion::class.java)
            startActivity(intent)
        }

        btn_regresar.setOnClickListener {
            val intent = Intent(this, navigatioPrincipal::class.java)
            startActivity(intent)
        }

    }
}