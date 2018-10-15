package my.com.fotia.osdec.assesment.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "assesmentAnswer")
public class AssesmentAnswer {
	
	@Id
	private String id;
	
	private String option;
	
	private boolean answer;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="assesment_question_id", nullable=false)
	@JsonBackReference
	private AssesmentQuestion assesmentQuestion;

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

	public AssesmentQuestion getAssesmentQuestion() {
		return assesmentQuestion;
	}

	public void setAssesmentQuestion(AssesmentQuestion assesmentQuestion) {
		this.assesmentQuestion = assesmentQuestion;
	}

	public boolean isAnswer() {
		return answer;
	}

	public void setAnswer(boolean answer) {
		this.answer = answer;
	}
	
}
