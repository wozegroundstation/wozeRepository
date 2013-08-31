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
	//��ͼ���������
    private BMapManager mBMapManager = null;
    //��ʾ��ͼ��View
    private MapView mMapView = null;

    /*
     * ���о�����������KEYʱ��Ӧ������һ��Ҫд��my_app_Ӧ������Ҳ����˵"my_app_"�Ǳ���Ҫ�еģ���
     * �ٶȵ�ͼSDK�ṩ�ķ�������ѵģ��ӿ���ʹ�ô������ơ�������������Կ��key)���ſ�ʹ�ø���SDK��
     */
    public static final String BAIDU_MAP_KEY = "65C650DF20CB94956BD239E1641D3909222901A6";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     // ע�⣺���ڵ���setContentViewǰ��ʼ��BMapManager���󣬷���ᱨ��
        mBMapManager = new BMapManager(this.getApplicationContext());
       //MKGeneralListener()�¼�֪ͨ�ӿڣ��ýӿڷ�������״̬����Ȩ��֤�Ƚ�����û���Ҫʵ�ָýӿ��Դ�����Ӧ�¼�
        mBMapManager.init(BAIDU_MAP_KEY, new MKGeneralListener() {

            @Override
            public void onGetNetworkState(int iError) {
                if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
                    Toast.makeText(MainActivity.this.getApplicationContext(),
                            "���������������", 
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onGetPermissionState(int iError) {
                if (iError == MKEvent.ERROR_PERMISSION_DENIED) {
                    // ��ȨKey����
                    Toast.makeText(MainActivity.this.getApplicationContext(), 
                            "��������ȷ����ȨKey��", 
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        setContentView(R.layout.activity_main);
        
        /*
         * ��ȡ��Ļ��Ⱥ͸߶�
         */
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
        
       /*
        * ���ǵ�ͼ
        */
        mMapView = (MapView) this.findViewById(R.id.bmapsView);
        // �����������õ����ſؼ�
        mMapView.setBuiltInZoomControls(true);

        // ��ȡ��ͼ��������������������ƽ�ƺ�����
        MapController mMapController = mMapView.getController();

        // �ø����ľ�γ�ȹ���һ��GeoPoint����λ��΢�� (�� * 1E6)

        // �����찲�ŵľ�γ�ȣ�39.915 * 1E6��116.404 * 1E6
       /* GeoPoint mGeoPoint = new GeoPoint(
                (int) (39.915 * 1E6), 
                (int) (116.404 * 1E6));*/

        // �Ϻ����ֶ�������GPSγ�Ⱦ���ֵ:31.224078,121.540419
        /*GeoPoint mGeoPoint = new GeoPoint(
                (int) (31.224078 * 1E6), 
                (int) (121.540419 * 1E6));*/
        
        //�Ͼ��ʵ��ѧ��γ�ȣ� ��γ32.09 * 1E6   ����118.78 * 1E6
        GeoPoint mGeoPoint = new GeoPoint(
                (int) (32.09 * 1E6), 
                (int) (118.78 * 1E6));
        // ���õ�ͼ�����ĵ�
        mMapController.setCenter(mGeoPoint);
        // ���õ�ͼ�����ż��� ���ֵ��ȡֵ��Χ��[3,18]�� 
        mMapController.setZoom(13);
        // �ڵ�ͼ����ʾʵʱ��ͨ��Ϣʾ
        // mMapView.setTraffic(true);
        // ��ʾ����ͼ
        mMapView.setSatellite(true);
    }
    // ��д���·���������API
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
