package HealthSync.healthsync

import HealthSync.healthsync.ConfirmarPreguntas
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import HealthSync.healthsync.R
import HealthSync.healthsync.pregunta2.Companion.edadSeleccionada
import HealthSync.healthsync.pregunta3.Companion.estaturaSeleccionada
import HealthSync.healthsync.pregunta4.Companion.pesoSeleccionado
import HealthSync.healthsync.pregunta5.Companion.enfermedadSeleccionada
import HealthSync.healthsync.pregunta6.Companion.imcSeleccionado
import Modelo.ClaseConexion
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class pregunta7 : AppCompatActivity() {

    companion object {
        lateinit var experienciaSeleccionada: String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pregunta7)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //ocultar barra de arriba
        supportActionBar?.hide()

        val btnSiguiente = findViewById<Button>(R.id.btnSiguiente)
        val btnAtras = findViewById<Button>(R.id.btnAtras)
        val txtExperiencia = findViewById<EditText>(R.id.txtExperiencia)

        txtExperiencia.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                experienciaSeleccionada = s.toString()
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
                val resultSet = obtenerIdExperiencia.executeQuery()
                var idExperiencia: Int = -1
                if (resultSet.next()) {
                    idExperiencia = resultSet.getInt(1)
                }

                // Insertar en la tabla Cliente
                val insertarCliente = objConexion.prepareStatement("INSERT INTO Cliente (edad, altura, peso, imc, padecimiento, idExperiencia) VALUES (?, ?, ?, ?, ?, ?)")
                insertarCliente.setString(1, edadSeleccionada)
                insertarCliente.setString(2, estaturaSeleccionada)
                insertarCliente.setString(3, pesoSeleccionado)
                insertarCliente.setString(4, imcSeleccionado)
                insertarCliente.setString(5, enfermedadSeleccionada)
                insertarCliente.setInt(6, idExperiencia)
                insertarCliente.executeUpdate()

                withContext(Dispatchers.Main) {
                    Toast.makeText(this@pregunta7, "Bienvenido a HealthSync", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@pregunta7, navigatioPrincipal::class.java)
                    startActivity(intent)
                }
            }
        }

        btnAtras.setOnClickListener {
            val intent = Intent(this, pregunta6::class.java)
            startActivity(intent)
        }
    }
}