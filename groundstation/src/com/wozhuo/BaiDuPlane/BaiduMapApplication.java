package com.wozhuo.BaiDuPlane;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
/** 
 * 初始化地图引擎对象，存放共享资源。 
 * 需要在AndroidMinifest.xml文件中的application标签添加name属性。 
 * {@link #<application android:name=".BaiduMapApplication" >} 
 * @author android_xiaoliTao 
 */  
public class BaiduMapApplication extends Application {
	/*
     * 
     * 经研究发现在申请KEY时：应用名称一定要写成my_app_应用名（也就是说"my_app_"是必须要有的）。
     * 百度地图SDK提供的服务是免费的，接口无使用次数限制。您需先申请密钥（key)，才可使用该套SDK。
     */ 
	public static final String BAIDU_MAP_KEY = "65C650DF20CB94956BD239E1641D3909222901A6"; 
  
    /** 
     * Log打印标签 
     */  
    private static final String TAG = "BaiduMapApplication";  
  
    /* package */public BMapManager mMapManager;  
  
    /* package */public Context mContext;  
  
    @Override  
    public void onCreate() {  
        super.onCreate();  
        Log.i(TAG, "BaiduMapApplication onCreate()");  
  
        mContext = getApplicationContext();  
        mMapManager = new BMapManager(getApplicationContext());  
  
        initMapManager();  
    }  
  
    /** 
     * 初始化BMapManager对象 
     *  
     * @return boolean 
     */  
    public void initMapManager() {  
        boolean result = mMapManager.init(BAIDU_MAP_KEY, new MKGeneralListener() {  
  
            @Override  
            public void onGetNetworkState(int error) {  
                if (error == MKEvent.ERROR_NETWORK_CONNECT) {  
                    Toast.makeText(mContext, "您的网络出错啦！", Toast.LENGTH_LONG).show();  
                } else if (error == MKEvent.ERROR_NETWORK_DATA) {  
                    Toast.makeText(mContext, "输入正确的检索条件！", Toast.LENGTH_LONG).show();  
                }  
            }  
  
            @Override  
            public void onGetPermissionState(int error) {  
                if (error == MKEvent.ERROR_PERMISSION_DENIED) {  
                    // 授权Key错误：  
                    Toast.makeText(mContext, "您的授权Key出错！", Toast.LENGTH_LONG).show();  
                }  
            }  
  
        });  
  
        if (!result) {  
            Toast.makeText(mContext, "初始化地图引擎失败！", Toast.LENGTH_LONG).show();  
        } else {  
            Log.i(TAG, "地图引擎初始化成功！");  
        }  
    }  
  
    @Override  
    public void onTerminate() {  
        Log.i(TAG, "BaiduMapApplication onTerminate()");  
  
        if (mMapManager != null) {  
            mMapManager.destroy();  
            mMapManager = null;  
        }  
  
        super.onTerminate();  
    }  
  
}  