package com.wozhuo.BaiDuPlane;

import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.platform.comapi.basestruct.GeoPoint;

import android.graphics.drawable.Drawable;
/*
 *  在地图上显示一个或一组覆盖物。
 * 从2.0.0版本开始，SDK不支持直接继承Overlay ,用户可通过继承ItemizedOverlay来添加覆盖物。
 * 添加覆盖物的一般流程如下：
 * 1.创建OverlayItem，准备overlay数据；
 * 2.继承ItemizedOverlay，至少重写createItem()和 size()方法。若要处理overlay点击事件，请重写onTap()方法；
 * 3.调用MapView.getOverlays().add()方法添加overlay到mapview中；
 * 4.调用MapView.refresh()使Overlay生效。
 */
public class PlaneItemizedOverlay extends ItemizedOverlay<OverlayItem>{
	private List<OverlayItem> overlayItem = new ArrayList<OverlayItem>();
    //用于在地图上标识坐标，用一个图片标注  
	public  PlaneItemizedOverlay(Drawable drawable) {
        super(drawable);
        	/*GeoPoint geoPoint1 = new GeoPoint( (int) ((32.09) * 1E6), (int) ((118.78)* 1E6));    
            overlayItem.add(new OverlayItem(geoPoint1, "A", "飞机模型"));  
            //刷新地图  
            populate();*/   
    } 
    
    public void addOverlay(){
    	GeoPoint geoPoint1 = new GeoPoint( (int) ((32.09) * 1E6), (int) ((118.78)* 1E6));    
        overlayItem.add(new OverlayItem(geoPoint1, "A", "飞机模型"));  
        //刷新地图  
        populate();
    }
	//返回指定的List集合中每一个坐标  
    @Override  
    protected OverlayItem createItem(int arg0) {  
        return overlayItem.get(arg0);  
    }  

    @Override  
    public int size() {  
        return overlayItem.size();  
    }
 
   
}

