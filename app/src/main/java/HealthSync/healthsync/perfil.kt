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

class perfil : Fragment() {

    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val perfilModelProvider = ViewModelProvider(this).get(PerfilViewModel::class.java)
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val txtNombrePerfil = root.findViewById<TextView>(R.id.txtNombrePerfil)
        val txtEdad = root.findViewById<TextView>(R.id.txtEdad)
        val txtAltura = root.findViewById<TextView>(R.id.txtAltura)
        val txtPeso = root.findViewById<TextView>(R.id.txtPeso)
        val txtIMC = root.findViewById<TextView>(R.id.txtIMC)
        val imgEditarPerfil = root.findViewById<ImageView>(R.id.imgEditarPerfil)
        val imgVolver = root.findViewById<ImageView>(R.id.imgCerrarSesion)

        // Obtener el correo del usuario que inició sesión
        val userEmail = login.userEmail

        // Cargar datos del usuario desde la base de datos
        GlobalScope.launch(Dispatchers.IO) {
            val objConexion = ClaseConexion().CadenaConexion()

            val query = """
                SELECT u.nombre, c.edad, c.altura, c.peso, c.imc
                FROM Usuarios u
                JOIN Cliente c ON u.idUsuario = c.idUsuario
                WHERE u.correo = ?
            """

            val stmt = objConexion?.prepareStatement(query)
            stmt?.setString(1, userEmail)
            val resultado = stmt?.executeQuery()

            if (resultado?.next() == true) {
                val nombre = resultado.getString("nombre")
                val edad = resultado.getInt("edad")
                val altura = resultado.getDouble("altura")
                val peso = resultado.getDouble("peso")
                val imc = resultado.getDouble("imc")

                withContext(Dispatchers.Main) {
                    txtNombrePerfil.text = nombre
                    txtEdad.text = edad.toString()
                    txtAltura.text = String.format("%.2f", altura)
                    txtPeso.text = String.format("%.2f", peso)
                    txtIMC.text = String.format("%.2f", imc)
                }
            }
        }

        imgEditarPerfil.setOnClickListener {
            val intent = Intent(requireContext(), EditarPerfil::class.java)
            startActivity(intent)
        }

        imgVolver.setOnClickListener {
            val intent = Intent(requireContext(), login::class.java)
            startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}