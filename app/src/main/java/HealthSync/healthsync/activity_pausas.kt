package HealthSync.healthsync

import HealthSync.healthsync.ui.dashboard.DashboardFragment
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


class activity_pausas : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")

    private lateinit var txtTimer: TextView
    private lateinit var pauseButton: ImageView
    private var countDownTimer: CountDownTimer? = null
    private var timeRemaining: Long = 15000 // 15 segundos en milisegundos
    private var isPaused: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pausas)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.hide()





        val regresar = findViewById<ImageButton>(R.id.button_regresamos)
        val siguientePantalla = findViewById<ImageView>(R.id.img_siguientePantalla)

        regresar.setOnClickListener{
            val intent = Intent(this, activity_tiempovascular::class.java)
            startActivity(intent)
        }

        //Tengo que ver como arreglo esto lo mas seguro me toque crear otras pantallas :/

        siguientePantalla.setOnClickListener{
            val intent = Intent(this, activity_estiramientodinamico::class.java)
            startActivity(intent)
        }

        // Referencias a los elementos en el layout
        txtTimer = findViewById(R.id.txt15seg)
        pauseButton = findViewById(R.id.imgpausa15seg)

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
               // val valorRecibido = intent.getStringExtra("identificador")

                //println("ESTE ES EL VALOR QUE RTECIBO $valorRecibido")

               // if(valorRecibido == "Primer"){
                    //intent a la segunda pantalla
                //    val intent = Intent(this@activity_pausas, activity_yoga2::class.java)
                  //  startActivity(intent)
                   // println("despues de cambair pantalla")
                 //   finish()
               // }else if(valorRecibido == "segunda"){
                    //iintent a la tercera
               // }


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