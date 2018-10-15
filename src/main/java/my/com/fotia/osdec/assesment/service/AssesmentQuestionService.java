package my.com.fotia.osdec.assesment.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import my.com.fotia.osdec.assesment.model.AssesmentQuestion;


public interface AssesmentQuestionService {

	AssesmentQuestion findById(String id);
	List<AssesmentQuestion> findAll();
	void deleteById(@Param("id") String id);
	void save(List<AssesmentQuestion> ls);
	void save(AssesmentQuestion assesmentQuestion);
	
	List<AssesmentQuestion> findByAssesment_IdAndQuestionContainingIgnoreCase
	(String assesmentId,String question, Pageable pageable);
	
	long countByAssesment_IdAndQuestionContainingIgnoreCase
	(String assesmentId,String question);
	
	boolean existsByIdAndAssesmentAnswer_IdAndAssesmentAnswer_Answer(String questionId, String answerId, boolean isAnswer);
}
