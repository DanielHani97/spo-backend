package my.com.fotia.osdec.assesment.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "userAssesmentAnswer")
public class UserAssesmentAnswer {
	
	@Id
	private String id;
	
	private String option;
	
	private boolean answer;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_assesment_question_id", nullable=false)
	@JsonBackReference
	private UserAssesmentQuestion userAssesmentQuestion;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public boolean isAnswer() {
		return answer;
	}

	public void setAnswer(boolean answer) {
		this.answer = answer;
	}

	public UserAssesmentQuestion getUserAssesmentQuestion() {
		return userAssesmentQuestion;
	}

	public void setUserAssesmentQuestion(UserAssesmentQuestion userAssesmentQuestion) {
		this.userAssesmentQuestion = userAssesmentQuestion;
	}
}
