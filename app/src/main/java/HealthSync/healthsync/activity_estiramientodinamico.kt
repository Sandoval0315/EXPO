package HealthSync.healthsync

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.os.CountDownTimer
import android.widget.TextView


class activity_estiramientodinamico : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")

    private lateinit var txtTimer: TextView
    private lateinit var pauseButton: ImageView
    private var countDownTimer: CountDownTimer? = null
    private var timeRemaining: Long = 60000 // 1 minuto en milisegundos
    private var isPaused: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_estiramientodinamico)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val regresarpausa = findViewById<ImageButton>(R.id.imgbuttonregresarpausa)

        regresarpausa.setOnClickListener{
            val intent = Intent(this, activity_pausas::class.java)
            startActivity(intent)
        }
        // Referencias a los elementos en el layout
        txtTimer = findViewById(R.id.txtestiramientoRCV)
        pauseButton = findViewById(R.id.imgIniciar1min)

        // Configurar el CountdownTimer
        countDownTimer = createCountDownTimer(timeRemaining)

        // Iniciar el temporizador
        countDownTimer?.start()

        // Configurar el botón de pausa
        pauseButton.setOnClickListener {
            if (isPaused) {
                // Reanudar el temporizador
                countDownTimer = createCountDownTimer(timeRemaining)
                countDownTimer?.start()
                isPaused = false
                pauseButton.setImageResource(R.drawable.pausa_png) // Cambia la imagen al ícono de pausa
            } else {
                // Pausar el temporizador
                countDownTimer?.cancel()
                isPaused = true
                pauseButton.setImageResource(R.drawable.imgplay1) // Cambia la imagen al ícono de reproducir
            }
        }
    }

    private fun createCountDownTimer(timeInMillis: Long): CountDownTimer {
        return object : CountDownTimer(timeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeRemaining = millisUntilFinished
                val secondsRemaining = millisUntilFinished / 1000
                txtTimer.text = String.format("%02d:%02d", secondsRemaining / 60, secondsRemaining % 60)
            }

           /// override fun onFinish() {
              ///  txtTimer.text = "00:00"
              ///  // Cambiar de pantalla cuando el temporizador termine
              //  val intent = Intent(this@MainActivity, otra pantalla::class.java)
              //  startActivity(intent)
               ///finish() // Finaliza la actividad actual
            }
    }

}