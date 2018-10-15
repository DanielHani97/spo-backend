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
@Table(name = "userAssesment")
public class UserAssesment {

	@Id
	@Column(nullable = false, columnDefinition="VARCHAR(100)")
	private String id;
	
	@Column(columnDefinition="VARCHAR(20)")
	private String level;
	
	@Column(columnDefinition="VARCHAR(20)")
	private String category;
	
	private int questionno;
	
	@OneToOne
	private Technology technology;
	
	private String userId;
	
	@OneToMany(mappedBy="userAssesment", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JsonManagedReference
	private List<UserAssesmentQuestion> userAssesmentQuestion;
	
	private String createdby;
	
	private Date createdon;
	
	private String modifiedby;
	
	private Date modifiedon;

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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getQuestionno() {
		return questionno;
	}

	public void setQuestionno(int questionno) {
		this.questionno = questionno;
	}

	public Technology getTechnology() {
		return technology;
	}

	public void setTechnology(Technology technology) {
		this.technology = technology;
	}

	public List<UserAssesmentQuestion> getUserAssesmentQuestion() {
		return userAssesmentQuestion;
	}

	public void setUserAssesmentQuestion(List<UserAssesmentQuestion> userAssesmentQuestion) {
		this.userAssesmentQuestion = userAssesmentQuestion;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
