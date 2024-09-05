package HealthSync.healthsync

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_intensisdad1 : AppCompatActivity() {

    private lateinit var txtTimer: TextView
    private lateinit var pauseButton: ImageView
    private var countDownTimer: CountDownTimer? = null
    private var timeRemaining: Long = 10000 // 1 min en milisegundos
    private var isPaused: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_intensisdad1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //ocultar barra de arriba
        supportActionBar?.hide()

        val regresarIntensidad = findViewById<ImageButton>(R.id.btnregresarIntensidad)

        regresarIntensidad.setOnClickListener{
            val intent = Intent(this, activity_altaintensidad::class.java)
            startActivity(intent)
        }

        // Referencias a los elementos en el layout
        txtTimer = findViewById(R.id.txt1minIntensiad1)
        pauseButton = findViewById(R.id.img1minIntensidad1)

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
            } else {
                // Pausar el temporizador
                countDownTimer?.cancel()
                isPaused = true
            }
        }
    }

    private fun createCountDownTimer(timeInMillis: Long): CountDownTimer {
        return object : CountDownTimer(timeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeRemaining = millisUntilFinished
                val secondsRemaining = millisUntilFinished / 1000
                txtTimer.text =
                    String.format("%02d:%02d", secondsRemaining / 60, secondsRemaining % 60)
            }

            override fun onFinish() {
                txtTimer.text = "00:00"
                val intent = Intent(this@activity_intensisdad1, activiti_pausai::class.java)
                intent.putExtra("identificador", "intensidad1")
                startActivity(intent)
                finish()

               // val intent = Intent(this@activity_runnig15, activity_pantallafinalderitmo::class.java)
               // intent.putExtra("identificador", "Runnig15")
              //  startActivity(intent)
               // finish()
            }
        }
    }
    //para pausar el tiempo automatico cuando sin cambia de pantalla
    override fun onPause() {
        super.onPause()
        countDownTimer?.cancel()
    }
}