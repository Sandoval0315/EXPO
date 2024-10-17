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

public class activity_stepCounter extends AppCompatActivity implements  SensorEventListener {


    private TextView txtPasosConta;

    private TextView txtDistanciaConta;

    private TextView txtTiempoConta;

    private Button btnPausaContador;

    private SensorManager sensorManager;

    private Sensor steapCounterSensor;

    private  int contadordePasos = 0;

    private ProgressBar ProgressBar;

    private  boolean isPaused = false;

    private  long timePaused = 0;

    private  float steapLeangthInMeters = 0.762f;

    private  long starTime;

    private  int stepCountTarget = 12000;

    private TextView txtMetaPasosConta;

    private SensorEvent sensorEvent;

    private Handler Timehandler = new Handler();

    private  Runnable TimeRunnable = new Runnable() {


        @Override
        public void run() {
            long milis = System.currentTimeMillis() - starTime;
            int seconds = (int) (milis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            txtTiempoConta.setText(String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds));
            Timehandler.postDelayed(this, 1000);


        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        if (steapCounterSensor != null){
            sensorManager.unregisterListener(this);

            Timehandler.removeCallbacks(TimeRunnable);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (steapCounterSensor != null){
            sensorManager.registerListener(this, steapCounterSensor, SensorManager.SENSOR_DELAY_NORMAL);

            Timehandler.postDelayed(TimeRunnable, 0);

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
        ProgressBar = findViewById(R.id.ProgressBar);


        starTime = System.currentTimeMillis();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        steapCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        ProgressBar.setMax(stepCountTarget);

        txtMetaPasosConta.setText("Meta de pasos: " + stepCountTarget);
        if (steapCounterSensor == null) {
            txtPasosConta.setText("No se encontro el sensor de pasos");

        }




    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            contadordePasos = (int) sensorEvent.values[0];
            txtPasosConta.setText("Pasos contados: " + contadordePasos);
            ProgressBar.setProgress(contadordePasos);




            if (contadordePasos >= stepCountTarget) {
                txtMetaPasosConta.setText("Meta de pasos alcanzada");
            }

            float distance = contadordePasos * steapLeangthInMeters/100;
            txtDistanciaConta.setText(String.format(Locale.getDefault(), "Distancia recorrida: %.2f metros", distance));

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    public void onPaueseButtonClicked(View view){
        if (isPaused){
            isPaused = false;
            btnPausaContador.setText("Detener");
            starTime= System.currentTimeMillis() - timePaused;
            Timehandler.postDelayed(TimeRunnable, 0);
        }
        else {

            isPaused = true;
            btnPausaContador.setText("Continuar");
            starTime= System.currentTimeMillis() - timePaused;
            Timehandler.removeCallbacks(TimeRunnable);

        }
    }
}
