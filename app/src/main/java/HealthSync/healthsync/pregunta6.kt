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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //ocultar barra de arriba
        supportActionBar?.hide()

        btnSiguiente = findViewById(R.id.btnSiguiente)
        val btnAtras = findViewById<Button>(R.id.btnAtras)
        val txtExperiencia = findViewById<EditText>(R.id.txtExperiencia)

        // Inicialmente ocultar el botón
        btnSiguiente.visibility = View.GONE

        txtExperiencia.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                experienciaSeleccionada = s.toString()
                // Mostrar el botón solo si hay texto
                btnSiguiente.visibility = if (s.isNullOrEmpty()) View.GONE else View.VISIBLE
            }
        })

        btnSiguiente.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val objConexion = ClaseConexion().CadenaConexion()

                // Insertar en la tabla Experiencia
                val insertarExperiencia = objConexion?.prepareStatement("INSERT INTO Experiencia (experiencia) VALUES (?)")!!
                insertarExperiencia.setString(1, experienciaSeleccionada)
                insertarExperiencia.executeUpdate()

                // Obtener el último idExperiencia insertado
                val obtenerIdExperiencia = objConexion.prepareStatement("SELECT MAX(idExperiencia) FROM Experiencia")
                val resultSetExperiencia = obtenerIdExperiencia.executeQuery()
                var idExperiencia: Int = -1
                if (resultSetExperiencia.next()) {
                    idExperiencia = resultSetExperiencia.getInt(1)
                }

                // Obtener el último idUsuario a partir del correo más reciente
                val obtenerIdUsuario = objConexion.prepareStatement("SELECT idUsuario FROM Usuarios WHERE correo = (SELECT MAX(correo) FROM Usuarios)")
                val resultSetUsuario = obtenerIdUsuario.executeQuery()
                var idUsuario: Int = -1
                if (resultSetUsuario.next()) {
                    idUsuario = resultSetUsuario.getInt(1)
                }

                // Calcular el IMC
                val peso = pesoSeleccionado.toFloat()
                val estatura = estaturaSeleccionada.toFloat() / 100 // Convertir cm a m
                val imc = peso / (estatura.pow(2))
                IMCtotal = String.format("%.2f", imc) // Guardar el IMC con 2 decimales

                // Insertar en la tabla Cliente
                val insertarCliente = objConexion.prepareStatement("INSERT INTO Cliente (edad, altura, peso, imc, padecimiento, idExperiencia, idUsuario) VALUES (?, ?, ?, ?, ?, ?, ?)")
                insertarCliente.setString(1, edadSeleccionada)
                insertarCliente.setString(2, estaturaSeleccionada)
                insertarCliente.setString(3, pesoSeleccionado)
                insertarCliente.setString(4, IMCtotal)
                insertarCliente.setString(5, enfermedadSeleccionada)
                insertarCliente.setInt(6, idExperiencia)
                insertarCliente.setInt(7, idUsuario)
                insertarCliente.executeUpdate()

                withContext(Dispatchers.Main) {
                    Toast.makeText(this@pregunta6, "Bienvenido a HealthSync", Toast.LENGTH_SHORT).show()
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