package my.com.fotia.osdec.feedback.service;

import java.util.List;

import my.com.fotia.osdec.feedback.model.UserFeedback;

public interface UserFeedbackService {

	UserFeedback findById(String id);
	
	UserFeedback findByType(String type);
	
	List<UserFeedback> findAll();
	
	void save(UserFeedback userFeedback);
	
	void save(List<UserFeedback> ls);
	
	long count();
	
	UserFeedback findByFeedbacktraxid(String id);
	
}
