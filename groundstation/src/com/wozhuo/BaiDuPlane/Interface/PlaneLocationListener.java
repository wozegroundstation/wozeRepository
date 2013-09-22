package com.wozhuo.BaiDuPlane.Interface;

import java.util.EventListener;

import com.baidu.location.BDLocation;
public interface PlaneLocationListener extends EventListener{
	public void isLocationChange(BDLocation location);
}
