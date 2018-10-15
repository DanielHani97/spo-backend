package my.com.fotia.osdec.assesment.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "userAssesmentQuestion")
public class UserAssesmentQuestion {

	@Id
	private String id;
	
	@Column(columnDefinition = "text")
	private String question;
	
	@OneToMany(mappedBy="userAssesmentQuestion", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JsonManagedReference
	private List<UserAssesmentAnswer> userAssesmentAnswer;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_assesment_id", nullable=false)
	@JsonBackReference
	private UserAssesment userAssesment;
	
	private String answer;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public List<UserAssesmentAnswer> getUserAssesmentAnswer() {
		return userAssesmentAnswer;
	}

	public void setUserAssesmentAnswer(List<UserAssesmentAnswer> userAssesmentAnswer) {
		this.userAssesmentAnswer = userAssesmentAnswer;
	}

	public UserAssesment getUserAssesment() {
		return userAssesment;
	}

	public void setUserAssesment(UserAssesment userAssesment) {
		this.userAssesment = userAssesment;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
