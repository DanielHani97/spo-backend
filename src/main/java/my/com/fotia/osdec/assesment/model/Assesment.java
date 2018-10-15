package my.com.fotia.osdec.assesment.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import my.com.fotia.osdec.technology.model.Technology;
import my.com.fotia.osdec.user.model.User;

@Entity
@Table(name = "assesment")
public class Assesment {

	@Id
	@Column(nullable = false, columnDefinition="VARCHAR(100)")
	private String id;
	
	@Column(columnDefinition="VARCHAR(20)")
	private String level;
	
	private String title;
	
	@Column(columnDefinition = "text")
	private String details;
	
	@Column(columnDefinition="VARCHAR(100)")
	private String instanceid;
	
	@Column(columnDefinition="VARCHAR(20)")
	private String category;
	
	@Column(columnDefinition="VARCHAR(100)")
	private String instancename;
	
	@OneToMany(mappedBy="assesment", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JsonManagedReference
	private List<AssesmentQuestion> assesmentQuestion;
	
	@OneToOne
	private Technology technology;
	
	private String createdby;
	
	private Date createdon;
	
	private String modifiedby;
	
	private Date modifiedon;
	
	private int questionno;
	
	@Transient
	private User user;
	
	public int getQuestionno() {
		return questionno;
	}

	public void setQuestionno(int questionno) {
		this.questionno = questionno;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getInstanceid() {
		return instanceid;
	}

	public void setInstanceid(String instanceid) {
		this.instanceid = instanceid;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getInstancename() {
		return instancename;
	}

	public void setInstancename(String instancename) {
		this.instancename = instancename;
	}

	public List<AssesmentQuestion> getAssesmentQuestion() {
		return assesmentQuestion;
	}

	public void setAssesmentQuestion(List<AssesmentQuestion> assesmentQuestion) {
		this.assesmentQuestion = assesmentQuestion;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public Date getCreatedon() {
		return createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	public String getModifiedby() {
		return modifiedby;
	}

	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}

	public Date getModifiedon() {
		return modifiedon;
	}

	public void setModifiedon(Date modifiedon) {
		this.modifiedon = modifiedon;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Technology getTechnology() {
		return technology;
	}

	public void setTechnology(Technology technology) {
		this.technology = technology;
	}
}
