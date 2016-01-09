# BaiduMap_Android
调用百度地图，完成定位和标志功能
## 调用百度地图API
### 效果图：
![](https://github.com/reallin/BaiduMap_Android/blob/master/1.png)
### 功能
* 能定位
* 在地图做标志
* 能结合传感器正确地定向
### 主要接口
* 注册地图
```java
SDKInitializer.initialize(getApplicationContext()); 
	        setContentView(R.layout.activity_main);  
	        mMapView = (MapView) findViewById(R.id.bmapView); 
	        mBaiduMap = mMapView.getMap();
	        MapStatusUpdate update = MapStatusUpdateFactory.zoomTo(15.0f);
	        mBaiduMap.setMapStatus(update);
```
* 调用普通地图
```java
mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
```
* 调用卫星地图
```java
mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
```
