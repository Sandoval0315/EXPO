package HealthSync.healthsync

import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class codigoVerificacion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_codigo_verificacion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //ocultar barra de arriba
        supportActionBar?.hide()

        val code1 = findViewById<EditText>(R.id.txtcode1)
        val code2 = findViewById<EditText>(R.id.txtcode2)
        val code3 = findViewById<EditText>(R.id.txtcode3)
        val code4 = findViewById<EditText>(R.id.txtcode4)
        val code5 = findViewById<EditText>(R.id.txtcode5)

    }
}