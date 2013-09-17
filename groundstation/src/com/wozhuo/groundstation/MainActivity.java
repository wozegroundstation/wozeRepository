package com.wozhuo.groundstation;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.wozhuo.BaiDuPlane.BaiduMapApplication;
import com.wozhuo.BaiDuPlane.PlaneItemizedOverlay;
import com.wozhuo.BaiDuPlane.Entity.Plane;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.app.Activity;
import android.graphics.drawable.Drawable;


public class MainActivity extends Activity {
	//地图引擎管理类
    private BMapManager mBMapManager = null;
    //显示地图的View
    private MapView mMapView = null;
    // MapView控制器对象的引用 
    protected MapController mMapController;
    //定位SDK的核心类   
    private LocationClient mLocationClient=null;
    private MyLocationListener mLocationListener;
    //地面站的位置信息数据  
    private LocationData locData;
    private LocationOverlay mLocationOverlay;
    private Button location_button;
    private BDLocation isChange_location=null;
	private PlaneItemizedOverlay  customItemizedOverlay;
    /* 
     * Application对象的引用 
     */  
    protected BaiduMapApplication mApplication;  
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        initApplication();
        setContentView(R.layout.activity_main);
        setWindowManager();
        groundStationlocation();
        buttonListener();
        
    }
    /*
     * 初始化地图引擎对象
     * @author android_xiaoliTao
     */
    public void initApplication(){
    	 mApplication = (BaiduMapApplication) this.getApplication();  
         
         if (mApplication.mMapManager == null) {  
             Log.e("BaiduMapApplication", "MainActivity mApplication.mMapManager is NULL!");  
               
             mApplication.mMapManager = new BMapManager(mApplication.mContext);  
             mApplication.initMapManager();  
         }  
    }
    /*
     * 获取屏幕宽度和高度
     * @author android_xiaoliTao
     */
    public void setWindowManager(){
    	DisplayMetrics  dm = new DisplayMetrics();  
        //取得窗口属性  
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //窗口的宽度和高度（像素）  
        int screenWidth =dm.widthPixels;
        int screeHight=dm.heightPixels;
        /*
         * 设置RelativeLayout占据屏幕宽度的2/3
         */
        RelativeLayout relativeLayout=(RelativeLayout)findViewById(R.id.RelativeLayout3);
        RelativeLayout.LayoutParams param_relativeLayout = new RelativeLayout.LayoutParams(2*screenWidth/3,screeHight);
        //设置RelativeLayout靠右显示（默认靠左显示）
        param_relativeLayout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 1);
        relativeLayout.setLayoutParams(param_relativeLayout);
    }
    /*
     * 提供通过GPS或者3G/2G网络（基站）定位，并将定位的结果（我当前的位置）标注在百度地图上。  
     *  
     * 注：当gps可用，而且获取了定位结果时，不再发起网络请求，直接返回给用户坐标。 
     * 如果gps不可用，再发起网络请求，进行定位。 
     * @author android_xiaoliTao 
     * 
     */  
    public void groundStationlocation(){
    	  
    	mLocationClient = new LocationClient(this.getApplicationContext());
    	mLocationListener = new MyLocationListener();  
        mLocationClient.registerLocationListener(mLocationListener); 
        //设置定位参数
        LocationClientOption locationOption = new LocationClientOption();  
        locationOption.setOpenGps(true);  
        locationOption.setCoorType("bd09ll");  
        locationOption.setPriority(LocationClientOption.GpsFirst);  
        locationOption.setAddrType("all");  
        locationOption.setProdName("GPS定位");
        locationOption.setScanSpan(1000);
        mLocationClient.setLocOption(locationOption); 
        
        mLocationClient.start();
        mLocationOverlay = new LocationOverlay(mMapView);  
        // 百度官方API文档解释：打开指南针，但是我试验觉得默认指南针就是打开的  
        mLocationOverlay.enableCompass();  
        locData = new LocationData();
        initMap();
    }
    /*
     * 初始化地图
     * @author android_xiaoliTao
     */
    private void initMap() { 
     mMapView = (MapView) this.findViewById(R.id.bmapsView);
     // 设置启用内置的缩放控件
     mMapView.setBuiltInZoomControls(true);

     // 获取地图控制器，可以用它控制平移和缩放
      mMapController = mMapView.getController();
     /* 
      * 用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
      *  南京邮电大学经纬度： 北纬32.086387 * 1E6   东经118.774869 * 1E6
      */
     GeoPoint mGeoPoint = new GeoPoint((int) (32.09 * 1E6), (int) (118.78 * 1E6));
     // 设置地图的中心点
     mMapController.setCenter(mGeoPoint);
     // 设置地图的缩放级别。 这个值的取值范围是[3,19]。 
     mMapController.setZoom(19);
     // 显示卫星图
     mMapView.setSatellite(true);
    }
    
    /* 
     * 在地图上标注定位得到我当前的位置 
     * @param location
     * @author android_xiaoliTao 
     */  
    private void markLocation(BDLocation location) { 
        locData.latitude = location.getLatitude();  
        locData.longitude = location.getLongitude();  
        locData.direction = location.getDerect();  
  
        // 判断是否有定位精度半径  
        if (location.hasRadius()) {  
            // 获取定位精度半径，单位是米  
            locData.accuracy = location.getRadius();  
        }  
  
        mLocationOverlay.setData(locData);  
        mMapView.getOverlays().add(mLocationOverlay);  
        mMapView.refresh();  
        
        // 将我的当前位置移动到地图的中心点  
        mMapController.animateTo(new GeoPoint((int) (locData.latitude * 1e6),(int) (locData.longitude * 1e6)));  
  
    }
  
    /*
     * 在百度地图上添加飞机图片
     * @author android_xiaoliTao
     */
      public void add_plane(BDLocation location){
    	  Plane plane=new Plane();
    	  plane.setMarker(getResources().getDrawable(R.drawable.ic_plane));
    	  plane.setGeoPoint(new GeoPoint((int) (location.getLatitude() * 1e6),(int) (location.getLongitude() * 1e6)));
    	  //plane.setGeoPoint(new GeoPoint((int) ((32.086387) * 1E6), (int) ((118.774869 )* 1E6)));
    	  Drawable marker=plane.getMarker();
    	  GeoPoint geoPoint=plane.getGeoPoint();
    	  customItemizedOverlay =new PlaneItemizedOverlay(marker);
    	  //添加飞机
    	  if(isChange_location==null){
          customItemizedOverlay.addOverlay(geoPoint);
		  mMapView.getOverlays().add(customItemizedOverlay);
    	  }else if(isChange_location!=null&&isChange_location!=location){
    		  //删除上一个飞机
    		  System.out.print("测试");
    		customItemizedOverlay.addOverlay(geoPoint);
    		//Boolean isRemove=mMapView.getOverlays().remove(customItemizedOverlay);
    		mMapView.getOverlays().clear();
    		  //添加新的飞机
    		//if(isRemove){
    		  mMapView.getOverlays().add(customItemizedOverlay);
    		//}
    	  }else{
    		  return;
    	  }
    	  isChange_location=location;
          mMapView.refresh(); // 刷新地图   
          
}
      /*
       * BUTTON监听
       * @author android_xiaoliTao
       */
      public void buttonListener(){
    	  location_button=(Button)findViewById(R.id.location);
          location_button.setOnClickListener(new OnClickListener(){

          	@Override
          	public void onClick(View v) {
          		//add_plane(location);
          		
          	}
          	 
           });
      }
      
      /*
       * 地面站位置覆盖物类
       * @author android_xiaoliTao
       */
      class LocationOverlay extends MyLocationOverlay {  
      	  
          public LocationOverlay(MapView mapView) {  
              super(mapView);  
          }  
      }
    /*
     * 监听地面站当前的位置类
     * @author android_xiaoliTao
     */
      class MyLocationListener implements BDLocationListener {
          //获取定位信息
  		@Override
  		public void onReceiveLocation(BDLocation location) {
  			if(location==null){
  				return;
  			}
  			    add_plane(location);
  	            // 在地图上标注定位得到地面站当前的位置  
  	            markLocation(location);
  	           
  	            
  		}

  		@Override
  		public void onReceivePoi(BDLocation arg0) {
  			// TODO Auto-generated method stub
  		}  
      }
      
   /*
    * 重写以下方法，管理API(non-Javadoc)
    * @author android_xiaoliTao
    */
      @Override
      protected void onResume() {
          mMapView.onResume();
          if (mBMapManager != null) {
              mBMapManager.start();
          }
          super.onResume();
      }

      @Override
      protected void onPause() {
          mMapView.onPause();
          if (mBMapManager != null) {
              mBMapManager.stop();
          }
          super.onPause();
      }

      @Override
      protected void onDestroy() {
          mMapView.destroy();
          if (mBMapManager != null) {
              mBMapManager.destroy();
              mBMapManager = null;
          }
          mLocationClient.stop();  
          mLocationClient.unRegisterLocationListener(mLocationListener);  
          this.mApplication.onTerminate();  
            
          super.onDestroy();  
      }
          

      }  
  