package com.wozhuo.BaiDuPlane.Entity;

import com.baidu.platform.comapi.basestruct.GeoPoint;

import android.graphics.drawable.Drawable;

public class Plane {
private Drawable marker;//¸²¸ÇÎï
private GeoPoint geoPoint;//¾­Î³¶È

public Drawable getMarker() {
	return marker;
}
public void setMarker(Drawable marker) {
	this.marker = marker;
}
public GeoPoint getGeoPoint() {
	return geoPoint;
}
public void setGeoPoint(GeoPoint geoPoint) {
	this.geoPoint = geoPoint;
}

}
