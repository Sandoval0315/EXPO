package HealthSync.healthsync

import Modelo.ClaseConexion
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.security.MessageDigest

class cambiodeclave : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cambiodeclave)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val txtCorreoR = findViewById<EditText>(R.id.txtCorreoR)
        val btnVerificarCode = findViewById<Button>(R.id.btnVerificarCode)

// Ocultar barra de arriba
        supportActionBar?.hide()

        fun hashPassword(password: String): String {
            val bytes = password.toByteArray()
            val md = MessageDigest.getInstance("SHA-256")
            val digest = md.digest(bytes)
            return digest.fold("") { str, it -> str + "%02x".format(it) }
        }


        btnVerificarCode.setOnClickListener {
            val contraseña = txtCorreoR.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                val objConexion = ClaseConexion().CadenaConexion()
                val updateClave = objConexion?.prepareStatement("update Usuarios set clave = ? where correo = ?")!!
                updateClave.setString(1, hashPassword(contraseña))
                updateClave.setString(2, Recuperacion.correo)
                updateClave.executeUpdate()
                val commit = objConexion.prepareStatement("commit")!!
                commit.executeUpdate()
            }
        }
    }
}