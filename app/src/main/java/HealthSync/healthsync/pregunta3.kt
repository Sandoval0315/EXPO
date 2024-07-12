package preguntas

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

class pregunta3 : AppCompatActivity(),SeekBar.OnSeekBarChangeListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pregunta3)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sbEdad = findViewById<SeekBar>(R.id.sbEdad)
        val txtEdad = findViewById<TextView>(R.id.txtEdad)
        val btnSiguiente = findViewById<Button>(R.id.btnSiguiente)
        val btnAtras = findViewById<Button>(R.id.btnAtras)

        sbEdad.setOnSeekBarChangeListener(this)

        btnSiguiente.visibility = View.GONE
        btnSiguiente.setOnClickListener{
            btnSiguiente.visibility = View.GONE}

        btnSiguiente.setOnClickListener {
            val intent = Intent(this, pregunta4::class.java)
            startActivity(intent)
        }
        btnAtras.setOnClickListener {
            val intent = Intent(this, pregunta5::class.java)
            startActivity(intent)
        }
    }
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        // con esto se actualiza el edit text cuando arrastre el seekbar
        findViewById<TextView>(R.id.txtEdad).text = progress.toString()
        val btnSiguiente = findViewById<Button>(R.id.btnSiguiente)
        val btnAtras = findViewById<Button>(R.id.btnAtras)

        btnSiguiente.visibility = View.VISIBLE
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}
    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
}