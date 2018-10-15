package my.com.fotia.osdec.feedback.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "userFeedback")
public class UserFeedback {

	@Id
	@Column(nullable = false, columnDefinition="VARCHAR(100)")
	private String id;
	
	@Column(nullable = false, columnDefinition="VARCHAR(100)")
	private String feedbacktraxid;
	
	@Column(columnDefinition="text")
	private String objective;
	
	@Column(columnDefinition="VARCHAR(20)")
	private String type;
	
	private String title;
	
	private double marks;
	
	@OneToMany(mappedBy="userFeedback", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JsonManagedReference
	private List<UserFeedbackSection> userFeedbackSection;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getObjective() {
		return objective;
	}

	public void setObjective(String objective) {
		this.objective = objective;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<UserFeedbackSection> getUserFeedbackSection() {
		return userFeedbackSection;
	}

	public void setUserFeedbackSection(List<UserFeedbackSection> userFeedbackSection) {
		this.userFeedbackSection = userFeedbackSection;
	}

	public double getMarks() {
		return marks;
	}

	public void setMarks(double marks) {
		this.marks = marks;
	}

	public String getFeedbacktraxid() {
		return feedbacktraxid;
	}

	public void setFeedbacktraxid(String feedbacktraxid) {
		this.feedbacktraxid = feedbacktraxid;
	}
}
