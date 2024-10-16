package HealthSync.healthsync

import HealthSync.healthsync.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class pregunta2 : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {

    companion object {
        lateinit var edadSeleccionada: String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pregunta2)
        window.statusBarColor = resources.getColor(R.color.colorOnSecondary, theme)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //ocultar barra de arriba
        supportActionBar?.hide()

        val sbEdad = findViewById<SeekBar>(R.id.sbEdad)
        val txtEdad = findViewById<TextView>(R.id.txtEdad)
        val btnSiguiente = findViewById<Button>(R.id.btnSiguiente)
        val btnAtras = findViewById<Button>(R.id.btnAtras)

        sbEdad.setOnSeekBarChangeListener(this)

        btnSiguiente.visibility = View.GONE
        btnSiguiente.setOnClickListener{
            btnSiguiente.visibility = View.GONE
        }

        btnSiguiente.setOnClickListener {
            edadSeleccionada = txtEdad.text.toString()
            val intent = Intent(this, pregunta3::class.java)
            startActivity(intent)
        }

        btnAtras.setOnClickListener {
            val intent = Intent(this, pregunta1::class.java)
            startActivity(intent)
        }
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        // con esto se actualiza el edit text cuando arrastre el seekbar
        val txtEdad = findViewById<TextView>(R.id.txtEdad)
        txtEdad.text = progress.toString()
        edadSeleccionada = progress.toString()
        val btnSiguiente = findViewById<Button>(R.id.btnSiguiente)
        btnSiguiente.visibility = View.VISIBLE
    }

    //estas dos son para cuando se mueva y se detenga el seekvar
    override fun onStartTrackingTouch(seekBar: SeekBar?) {}
    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
}
