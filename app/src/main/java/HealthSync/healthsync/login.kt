package HealthSync.healthsync
import Modelo.ClaseConexion
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.*
import java.security.MessageDigest
class login : AppCompatActivity() {
    companion object {
        lateinit var userEmail: String
    }
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
        val txtClave = findViewById<EditText>(R.id.txtClave)
        val btnAcceder = findViewById<Button>(R.id.btnAcceder)
        val imgBack = findViewById<ImageView>(R.id.imgBack)
        val lbRecuperarC = findViewById<TextView>(R.id.lbRecuperarC)

        lbRecuperarC.setOnClickListener {
            val intent = Intent(this, Recuperacion::class.java)
            startActivity(intent)
        }

        imgBack.setOnClickListener {
            val intent = Intent(this, Bienvenida::class.java)
            startActivity(intent)
        }

        btnAcceder.setOnClickListener {
            val txtCorreo = txtCorreo.text.toString()
            val txtContraseña = hashPassword(txtClave.text.toString())

            if (txtCorreo.isEmpty() || txtContraseña.isEmpty()) {
                Toast.makeText(this, "Campos incompletos", Toast.LENGTH_SHORT).show()
            } else {
                // Store the email in the companion object
                userEmail = txtCorreo

                GlobalScope.launch(Dispatchers.IO) {
                    val objConexion = ClaseConexion().CadenaConexion()

                    // Verificar las credenciales en la tabla Usuarios
                    val queryUsuarios = "SELECT * FROM Usuarios WHERE correo = ? AND clave = ?"
                    val stmtUsuarios = objConexion?.prepareStatement(queryUsuarios)
                    stmtUsuarios?.setString(1, txtCorreo)
                    stmtUsuarios?.setString(2, txtContraseña)
                    val resultadoUsuarios = stmtUsuarios?.executeQuery()

                    if (resultadoUsuarios?.next() == true) {
                        // Si las credenciales son correctas, verificar si existe un registro en Cliente
                        val queryCliente = "SELECT * FROM Cliente WHERE idUsuario = ?"
                        val stmtCliente = objConexion?.prepareStatement(queryCliente)
                        stmtCliente?.setInt(1, resultadoUsuarios.getInt("idUsuario"))
                        val resultadoCliente = stmtCliente?.executeQuery()

                        if (resultadoCliente?.next() == true) {
                            // Si existe un registro en Cliente, navegar a NavigationPrincipal
                            withContext(Dispatchers.Main) {
                                val intent = Intent(this@login, navigatioPrincipal::class.java)
                                startActivity(intent)
                            }
                        } else {
                            // Si no existe registro en Cliente, continuar con el flujo normal
                            withContext(Dispatchers.Main) {
                                val intent = Intent(this@login, LoadingActivity2::class.java)
                                startActivity(intent)
                            }
                        }
                    } else {
                        // Credenciales incorrectas
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@login, "La contraseña o el correo son incorrectos",
                                Toast.LENGTH_SHORT).show()
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