package HealthSync.healthsync

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.sql.Connection
import java.sql.PreparedStatement

// Importa la clase ClaseConexion correctamente
import Modelo.ClaseConexion
import android.widget.ImageView

class editarPerfil : AppCompatActivity() {
    private lateinit var txtEdad: EditText
    private lateinit var txtAltura: EditText
    private lateinit var txtPeso: EditText
    private lateinit var txtIMC: EditText
    private lateinit var txtEnfermedades: EditText
    private lateinit var btnAgregar: Button

    companion object {
        const val REQUEST_CODE_ACTUALIZAR_PERFIL = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_editar_perfil)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.hide()

        val imgRegresarPerfil = findViewById<ImageView>(R.id.imgVolveralPerfil)

        imgRegresarPerfil.setOnClickListener {
            finish()
        }

        txtEdad = findViewById(R.id.txtEdadPerfil)
        txtAltura = findViewById(R.id.txtAlturaPerfil)
        txtPeso = findViewById(R.id.txtPesoPerfil)
        txtIMC = findViewById(R.id.txtIMCPerfil)
        txtEnfermedades = findViewById(R.id.txtEnfermedadesPerfil)
        btnAgregar = findViewById(R.id.btnActualizarPerfil)

        // Inicialmente oculta el botón
        btnAgregar.visibility = Button.INVISIBLE

        // Añade listeners para mostrar el botón si todos los campos tienen texto
        val textFields = arrayOf(txtEdad, txtAltura, txtPeso, txtIMC, txtEnfermedades)
        for (field in textFields) {
            field.addTextChangedListener(object : android.text.TextWatcher {
                override fun afterTextChanged(s: android.text.Editable?) {
                    btnAgregar.visibility = if (allFieldsFilled()) Button.VISIBLE else Button.INVISIBLE
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }

        btnAgregar.setOnClickListener {
            updateData()
        }
    }

    private fun allFieldsFilled(): Boolean {
        return txtEdad.text.isNotEmpty() && txtAltura.text.isNotEmpty() && txtPeso.text.isNotEmpty() &&
                txtIMC.text.isNotEmpty() && txtEnfermedades.text.isNotEmpty()
    }

    private fun updateData() {
        val edad = txtEdad.text.toString().toInt()
        val altura = txtAltura.text.toString().toDouble()
        val peso = txtPeso.text.toString().toDouble()
        val imc = txtIMC.text.toString().toDouble()
        val enfermedades = txtEnfermedades.text.toString()
        val idCliente = 1 // Reemplaza con el ID del cliente correspondiente

        GlobalScope.launch(Dispatchers.IO) {
            var connection: Connection? = null
            var preparedStatement: PreparedStatement? = null

            try {
                // Obtener la conexión usando la clase ClaseConexion
                val claseConexion = ClaseConexion()
                connection = claseConexion.CadenaConexion()

                if (connection != null) {
                    val updateSQL = "UPDATE Cliente SET edad = ?, altura = ?, peso = ?, imc = ?, padecimiento = ? WHERE idCliente = ?"
                    preparedStatement = connection.prepareStatement(updateSQL)
                    preparedStatement.setInt(1, edad)
                    preparedStatement.setDouble(2, altura)
                    preparedStatement.setDouble(3, peso)
                    preparedStatement.setDouble(4, imc)
                    preparedStatement.setString(5, enfermedades)
                    preparedStatement.setInt(6, idCliente)

                    preparedStatement.executeUpdate()

                    // Commit de la transacción
                    val commitStatement = connection.prepareStatement("COMMIT")
                    commitStatement.executeUpdate()

                    // Actualización exitosa
                    runOnUiThread {
                        Toast.makeText(this@editarPerfil, "Datos actualizados", Toast.LENGTH_SHORT).show()
                        clearFields()
                        setResult(Activity.RESULT_OK)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                preparedStatement?.close()
                connection?.close()
            }
        }
    }

    private fun clearFields() {
        txtEdad.text.clear()
        txtAltura.text.clear()
        txtPeso.text.clear()
        txtIMC.text.clear()
        txtEnfermedades.text.clear()
        btnAgregar.visibility = Button.INVISIBLE
    }
}