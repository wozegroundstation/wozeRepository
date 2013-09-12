package com.wozhuo.BaiDuPlane;

import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.platform.comapi.basestruct.GeoPoint;

import android.graphics.drawable.Drawable;
/*
 *  �ڵ�ͼ����ʾһ����һ�鸲���
 * ��2.0.0�汾��ʼ��SDK��֧��ֱ�Ӽ̳�Overlay ,�û���ͨ���̳�ItemizedOverlay����Ӹ����
 * ��Ӹ������һ���������£�
 * 1.����OverlayItem��׼��overlay���ݣ�
 * 2.�̳�ItemizedOverlay��������дcreateItem()�� size()��������Ҫ����overlay����¼�������дonTap()������
 * 3.����MapView.getOverlays().add()�������overlay��mapview�У�
 * 4.����MapView.refresh()ʹOverlay��Ч��
 */
public class PlaneItemizedOverlay extends ItemizedOverlay<OverlayItem>{
	private List<OverlayItem> overlayItem = new ArrayList<OverlayItem>();
    //�����ڵ�ͼ�ϱ�ʶ���꣬��һ��ͼƬ��ע  
	public  PlaneItemizedOverlay(Drawable drawable) {
        super(drawable);
        	/*GeoPoint geoPoint1 = new GeoPoint( (int) ((32.09) * 1E6), (int) ((118.78)* 1E6));    
            overlayItem.add(new OverlayItem(geoPoint1, "A", "�ɻ�ģ��"));  
            //ˢ�µ�ͼ  
            populate();*/   
    } 
    
    public void addOverlay(){
    	GeoPoint geoPoint1 = new GeoPoint( (int) ((32.09) * 1E6), (int) ((118.78)* 1E6));    
        overlayItem.add(new OverlayItem(geoPoint1, "A", "�ɻ�ģ��"));  
        //ˢ�µ�ͼ  
        populate();
    }
	//����ָ����List������ÿһ������  
    @Override  
    protected OverlayItem createItem(int arg0) {  
        return overlayItem.get(arg0);  
    }  

    @Override  
    public int size() {  
        return overlayItem.size();  
    }
 
   
}

