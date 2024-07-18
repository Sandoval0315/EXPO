package HealthSync.healthsync

import HealthSync.healthsync.databinding.FragmentHomeBinding
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import HealthSync.healthsync.pregunta1.Companion.generoSeleccionado
import HealthSync.healthsync.pregunta2.Companion.edadSeleccionada
import HealthSync.healthsync.pregunta3.Companion.estaturaSeleccionada
import HealthSync.healthsync.pregunta4.Companion.pesoSeleccionado
import HealthSync.healthsync.registrarse.Companion.nombreUsuario
import HealthSync.healthsync.pregunta5.Companion.enfermedadSeleccionada
import HealthSync.healthsync.pregunta6.Companion.imcSeleccionado
import HealthSync.healthsync.pregunta7.Companion.experienciaSeleccionada
import HealthSync.healthsync.ui.perfil.PerfilViewModel
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import HealthSync.healthsync.databinding.FragmentPerfilBinding
import android.content.Intent
import android.widget.ImageView


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [perfil.newInstance] factory method to
 * create an instance of this fragment.
 */
class perfil : Fragment() {

    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val pefilModelProvider = ViewModelProvider(this).get(PerfilViewModel::class.java)
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)
        val root: View =  binding.root

        val txtNombrePerfil = root.findViewById<TextView>(R.id.txtNombrePerfil)
        val txtEdad = root.findViewById<TextView>(R.id.txtEdad)
        val txtAltura = root.findViewById<TextView>(R.id.txtAltura)
        val txtPeso = root.findViewById<TextView>(R.id.txtPeso)
        val txtIMC = root.findViewById<TextView>(R.id.txtIMC)
        val txtGenero = root.findViewById<TextView>(R.id.txtGenero)
        val imgEditarPerfil = root.findViewById<ImageView>(R.id.imgEditarPerfil)

        txtNombrePerfil.text = nombreUsuario
        txtIMC.text = imcSeleccionado
        txtGenero.text = generoSeleccionado
        txtEdad.text = edadSeleccionada.toString()
        txtAltura.text = estaturaSeleccionada.toString()
        txtPeso.text = pesoSeleccionado.toString()

        imgEditarPerfil.setOnClickListener {
            val intent = Intent(requireActivity(), editarPerfil::class.java)
            startActivity(intent)
        }

        return  root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
