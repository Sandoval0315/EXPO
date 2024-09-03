package HealthSync.healthsync

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_tiempovascular21 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tiempovascular21)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.hide()

        val btnT21 = findViewById<Button>(R.id.btnT21)

        btnT21.setOnClickListener{
            val intent = Intent(this@activity_tiempovascular21, activity_tomadescaso2::class.java)
            intent.putExtra("identificador", "tiempo21")
            startActivity(intent)
            finish()
        }

    }
}