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
import kotlin.math.pow
import Modelo.ClaseConexion
import android.widget.ImageView

class editarPerfil : AppCompatActivity() {
    private lateinit var txtEdad: EditText
    private lateinit var txtAltura: EditText
    private lateinit var txtPeso: EditText
    private lateinit var txtEnfermedades: EditText
    private lateinit var btnAgregar: Button

    companion object {
        const val REQUEST_CODE_ACTUALIZAR_PERFIL = 100 // Se agregó esta línea

        lateinit var edadPerfilActu: String
            private set

        lateinit var pesoPerfilActu: String
            private set

        lateinit var alturaPerfilActu: String
            private set

        lateinit var enfermedadesPerfilActu: String
            private set

        lateinit var txtIMCactu: String
            private set

        // Funciones de verificación para comprobar si se han inicializado
        fun isEdadPerfilActuInitialized() = ::edadPerfilActu.isInitialized
        fun isPesoPerfilActuInitialized() = ::pesoPerfilActu.isInitialized
        fun isAlturaPerfilActuInitialized() = ::alturaPerfilActu.isInitialized
        fun isTxtIMCactuInitialized() = ::txtIMCactu.isInitialized
        fun isEnfermedadesPerfilActuInitialized() = ::enfermedadesPerfilActu.isInitialized
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
        txtEnfermedades = findViewById(R.id.txtEnfermedadesPerfil)
        btnAgregar = findViewById(R.id.btnActualizarPerfil)

        // Inicialmente oculta el botón
        btnAgregar.visibility = Button.INVISIBLE

        // Añade listeners para mostrar el botón si todos los campos tienen texto
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
        edadPerfilActu = txtEdad.text.toString()
        alturaPerfilActu = txtAltura.text.toString()
        pesoPerfilActu = txtPeso.text.toString()
        enfermedadesPerfilActu = txtEnfermedades.text.toString()

        // Calcular el IMC
        val peso = pesoPerfilActu.toFloat()
        val estatura = alturaPerfilActu.toFloat() / 100 // Convertir cm a m
        val imc = peso / (estatura.pow(2))
        txtIMCactu = String.format("%.2f", imc)

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
                    preparedStatement.setInt(1, edadPerfilActu.toInt())
                    preparedStatement.setDouble(2, alturaPerfilActu.toDouble())
                    preparedStatement.setDouble(3, pesoPerfilActu.toDouble())
                    preparedStatement.setDouble(4, txtIMCactu.toDouble())
                    preparedStatement.setString(5, enfermedadesPerfilActu)
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
        txtEnfermedades.text.clear()
        btnAgregar.visibility = Button.INVISIBLE
    }
}