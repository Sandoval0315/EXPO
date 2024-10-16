package HealthSync.healthsync

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_tiempovascular17 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tiempovascular17)
        window.statusBarColor = resources.getColor(R.color.colorOnSecondary, theme)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.hide()

        //Nos manda al inicio de la rutina (Boton para atras)
        val regresaraInicio = findViewById<ImageView>(R.id.imgBackk)

        regresaraInicio.setOnClickListener{
            val intent = Intent(this, activity_rutina_cardiovascular::class.java)
            startActivity(intent)
        }


        val btnT17 = findViewById<Button>(R.id.btnT17)

        btnT17.setOnClickListener{
            val intent = Intent(this@activity_tiempovascular17, activity_tomadescaso2::class.java)
            intent.putExtra("identificador", "tiempo17")
            startActivity(intent)
            finish()
        }
    }
}