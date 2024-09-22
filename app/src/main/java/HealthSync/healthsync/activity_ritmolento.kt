package HealthSync.healthsync

import HealthSync.healthsync.ui.dashboard.DashboardFragment
import android.annotation.SuppressLint
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import android.os.CountDownTimer
import android.widget.TextView
import androidx.core.view.WindowInsetsCompat

class activity_ritmolento : AppCompatActivity() {

    private lateinit var txtTimer: TextView
    private lateinit var pauseButton: ImageView
    private var countDownTimer: CountDownTimer? = null
    private var timeRemaining: Long = 60000 // 1 minuto en milisegundos
    private var isPaused: Boolean = false

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ritmolento)
        window.statusBarColor = resources.getColor(R.color.colorOnSecondary, theme)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.hide()


        val regresar = findViewById<ImageView>(R.id.imgBackk)
        val descanso = findViewById<ImageView>(R.id.imgdescanso)

        regresar.setOnClickListener{
            val intent = Intent(this, DashboardFragment::class.java)
            startActivity(intent)
        }

        descanso.setOnClickListener{
            val intent = Intent(this, activity_pausas::class.java)
            startActivity(intent)
        }

        // Referencias a los elementos en el layout
        txtTimer = findViewById(R.id.txt30segRL)
        pauseButton = findViewById(R.id.img30segRL)

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
                txtTimer.text = String.format("%02d:%02d", secondsRemaining / 60, secondsRemaining % 60)
            }

            override fun onFinish() {
                txtTimer.text = "00:00"
            }
            // Cambiar de pantalla cuando el temporizador termine
            //   val intent = Intent(this@activity_estiramientodinamico, otra pantalla::class.java)
            ///    startActivity(intent)
            //finish() // Finaliza la actividad actual
        }




    }
    //para pausar el tiempo automatico cuando sin cambia de pantalla
    override fun onPause() {
        super.onPause()
        countDownTimer?.cancel()
    }
}