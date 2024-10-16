package HealthSync.healthsync

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import HealthSync.healthsync.pregunta2.Companion.edadSeleccionada
import HealthSync.healthsync.pregunta3.Companion.estaturaSeleccionada
import HealthSync.healthsync.pregunta4.Companion.pesoSeleccionado
import HealthSync.healthsync.pregunta5.Companion.enfermedadSeleccionada
import Modelo.ClaseConexion
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.pow

class pregunta6 : AppCompatActivity() {
    companion object {
        lateinit var experienciaSeleccionada: String
        lateinit var IMCtotal: String
    }

    private lateinit var btnSiguiente: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pregunta6)
        window.statusBarColor = resources.getColor(R.color.colorOnSecondary, theme)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Ocultar barra de arriba
        supportActionBar?.hide()

        btnSiguiente = findViewById(R.id.btnSiguiente)
        val btnAtras = findViewById<Button>(R.id.btnAtras)
        val txtExperiencia = findViewById<EditText>(R.id.txtExperiencia)

        // Inicialmente ocultar el botón
        btnSiguiente.visibility = View.GONE

        // Agregar un TextWatcher para controlar la entrada de texto
        txtExperiencia.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val experiencia = s.toString().toIntOrNull()

                when {
                    s.isNullOrEmpty() -> {
                        btnSiguiente.visibility = View.GONE
                    }
                    experiencia in 1..10 -> {
                        btnSiguiente.visibility = View.VISIBLE
                        experienciaSeleccionada = experiencia.toString()
                    }
                    else -> {
                        Toast.makeText(this@pregunta6, "Ingresa un nivel de experiencia válido", Toast.LENGTH_SHORT).show()
                        btnSiguiente.visibility = View.GONE
                    }
                }
            }
        })

        btnSiguiente.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val objConexion = ClaseConexion().CadenaConexion()

                // Usar el procedimiento almacenado para insertar la experiencia
                val nivelExperiencia = experienciaSeleccionada.toIntOrNull() ?: 1
                val callProcedure = objConexion?.prepareCall("{call sp_InsertarExperiencia(?)}")
                callProcedure?.setInt(1, nivelExperiencia)
                callProcedure?.execute()

                // Obtener el último idExperiencia insertado
                val obtenerIdExperiencia = objConexion?.prepareStatement("SELECT MAX(idExperiencia) FROM Experiencia")
                val resultSetExperiencia = obtenerIdExperiencia?.executeQuery()
                var idExperiencia: Int = -1
                if (resultSetExperiencia?.next() == true) {
                    idExperiencia = resultSetExperiencia.getInt(1)
                }

                // Obtener el último idUsuario insertado usando ROWNUM
                // Esta es la consulta corregida que obtiene el último usuario insertado basándose en el ID más alto
                val obtenerIdUsuario = objConexion?.prepareStatement(
                    "SELECT idUsuario FROM Usuarios WHERE idUsuario = (SELECT MAX(idUsuario) FROM Usuarios)"
                )
                val resultSetUsuario = obtenerIdUsuario?.executeQuery()
                var idUsuario: Int = -1
                if (resultSetUsuario?.next() == true) {
                    idUsuario = resultSetUsuario.getInt(1)
                }

                // Calcular el IMC
                val peso = pesoSeleccionado.toFloat()
                val estatura = estaturaSeleccionada.toFloat() / 100
                val imc = peso / (estatura.pow(2))
                IMCtotal = String.format("%.2f", imc)

                // Insertar en la tabla Cliente
                val insertarCliente = objConexion?.prepareStatement(
                    "INSERT INTO Cliente (edad, altura, peso, imc, padecimiento, idExperiencia, idUsuario) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)"
                )
                insertarCliente?.setInt(1, edadSeleccionada.toInt())
                insertarCliente?.setFloat(2, estaturaSeleccionada.toFloat())
                insertarCliente?.setFloat(3, pesoSeleccionado.toFloat())
                insertarCliente?.setFloat(4, IMCtotal.toFloat())
                insertarCliente?.setString(5, enfermedadSeleccionada)
                insertarCliente?.setInt(6, idExperiencia)
                insertarCliente?.setInt(7, idUsuario)
                insertarCliente?.executeUpdate()

                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@pregunta6,
                        "Bienvenido a HealthSync",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this@pregunta6, navigatioPrincipal::class.java)
                    startActivity(intent)
                }
            }
        }

        btnAtras.setOnClickListener {
            val intent = Intent(this, pregunta5::class.java)
            startActivity(intent)
        }
    }
}