package com.lib.picturecontrol.bean;

import android.graphics.Bitmap;

/**
 * 附件对象
 * @author gyh
 *
 */
public class ApprovalAttachBean {

	public String id;
	public String url;
	public Bitmap bm;
	public int imgRes;
	public boolean hasUploaded;
	
	public ApprovalAttachBean(String id, String url, Bitmap bm){
		this.id = id;
		this.url = url;
		this.bm = bm;
	}
	
	public ApprovalAttachBean(String url, Bitmap bm){
		this.url = url;
		this.bm = bm;
	}
	
	public ApprovalAttachBean(int imgRes){
		this.imgRes = imgRes;
	}
}
