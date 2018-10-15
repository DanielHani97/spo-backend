package my.com.fotia.osdec.feedback.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import my.com.fotia.osdec.feedback.model.FeedbackSectionQuestion;

public interface FeedbackSectionQuestionService {

	FeedbackSectionQuestion findById(String id);
	
	void save(FeedbackSectionQuestion feedbackSectionQuestion);
	
	void save(List<FeedbackSectionQuestion> ls);
	
	void deleteByFeedbackSectionId(String id);
	
	boolean existsByFeedbackSection_Id(String id);
	
	boolean existsById(String id);
	
	void deleteById(String id);
}
