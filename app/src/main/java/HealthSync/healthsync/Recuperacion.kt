package HealthSync.healthsync

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Recuperacion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recuperacion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //ocultar barra de arriba
        supportActionBar?.hide()

        val btnRecuperar = findViewById<Button>(R.id.btnRecuperarC)
        val imgBack = findViewById<ImageView>(R.id.imgBack)
        val txtCorreoR = findViewById<EditText>(R.id.txtCorreoR)

        imgBack.setOnClickListener {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }

        btnRecuperar.setOnClickListener {
            val correo = txtCorreoR.text.toString().trim()

            if (correo.isNotEmpty()) {
                CoroutineScope(Dispatchers.Main).launch {
                    try {
                        withContext(Dispatchers.IO) {
                            EnvioDeCorreo("${txtCorreoR.text}", "Recuperación de contraseña", "Hola")
                        }
                        Toast.makeText(this@Recuperacion, "Correo enviado satisfactoriamente", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Toast.makeText(this@Recuperacion, "No se pudo enviar el correo", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this@Recuperacion, "Por favor, ingrese un correo electrónico", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
