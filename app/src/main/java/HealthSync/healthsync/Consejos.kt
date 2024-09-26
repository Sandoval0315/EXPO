package HealthSync.healthsync

import Modelo.ClaseConexion
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.*

class Consejos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_consejos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Oculta la barra de acción
        supportActionBar?.hide()

        // Mandar a traer las cosas de as vista
        val imgRegresar = findViewById<ImageView>(R.id.imgRegresarHome)
        val txtConsejoDia = findViewById<TextView>(R.id.txtConsejoDelDia)
        val txtConsejoEntrenamiento = findViewById<TextView>(R.id.txtConsejoDeEntrenamiento)

        // Configura el listener para el botón de regresar
        imgRegresar.setOnClickListener {
            val intent = Intent(this, navigatioPrincipal::class.java)
            startActivity(intent)
        }

        // Obtiene el correo del usuario logueado
        val userEmail = login.userEmail

        // Utiliza corrutinas para realizar la operación de base de datos en un hilo secundario
        GlobalScope.launch(Dispatchers.IO) {
            val objConexion = ClaseConexion().CadenaConexion()

            // Prepara la consulta SQL con INNER JOINs para obtener los consejos del usuario logueado
            val query = """
                SELECT c.fraseDia, c.consejoEjercicio
                FROM Consejo c
                INNER JOIN Cliente cl ON c.idCliente = cl.idCliente
                INNER JOIN Usuarios u ON cl.idUsuario = u.idUsuario
                WHERE u.correo = ?
            """.trimIndent()

            // Ejecuta la consulta
            val stmt = objConexion?.prepareStatement(query)
            stmt?.setString(1, userEmail)
            val resultado = stmt?.executeQuery()

            // Procesa el resultado
            if (resultado?.next() == true) {
                val fraseDia = resultado.getString("fraseDia")
                val consejoEjercicio = resultado.getString("consejoEjercicio")

                // Actualiza la UI en el hilo principal
                withContext(Dispatchers.Main) {
                    txtConsejoDia.text = fraseDia
                    txtConsejoEntrenamiento.text = consejoEjercicio
                }
            } else {
                // Si no se encuentran consejos, muestra un mensaje por defecto
                withContext(Dispatchers.Main) {
                    txtConsejoDia.text = "No hay consejo del día disponible"
                    txtConsejoEntrenamiento.text = "No hay consejo de entrenamiento disponible"
                }
            }

            // Cierra la conexión y los recursos
            resultado?.close()
            stmt?.close()
            objConexion?.close()
        }
    }
}