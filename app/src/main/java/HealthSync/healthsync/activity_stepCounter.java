package HealthSync.healthsync;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class activity_stepCounter extends AppCompatActivity implements SensorEventListener {

    private TextView txtPasosConta;
    private TextView txtDistanciaConta;
    private TextView txtTiempoConta;
    private Button btnPausaContador;
    private SensorManager sensorManager;
    private Sensor steapCounterSensor;
    private int contadordePasos = 0;
    private ProgressBar progressBar;
    private boolean isPaused = false;
    private long timePaused = 0;
    private float steapLeangthInMeters = 0.762f;
    private long starTime;
    private int stepCountTarget = 12000;
    private TextView txtMetaPasosConta;
    private Handler timeHandler = new Handler();

    private Runnable timeRunnable = new Runnable() {
        @Override
        public void run() {
            long milis = System.currentTimeMillis() - starTime;
            int seconds = (int) (milis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            txtTiempoConta.setText(String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds));
            timeHandler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        if (steapCounterSensor != null) {
            sensorManager.unregisterListener(this);
            timeHandler.removeCallbacks(timeRunnable);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (steapCounterSensor != null) {
            sensorManager.registerListener(this, steapCounterSensor, SensorManager.SENSOR_DELAY_NORMAL);
            timeHandler.postDelayed(timeRunnable, 0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_step_counter);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtPasosConta = findViewById(R.id.txtPasosConta);
        txtDistanciaConta = findViewById(R.id.txtDistanciaConta);
        txtTiempoConta = findViewById(R.id.txtTiempoConta);
        btnPausaContador = findViewById(R.id.btnPausaContador);
        txtMetaPasosConta = findViewById(R.id.txtMetaPasosConta);
        progressBar = findViewById(R.id.ProgressBar);

        starTime = System.currentTimeMillis();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        steapCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        progressBar.setMax(stepCountTarget);
        txtMetaPasosConta.setText("Meta de pasos: " + stepCountTarget);
        if (steapCounterSensor == null) {
            txtPasosConta.setText("No se encontró el sensor de pasos");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            contadordePasos = (int) event.values[0];
            txtPasosConta.setText("Pasos contados: " + contadordePasos);
            progressBar.setProgress(contadordePasos);

            if (contadordePasos >= stepCountTarget) {
                txtMetaPasosConta.setText("Meta de pasos alcanzada");
            }

            float distance = contadordePasos * steapLeangthInMeters / 100;
            txtDistanciaConta.setText(String.format(Locale.getDefault(), "Distancia recorrida: %.2f metros", distance));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        
    }

    public void onPausaButtonClicked(View view) {
        if (isPaused) {
            isPaused = false;
            btnPausaContador.setText("Detener");
            starTime = System.currentTimeMillis() - timePaused;
            timeHandler.postDelayed(timeRunnable, 0);
        } else {
            isPaused = true;
            btnPausaContador.setText("Continuar");
            timePaused = System.currentTimeMillis() - starTime;
            timeHandler.removeCallbacks(timeRunnable);
        }
    }
}
