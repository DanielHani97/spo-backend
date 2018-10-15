package my.com.fotia.osdec.user.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import my.com.fotia.osdec.general.agency.model.Agency;
import my.com.fotia.osdec.general.agency.model.Company;
import my.com.fotia.osdec.general.project.model.Project;
import my.com.fotia.osdec.general.schema.model.Schema;
import my.com.fotia.osdec.general.skill.model.Skill;
import my.com.fotia.osdec.general.certificate.model.Certificate;
import my.com.fotia.osdec.general.grade.model.Grade;
import my.com.fotia.osdec.references.model.City;
import my.com.fotia.osdec.references.model.State;


@Entity
@Table(name="userSecurity")
public class User {

	@Id
	private String id;
	
	@Column(nullable=false, unique=true)
	private String username;
	
	@Column(nullable=false)
	private String name;

	@Column(nullable=false, unique=true)
	private String email;
	
	private String new_password;	
	private String old_password;
	private String address;
	
	private String position;
	private String postcode;
	private String phoneNo;
	
	private String created_by;
	private String modified_by;
	
	@Column(columnDefinition = "TIMESTAMP")
	private Date created_date;
	
	@Column(columnDefinition = "TIMESTAMP")
	private Date modified_date;
	
	@Column(name = "ENABLED")
    @NotNull
    private Boolean enabled;

    @Column(name = "LASTPASSWORDRESETDATE")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date lastPasswordResetDate;
    
    /*@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USER_AUTHORITY",
            joinColumns = {@JoinColumn(name = "USER_ID", columnDefinition="VARCHAR(100)", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "AUTHORITY_ID", columnDefinition="VARCHAR(100)", referencedColumnName = "ID")})
    private List<Authority> authorities;*/
    
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    @JsonIgnore
    private List<UserAuthorities> userAuthLs;
	
	@OneToOne
	private Agency agency;
	@OneToOne
	private State state;
	@OneToOne
	private City city;
	@OneToOne
	private Company company;
	
	@OneToMany
    @JsonIgnore
	private List<Project> ProjectLs;
	
	@OneToMany(mappedBy="user")
	@JsonManagedReference
	private List<Skill> skill;
	
	@OneToOne
	private Grade grade;
	
	@OneToOne
	private Schema schema;
	
	private byte[] image;
	
	private String type;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNew_password() {
		return new_password;
	}
	public void setNew_password(String new_password) {
		this.new_password = new_password;
	}
	public String getOld_password() {
		return old_password;
	}
	public void setOld_password(String old_password) {
		this.old_password = old_password;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	public String getModified_by() {
		return modified_by;
	}
	public void setModified_by(String modified_by) {
		this.modified_by = modified_by;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public Date getLastPasswordResetDate() {
		return lastPasswordResetDate;
	}
	public void setLastPasswordResetDate(Date lastPasswordResetDate) {
		this.lastPasswordResetDate = lastPasswordResetDate;
	}
	public Agency getAgency() {
		return agency;
	}
	public void setAgency(Agency agency) {
		this.agency = agency;
	}
	public Grade getGrade() {
		return grade;
	}
	public void setGrade(Grade grade) {
		this.grade = grade;
	}
	
	public Schema getSchema() {
		return schema;
	}
	
	public void setSchema(Schema schema) {
		this.schema = schema;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	public Date getCreated_date() {
		return created_date;
	}
	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}
	public Date getModified_date() {
		return modified_date;
	}
	public void setModified_date(Date modified_date) {
		this.modified_date = modified_date;
	}
	public List<UserAuthorities> getUserAuthLs() {
		return userAuthLs;
	}
	public void setUserAuthLs(List<UserAuthorities> userAuthLs) {
		this.userAuthLs = userAuthLs;
	}
	public List<Project> getProjectLs(){
		return ProjectLs;
	}
	public void setProjectLs(List<Project> ProjectLs) {
		this.ProjectLs = ProjectLs;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public List<Skill> getSkill() {
		return skill;
	}
	public void setSkill(List<Skill> skill) {
		this.skill = skill;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
}
