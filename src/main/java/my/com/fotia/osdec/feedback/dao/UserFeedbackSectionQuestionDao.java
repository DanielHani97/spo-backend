package my.com.fotia.osdec.feedback.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import my.com.fotia.osdec.feedback.model.UserFeedbackSectionQuestion;

@Transactional
public interface UserFeedbackSectionQuestionDao extends JpaRepository<UserFeedbackSectionQuestion, Long>{

	UserFeedbackSectionQuestion findById(String id);
	
	boolean existsByUserFeedbackSection_Id(String id);
	
	boolean existsById(String id);
}
