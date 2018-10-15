package my.com.fotia.osdec.coaching.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToOne;

import my.com.fotia.osdec.general.agency.model.Agency;
import my.com.fotia.osdec.references.model.Filestorage;
import my.com.fotia.osdec.user.model.User;
import my.com.fotia.osdec.technology.model.Technology;

@Entity
@Table(name="coaching")
public class Coaching {

	@Id
	@Column(nullable = false, columnDefinition="VARCHAR(100)")
	private String id;
	
	private String name;
	
	@OneToOne
	private User user;
	
	@OneToOne
	private Agency agency;
	
	@OneToOne
	private Technology frontend;
	
	@OneToOne
	private Technology backend;
	
	@OneToOne
	private Technology database;
	
	private String frontendlevel;
	private String backendlevel;
	private String databaselevel;
	private String mandayUsed;
	private String mandayReserved;
	private String status;
	
	@Column(columnDefinition="TEXT")
	private String remarks;
	
	@Column(columnDefinition="TEXT")
	private String admin_remarks;

	private String kelayakan;
	
	@Column(columnDefinition="TEXT")
	private String coach_remarks;
	
	private String duration;

	@Column(columnDefinition="TIMESTAMP")
	private Date starting_date;
	
	@Column(columnDefinition="TIMESTAMP")
	private Date ending_date;
	
	@OneToOne
	private User createdBy;
	
	@Column(columnDefinition="TIMESTAMP")
	private Date createdDate;
	
	@OneToOne
	private User modifiedBy;
	
	@Column(columnDefinition="TIMESTAMP")
	private Date modifiedDate;
	
	@OneToOne
	private User evaluatedBy;
	
	@Column(columnDefinition="TIMESTAMP")
	private Date evaluatedDate;
	
	@OneToOne
	private User approvedBy;
	
	@Column(columnDefinition="TIMESTAMP")
	private Date approvedDate;
	
	@OneToOne
	private User verifiedBy;
	
	@Column(columnDefinition="TIMESTAMP")
	private Date verifiedDate;
	
	@OneToOne(cascade = CascadeType.MERGE)
	private Filestorage urs;
	
	@OneToOne(cascade = {CascadeType.MERGE})
	private Filestorage srs;
	
	@OneToOne(cascade = CascadeType.MERGE)
	private Filestorage sds;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMandayUsed() {
		return mandayUsed;
	}

	public String getMandayReserved() {
		return mandayReserved;
	}

	public void setMandayUsed(String mandayUsed) {
		this.mandayUsed = mandayUsed;
	}

	public void setMandayReserved(String mandayReserved) {
		this.mandayReserved = mandayReserved;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Agency getAgency() {
		return agency;
	}

	public void setAgency(Agency agency) {
		this.agency = agency;
	}

	public Technology getFrontend() {
		return frontend;
	}

	public void setFrontend(Technology frontend) {
		this.frontend = frontend;
	}

	public Technology getBackend() {
		return backend;
	}

	public void setBackend(Technology backend) {
		this.backend = backend;
	}

	public Technology getDatabase() {
		return database;
	}

	public void setDatabase(Technology database) {
		this.database = database;
	}

	public String getFrontendlevel() {
		return frontendlevel;
	}

	public void setFrontendlevel(String frontendlevel) {
		this.frontendlevel = frontendlevel;
	}

	public String getBackendlevel() {
		return backendlevel;
	}

	public void setBackendlevel(String backendlevel) {
		this.backendlevel = backendlevel;
	}

	public String getDatabaselevel() {
		return databaselevel;
	}

	public void setDatabaselevel(String databaselevel) {
		this.databaselevel = databaselevel;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getAdmin_remarks() {
		return admin_remarks;
	}

	public void setAdmin_remarks(String admin_remarks) {
		this.admin_remarks = admin_remarks;
	}
	
	public String getKelayakan() {
		return kelayakan;
	}

	public void setKelayakan(String kelayakan) {
		this.kelayakan = kelayakan;
	}

	public String getCoach_remarks() {
		return coach_remarks;
	}

	public void setCoach_remarks(String coach_remarks) {
		this.coach_remarks = coach_remarks;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}
	
	public Date getStarting_date() {
		return starting_date;
	}

	public void setStarting_date(Date starting_date) {
		this.starting_date = starting_date;
	}

	public Date getEnding_date() {
		return ending_date;
	}

	public void setEnding_date(Date ending_date) {
		this.ending_date = ending_date;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public User getModifiedBy() {
		return modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
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

	public User getVerifiedBy() {
		return verifiedBy;
	}

	public Date getVerifiedDate() {
		return verifiedDate;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
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

	public void setVerifiedBy(User verifiedBy) {
		this.verifiedBy = verifiedBy;
	}

	public void setVerifiedDate(Date verifiedDate) {
		this.verifiedDate = verifiedDate;
	}

	public Filestorage getUrs() {
		return urs;
	}

	public void setUrs(Filestorage urs) {
		this.urs = urs;
	}

	public Filestorage getSrs() {
		return srs;
	}

	public void setSrs(Filestorage srs) {
		this.srs = srs;
	}

	public Filestorage getSds() {
		return sds;
	}

	public void setSds(Filestorage sds) {
		this.sds = sds;
	}
	
}
