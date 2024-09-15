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
    companion object {
        var codigoVer: String = ""
        var correo : String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recuperacion)
        window.statusBarColor = resources.getColor(R.color.colorPrimary, theme)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Ocultar barra de arriba
        supportActionBar?.hide()

        val btnRecuperar = findViewById<Button>(R.id.btnRecuperarC)
        val imgBack = findViewById<ImageView>(R.id.imgVolveralPerfil)
        val txtCorreoR = findViewById<EditText>(R.id.txtCorreoR)

        imgBack.setOnClickListener {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }

        btnRecuperar.setOnClickListener {
            correo = txtCorreoR.text.toString().trim()

            if (correo.isNotEmpty()) {
                codigoVer = generarCodigoVerificacion()
                val mensaje = generarMensajeHtml(codigoVer)

                CoroutineScope(Dispatchers.Main).launch {
                    try {
                        withContext(Dispatchers.IO) {
                            enviarCorreo(correo, "Recuperación de contraseña", mensaje)
                        }
                        Toast.makeText(this@Recuperacion, "Correo enviado satisfactoriamente", Toast.LENGTH_SHORT).show()
                        txtCorreoR.text.clear()  // Limpiar el campo de correo
                        // Redirigir a la actividad de código de verificación
                    } catch (e: Exception) {
                        Toast.makeText(this@Recuperacion, "No se pudo enviar el correo", Toast.LENGTH_SHORT).show()
                    }
                    val intent = Intent(this@Recuperacion, codigoVerificacion::class.java)
                    startActivity(intent)
                }
            } else {
                Toast.makeText(this@Recuperacion, "Por favor, ingrese un correo electrónico", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun generarCodigoVerificacion(): String {
        val caracteres = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        return (1..5).map { caracteres.random() }.joinToString("")
    }

    private fun generarMensajeHtml(codigo: String): String {
        return """
            <html>
            <body style="font-family: Arial, sans-serif; background-color: #f4f4f9; margin: 0; padding: 0;">
                <div style="max-width: 600px; margin: auto; padding: 20px; background-color: #ffffff; border-radius: 10px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);">
                    <h1 style="color: #6a1b9a; text-align: center;">HealthSync</h1>
                    <h2 style="color: #6a1b9a; text-align: center;">Recuperación de Contraseña</h2>
                    <p style="color: #333333;">Hola,</p>
                    <p style="color: #333333;">Has solicitado la recuperación de tu contraseña. Utiliza el siguiente código de verificación:</p>
                    <div style="background-color: #6a1b9a; color: #ffffff; padding: 20px; text-align: center; border-radius: 5px;">
                        <h2>$codigo</h2>
                    </div>
                    <p style="color: #333333;">Por favor, ingresa este código en la aplicación para continuar con el proceso de recuperación.</p>
                    <p style="color: #333333;">Saludos,</p>
                    <p style="color: #333333;">El equipo de HealthSync</p>
                </div>
            </body>
            </html>
        """.trimIndent()
    }
}