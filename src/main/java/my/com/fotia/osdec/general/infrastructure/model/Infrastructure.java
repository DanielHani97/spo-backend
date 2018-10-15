package my.com.fotia.osdec.general.infrastructure.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import my.com.fotia.osdec.general.agency.model.Agency;
import my.com.fotia.osdec.technology.model.Technology;
import my.com.fotia.osdec.user.model.User;

@Entity
@Table(name = "infrastructure")
public class Infrastructure {
	
	@Id
	@Column(nullable = false, columnDefinition="VARCHAR(100)")
	private String id;
	
	@OneToOne
	private User user;
	
	@OneToOne
	private Agency agency;
	
	@Column(nullable = false, columnDefinition = "TEXT")
	private String remarks;
	
	@Column(columnDefinition = "TEXT")
	private String adminRemarks;
	
	private String type;
	private String os;
	private String vcpu;
	private String memori;
	private String rootDisk;
	private String ephemeralDisk;
	private String swapDisk;
	private String persistentDisk;
	private String webServer;
	private String language;
	
	@OneToOne
	private Technology framework;
	
	@OneToOne
	private Technology database;
	
	private String status;
	
	@OneToOne
	private User createdBy;
	
	@Column(columnDefinition="TIMESTAMP")
	private Date createdDate;
	
	@OneToOne
	private User modifiedBy;
	
	@Column(columnDefinition="TIMESTAMP")
	private Date modifiedDate;

	public String getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public Agency getAgency() {
		return agency;
	}

	public String getRemarks() {
		return remarks;
	}

	public String getOs() {
		return os;
	}

	public String getVcpu() {
		return vcpu;
	}

	public String getMemori() {
		return memori;
	}

	public String getRootDisk() {
		return rootDisk;
	}

	public String getEphemeralDisk() {
		return ephemeralDisk;
	}

	public String getSwapDisk() {
		return swapDisk;
	}

	public String getWebServer() {
		return webServer;
	}

	public Technology getDatabase() {
		return database;
	}

	public String getStatus() {
		return status;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setAgency(Agency agency) {
		this.agency = agency;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public void setVcpu(String vcpu) {
		this.vcpu = vcpu;
	}

	public void setMemori(String memori) {
		this.memori = memori;
	}

	public void setRootDisk(String rootDisk) {
		this.rootDisk = rootDisk;
	}

	public void setEphemeralDisk(String ephemeralDisk) {
		this.ephemeralDisk = ephemeralDisk;
	}

	public void setSwapDisk(String swapDisk) {
		this.swapDisk = swapDisk;
	}

	public void setWebServer(String webServer) {
		this.webServer = webServer;
	}

	public void setDatabase(Technology database) {
		this.database = database;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public String getPersistentDisk() {
		return persistentDisk;
	}

	public Technology getFramework() {
		return framework;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setPersistentDisk(String persistentDisk) {
		this.persistentDisk = persistentDisk;
	}

	public void setFramework(Technology framework) {
		this.framework = framework;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getAdminRemarks() {
		return adminRemarks;
	}

	public void setAdminRemarks(String adminRemarks) {
		this.adminRemarks = adminRemarks;
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
	
		
}
