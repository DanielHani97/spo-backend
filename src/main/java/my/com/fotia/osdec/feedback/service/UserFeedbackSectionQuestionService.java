package my.com.fotia.osdec.feedback.service;

import java.util.List;

import my.com.fotia.osdec.feedback.model.UserFeedbackSectionQuestion;

public interface UserFeedbackSectionQuestionService {
	
	void save(UserFeedbackSectionQuestion userFeedbackSectionQuestion);
	
	void save(List<UserFeedbackSectionQuestion> ls);
	
	UserFeedbackSectionQuestion findById(String id);
	
	boolean existsByUserFeedbackSection_Id(String id);
	
	boolean existsById(String id);
}
