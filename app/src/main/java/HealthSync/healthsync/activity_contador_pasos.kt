package HealthSync.healthsync

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/*class activity_contador_pasos : AppCompatActivity(), SensorEventListener {

    private var sensorManager: SensorManager?= null
    private  var running = false
    private var totalstep = 0f
    private var previototalstep = 0f

    val tv_stepsTaken = findViewById<TextView>(R.id.tv_stepsTaken)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contador_pasos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        loadData()
        resetSteps()

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

    }

    override fun onResume() {
        super.onResume()
        running = true
        val stepSensor : Sensor? = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        if (stepSensor == null){
            Toast.makeText(this, "No sensor detected on this device", Toast.LENGTH_SHORT).show()
        } else{
            sensorManager?.registerListener(this, stepSensor,SensorManager.SENSOR_DELAY_UI)
        }

    }


    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {

    if (running){
        totalstep = event!!.values[0]
        val currentSteps = totalstep.toInt() - previototalstep.toInt()
        tv_stepsTaken.text = ("$currentSteps")

        progrees_circular.apply{
            setProgressWithAnimation(currentSteps.toFloat())
        }
        }
    }

    fun resetSteps(){
        tv_stepsTaken.setOnClickListener{
            Toast.makeText(this,"Long tap to reset steps", Toast.LENGTH_SHORT).show()
        }

        tv_stepsTaken.setOnClickListener{
            previototalstep = totalstep
            tv_stepsTaken.text = 0.toString()
            saveData()

            true
        }
    }
    private fun saveData(){
        val sharedPreferences = getSharedPreferences("myPrefs",Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putFloat("key1",previototalstep)
        editor.apply()
    }

    private fun loadData(){
        val sharedPreferences = getSharedPreferences("myPrefs",Context.MODE_PRIVATE)
        val savedNumber = sharedPreferences.getFloat("key1",0f)
        log.d("activity_contador_pasos","$savedNumber")
        previototalstep = savedNumber
    }
}
*/