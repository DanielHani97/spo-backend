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
@Table(name = "userAssesmentTrax")
public class UserAssesmentTrax {

	@Id
	@Column(nullable = false, columnDefinition="VARCHAR(100)")
	private String id;
	
	@Column(columnDefinition="VARCHAR(100)")
	private String userAssesId;
	
	@Column(columnDefinition="VARCHAR(100)")
	private String questionId;
	
	@Column(columnDefinition="VARCHAR(100)")
	private String answerId;
	
	@Column(columnDefinition="text")
	private String question;

	@Column(columnDefinition="text")
	private String answer;
	
	private int mark;
	
	@OneToOne
	private UserAssesment userAssesment;
	
	private String createdby;
	
	private Date createdon;
	
	private String modifiedby;
	
	private Date modifiedon;
	
	private String userId;
	
	private String technologyId;
	
	private String level;
	
	@Transient
	private User user;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserAssesId() {
		return userAssesId;
	}

	public void setUserAssesId(String userAssesId) {
		this.userAssesId = userAssesId;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getAnswerId() {
		return answerId;
	}

	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	public UserAssesment getUserAssesment() {
		return userAssesment;
	}

	public void setUserAssesment(UserAssesment userAssesment) {
		this.userAssesment = userAssesment;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTechnologyId() {
		return technologyId;
	}

	public void setTechnologyId(String technologyId) {
		this.technologyId = technologyId;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
}
