package my.com.fotia.osdec.feedback.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import my.com.fotia.osdec.feedback.model.FeedbackSection;

@Transactional
public interface FeedbackSectionDao extends JpaRepository<FeedbackSection, Long>{

	FeedbackSection findById(String id);
	
	List<FeedbackSection> findByFeedback_Id(String feedbackId);
	
	@Modifying
	@Query("DELETE from FeedbackSection where feedback_id = :id")
	void deleteByFeedbackId(@Param("id") String id);
	
	@Modifying
	@Query("DELETE from FeedbackSection where id = :id")
	void deleteById(@Param("id") String id);
	
	boolean existsByFeedback_Id(String id);
	boolean existsById(String id);
}
