package HealthSync.healthsync

import Modelo.ClaseConexion
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
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
        window.statusBarColor = resources.getColor(R.color.colorOnSecondary, theme)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val txtCorreoR = findViewById<EditText>(R.id.txtCorreoR)
        val imgBack = findViewById<ImageView>(R.id.imgBackk)

// Ocultar barra de arriba
        supportActionBar?.hide()

        imgBack.setOnClickListener {
            val intent = Intent(this, Recuperacion::class.java)
            startActivity(intent)
        }
        fun hashPassword(password: String): String {
            val bytes = password.toByteArray()
            val md = MessageDigest.getInstance("SHA-256")
            val digest = md.digest(bytes)
            return digest.fold("") { str, it -> str + "%02x".format(it) }
        }
        val btnRecuperar = findViewById<Button>(R.id.btnRecuperarC)
        btnRecuperar.setOnClickListener {
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

            txtCorreoR.setText("")
        }
    }
}