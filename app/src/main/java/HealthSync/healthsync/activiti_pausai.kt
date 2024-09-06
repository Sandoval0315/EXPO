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

class activiti_pausai : AppCompatActivity() {

    private lateinit var txtTimer: TextView
    private lateinit var pauseButton: ImageView
    private var countDownTimer: CountDownTimer? = null
    private var timeRemaining: Long = 10000 // 30 seg en milisegundos
    private var isPaused: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_activiti_pausai)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.hide()


        // Referencias a los elementos en el layout
        txtTimer = findViewById(R.id.txtpausaIn)
        pauseButton = findViewById(R.id.img15segIn)

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

                if(valorRecibido == "intensidad1"){
                    //intent a la segunda pantalla
                    val intent = Intent(this@activiti_pausai, activity_intensidad3::class.java)
                    startActivity(intent)
                    println("despues de cambair pantalla")
                    finish()
                }else if(valorRecibido == "intensidad3"){
                    //iintent a la tercera
                    val intent = Intent(this@activiti_pausai, ativity_intensidad5::class.java)
                    startActivity(intent)
                    println("despues de cambair pantalla")
                    finish()
                }else if(valorRecibido == "intensidad5"){
                    //intent a la cuarta
                    val intent = Intent(this@activiti_pausai, activity_intensidad7::class.java)
                    startActivity(intent)
                    println("despues de cambair pantalla")
                    finish()
                }else if(valorRecibido == "intensidad7"){
                    //intent a la quinta
                    val intent = Intent(this@activiti_pausai, activity_intensidad9::class.java)
                    startActivity(intent)
                    println("despues de cambair pantalla")
                    finish()
                }else if(valorRecibido == "intensidad9"){
                    //intent a la sexta
                    val intent = Intent(this@activiti_pausai, activity_intensidad11::class.java)
                    startActivity(intent)
                    println("despues de cambair pantalla")
                    finish()
                }else if(valorRecibido == "intensidad11"){
                    //intent a la septima
                    val intent = Intent(this@activiti_pausai, activity_intensidad13::class.java)
                    startActivity(intent)
                    println("despues de cambair pantalla")
                    finish()
                }else if(valorRecibido == "intensidad13"){
                    //intent a la octava
                    val intent = Intent(this@activiti_pausai, activity_intensidad15::class.java)
                    startActivity(intent)
                    println("despues de cambair pantalla")
                    finish()
                }else if (valorRecibido== "intensidad15"){
                    //intent a la novena
                    val intent = Intent(this@activiti_pausai, activity_intensidad17::class.java)
                    startActivity(intent)
                    println("despues de cambair pantalla")
                    finish()
                }else if (valorRecibido == "intensidad17"){
                    //intent a la decima
                    val intent = Intent(this@activiti_pausai, activity_intensidad19::class.java)
                    startActivity(intent)
                    println("despues de cambair pantalla")
                    finish()
                }else if (valorRecibido == "intensidad19"){
                    //intent a la decima primera
                    val intent = Intent(this@activiti_pausai, activity_intensidad21::class.java)
                    startActivity(intent)
                    println("despues de cambair pantalla")
                    finish()
                }else if (valorRecibido == "intensidad21"){
                    //intent a la decimo segunda
                    val intent = Intent(this@activiti_pausai, activity_intensidad23::class.java)
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