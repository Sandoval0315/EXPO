package HealthSync.healthsync

import android.app.Activity
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
import kotlin.math.pow
import Modelo.ClaseConexion
import android.widget.ImageView

class EditarPerfil : AppCompatActivity() {
    private lateinit var txtEdad: EditText
    private lateinit var txtAltura: EditText
    private lateinit var txtPeso: EditText
    private lateinit var txtEnfermedades: EditText
    private lateinit var btnAgregar: Button

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
        txtEnfermedades = findViewById(R.id.txtEnfermedadesPerfil)
        btnAgregar = findViewById(R.id.btnActualizarPerfil)

        btnAgregar.visibility = Button.INVISIBLE

        val textFields = arrayOf(txtEdad, txtAltura, txtPeso, txtEnfermedades)
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
                txtEnfermedades.text.isNotEmpty()
    }

    private fun updateData() {
        val edad = txtEdad.text.toString().toInt()
        val altura = txtAltura.text.toString().toDouble()
        val peso = txtPeso.text.toString().toDouble()
        val padecimiento = txtEnfermedades.text.toString()
        val imc = peso / (altura / 100).pow(2)

        val idCliente = 1 // Reemplaza con el ID del cliente correspondiente

        GlobalScope.launch(Dispatchers.IO) {
            var connection: Connection? = null
            var preparedStatement: PreparedStatement? = null

            try {
                val claseConexion = ClaseConexion()
                connection = claseConexion.CadenaConexion()

                if (connection != null) {
                    val updateSQL = "UPDATE Cliente SET edad = ?, altura = ?, peso = ?, imc = ?, padecimiento = ? WHERE idCliente = ?"
                    preparedStatement = connection.prepareStatement(updateSQL)
                    preparedStatement.setInt(1, edad)
                    preparedStatement.setDouble(2, altura)
                    preparedStatement.setDouble(3, peso)
                    preparedStatement.setDouble(4, imc)
                    preparedStatement.setString(5, padecimiento)
                    preparedStatement.setInt(6, idCliente)

                    preparedStatement.executeUpdate()

                    val commitStatement = connection.prepareStatement("COMMIT")
                    commitStatement.executeUpdate()

                    runOnUiThread {
                        Toast.makeText(this@EditarPerfil, "Datos actualizados", Toast.LENGTH_SHORT).show()
                        clearFields()
                        setResult(Activity.RESULT_OK)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(this@EditarPerfil, "Error al actualizar los datos", Toast.LENGTH_SHORT).show()
                }
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
        txtEnfermedades.text.clear()
        btnAgregar.visibility = Button.INVISIBLE
    }
}