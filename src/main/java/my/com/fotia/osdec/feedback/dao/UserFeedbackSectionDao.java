package my.com.fotia.osdec.feedback.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import my.com.fotia.osdec.feedback.model.UserFeedbackSection;

@Transactional
public interface UserFeedbackSectionDao extends JpaRepository<UserFeedbackSection, Long>{

	UserFeedbackSection findById(String id);
	
	boolean existsByUserFeedback_Id(String id);
	boolean existsById(String id);
	
	List<UserFeedbackSection> findByUserFeedback_Id(String id);
}
