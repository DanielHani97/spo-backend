package my.com.fotia.osdec.certification.user.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import my.com.fotia.osdec.certification.model.Certification;
import my.com.fotia.osdec.references.model.Filestorage;
import my.com.fotia.osdec.user.model.User;

@Entity
@Table(name="certificationUser")
public class CertificationUser {
	
	@Id
	@Column(nullable = false, columnDefinition="VARCHAR(100)")
	private String id;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Certification certification;
	
	@Column(columnDefinition="TEXT")
	private String coach_remarks;
	
	@Column(columnDefinition="TEXT")
	private String admin_remarks;
	
	@Column(columnDefinition="CHAR")
	private String status;
	// 1=Permohonan Baru
	// 2=Penilaian
	// 3=Diterima
	// 4=Ditolak
	// 5=Dibatal
	
	@Column(columnDefinition="CHAR")
	private String statusResult;
	// 1=Lulus
	// 2=Tidak Lulus
	// 3=Lain-lain
	
	@Column(columnDefinition = "TEXT")
	private String remarks;
	
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
	
	@OneToOne(cascade = CascadeType.MERGE)
	private Filestorage cert;

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

	public Certification getCertification() {
		return certification;
	}

	public void setCertification(Certification certification) {
		this.certification = certification;
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

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusResult() {
		return statusResult;
	}

	public void setStatusResult(String statusResult) {
		this.statusResult = statusResult;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Filestorage getCert() {
		return cert;
	}

	public void setCert(Filestorage cert) {
		this.cert = cert;
	}
}
