package HealthSync.healthsync

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
import HealthSync.healthsync.pregunta6.Companion.IMCtotal
import HealthSync.healthsync.editarPerfil.Companion.edadPerfilActu
import HealthSync.healthsync.editarPerfil.Companion.alturaPerfilActu
import HealthSync.healthsync.editarPerfil.Companion.pesoPerfilActu
import HealthSync.healthsync.editarPerfil.Companion.txtIMCactu
import HealthSync.healthsync.editarPerfil.Companion.enfermedadesPerfilActu
import HealthSync.healthsync.editarPerfil.Companion.isEdadPerfilActuInitialized
import HealthSync.healthsync.editarPerfil.Companion.isAlturaPerfilActuInitialized
import HealthSync.healthsync.editarPerfil.Companion.isPesoPerfilActuInitialized
import HealthSync.healthsync.editarPerfil.Companion.isTxtIMCactuInitialized
import HealthSync.healthsync.ui.perfil.PerfilViewModel
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import HealthSync.healthsync.databinding.FragmentPerfilBinding
import android.content.Intent
import android.widget.ImageView

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class perfil : Fragment() {

    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!
    private var param1: String? = null
    private var param2: String? = null

    private var shouldLoadActuData: Boolean = false

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
        val pefilModelProvider = ViewModelProvider(this).get(PerfilViewModel::class.java)
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val txtNombrePerfil = root.findViewById<TextView>(R.id.txtNombrePerfil)
        val txtEdad = root.findViewById<TextView>(R.id.txtEdad)
        val txtAltura = root.findViewById<TextView>(R.id.txtAltura)
        val txtPeso = root.findViewById<TextView>(R.id.txtPeso)
        val txtIMC = root.findViewById<TextView>(R.id.txtIMC)
        val txtGenero = root.findViewById<TextView>(R.id.txtGenero)
        val imgEditarPerfil = root.findViewById<ImageView>(R.id.imgEditarPerfil)

        if (shouldLoadActuData) {
            if (isEdadPerfilActuInitialized()) txtEdad.text = edadPerfilActu
            if (isAlturaPerfilActuInitialized()) txtAltura.text = alturaPerfilActu
            if (isPesoPerfilActuInitialized()) txtPeso.text = pesoPerfilActu
            if (isTxtIMCactuInitialized()) txtIMC.text = txtIMCactu
        } else {
            txtNombrePerfil.text = nombreUsuario
            txtEdad.text = edadSeleccionada
            txtAltura.text = estaturaSeleccionada
            txtPeso.text = pesoSeleccionado
            txtIMC.text = IMCtotal
            txtGenero.text = generoSeleccionado
        }

        imgEditarPerfil.setOnClickListener {
            shouldLoadActuData = true
            val intent = Intent(requireContext(), editarPerfil::class.java)
            startActivityForResult(intent, editarPerfil.REQUEST_CODE_ACTUALIZAR_PERFIL)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            perfil().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

