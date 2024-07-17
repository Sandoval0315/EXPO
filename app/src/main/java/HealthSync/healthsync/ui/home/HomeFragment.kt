package HealthSync.healthsync.ui.home

import HealthSync.healthsync.R
import HealthSync.healthsync.activity_Hidra_y_Alimen
import HealthSync.healthsync.activity_contador_pasos
import HealthSync.healthsync.activity_rutinas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import HealthSync.healthsync.databinding.FragmentHomeBinding
import android.content.Intent
import android.widget.Button

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val btncontadordepaso = root.findViewById<Button>(R.id.btncontadordepaso)
        val btnaliyhidra = root.findViewById<Button>(R.id.btn_ali_y_hidra)
        val btnrutinas = root.findViewById<Button>(R.id.btnrutinas)
        val btnproductividad = root.findViewById<Button>(R.id.btnproductividad)
        val btnmotivacion = root.findViewById<Button>(R.id.btnmotivacion)
        val btnmapa = root.findViewById<Button>(R.id.btnmapa)

        btncontadordepaso.setOnClickListener {
            val intent = Intent(requireContext(), activity_contador_pasos::class.java)
            startActivity(intent)
        }
        btnaliyhidra.setOnClickListener {
            val intent = Intent(requireContext(), activity_Hidra_y_Alimen::class.java)
            startActivity(intent)
        }
        btnrutinas.setOnClickListener {
            val intent = Intent(requireContext(), activity_rutinas::class.java)
            startActivity(intent)
        }

        //programar este boton no encuentro la activity
       /*/ btnproductividad.setOnClickListener {
            val intent = Intent(requireContext(), ::class.java)
            startActivity(intent)
        }*/
        btnmotivacion.setOnClickListener {
            
        }g
        btnmapa.setOnClickListener {}

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}