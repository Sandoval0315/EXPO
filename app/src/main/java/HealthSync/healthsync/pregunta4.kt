package HealthSync.healthsync

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import HealthSync.healthsync.R
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText

class pregunta4 : AppCompatActivity() {

    companion object {
        lateinit var pesoSeleccionado: String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pregunta4)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //ocultar barra de arriba
        supportActionBar?.hide()

        val btnSiguiente = findViewById<Button>(R.id.btnSiguiente)
        val btnAtras = findViewById<Button>(R.id.btnAtras)
        val txtPeso = findViewById<EditText>(R.id.txtPeso)

        btnSiguiente.visibility = View.GONE

        txtPeso.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                pesoSeleccionado = s.toString()
                btnSiguiente.visibility = if (s?.toString()?.isNotEmpty() == true) View.VISIBLE else View.GONE
            }
        })

        btnSiguiente.setOnClickListener {
            val intent = Intent(this, pregunta5::class.java)
            startActivity(intent)
        }
        btnAtras.setOnClickListener {
            val intent = Intent(this, pregunta3::class.java)
            startActivity(intent)
        }
    }
}