package HealthSync.healthsync

import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_hidratacion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_hidratacion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // ocultar barra de arriba
        supportActionBar?.hide()

        val btnsalirdehidra = findViewById<ImageButton>(R.id.imgBackk)
        val imgEditar = findViewById<ImageView>(R.id.imgEditarHidra)
        val txtVasos = findViewById<TextView>(R.id.txtMetaDiaria)

        btnsalirdehidra.setOnClickListener {
            val intent = Intent(this, activity_Hidra_y_Alimen::class.java)
            startActivity(intent)
        }

        imgEditar.setOnClickListener {
            showConfigDialog(txtVasos)
        }
    }

    private fun showConfigDialog(txtVasos: TextView) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Configurar meta")
        builder.setMessage("¿Qué desea configurar?")

        builder.setPositiveButton("Editar") { dialog, which ->
            showEditDialog(txtVasos)
        }

        builder.setNegativeButton("Eliminar") { dialog, which ->
            showDeleteDialog(txtVasos)
        }

        builder.show()
    }

    private fun showEditDialog(txtVasos: TextView) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Editar meta diaria")

        val input = EditText(this)
        input.hint = "¿Cuántos vasos al día desea tomar?"
        input.inputType = InputType.TYPE_CLASS_NUMBER
        input.filters = arrayOf(InputFilter.LengthFilter(2)) // Limita la entrada a dos dígitos

        builder.setView(input)

        builder.setPositiveButton("Aceptar") { dialog, which ->
            val text = input.text.toString()
            if (text.isNotEmpty()) {
                txtVasos.text = "$text VASOS"
            }
        }

        builder.setNegativeButton("Cancelar") { dialog, which ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun showDeleteDialog(txtVasos: TextView) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Dejar meta por defecto")
        builder.setMessage("¿Desea dejar la meta por defecto?")

        builder.setPositiveButton("Sí") { dialog, which ->
            txtVasos.text = "10 VASOS"
        }

        builder.setNegativeButton("No") { dialog, which ->
            dialog.cancel()
        }

        builder.show()
    }
}

