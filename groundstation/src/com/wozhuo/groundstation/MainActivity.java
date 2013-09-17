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
	//��ͼ���������
    private BMapManager mBMapManager = null;
    //��ʾ��ͼ��View
    private MapView mMapView = null;
    // MapView��������������� 
    protected MapController mMapController;
    //��λSDK�ĺ�����   
    private LocationClient mLocationClient=null;
    private MyLocationListener mLocationListener;
    //����վ��λ����Ϣ����  
    private LocationData locData;
    private LocationOverlay mLocationOverlay;
    private Button location_button;
    private BDLocation isChange_location=null;
	private PlaneItemizedOverlay  customItemizedOverlay;
    /* 
     * Application��������� 
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
     * ��ʼ����ͼ�������
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
     * ��ȡ��Ļ��Ⱥ͸߶�
     * @author android_xiaoliTao
     */
    public void setWindowManager(){
    	DisplayMetrics  dm = new DisplayMetrics();  
        //ȡ�ô�������  
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //���ڵĿ�Ⱥ͸߶ȣ����أ�  
        int screenWidth =dm.widthPixels;
        int screeHight=dm.heightPixels;
        /*
         * ����RelativeLayoutռ����Ļ��ȵ�2/3
         */
        RelativeLayout relativeLayout=(RelativeLayout)findViewById(R.id.RelativeLayout3);
        RelativeLayout.LayoutParams param_relativeLayout = new RelativeLayout.LayoutParams(2*screenWidth/3,screeHight);
        //����RelativeLayout������ʾ��Ĭ�Ͽ�����ʾ��
        param_relativeLayout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 1);
        relativeLayout.setLayoutParams(param_relativeLayout);
    }
    /*
     * �ṩͨ��GPS����3G/2G���磨��վ����λ��������λ�Ľ�����ҵ�ǰ��λ�ã���ע�ڰٶȵ�ͼ�ϡ�  
     *  
     * ע����gps���ã����һ�ȡ�˶�λ���ʱ�����ٷ�����������ֱ�ӷ��ظ��û����ꡣ 
     * ���gps�����ã��ٷ����������󣬽��ж�λ�� 
     * @author android_xiaoliTao 
     * 
     */  
    public void groundStationlocation(){
    	  
    	mLocationClient = new LocationClient(this.getApplicationContext());
    	mLocationListener = new MyLocationListener();  
        mLocationClient.registerLocationListener(mLocationListener); 
        //���ö�λ����
        LocationClientOption locationOption = new LocationClientOption();  
        locationOption.setOpenGps(true);  
        locationOption.setCoorType("bd09ll");  
        locationOption.setPriority(LocationClientOption.GpsFirst);  
        locationOption.setAddrType("all");  
        locationOption.setProdName("GPS��λ");
        locationOption.setScanSpan(1000);
        mLocationClient.setLocOption(locationOption); 
        
        mLocationClient.start();
        mLocationOverlay = new LocationOverlay(mMapView);  
        // �ٶȹٷ�API�ĵ����ͣ���ָ���룬�������������Ĭ��ָ������Ǵ򿪵�  
        mLocationOverlay.enableCompass();  
        locData = new LocationData();
        initMap();
    }
    /*
     * ��ʼ����ͼ
     * @author android_xiaoliTao
     */
    private void initMap() { 
     mMapView = (MapView) this.findViewById(R.id.bmapsView);
     // �����������õ����ſؼ�
     mMapView.setBuiltInZoomControls(true);

     // ��ȡ��ͼ��������������������ƽ�ƺ�����
      mMapController = mMapView.getController();
     /* 
      * �ø����ľ�γ�ȹ���һ��GeoPoint����λ��΢�� (�� * 1E6)
      *  �Ͼ��ʵ��ѧ��γ�ȣ� ��γ32.086387 * 1E6   ����118.774869 * 1E6
      */
     GeoPoint mGeoPoint = new GeoPoint((int) (32.09 * 1E6), (int) (118.78 * 1E6));
     // ���õ�ͼ�����ĵ�
     mMapController.setCenter(mGeoPoint);
     // ���õ�ͼ�����ż��� ���ֵ��ȡֵ��Χ��[3,19]�� 
     mMapController.setZoom(19);
     // ��ʾ����ͼ
     mMapView.setSatellite(true);
    }
    
    /* 
     * �ڵ�ͼ�ϱ�ע��λ�õ��ҵ�ǰ��λ�� 
     * @param location
     * @author android_xiaoliTao 
     */  
    private void markLocation(BDLocation location) { 
        locData.latitude = location.getLatitude();  
        locData.longitude = location.getLongitude();  
        locData.direction = location.getDerect();  
  
        // �ж��Ƿ��ж�λ���Ȱ뾶  
        if (location.hasRadius()) {  
            // ��ȡ��λ���Ȱ뾶����λ����  
            locData.accuracy = location.getRadius();  
        }  
  
        mLocationOverlay.setData(locData);  
        mMapView.getOverlays().add(mLocationOverlay);  
        mMapView.refresh();  
        
        // ���ҵĵ�ǰλ���ƶ�����ͼ�����ĵ�  
        mMapController.animateTo(new GeoPoint((int) (locData.latitude * 1e6),(int) (locData.longitude * 1e6)));  
  
    }
  
    /*
     * �ڰٶȵ�ͼ����ӷɻ�ͼƬ
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
    	  //��ӷɻ�
    	  if(isChange_location==null){
          customItemizedOverlay.addOverlay(geoPoint);
		  mMapView.getOverlays().add(customItemizedOverlay);
    	  }else if(isChange_location!=null&&isChange_location!=location){
    		  //ɾ����һ���ɻ�
    		  System.out.print("����");
    		customItemizedOverlay.addOverlay(geoPoint);
    		//Boolean isRemove=mMapView.getOverlays().remove(customItemizedOverlay);
    		mMapView.getOverlays().clear();
    		  //����µķɻ�
    		//if(isRemove){
    		  mMapView.getOverlays().add(customItemizedOverlay);
    		//}
    	  }else{
    		  return;
    	  }
    	  isChange_location=location;
          mMapView.refresh(); // ˢ�µ�ͼ   
          
}
      /*
       * BUTTON����
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
       * ����վλ�ø�������
       * @author android_xiaoliTao
       */
      class LocationOverlay extends MyLocationOverlay {  
      	  
          public LocationOverlay(MapView mapView) {  
              super(mapView);  
          }  
      }
    /*
     * ��������վ��ǰ��λ����
     * @author android_xiaoliTao
     */
      class MyLocationListener implements BDLocationListener {
          //��ȡ��λ��Ϣ
  		@Override
  		public void onReceiveLocation(BDLocation location) {
  			if(location==null){
  				return;
  			}
  			    add_plane(location);
  	            // �ڵ�ͼ�ϱ�ע��λ�õ�����վ��ǰ��λ��  
  	            markLocation(location);
  	           
  	            
  		}

  		@Override
  		public void onReceivePoi(BDLocation arg0) {
  			// TODO Auto-generated method stub
  		}  
      }
      
   /*
    * ��д���·���������API(non-Javadoc)
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
  