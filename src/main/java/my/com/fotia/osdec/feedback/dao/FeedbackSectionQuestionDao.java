package my.com.fotia.osdec.feedback.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import my.com.fotia.osdec.feedback.model.FeedbackSectionQuestion;

@Transactional
public interface FeedbackSectionQuestionDao extends JpaRepository<FeedbackSectionQuestion, Long>{

	FeedbackSectionQuestion findById(String id);
	
	boolean existsByFeedbackSection_Id(String id);
	
	@Modifying
	@Query("DELETE from FeedbackSectionQuestion where feedback_section_id = :id")
	void deleteByFeedbackSectionId(@Param("id") String id);
	
	@Modifying
	@Query("DELETE from FeedbackSectionQuestion where id = :id")
	void deleteById(@Param("id") String id);
	
	boolean existsById(String id);
}
