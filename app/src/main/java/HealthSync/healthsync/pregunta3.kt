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

class pregunta3 : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {

    companion object {
        lateinit var estaturaSeleccionada: String
    }

    private lateinit var btnSiguiente: Button
    private var seekBarMoved = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pregunta3)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //ocultar barra de arriba
        supportActionBar?.hide()

        val sbEstatura = findViewById<SeekBar>(R.id.sbEstatura)
        val txtEstatura = findViewById<TextView>(R.id.txtEstatura)
        btnSiguiente = findViewById(R.id.btnSiguiente)
        val btnAtras = findViewById<Button>(R.id.btnAtras)

        sbEstatura.setOnSeekBarChangeListener(this)

        // Initially hide the button
        btnSiguiente.visibility = View.GONE

        btnSiguiente.setOnClickListener {
            estaturaSeleccionada = txtEstatura.text.toString()
            val intent = Intent(this, pregunta4::class.java)
            startActivity(intent)
        }

        btnAtras.setOnClickListener {
            val intent = Intent(this, pregunta2::class.java)
            startActivity(intent)
        }
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        val txtEstatura = findViewById<TextView>(R.id.txtEstatura)
        txtEstatura.text = progress.toString()
        estaturaSeleccionada = progress.toString()

        if (!seekBarMoved) {
            seekBarMoved = true
            btnSiguiente.visibility = View.VISIBLE
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}
    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
}