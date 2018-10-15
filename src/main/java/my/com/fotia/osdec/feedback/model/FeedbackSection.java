package my.com.fotia.osdec.feedback.model;

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
@Table(name = "feedbackSection")
public class FeedbackSection {

	@Id
	@Column(nullable = false, columnDefinition="VARCHAR(100)")
	private String id;
	
	private String title;
	
	@OneToMany(mappedBy="feedbackSection", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JsonManagedReference
	private List<FeedbackSectionQuestion> feedbackSectionQuestion;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="feedback_id", nullable=false)
	@JsonBackReference
	private Feedback feedback;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<FeedbackSectionQuestion> getFeedbackSectionQuestion() {
		return feedbackSectionQuestion;
	}

	public void setFeedbackSectionQuestion(List<FeedbackSectionQuestion> feedbackSectionQuestion) {
		this.feedbackSectionQuestion = feedbackSectionQuestion;
	}

	public Feedback getFeedback() {
		return feedback;
	}

	public void setFeedback(Feedback feedback) {
		this.feedback = feedback;
	}
	
}
