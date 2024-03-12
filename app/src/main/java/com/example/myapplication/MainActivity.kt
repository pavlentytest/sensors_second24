package com.example.myapplication

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), SensorEventListener {

    lateinit var sensorManager: SensorManager
    var lightSensor: Sensor? = null
    var pressureSensor: Sensor? = null
    var rotateSensor: Sensor? = null
    var tempSensor: Sensor? = null
    lateinit var lightText: TextView
    lateinit var pressureText: TextView
    lateinit var tempText: TextView
    lateinit var imageView: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val list: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
        println("Size=${list.size}")
        println(list.joinToString("\n"))

        lightText = findViewById(R.id.lightValue)
        pressureText = findViewById(R.id.pressureValue)
        tempText = findViewById(R.id.tempValue)
        imageView = findViewById(R.id.imageView)

        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        if(lightSensor != null) {
            sensorManager.registerListener(this,lightSensor, SensorManager.SENSOR_DELAY_GAME)
        }
        pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
        if(pressureSensor != null) {
            sensorManager.registerListener(this,pressureSensor, SensorManager.SENSOR_DELAY_GAME)
        }
        tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        if(tempSensor != null) {
            sensorManager.registerListener(this,tempSensor, SensorManager.SENSOR_DELAY_GAME)
        }
        rotateSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        if(rotateSensor != null) {
            sensorManager.registerListener(this,rotateSensor, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(lightSensor == null) {
            lightText.text = "No light sensor!"
        } else if(event!!.sensor.type == lightSensor!!.type) {
            lightText.text = "Light: ${event.values[0]}" // Lux
        }
        if(pressureSensor == null) {
            pressureText.text = "No pressure sensor!"
        } else if(event!!.sensor.type == pressureSensor!!.type) {
            pressureText.text = "Pressure: ${event.values[0]}" // kPa
        }
        if(tempSensor == null) {
            tempText.text = "No temperature sensor!"
        } else if(event!!.sensor.type == tempSensor!!.type) {
            tempText.text = "Temp: ${event.values[0]}" //
        }
        if(event!!.sensor.type == Sensor.TYPE_GYROSCOPE) {
            imageView.rotation = event.values[2]*10
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }


}