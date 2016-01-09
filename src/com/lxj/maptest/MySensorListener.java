package com.lxj.maptest;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class MySensorListener implements SensorEventListener{
	private SensorManager mSensorManager = null;
	private Sensor mSensor = null;
	private Context mContext;
	private float lastX;
	private MyOrientationListener myOrientationListener;
	public MySensorListener(Context context){
		this.mContext = context;
	}
	
	public void start(){
		mSensorManager = (SensorManager)this.mContext.getSystemService(Context.SENSOR_SERVICE);
		if(mSensorManager != null)
		mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		if(mSensor != null){
			mSensorManager.registerListener(this, mSensor,
					SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
		}
	}
	
	public void stop(){
	if(mSensorManager != null){
		mSensorManager.unregisterListener(this);
	}
	}
		
	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		if(event.sensor.getType() == Sensor.TYPE_ORIENTATION){
			float x = event.values[SensorManager.DATA_X];//获得x方向的角度
			if(Math.abs(x-lastX)>1.0){
				if(myOrientationListener!=null)
				myOrientationListener.OrientationChange(x);
			}
			lastX = x;
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
	public void setMyOrientationListener(MyOrientationListener mOrientationListener){
		this.myOrientationListener = mOrientationListener;
	}
	public interface MyOrientationListener{
		void OrientationChange(float x);
	}

}
