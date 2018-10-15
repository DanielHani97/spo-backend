package my.com.fotia.osdec.capability.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToOne;

import my.com.fotia.osdec.user.model.User;

@Entity
@Table(name="capabilityUser")
public class CapabilityUser {
	
	@Id
	@Column(nullable = false, columnDefinition="VARCHAR(100)")
	private String id;
	
	@OneToOne
	private User user;
	
	@OneToOne
	private Capability capability;
	
	private String status;
	
	@Column(columnDefinition="TEXT")
	private String coach_remarks;
	
	@Column(columnDefinition="TEXT")
	private String admin_remarks;
	
	@OneToOne
	private User createdBy;
	
	@Column(columnDefinition="TIMESTAMP")
	private Date createdDate;
	
	@OneToOne
	private User evaluatedBy;
	
	@Column(columnDefinition="TIMESTAMP")
	private Date evaluatedDate;
	
	@OneToOne
	private User approvedBy;
	
	@Column(columnDefinition="TIMESTAMP")
	private Date approvedDate;

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

	public Capability getCapability() {
		return capability;
	}

	public void setCapability(Capability capability) {
		this.capability = capability;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCoach_remarks() {
		return coach_remarks;
	}

	public void setCoach_remarks(String coach_remarks) {
		this.coach_remarks = coach_remarks;
	}

	public String getAdmin_remarks() {
		return admin_remarks;
	}

	public void setAdmin_remarks(String admin_remarks) {
		this.admin_remarks = admin_remarks;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public User getEvaluatedBy() {
		return evaluatedBy;
	}

	public Date getEvaluatedDate() {
		return evaluatedDate;
	}

	public User getApprovedBy() {
		return approvedBy;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setEvaluatedBy(User evaluatedBy) {
		this.evaluatedBy = evaluatedBy;
	}

	public void setEvaluatedDate(Date evaluatedDate) {
		this.evaluatedDate = evaluatedDate;
	}

	public void setApprovedBy(User approvedBy) {
		this.approvedBy = approvedBy;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	
	
}
