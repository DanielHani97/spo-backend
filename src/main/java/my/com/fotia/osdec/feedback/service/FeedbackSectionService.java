package my.com.fotia.osdec.feedback.service;

import java.util.List;

import my.com.fotia.osdec.feedback.model.FeedbackSection;

public interface FeedbackSectionService {

	FeedbackSection findById(String id);
	
	List<FeedbackSection> findByFeedback_Id(String feedbackId);
	
	void save(FeedbackSection feedbackSection);
	
	void save(List<FeedbackSection> ls);
	
	void deleteByFeedbackId(String id);
	
	boolean existsByFeedback_Id(String id);
	
	boolean existsById(String id);
	
	void deleteById(String id);
}
