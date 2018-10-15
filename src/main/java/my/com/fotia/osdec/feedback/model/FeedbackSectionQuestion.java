package my.com.fotia.osdec.feedback.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;

import my.com.fotia.osdec.user.model.User;

@Entity
@Table(name = "feedbackSectionQuestion")
public class FeedbackSectionQuestion {

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
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="feedback_section_id", nullable=false)
	@JsonBackReference
	private FeedbackSection feedbackSection;
	
	private String createdby;
	
	private Date createdon;
	
	private String modifiedby;
	
	private Date modifiedon;
	
	@Transient
	private User user;

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

	public FeedbackSection getFeedbackSection() {
		return feedbackSection;
	}

	public void setFeedbackSection(FeedbackSection feedbackSection) {
		this.feedbackSection = feedbackSection;
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
}
