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
@Table(name = "assesmentQuestion")
public class AssesmentQuestion {

	@Id
	private String id;
	
	@Column(columnDefinition = "text")
	private String question;
	
	@OneToMany(mappedBy="assesmentQuestion", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JsonManagedReference
	private List<AssesmentAnswer> assesmentAnswer;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="assesment_id", nullable=false)
	@JsonBackReference
	private Assesment assesment;

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

	public List<AssesmentAnswer> getAssesmentAnswer() {
		return assesmentAnswer;
	}

	public void setAssesmentAnswer(List<AssesmentAnswer> assesmentAnswer) {
		this.assesmentAnswer = assesmentAnswer;
	}

	public Assesment getAssesment() {
		return assesment;
	}

	public void setAssesment(Assesment assesment) {
		this.assesment = assesment;
	}
}
