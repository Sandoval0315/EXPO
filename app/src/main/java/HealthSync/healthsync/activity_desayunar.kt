package HealthSync.healthsync

import Modelo.ClaseConexion
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.*

class activity_desayunar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_desayunar)

        // Establece el color de la barra de estado
        window.statusBarColor = resources.getColor(R.color.colorOnSecondary, theme)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Oculta la barra de acción
        supportActionBar?.hide()

        // Inicializa las vistas
        val txtDesayuno = findViewById<TextView>(R.id.txtDesayuno)
        val txtAlmuerzo = findViewById<TextView>(R.id.txtAlmuerzo)
        val txtCena = findViewById<TextView>(R.id.txtCena)

        // Obtiene el correo del usuario logueado
        val userEmail = login.userEmail

        // Utiliza corrutinas para realizar la operación de base de datos en un hilo secundario
        GlobalScope.launch(Dispatchers.IO) {
            val objConexion = ClaseConexion().CadenaConexion()

            // Prepara la consulta SQL con INNER JOINs para obtener la dieta del usuario logueado
            val query = """
                SELECT d.tipoDesayunos, d.tipoAlmuerzos, d.tipoCenas
                FROM Dieta d
                INNER JOIN Cliente c ON d.idCliente = c.idCliente
                INNER JOIN Usuarios u ON c.idUsuario = u.idUsuario
                WHERE u.correo = ?
            """.trimIndent()

            // Ejecuta la consulta
            val stmt = objConexion?.prepareStatement(query)
            stmt?.setString(1, userEmail)
            val resultado = stmt?.executeQuery()

            // Procesa el resultado
            if (resultado?.next() == true) {
                val tipoDesayunos = resultado.getString("tipoDesayunos")
                val tipoAlmuerzos = resultado.getString("tipoAlmuerzos")
                val tipoCenas = resultado.getString("tipoCenas")

                // Actualiza la UI en el hilo principal
                withContext(Dispatchers.Main) {
                    txtDesayuno.text = tipoDesayunos
                    txtAlmuerzo.text = tipoAlmuerzos
                    txtCena.text = tipoCenas
                }
            } else {
                // Si no se encuentra una dieta, muestra mensajes por defecto
                withContext(Dispatchers.Main) {
                    txtDesayuno.text = "No hay información de desayuno disponible"
                    txtAlmuerzo.text = "No hay información de almuerzo disponible"
                    txtCena.text = "No hay información de cena disponible"
                }
            }

            // Cierra la conexión y los recursos
            resultado?.close()
            stmt?.close()
            objConexion?.close()
        }
    }
}