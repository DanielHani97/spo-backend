package my.com.fotia.osdec.assesment.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import my.com.fotia.osdec.assesment.model.AssesmentQuestion;

@Transactional
public interface AssesmentQuestionDao extends JpaRepository<AssesmentQuestion, Long> {

	AssesmentQuestion findById(String id);
	List<AssesmentQuestion> findAll();
	
	@Modifying
	@Query("DELETE from AssesmentQuestion where id = :id")
	void deleteById(@Param("id") String id);
	
	List<AssesmentQuestion> findByAssesment_IdAndQuestionContainingIgnoreCase
	(String assesmentId,String question, Pageable pageable);
	
	long countByAssesment_IdAndQuestionContainingIgnoreCase
	(String assesmentId,String question);
	
	boolean existsByIdAndAssesmentAnswer_IdAndAssesmentAnswer_Answer(String questionId, String answerId, boolean isAnswer);
	
}
