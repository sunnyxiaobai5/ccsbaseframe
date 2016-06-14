package cn.ccsgroup.ccsframework.entity;

/******************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.entity.Annex.java]  
 * @ClassName:    [Annex]   
 * @Description:  [附件类]   
 * @Author:       [安宗源]   
 * @CreateDate:   [2014-1-8 下午06:43:55]   
 * @UpdateUser:   [安宗源]   
 * @UpdateDate:   [2014-1-8 下午06:43:55]   
 * @UpdateRemark: [新增]  
 * @Version:      [v1.0] 
 */
public class Annex {

	private Integer id;
	private String filename;
	private String fileformat;
	private byte[] filecontent;
	private double filesize;
	private Integer infoid;
	private Integer status;
	
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFileformat() {
		return fileformat;
	}
	public void setFileformat(String fileformat) {
		this.fileformat = fileformat;
	}
	
	public byte[] getFilecontent() {
		return filecontent;
	}
	public void setFilecontent(byte[] filecontent) {
		this.filecontent = filecontent;
	}
	public double getFilesize() {
		return filesize;
	}
	public void setFilesize(double filesize) {
		this.filesize = filesize;
	}
	public Integer getInfoid() {
		return infoid;
	}
	public void setInfoid(Integer infoid) {
		this.infoid = infoid;
	}
	
	
}
