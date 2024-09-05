package HealthSync.healthsync

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_tomadescaso2 : AppCompatActivity() {

    private lateinit var txtTimer: TextView
    private lateinit var pauseButton: ImageView
    private var countDownTimer: CountDownTimer? = null
    private var timeRemaining: Long = 20000 // 20 seg en milisegundos
    private var isPaused: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tomadescaso2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.hide()


        // Referencias a los elementos en el layout
        txtTimer = findViewById(R.id.txt20s)
        pauseButton = findViewById(R.id.img20seg)

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

                val valorRecibido = intent.getStringExtra("identificador")

                println("ESTE ES EL VALOR QUE RTECIBO $valorRecibido")

                if(valorRecibido == "tiempo1"){
                    //intent a la segunda pantalla
                    val intent = Intent(this@activity_tomadescaso2, activity_estiramientodinamico::class.java)
                    startActivity(intent)
                    println("despues de cambair pantalla")
                    finish()
                }else if(valorRecibido == "tiempo2"){
                    val intent = Intent(this@activity_tomadescaso2, activity_tiempovascular5::class.java)
                    startActivity(intent)
                    println("despues de cambair pantalla")
                    finish()
                }else if(valorRecibido == "tiempo5"){
                    val intent = Intent(this@activity_tomadescaso2, activity_tiempovascular6::class.java)
                    startActivity(intent)
                    println("despues de cambair pantalla")
                    finish()
                }else if (valorRecibido == "tiempo6"){
                    val intent = Intent(this@activity_tomadescaso2, activity_tiempovascular8::class.java)
                    startActivity(intent)
                    println("despues de cambair pantalla")
                    finish()
                }else if (valorRecibido == "tiempo8"){
                    val intent = Intent(this@activity_tomadescaso2, activity_tiempovascular11::class.java)
                    startActivity(intent)
                    println("despues de cambair pantalla")
                    finish()
                }else if (valorRecibido == "tiempo11"){
                    val intent = Intent(this@activity_tomadescaso2, activity_timepovascular13::class.java)
                    startActivity(intent)
                    println("despues de cambair pantalla")
                    finish()
                }else if (valorRecibido == "tiempo13"){
                    val intent = Intent(this@activity_tomadescaso2, activity_tiempovascular15::class.java)
                    startActivity(intent)
                    println("despues de cambair pantalla")
                    finish()
                }else if (valorRecibido == "tiempo15"){
                    val intent = Intent(this@activity_tomadescaso2, activity_tiempovascular17::class.java)
                    startActivity(intent)
                    println("despues de cambair pantalla")
                    finish()
                }else if (valorRecibido == "tiempo17"){
                    val intent = Intent(this@activity_tomadescaso2, activity_tiempovascular19::class.java)
                    startActivity(intent)
                    println("despues de cambair pantalla")
                    finish()
                }else if (valorRecibido == "tiempo19"){
                    val intent = Intent(this@activity_tomadescaso2, activity_tiempovascular21::class.java)
                    startActivity(intent)
                    println("despues de cambair pantalla")
                    finish()
                }else if (valorRecibido == "tiempo21"){
                    val intent = Intent(this@activity_tomadescaso2, activity_tiempovascular24::class.java)
                    startActivity(intent)
                    println("despues de cambair pantalla")
                    finish()
                }else if (valorRecibido == "tiempo24"){
                    val intent = Intent(this@activity_tomadescaso2, activity_tiempovascular26::class.java)
                    startActivity(intent)
                    println("despues de cambair pantalla")
                    finish()
                }

            }

        }

    }
    //para pausar el tiempo automatico cuando sin cambia de pantalla
    override fun onPause() {
        super.onPause()
        countDownTimer?.cancel()
    }
}