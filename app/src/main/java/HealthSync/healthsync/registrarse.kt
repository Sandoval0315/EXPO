package HealthSync.healthsync

import Modelo.ClaseConexion
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
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
import java.security.MessageDigest
import java.util.UUID

class registrarse : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrarse)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //ocultar barra de arriba
        supportActionBar?.hide()

        val txtNombre = findViewById<EditText>(R.id.txtNombre)
        val txtCorreo = findViewById<EditText>(R.id.txtCorreo)
        val txtContraseña = findViewById<EditText>(R.id.txtContraseña)
        val btnCrearCuenta = findViewById<Button>(R.id.btnAcceder)
        val btnIrAlLogin = findViewById<Button>(R.id.btnIrAlLogin)
        val imgBack = findViewById<ImageView>(R.id.imgBack)

        imgBack.setOnClickListener{
            val intent = Intent(this, Bienvenida::class.java)
            startActivity(intent)
        }

        btnIrAlLogin.setOnClickListener {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }

        //codigo para quitar el boton de crear cuenta luego dde crearla, para que salga el de ir al inicio de sesion
        fun showCreateAccountButton() {
            btnCrearCuenta.visibility = View.VISIBLE
            btnIrAlLogin.visibility = View.GONE
        }

        // esto sirve para cuando crea una cuenta, al tocar algun edit text se ponga el boton de crear cuenta en caso de que quiera crear otra
        val textWatcher = object : View.OnFocusChangeListener, View.OnClickListener {
            override fun onClick(v: View?) {
                showCreateAccountButton()
            }

            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if (hasFocus) {
                    showCreateAccountButton()
                }
            }
        }
        txtNombre.setOnFocusChangeListener(textWatcher)
        txtCorreo.setOnFocusChangeListener(textWatcher)
        txtContraseña.setOnFocusChangeListener(textWatcher)


        //Escriptacion de la contraseña
        fun hashPassword(password: String): String {
            val bytes = password.toByteArray()
            val md = MessageDigest.getInstance("SHA-256")
            val digest = md.digest(bytes)
            return digest.fold("") { str, it -> str + "%02x".format(it) }
        }



        btnCrearCuenta.setOnClickListener {
            val nombre = txtNombre.text.toString()
            val correo = txtCorreo.text.toString()
            //contraseña ya encriptada
            val contraseña = hashPassword(txtContraseña.text.toString())


            if (nombre.isEmpty() || correo.isEmpty() || contraseña.isEmpty()) {
                // mostrar error en caso de querer crear cuenta con campos vacios
                val toast = Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_SHORT)
                toast.show()
                Handler(Looper.getMainLooper()).postDelayed({ toast.cancel() }, 3000)
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    val objConexion = ClaseConexion().CadenaConexion()

                    // Insertar datos en la tabla
                    val addNombre = objConexion?.prepareStatement("insert into Usuarios (uuid, nombre, correo, contraseña) values(?,?,?,?)")!!
                    addNombre.setString(1, UUID.randomUUID().toString())
                    addNombre.setString(2, nombre)
                    addNombre.setString(3, correo)
                    addNombre.setString(4, contraseña)
                    addNombre.executeUpdate()

                    runOnUiThread {
                        // limpiar campos al hacer clic
                        txtNombre.setText("")
                        txtCorreo.setText("")
                        txtContraseña.setText("")

                        // quitar botón de crear cuenta y poner botón para que lo mande al iniciar sesión
                        btnCrearCuenta.visibility = View.GONE
                        btnIrAlLogin.visibility = View.VISIBLE
                    }
                }
            }

        }

 
    }
}
