package com.lxj.maptest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.lxj.maptest.MySensorListener.MyOrientationListener;

public class MainActivity extends Activity {
	 MapView mMapView = null; 
	 private BaiduMap mBaiduMap;
		private LocationClient mLocationClient;
		private MyLocationListener mLocationListener;
		private boolean isFirst = true;
		private Context context;
		private BitmapDescriptor mIconLocation;
		private LocationMode mLocationMode;//指示图标模式
		private MySensorListener mySensorListener;
		private float locationX;
		private Marker marker = null;
		private OverlayOptions options;
		private BitmapDescriptor markBitmapDescriptor;
		//private MyOrientationListener myOrientationListener;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext()); 
		  
	        setContentView(R.layout.activity_main);  

			this.context = this;

			initView();
			// 初始化定位
			initLocation();
	        /*mMapView = (MapView) findViewById(R.id.bmapView); 
	        
	        mLocationMode = LocationMode.NORMAL;
			mLocationClient = new LocationClient(this);
			mLocationListener = new MyLocationListener();
			mLocationClient.registerLocationListener(mLocationListener);

			LocationClientOption option = new LocationClientOption();
			option.setCoorType("bd09ll");
			option.setIsNeedAddress(true);
			option.setOpenGps(true);
			option.setScanSpan(1000);
			mLocationClient.setLocOption(option);
	        mLocationClient = new LocationClient(this);
	        mLocationListener = new MyLocationListener();
	        mLocationClient.registerLocationListener(mLocationListener);
	        mBaiduMap = mMapView.getMap();*/
	      /*  MapStatusUpdate update = MapStatusUpdateFactory.zoomTo(15.0f);
	        mBaiduMap.setMapStatus(update);*/
	 /*       mClient = new LocationClient(this);
	        mListener = new MyLocationListener();*/
	        //自定义图标
	        mIconLocation = BitmapDescriptorFactory.fromResource(R.drawable.navi_map_gps_locked);
	        //mLocationClient.registerLocationListener(mListener);
	        /*LocationClientOption option = new LocationClientOption();
			option.setCoorType("bd09ll");
			option.setIsNeedAddress(true);
			option.setOpenGps(true);
			option.setScanSpan(1000);
			mClient.setLocOption(option);
	        */
	        initLocation();
	        mark();
	}
	 class myOrientationListener implements MyOrientationListener{

		@Override
		public void OrientationChange(float x) {
			// TODO Auto-generated method stub
			locationX = x;
		}
		
	}
	private void initLocation()
	{

		mLocationMode = LocationMode.NORMAL;
		mLocationClient = new LocationClient(this);
		mLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mLocationListener);

		LocationClientOption option = new LocationClientOption();
		option.setCoorType("bd09ll");
		option.setIsNeedAddress(true);
		option.setOpenGps(true);
		option.setScanSpan(1000);
		mLocationClient.setLocOption(option);
		// 初始化图标
		mIconLocation = BitmapDescriptorFactory
				.fromResource(R.drawable.navi_map_gps_locked);
		mySensorListener = new MySensorListener(this);
		myOrientationListener orientationListener = new myOrientationListener();
		mySensorListener.setMyOrientationListener(orientationListener);
		

	}

	private void initView()
	{
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
		mBaiduMap.setMapStatus(msu);
	}
	public class MyLocationListener implements BDLocationListener{

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			MyLocationData mData = new MyLocationData.Builder()
			.direction(locationX)
			.accuracy(location.getRadius())//
			.latitude(location.getLatitude())//
			.longitude(location.getLongitude())//
			.build();
			mBaiduMap.setMyLocationData(mData);
			MyLocationConfiguration configuration = new MyLocationConfiguration(LocationMode.NORMAL, true, mIconLocation);
			//MyLocationConfiguration myLocationConfiguration = new MyLocationConfiguration(arg0, arg1, arg2)
			mBaiduMap.setMyLocationConfigeration(configuration);
			if(isFirst){
				//只有第一次才定位
				LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
				MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(latLng);
				mBaiduMap.animateMapStatus(update);
				isFirst = false; 
				Toast.makeText(MainActivity.this, location.getAddrStr(), Toast.LENGTH_SHORT).show();
			}
		}
	
	}
	public void mark(){
		markBitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.maker);
		LatLng latLng = new LatLng(30.5, 114.3);
		 options = new MarkerOptions().position(latLng).icon(markBitmapDescriptor).zIndex(5);
		marker = (Marker)mBaiduMap.addOverlay(options);
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
		if(!mLocationClient.isStarted()){
			mLocationClient.start();
		}
		mySensorListener.start();
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		mBaiduMap.setMyLocationEnabled(false);

		mLocationClient.stop();
		mySensorListener.stop();
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
