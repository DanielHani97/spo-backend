package my.com.fotia.osdec.feedback.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "feedback")
public class Feedback {

	@Id
	@Column(nullable = false, columnDefinition="VARCHAR(100)")
	private String id;
	
	@Column(columnDefinition="text")
	private String objective;
	
	@Column(columnDefinition="VARCHAR(20)")
	private String type;
	
	private String title;
	
	@OneToMany(mappedBy="feedback", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JsonManagedReference
	private List<FeedbackSection> feedbackSection;
	
	@Transient
	private String instanceid;
	
	public String getInstanceid() {
		return instanceid;
	}

	public void setInstanceid(String instanceid) {
		this.instanceid = instanceid;
	}

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

	public List<FeedbackSection> getFeedbackSection() {
		return feedbackSection;
	}

	public void setFeedbackSection(List<FeedbackSection> feedbackSection) {
		this.feedbackSection = feedbackSection;
	}
}
