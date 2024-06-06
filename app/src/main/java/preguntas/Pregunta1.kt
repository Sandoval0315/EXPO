package preguntas

import HealthSync.healthsync.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Pregunta1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pregunta1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val imgHombre = findViewById<ImageView>(R.id.imgHombre)
        val imgMujer = findViewById<ImageView>(R.id.imgMujer)
        val btnSiguiente = findViewById<Button>(R.id.btnSiguiente)

        btnSiguiente.visibility = View.GONE

        imgHombre.setOnClickListener {
            btnSiguiente.visibility = View.VISIBLE
        }

        imgMujer.setOnClickListener {
            btnSiguiente.visibility = View.VISIBLE
        }

        btnSiguiente.setOnClickListener {
            val intent = Intent(this, Pregunta2::class.java)
            startActivity(intent)
        }



    }
}