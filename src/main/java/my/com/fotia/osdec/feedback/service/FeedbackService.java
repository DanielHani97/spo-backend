package my.com.fotia.osdec.feedback.service;

import java.util.List;

import my.com.fotia.osdec.feedback.model.Feedback;

public interface FeedbackService {

	Feedback findById(String id);
	
	Feedback findByType(String type);
	
	List<Feedback> findAll();
	
	void save(Feedback feedback);
	
	void save(List<Feedback> ls);
	
	long count();
	
}
