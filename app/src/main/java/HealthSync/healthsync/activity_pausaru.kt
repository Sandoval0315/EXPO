package HealthSync.healthsync

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_pausaru : AppCompatActivity() {

    private lateinit var txtTimer: TextView
    private lateinit var pauseButton: ImageView
    private var countDownTimer: CountDownTimer? = null
    private var timeRemaining: Long = 10000 // 30 seg en milisegundos
    private var isPaused: Boolean = false


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pausaru)
        window.statusBarColor = resources.getColor(R.color.colorOnSecondary, theme)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //ocultar barra de arriba
        supportActionBar?.hide()

        //Nos manda al inicio de la rutina (Boton para atras)
        val regresaraInicio = findViewById<ImageView>(R.id.imgBackk)



        // Referencias a los elementos en el layout
        txtTimer = findViewById(R.id.txt30segrunnig)
        pauseButton = findViewById(R.id.img30segrunnig)

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

        regresaraInicio.setOnClickListener {
            mostrarDialogoConfirmacion()
        }
    }

    private fun mostrarDialogoConfirmacion() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Regresar a la pantalla anterior?")
        builder.setMessage("Tu tiempo de actividad no se guardará")
        builder.setPositiveButton("Sí") { dialog, which ->
            val intent = Intent(this, activity_runnig::class.java)
            startActivity(intent) // Inicia la actividad deseada
            finish() // Cierra la actividad actual
        }
        builder.setNegativeButton("No") { dialog, which ->
            dialog.dismiss() // Cierra el diálogo sin realizar acción
        }
        val dialog = builder.create()
        dialog.show()
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

                if(valorRecibido == "Runnig1"){
                    //intent a la segunda pantalla
                    val intent = Intent(this@activity_pausaru, activity_runnig3::class.java)
                    startActivity(intent)
                    println("despues de cambair pantalla")
                    finish()
                }else if(valorRecibido == "Runnig3"){
                    //iintent a la tercera
                    val intent = Intent(this@activity_pausaru, activity_runnig5::class.java)
                    startActivity(intent)
                    println("despues de cambair pantalla")
                    finish()
                }else if(valorRecibido == "Runnig5"){
                    //intent a la cuarta
                    val intent = Intent(this@activity_pausaru, activity_running9::class.java)
                    startActivity(intent)
                    println("despues de cambair pantalla")
                    finish()

                }else if(valorRecibido == "Runnig9"){
                    //intent a la quinta
                    val intent = Intent(this@activity_pausaru, activity_runnig11::class.java)
                    startActivity(intent)
                    println("despues de cambair pantalla")
                    finish()
                }else if(valorRecibido == "Runnig11"){
                    //intent a la sexta
                    val intent = Intent(this@activity_pausaru, activity_runnig13::class.java)
                    startActivity(intent)
                    println("despues de cambair pantalla")
                    finish()
                }else if(valorRecibido=="Runnig13"){
                    //intent a la septima
                    val intent = Intent(this@activity_pausaru, activity_runnig15::class.java)
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