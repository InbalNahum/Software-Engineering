package entity;

import java.io.Serializable;
import java.util.Date;

public class CustomerComplaint implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public enum Status{opened,closed};
	
	private int id;
	private String body;
	private Date createTime;
	private Status status;
	
	public CustomerComplaint() {}
	
	public CustomerComplaint(int id, String body, Date createTime, Status status) {
		super();
		this.id = id;
		this.body = body;
		this.createTime = createTime;
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}

}
