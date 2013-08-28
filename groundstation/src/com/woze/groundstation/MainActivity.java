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
        
    }

 

    
}
