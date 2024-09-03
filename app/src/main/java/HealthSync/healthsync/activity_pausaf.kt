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

class activity_pausaf : AppCompatActivity() {

    private lateinit var txtTimer: TextView
    private lateinit var pauseButton: ImageView
    private var countDownTimer: CountDownTimer? = null
    private var timeRemaining: Long = 10000 // 30 seg en milisegundos
    private var isPaused: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pausaf)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.hide()


        // Referencias a los elementos en el layout
        txtTimer = findViewById(R.id.txt15segF)
        pauseButton = findViewById(R.id.img15segF)

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

                val valorRecibido = intent.getStringExtra("identificador")

                println("ESTE ES EL VALOR QUE RTECIBO $valorRecibido")

                if(valorRecibido == "fuerza1"){
                    //intent a la segunda pantalla
                    val intent = Intent(this@activity_pausaf, activity_fuerza3::class.java)
                    startActivity(intent)
                    println("despues de cambair pantalla")
                    finish()
                }else if(valorRecibido == "fuerza3"){
                    //iintent a la tercera
                    val intent = Intent(this@activity_pausaf, activityfuerza5::class.java)
                    startActivity(intent)
                    println("despues de cambair pantalla")
                    finish()
                }else if(valorRecibido == "fuerza5"){
                    //intent a la cuarta
                    val intent = Intent(this@activity_pausaf, activity_fuerza7::class.java)
                    startActivity(intent)
                    println("despues de cambair pantalla")
                    finish()
                }else if(valorRecibido == "fuerza7"){
                    //intent a la quinta
                    val intent = Intent(this@activity_pausaf, activity_fuerza9::class.java)
                    startActivity(intent)
                    println("despues de cambair pantalla")
                    finish()
                }else if(valorRecibido == "fuerza9"){
                    //intent a la sexta
                    val intent = Intent(this@activity_pausaf, activity_fuerza11::class.java)
                    startActivity(intent)
                    println("despues de cambair pantalla")
                    finish()
                }else if(valorRecibido == "fuerza11"){
                    //intent a la septima
                    val intent = Intent(this@activity_pausaf, activity_fuerza13::class.java)
                    startActivity(intent)
                    println("despues de cambair pantalla")
                    finish()
                }else if(valorRecibido == "fuerza13"){
                    //intent a la octava
                    val intent = Intent(this@activity_pausaf, activity_fuerza15::class.java)
                    startActivity(intent)
                    println("despues de cambair pantalla")
                    finish()
                }else if (valorRecibido== "fuerza15"){
                    //intent a la novena
                    val intent = Intent(this@activity_pausaf, activity_fuerza17::class.java)
                    startActivity(intent)
                    println("despues de cambair pantalla")
                    finish()
                }else if (valorRecibido == "fuerza17"){
                    //intent a la decima
                    val intent = Intent(this@activity_pausaf, activity_fuerza19::class.java)
                    startActivity(intent)
                    println("despues de cambair pantalla")
                    finish()
                }else if (valorRecibido == "fuerza19"){
                    //intent a la decima primera
                    val intent = Intent(this@activity_pausaf, activity_fuerza21::class.java)
                    startActivity(intent)
                    println("despues de cambair pantalla")
                    finish()
                }else if (valorRecibido == "fuerza21"){
                    //intent a la decimo segunda
                    val intent = Intent(this@activity_pausaf, activity_fuerza23::class.java)
                    startActivity(intent)
                    println("despues de cambair pantalla")
                    finish()
                }


            }
        }
    }
}