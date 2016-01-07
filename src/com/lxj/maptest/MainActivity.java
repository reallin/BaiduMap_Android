package com.lxj.maptest;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

public class MainActivity extends Activity {
	 MapView mMapView = null; 
	 private BaiduMap mBaiduMap;
		private LocationClient mClient;
		private MyLocationListener mListener;
		private boolean isFirst = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext()); 
		setContentView(R.layout.activity_main);
		  
	        setContentView(R.layout.activity_main);  
	        mMapView = (MapView) findViewById(R.id.bmapView); 
	        mBaiduMap = mMapView.getMap();
	        MapStatusUpdate update = MapStatusUpdateFactory.zoomTo(15.0f);
	        mBaiduMap.setMapStatus(update);
	        mClient = new LocationClient(this);
	        mListener = new MyLocationListener();
	        mClient.registerLocationListener(mListener);
	        LocationClientOption option = new LocationClientOption();
	        option.setCoorType("bd09ll");
	        option.setIsNeedAddress(true);
	        option.setOpenGps(true);
	        option.setScanSpan(1000);//每隔1s请求
	        
	}
	public class MyLocationListener implements BDLocationListener{

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			MyLocationData mData = new MyLocationData.Builder()
			.accuracy(location.getRadius())//下面的经纬度
			.longitude(location.getLongitude())
			.latitude(location.getAltitude())
			.build();
			mBaiduMap.setMyLocationData(mData);
			//MyLocationConfiguration myLocationConfiguration = new MyLocationConfiguration(arg0, arg1, arg2)
			if(isFirst){
				//只有第一次才定位
				LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
				MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(latLng);
				mBaiduMap.animateMapStatus(update);
				isFirst = false; 
			}
		}
	
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.map:
			mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);break;
		case R.id.weixing:
			mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);break;
		case R.id.traffic:
			if(mBaiduMap.isTrafficEnabled()){
				mBaiduMap.setTrafficEnabled(false);
				item.setTitle("实时交通己关闭"); 
			}else{
				mBaiduMap.setTrafficEnabled(true);
				item.setTitle("实时交通己打开"); 
			}break;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		mBaiduMap.setMyLocationEnabled(true);
		if(!mClient.isStarted()){
			mClient.start();
		}
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		mBaiduMap.setMyLocationEnabled(false);

			mClient.stop();
		
	}
	@Override  
    protected void onDestroy() {  
        super.onDestroy();  
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理  
        mMapView.onDestroy();  
    }  
    @Override  
    protected void onResume() {  
        super.onResume();  
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理  
        mMapView.onResume();  
        }  
    @Override  
    protected void onPause() {  
        super.onPause();  
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理  
        mMapView.onPause();  
        }  
}
