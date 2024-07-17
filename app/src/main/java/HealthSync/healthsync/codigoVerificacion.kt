package HealthSync.healthsync

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class codigoVerificacion : AppCompatActivity() {
    private lateinit var code1: EditText
    private lateinit var code2: EditText
    private lateinit var code3: EditText
    private lateinit var code4: EditText
    private lateinit var code5: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_codigo_verificacion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Ocultar barra de arriba
        supportActionBar?.hide()

        code1 = findViewById(R.id.txtcode1)
        code2 = findViewById(R.id.txtcode2)
        code3 = findViewById(R.id.txtcode3)
        code4 = findViewById(R.id.txtcode4)
        code5 = findViewById(R.id.txtcode5)

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 1) {
                    when (currentFocus?.id) {
                        R.id.txtcode1 -> code2.requestFocus()
                        R.id.txtcode2 -> code3.requestFocus()
                        R.id.txtcode3 -> code4.requestFocus()
                        R.id.txtcode4 -> code5.requestFocus()
                        R.id.txtcode5 -> {
                            // Aquí verificamos el código
                            val codigoIngresado = "${code1.text}${code2.text}${code3.text}${code4.text}${code5.text}"
                            val codigoCorrecto = Recuperacion.codigoVer

                            if (codigoIngresado == codigoCorrecto) {
                                // Redirigir a la pantalla de cambio de contraseña
                                val intent = Intent(this@codigoVerificacion, cambiodeclave::class.java)
                                startActivity(intent)
                            } else {
                                // Mostrar mensaje de código incorrecto
                                Toast.makeText(this@codigoVerificacion, "Código incorrecto", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        code1.addTextChangedListener(textWatcher)
        code2.addTextChangedListener(textWatcher)
        code3.addTextChangedListener(textWatcher)
        code4.addTextChangedListener(textWatcher)
        code5.addTextChangedListener(textWatcher)
    }
}