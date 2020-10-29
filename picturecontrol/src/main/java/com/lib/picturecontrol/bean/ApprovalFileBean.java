package com.lib.picturecontrol.bean;

/**
 * 附件对象(文件)
 * @author gyh
 *
 */
public class ApprovalFileBean {

	public String id;
	public String name;
	public String size;
	public String path;
	public String uuID;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUuID() {
		return uuID;
	}

	public void setUuID(String uuID) {
		this.uuID = uuID;
	}

	public ApprovalFileBean(String id, String name, String size, String path) {
		super();
		this.id = id;
		this.name = name;
		this.size = size;
		this.path = path;
	}
	
	public ApprovalFileBean(String id, String name, String size, String path, String uuid) {
		super();
		this.id = id;
		this.name = name;
		this.size = size;
		this.path = path;
		this.uuID = uuid;
	}
	
	public ApprovalFileBean(String name, String size, String path) {
		super();
		this.name = name;
		this.size = size;
		this.path = path;
	}
	
	public ApprovalFileBean() {
	}
}
