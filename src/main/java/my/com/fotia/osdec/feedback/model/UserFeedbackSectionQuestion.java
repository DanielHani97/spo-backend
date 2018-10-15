package my.com.fotia.osdec.feedback.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "userFeedbackSectionQuestion")
public class UserFeedbackSectionQuestion {

	@Id
	@Column(nullable = false, columnDefinition="VARCHAR(100)")
	private String id;
	
	private String title;
	
	@Column(columnDefinition="VARCHAR(20)")
	private String type;
	
	private int min;
	
	private int max;
	
	private String minlbl;
	
	private String maxlbl;
	
	@Column(columnDefinition="text")
	private String detail;
	
	private int score;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_feedback_section_id", nullable=false)
	@JsonBackReference
	private UserFeedbackSection userFeedbackSection;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public String getMinlbl() {
		return minlbl;
	}

	public void setMinlbl(String minlbl) {
		this.minlbl = minlbl;
	}

	public String getMaxlbl() {
		return maxlbl;
	}

	public void setMaxlbl(String maxlbl) {
		this.maxlbl = maxlbl;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public UserFeedbackSection getUserFeedbackSection() {
		return userFeedbackSection;
	}

	public void setUserFeedbackSection(UserFeedbackSection userFeedbackSection) {
		this.userFeedbackSection = userFeedbackSection;
	}
	
	
}
