package HealthSync.healthsync

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import HealthSync.healthsync.databinding.ActivityNavigatioPrincipalBinding

class navigatioPrincipal : AppCompatActivity() {

    private lateinit var binding: ActivityNavigatioPrincipalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNavigatioPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)
//ocultar barra de arriba
        supportActionBar?.hide()
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_navigatio_principal)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.inicio, R.id.rutinas, R.id.estadisticas
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        if (intent.getBooleanExtra("ir_a_agregar_rutinas", false)) {
            navController.navigate(R.id.rutinas)
        }

        if (intent.getBooleanExtra("ir_al_home", false)) {
            navController.navigate(R.id.inicio)
        }


    }
}