package my.com.fotia.osdec.feedback.model;

import my.com.fotia.osdec.user.model.User;

public class FeedbackCollection {

	private Feedback feedback;
	private FeedbackSection[] feedbackSection;
	private FeedbackSectionQuestion[] FeedbackSectionQuestion;
	private User createdby;
	private User user;
	private String type;
	
	public Feedback getFeedback() {
		return feedback;
	}
	public void setFeedback(Feedback feedback) {
		this.feedback = feedback;
	}
	public FeedbackSection[] getFeedbackSection() {
		return feedbackSection;
	}
	public void setFeedbackSection(FeedbackSection[] feedbackSection) {
		this.feedbackSection = feedbackSection;
	}
	public FeedbackSectionQuestion[] getFeedbackSectionQuestion() {
		return FeedbackSectionQuestion;
	}
	public void setFeedbackSectionQuestion(FeedbackSectionQuestion[] feedbackSectionQuestion) {
		FeedbackSectionQuestion = feedbackSectionQuestion;
	}
	public User getCreatedby() {
		return createdby;
	}
	public void setCreatedby(User createdby) {
		this.createdby = createdby;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
