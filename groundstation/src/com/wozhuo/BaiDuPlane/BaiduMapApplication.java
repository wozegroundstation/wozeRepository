package com.wozhuo.BaiDuPlane;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
/** 
 * ��ʼ����ͼ������󣬴�Ź�����Դ�� 
 * ��Ҫ��AndroidMinifest.xml�ļ��е�application��ǩ���name���ԡ� 
 * {@link #<application android:name=".BaiduMapApplication" >} 
 * @author android_xiaoliTao 
 */  
public class BaiduMapApplication extends Application {
	/*
     * 
     * ���о�����������KEYʱ��Ӧ������һ��Ҫд��my_app_Ӧ������Ҳ����˵"my_app_"�Ǳ���Ҫ�еģ���
     * �ٶȵ�ͼSDK�ṩ�ķ�������ѵģ��ӿ���ʹ�ô������ơ�������������Կ��key)���ſ�ʹ�ø���SDK��
     */ 
	public static final String BAIDU_MAP_KEY = "65C650DF20CB94956BD239E1641D3909222901A6"; 
  
    /** 
     * Log��ӡ��ǩ 
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
     * ��ʼ��BMapManager���� 
     *  
     * @return boolean 
     */  
    public void initMapManager() {  
        boolean result = mMapManager.init(BAIDU_MAP_KEY, new MKGeneralListener() {  
  
            @Override  
            public void onGetNetworkState(int error) {  
                if (error == MKEvent.ERROR_NETWORK_CONNECT) {  
                    Toast.makeText(mContext, "���������������", Toast.LENGTH_LONG).show();  
                } else if (error == MKEvent.ERROR_NETWORK_DATA) {  
                    Toast.makeText(mContext, "������ȷ�ļ���������", Toast.LENGTH_LONG).show();  
                }  
            }  
  
            @Override  
            public void onGetPermissionState(int error) {  
                if (error == MKEvent.ERROR_PERMISSION_DENIED) {  
                    // ��ȨKey����  
                    Toast.makeText(mContext, "������ȨKey����", Toast.LENGTH_LONG).show();  
                }  
            }  
  
        });  
  
        if (!result) {  
            Toast.makeText(mContext, "��ʼ����ͼ����ʧ�ܣ�", Toast.LENGTH_LONG).show();  
        } else {  
            Log.i(TAG, "��ͼ�����ʼ���ɹ���");  
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