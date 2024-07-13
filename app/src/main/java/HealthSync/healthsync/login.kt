package HealthSync.healthsync

import Modelo.ClaseConexion
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
import java.security.MessageDigest

class login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//ocultar barra de arriba
        supportActionBar?.hide()
        val txtCorreo = findViewById<EditText>(R.id.txtCorreo)
        val txtContraseña = findViewById<EditText>(R.id.txtContraseña)
        val btnAcceder = findViewById<Button>(R.id.btnAcceder)
        val imgBack = findViewById<ImageView>(R.id.imgBack1)

        imgBack.setOnClickListener{
            val intent = Intent(this, Bienvenida::class.java)
            startActivity(intent)
        }

        btnAcceder.setOnClickListener {
            val txtCorreo = txtCorreo.text.toString()
            val txtContraseña = hashPassword(txtContraseña.text.toString())

            if (txtCorreo.isEmpty() || txtContraseña.isEmpty()) {
                Toast.makeText(this, "Campos incompletos", Toast.LENGTH_SHORT).show()
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    val objConexion = ClaseConexion().CadenaConexion()

                    val addAcceder = "select * from Usuarios where correo = ? AND contraseña = ?"
                    val objAcceder = objConexion?.prepareStatement(addAcceder)
                    objAcceder?.setString(1, txtCorreo)
                    objAcceder?.setString(2, txtContraseña)

                    val resultado = objAcceder?.executeQuery()

                    if (resultado?.next() == true) {
                        withContext(Dispatchers.IO) {
                            // si esta correctas las credenciales pasa al main activity(inicio)
                            val intent = Intent(this@login, LoadingActivity2::class.java)
                            startActivity(intent)
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            // mensaje de error en credenciales incorrectas
                            Toast.makeText(this@login, "La contraseña o el correo son incorrectos", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    // Método para hashear la contraseña con SHA-256
    private fun hashPassword(password: String): String {
        val bytes = password.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("", { str, it -> str + "%02x".format(it) })
    }
}
