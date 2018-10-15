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
@Table(name = "userFeedbackSection")
public class UserFeedbackSection {

	@Id
	@Column(nullable = false, columnDefinition="VARCHAR(100)")
	private String id;
	
	private String title;
	
	@OneToMany(mappedBy="userFeedbackSection", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JsonManagedReference
	private List<UserFeedbackSectionQuestion> userFeedbackSectionQuestion;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_feedback_id", nullable=false)
	@JsonBackReference
	private UserFeedback userFeedback;

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

	public List<UserFeedbackSectionQuestion> getUserFeedbackSectionQuestion() {
		return userFeedbackSectionQuestion;
	}

	public void setUserFeedbackSectionQuestion(List<UserFeedbackSectionQuestion> userFeedbackSectionQuestion) {
		this.userFeedbackSectionQuestion = userFeedbackSectionQuestion;
	}

	public UserFeedback getUserFeedback() {
		return userFeedback;
	}

	public void setUserFeedback(UserFeedback userFeedback) {
		this.userFeedback = userFeedback;
	}
	
}
