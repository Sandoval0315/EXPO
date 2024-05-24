package HealthSync.healthsync

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class LoadingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        // codigo para que cambie a otra pantalla luego de cierto tiempo
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, Bienvenida::class.java)
            startActivity(intent)
            finish() //
        }, 3000)
    }
}
