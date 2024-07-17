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
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement

/*class pregunta7 : AppCompatActivity() {

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

            val objConexion = ClaseConexion().CadenaConexion()

            val insertarPreguntas = objConexion?.prepareStatement("INSERT INTO Cliente (edad, altura, peso, imc, padecimiento, experiencia) VALUES (?, ?, ?, ?, ?, ?)")!!

            insertarPreguntas.setString(1, edadSeleccionada)
            insertarPreguntas.setString(2, estaturaSeleccionada)
            insertarPreguntas.setString(3, pesoSeleccionado)
            insertarPreguntas.setString(4, imcSeleccionado)
            insertarPreguntas.setString(5, enfermedadSeleccionada)
            insertarPreguntas.setString(6, experienciaSeleccionada)
            insertarPreguntas.executeUpdate()

            Toast.makeText(this, "Bienvenido a HealthSync", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, navigatioPrincipal::class.java)
            startActivity(intent)
        }

        btnAtras.setOnClickListener {
            val intent = Intent(this, pregunta6::class.java)
            startActivity(intent)
        }
    }
}
*/