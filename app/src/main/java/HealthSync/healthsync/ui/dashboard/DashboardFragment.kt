package HealthSync.healthsync.ui.dashboard

import HealthSync.healthsync.Activity_rutinafuerza
import HealthSync.healthsync.R
import HealthSync.healthsync.activity_altaintensidad
import HealthSync.healthsync.activity_rutina_cardiovascular
import HealthSync.healthsync.activity_rutinas
import HealthSync.healthsync.activity_yoga
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import HealthSync.healthsync.databinding.FragmentDashboardBinding
import android.content.Intent
import android.widget.Button

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.lbRutinaFuerza
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val btnRutinaCardio = root.findViewById<Button>(R.id.btnRutinaCardio)
        val btnFuerzaPesas = root.findViewById<Button>(R.id.btnFuerzaPesas)
        val btnRunnig = root.findViewById<Button>(R.id.btnRunnig)
        val btnAltaIntensidad = root.findViewById<Button>(R.id.btnAltaIntensidad)
        val btnYoga = root.findViewById<Button>(R.id.btnYoga)

        btnRutinaCardio.setOnClickListener{
            val intent = Intent(requireContext(), activity_rutina_cardiovascular::class.java)
            startActivity(intent)
        }
        btnFuerzaPesas.setOnClickListener {
            val intent = Intent(requireContext(), Activity_rutinafuerza::class.java)
            startActivity(intent)
        }

        btnAltaIntensidad.setOnClickListener {
            val intent = Intent(requireContext(), activity_altaintensidad::class.java)
            startActivity(intent)
        }
        btnYoga.setOnClickListener {
            val intent = Intent(requireContext(), activity_yoga::class.java)
            startActivity(intent)
        }



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
