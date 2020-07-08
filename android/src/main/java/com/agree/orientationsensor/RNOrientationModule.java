
package com.agree.orientationsensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import javax.annotation.Nullable;

public class RNOrientationModule extends ReactContextBaseJavaModule implements SensorEventListener {

    private static final String TAG = "RNOrientationSensor";
    private final ReactApplicationContext reactContext;
    private final SensorManager sensorManager;

    private final Sensor accelerometer;
    private final Sensor magnetic;

    private float[] accelerometerValues = new float[3];
    private float[] magneticFieldValues = new float[3];

    private int interval;

    public RNOrientationModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        this.sensorManager = (SensorManager) reactContext.getSystemService(reactContext.SENSOR_SERVICE);
        accelerometer = this.sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetic = this.sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    private float calculateOrientation() {
        float[] values = new float[3];
        float[] R = new float[9];
        SensorManager.getRotationMatrix(R, null, accelerometerValues, magneticFieldValues);
        SensorManager.getOrientation(R, values);
        values[0] = (float) Math.toDegrees(values[0]);
        values[1] = (float) Math.toDegrees(values[1]);
        values[2] = (float) Math.toDegrees(values[2]);
        // 修正当设备竖直超过90度或横屏时的方向
        values[0]=values[0]-values[2];
        if(values[0]>180){
            values[0]=values[0]-360;
        }
        Log.i(TAG, values[0] + "");
        return values[0];
    }

    // RN Methods
    @ReactMethod
    public void isAvailable(Promise promise) {
        if (this.accelerometer == null || this.magnetic == null) {
            Log.e(TAG, "No Accelerometer found or No Magnetic found");
            // No sensor found, throw error
            promise.reject(new RuntimeException("No Accelerometer found or No Magnetic found"));
            return;
        }
        promise.resolve(null);
    }

    @ReactMethod
    public void setUpdateInterval(int newInterval) {
        this.interval = newInterval;
    }

    @ReactMethod
    public void startUpdates() {
        // Milisecond to Mikrosecond conversion
        sensorManager.registerListener(this, accelerometer, this.interval * 1000);
        sensorManager.registerListener(this, magnetic, this.interval * 1000);
    }

    @ReactMethod
    public void stopUpdates() {
        sensorManager.unregisterListener(this);
    }

    private void sendEvent(String eventName, @Nullable float azimuthAngle) {
        try {
            this.reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit(eventName, azimuthAngle);
        } catch (RuntimeException e) {
            Log.e("ERROR", "java.lang.RuntimeException: Trying to invoke Javascript before CatalystInstance has been set!");
        }
    }

    @Override
    public String getName() {
        return "RNOrientation";
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelerometerValues = event.values;
        }
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            magneticFieldValues = event.values;
        }
        float angle = calculateOrientation();
        sendEvent("RNOrientation", angle);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


}