package my.com.fotia.osdec.attendance.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToOne;
import my.com.fotia.osdec.user.model.User;

@Entity
@Table(name = "attendance")
public class Attendance {
	
	@Id
	@Column(nullable = false, columnDefinition = "VARCHAR(100)")
	private String id;
	
	@OneToOne
	private User user;
	
	private String title;
	
	private String instanceId;
	
	private String category;
	
	@Column(columnDefinition = "CHAR")
	private String status;
	
	@Column(columnDefinition = "TIMESTAMP")
	private Date date;
	
	@Column(columnDefinition = "TIMESTAMP")
	private Date start_time;
	
	@Column(columnDefinition = "TIMESTAMP")
	private Date end_time;
	
	@Column(columnDefinition = "TEXT")
	private String remarks;
	
	private String created_by;
	
	@Column(columnDefinition = "TIMESTAMP")
	private Date created_date;
	
	private String modified_by;
	
	@Column(columnDefinition = "TIMESTAMP")
	private Date modified_date;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

	public Date getDate() {
		return date;
	}

	public Date getModified_date() {
		return modified_date;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setModified_date(Date modified_date) {
		this.modified_date = modified_date;
	}

	public String getModified_by() {
		return modified_by;
	}

	public void setModified_by(String modified_by) {
		this.modified_by = modified_by;
	}
	
}
