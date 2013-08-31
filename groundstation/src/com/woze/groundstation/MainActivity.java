package com.woze.groundstation;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.platform.comapi.basestruct.GeoPoint;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.app.Activity;


public class MainActivity extends Activity {
	//地图引擎管理类
    private BMapManager mBMapManager = null;
    //显示地图的View
    private MapView mMapView = null;

    /*
     * 经研究发现在申请KEY时：应用名称一定要写成my_app_应用名（也就是说"my_app_"是必须要有的）。
     * 百度地图SDK提供的服务是免费的，接口无使用次数限制。您需先申请密钥（key)，才可使用该套SDK。
     */
    public static final String BAIDU_MAP_KEY = "65C650DF20CB94956BD239E1641D3909222901A6";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     // 注意：请在调用setContentView前初始化BMapManager对象，否则会报错
        mBMapManager = new BMapManager(this.getApplicationContext());
       //MKGeneralListener()事件通知接口，该接口返回网络状态，授权验证等结果，用户需要实现该接口以处理相应事件
        mBMapManager.init(BAIDU_MAP_KEY, new MKGeneralListener() {

            @Override
            public void onGetNetworkState(int iError) {
                if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
                    Toast.makeText(MainActivity.this.getApplicationContext(),
                            "您的网络出错啦！", 
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onGetPermissionState(int iError) {
                if (iError == MKEvent.ERROR_PERMISSION_DENIED) {
                    // 授权Key错误：
                    Toast.makeText(MainActivity.this.getApplicationContext(), 
                            "请输入正确的授权Key！", 
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        setContentView(R.layout.activity_main);
        
        /*
         * 获取屏幕宽度和高度
         */
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
        
       /*
        * 卫星地图
        */
        mMapView = (MapView) this.findViewById(R.id.bmapsView);
        // 设置启用内置的缩放控件
        mMapView.setBuiltInZoomControls(true);

        // 获取地图控制器，可以用它控制平移和缩放
        MapController mMapController = mMapView.getController();

        // 用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)

        // 北京天安门的经纬度：39.915 * 1E6，116.404 * 1E6
       /* GeoPoint mGeoPoint = new GeoPoint(
                (int) (39.915 * 1E6), 
                (int) (116.404 * 1E6));*/

        // 上海市浦东新区的GPS纬度经度值:31.224078,121.540419
        /*GeoPoint mGeoPoint = new GeoPoint(
                (int) (31.224078 * 1E6), 
                (int) (121.540419 * 1E6));*/
        
        //南京邮电大学经纬度： 北纬32.09 * 1E6   东经118.78 * 1E6
        GeoPoint mGeoPoint = new GeoPoint(
                (int) (32.09 * 1E6), 
                (int) (118.78 * 1E6));
        // 设置地图的中心点
        mMapController.setCenter(mGeoPoint);
        // 设置地图的缩放级别。 这个值的取值范围是[3,18]。 
        mMapController.setZoom(13);
        // 在地图中显示实时交通信息示
        // mMapView.setTraffic(true);
        // 显示卫星图
        mMapView.setSatellite(true);
    }
    // 重写以下方法，管理API
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
        super.onDestroy();
    }
 

    
}
