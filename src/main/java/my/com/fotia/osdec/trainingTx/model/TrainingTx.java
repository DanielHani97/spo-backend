package my.com.fotia.osdec.trainingTx.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import my.com.fotia.osdec.training.model.Training;
import my.com.fotia.osdec.user.model.User;

@Entity
@Table(name="trainingTx")
public class TrainingTx {
	
	@Id
	@Column(nullable = false, columnDefinition="VARCHAR(100)")
	private String id;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Training training;
	
	@Column(columnDefinition="TEXT")
	private String coach_remarks;
	
	@Column(columnDefinition="TEXT")
	private String admin_remarks;
	
	@Column(columnDefinition="CHAR")
	private String status;

	@Column(columnDefinition="CHAR")
	private String qualification;
	
	@Column(columnDefinition="TIMESTAMP")
	private Date approvedDate;
	@OneToOne
	private User approvedBy;
	
	@Column(columnDefinition="TIMESTAMP")
	private Date evaluatedDate;
	@OneToOne
	private User evaluatedBy;
	
	@Column(columnDefinition="TIMESTAMP")
	private Date createdDate;
	@OneToOne
	private User createdBy;

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public User getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(User approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Date getEvaluatedDate() {
		return evaluatedDate;
	}

	public void setEvaluatedDate(Date evaluatedDate) {
		this.evaluatedDate = evaluatedDate;
	}

	public User getEvaluatedBy() {
		return evaluatedBy;
	}

	public void setEvaluatedBy(User evaluatedBy) {
		this.evaluatedBy = evaluatedBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

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

	public Training getTraining() {
		return training;
	}

	public void setTraining(Training training) {
		this.training = training;
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

	public String getStatus() {
		return status;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
