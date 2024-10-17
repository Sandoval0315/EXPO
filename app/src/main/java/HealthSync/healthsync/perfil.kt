// Importaciones necesarias para el funcionamiento del fragmento
package HealthSync.healthsync
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import HealthSync.healthsync.databinding.FragmentPerfilBinding
import HealthSync.healthsync.ui.perfil.PerfilViewModel
import android.content.Intent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import Modelo.ClaseConexion

// Definición de la clase perfil que hereda de Fragment
class perfil : Fragment() {
    // Variables para el binding del fragmento
    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!

    // Método que se llama cuando se crea la vista del fragmento
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inicialización del ViewModel para el perfil
        val perfilModelProvider = ViewModelProvider(this).get(PerfilViewModel::class.java)

        // Inflado del layout del fragmento
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Referencias a los elementos de la UI
        val txtNombrePerfil = root.findViewById<TextView>(R.id.txtNombrePerfil)
        val txtEdad = root.findViewById<TextView>(R.id.txtEdad)
        val txtAltura = root.findViewById<TextView>(R.id.txtAltura)
        val txtPeso = root.findViewById<TextView>(R.id.txtPeso)
        val txtIMC = root.findViewById<TextView>(R.id.txtIMC)
        val imgEditarPerfil = root.findViewById<ImageView>(R.id.imgEditarPerfil)
        val imgVolver = root.findViewById<ImageView>(R.id.imgCerrarSesion)

        // Obtener el correo del usuario de la sesión actual
        val userEmail = login.userEmail

        // Iniciar corrutina para la consulta a la base de datos
        GlobalScope.launch(Dispatchers.IO) {
            // Establecer conexión con la base de datos
            val objConexion = ClaseConexion().CadenaConexion()

            // Nueva consulta utilizando SELECT MAX para obtener los datos más recientes
            val query = """
                SELECT 
                    u.nombre,
                    (SELECT MAX(c.edad) FROM Cliente c WHERE c.idUsuario = u.idUsuario) as edad,
                    (SELECT MAX(c.altura) FROM Cliente c WHERE c.idUsuario = u.idUsuario) as altura,
                    (SELECT MAX(c.peso) FROM Cliente c WHERE c.idUsuario = u.idUsuario) as peso,
                    (SELECT MAX(c.imc) FROM Cliente c WHERE c.idUsuario = u.idUsuario) as imc
                FROM Usuarios u
                WHERE u.correo = ?
            """

            // Preparar y ejecutar la consulta
            val stmt = objConexion?.prepareStatement(query)
            stmt?.setString(1, userEmail)
            val resultado = stmt?.executeQuery()

            // Procesar los resultados de la consulta
            if (resultado?.next() == true) {
                val nombre = resultado.getString("nombre")
                val edad = resultado.getInt("edad")
                val altura = resultado.getDouble("altura")
                val peso = resultado.getDouble("peso")
                val imc = resultado.getDouble("imc")

                // Actualizar la UI en el hilo principal
                withContext(Dispatchers.Main) {
                    txtNombrePerfil.text = nombre
                    txtEdad.text = edad.toString()
                    txtAltura.text = String.format("%.2f", altura)
                    txtPeso.text = String.format("%.2f", peso)
                    txtIMC.text = String.format("%.2f", imc)
                }
            }
        }

        // Configurar el listener para el botón de editar perfil
        imgEditarPerfil.setOnClickListener {
            val intent = Intent(requireContext(), EditarPerfil::class.java)
            startActivity(intent)
        }

        // Configurar el listener para el botón de cerrar sesión
        imgVolver.setOnClickListener {
            val intent = Intent(requireContext(), login::class.java)
            startActivity(intent)
        }

        return root
    }

    // Método que se llama cuando se destruye la vista del fragmento
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}