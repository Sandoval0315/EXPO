package HealthSync.healthsync

import Modelo.ClaseConexion
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import android.text.InputType
import android.os.Handler
import android.os.Looper

class registrarse : AppCompatActivity() {

    companion object {
        lateinit var nombreUsuario: String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrarse)
        window.statusBarColor = resources.getColor(R.color.colorPrimary, theme)

        // Configuración de insets para manejar la barra de sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Ocultar la barra de acción
        supportActionBar?.hide()

        // Obtener referencias a los elementos de la UI
        val txtNombre = findViewById<EditText>(R.id.txtNombre)
        val txtCorreo = findViewById<EditText>(R.id.txtCorreo)
        val txtContraseña = findViewById<EditText>(R.id.txtContraseña)
        val txtConfirmarContrasena = findViewById<EditText>(R.id.txtConfirmarContraseña)
        val btnCrearCuenta = findViewById<Button>(R.id.btnRegistrarse)
        val imgBack = findViewById<ImageView>(R.id.imgBackk)
        val imgVerContrasena = findViewById<ImageView>(R.id.imgVerContraseña)
        val imgVerConfirmarContrasena = findViewById<ImageView>(R.id.imgVerConfirmarContraseña)
        val tvSuccessMessage = findViewById<TextView>(R.id.tvSuccessMessage)

        // Configurar el listener para el botón de regresar
        imgBack.setOnClickListener {
            val intent = Intent(this, Bienvenida::class.java)
            startActivity(intent)
        }

        // Función para mostrar el botón de crear cuenta y ocultar el botón de regresar
        fun showCreateAccountButton() {
            btnCrearCuenta.visibility = View.VISIBLE
            imgBack.visibility = View.GONE
        }

        // Crear un observador para los campos de texto
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

        // Asignar el observador a los campos de texto
        txtNombre.setOnFocusChangeListener(textWatcher)
        txtCorreo.setOnFocusChangeListener(textWatcher)
        txtContraseña.setOnFocusChangeListener(textWatcher)

        // Función para encriptar la contraseña
        fun hashPassword(password: String): String {
            val bytes = password.toByteArray()
            val md = MessageDigest.getInstance("SHA-256")
            val digest = md.digest(bytes)
            return digest.fold("") { str, it -> str + "%02x".format(it) }
        }

        // Función para validar el formato del correo electrónico
        fun isValidEmail(email: String): Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        // Función para validar la fortaleza de la contraseña
        fun isValidPassword(password: String): Boolean {
            return password.length >= 8 && password.any { it.isUpperCase() } && password.any { it.isLowerCase() }
        }

        // Función para verificar si el correo ya está registrado
        suspend fun isEmailAlreadyRegistered(email: String): Boolean {
            return withContext(Dispatchers.IO) {
                val objConexion = ClaseConexion().CadenaConexion()
                val query = "SELECT COUNT(*) FROM Usuarios WHERE correo = ?"
                val statement = objConexion?.prepareStatement(query)
                statement?.setString(1, email)
                val resultSet = statement?.executeQuery()
                if (resultSet?.next() == true) {
                    resultSet.getInt(1) > 0
                } else {
                    false
                }
            }
        }

        // Función para alternar la visibilidad de la contraseña
        fun togglePasswordVisibility(editText: EditText) {
            editText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            Handler(Looper.getMainLooper()).postDelayed({
                editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }, 5000)
        }

        // Configurar listeners para los botones de ver contraseña
        imgVerContrasena.setOnClickListener {
            togglePasswordVisibility(txtContraseña)
        }

        imgVerConfirmarContrasena.setOnClickListener {
            togglePasswordVisibility(txtConfirmarContrasena)
        }

        // Configurar el listener para el botón de crear cuenta
        btnCrearCuenta.setOnClickListener {
            val nombre = txtNombre.text.toString()
            val correo = txtCorreo.text.toString()
            val contraseñaPlana = txtContraseña.text.toString()
            val confirmarContraseña = txtConfirmarContrasena.text.toString()

            // Validar los campos ingresados
            if (nombre.isEmpty() || correo.isEmpty() || contraseñaPlana.isEmpty() || confirmarContraseña.isEmpty()) {
                Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show()
            } else if (!isValidEmail(correo)) {
                Toast.makeText(this, "Ingrese un correo electrónico válido", Toast.LENGTH_SHORT).show()
            } else if (!isValidPassword(contraseñaPlana)) {
                Toast.makeText(this, "Contraseña insegura: debe tener al menos 8 caracteres, una mayúscula y una minúscula", Toast.LENGTH_SHORT).show()
            } else if (contraseñaPlana != confirmarContraseña) {
                Toast.makeText(this, "Ingrese correctamente la contraseña", Toast.LENGTH_SHORT).show()
            } else {
                // Iniciar proceso de registro
                GlobalScope.launch(Dispatchers.Main) {
                    if (isEmailAlreadyRegistered(correo)) {
                        Toast.makeText(this@registrarse, "Correo inválido: ya está en uso", Toast.LENGTH_SHORT).show()
                    } else {
                        val contraseñaEncriptada = hashPassword(contraseñaPlana)
                        withContext(Dispatchers.IO) {
                            val objConexion = ClaseConexion().CadenaConexion()
                            // Modificación: Agregar idRol con valor fijo 4
                            val addNombre = objConexion?.prepareStatement("INSERT INTO Usuarios (correo, clave, nombre, idRol) VALUES (?, ?, ?, 4)")
                            addNombre?.setString(1, correo)
                            addNombre?.setString(2, contraseñaEncriptada)
                            addNombre?.setString(3, nombre)
                            addNombre?.executeUpdate()
                        }

                        // Guardar el nombre en el companion object
                        nombreUsuario = nombre

                        // Mostrar mensaje de éxito y limpiar campos
                        Toast.makeText(this@registrarse, "Usuario registrado", Toast.LENGTH_SHORT).show()
                        txtNombre.setText("")
                        txtCorreo.setText("")
                        txtContraseña.setText("")
                        txtConfirmarContrasena.setText("")
                        btnCrearCuenta.visibility = View.GONE
                        imgBack.visibility = View.VISIBLE

                        // Mostrar mensaje de éxito con animación
                        val fadeInOut = AnimationUtils.loadAnimation(this@registrarse, R.anim.fade_in_out)
                        tvSuccessMessage.visibility = View.VISIBLE
                        tvSuccessMessage.startAnimation(fadeInOut)
                        fadeInOut.setAnimationListener(object : Animation.AnimationListener {
                            override fun onAnimationStart(animation: Animation?) {
                                // No hacer nada
                            }

                            override fun onAnimationEnd(animation: Animation?) {
                                // Ocultar el mensaje después de la animación
                                tvSuccessMessage.visibility = View.GONE
                            }

                            override fun onAnimationRepeat(animation: Animation?) {
                                // No hacer nada
                            }
                        })
                    }
                }
            }
        }
    }
}