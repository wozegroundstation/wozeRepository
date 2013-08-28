package com.woze.groundstation;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.app.Activity;


public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        
    }

 

    
}
