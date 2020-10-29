package com.lib.picturecontrol.bean;

import java.io.Serializable;


/**
 * 附件类 T_ATTACHMENT
 * 
 * @author H.L.Wan
 * @version [版本号, 2015年6月30日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class Attachment implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1930268148708007527L;

	private int id;

	private Long relId;// 关联ID

	private Integer relType;// 关联类型

	private Long employeeId;// 员工ID

	private String employeeName;// 员工名称

	private Long companyId;// 公司ID

	private String fileName;// 附件名称

	private String host;// 文件服务器地址

	private String path;// 文件路径
	
	private Long fileSize ;//大小
	
	private String createTime;// 创建时间

	private Long otherRelId;// 其他关联ID
	  private String uuid;// 文件路径
		private String fullPath;// 文件路径
	private String localId;//本地标记，与绑定本地的数据源绑定
	/**
	 * 扩展字段
	 */
	private Long projectPicId;// projectPicID
	public String getUuid()
    {
        return uuid;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }


	public String getFullPath()
    {
        return fullPath;
    }

    public void setFullPath(String fullPath)
    {
        this.fullPath = fullPath;
    }


	public String getLocalId() {
		return localId;
	}

	public void setLocalId(String localId) {
		this.localId = localId;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Long getRelId() {
		return relId;
	}

	public void setRelId(Long relId) {
		this.relId = relId;
	}

	public Integer getRelType() {
		return relType;
	}

	public void setRelType(Integer relType) {
		this.relType = relType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	

	public Long getOtherRelId() {
		return otherRelId;
	}

	public void setOtherRelId(Long otherRelId) {
		this.otherRelId = otherRelId;
	}

	public Long getProjectPicId() {
	    return projectPicId;
    }

	public void setProjectPicId(Long projectPicId) {
	    this.projectPicId = projectPicId;
    }

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

}
