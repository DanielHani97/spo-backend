package my.com.fotia.osdec.feedback.service;

import java.util.List;

import my.com.fotia.osdec.feedback.model.UserFeedbackSection;

public interface UserFeedbackSectionService {

	
	void save(UserFeedbackSection feedbackSection);
	
	void save(List<UserFeedbackSection> ls);
	
	UserFeedbackSection findById(String id);
	
	List<UserFeedbackSection> findByUserFeedback_Id(String feedbackId);
	
	boolean existsByUserFeedback_Id(String id);
	boolean existsById(String id);
}
