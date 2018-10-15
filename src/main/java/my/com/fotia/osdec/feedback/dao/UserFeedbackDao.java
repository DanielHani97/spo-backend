package my.com.fotia.osdec.feedback.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import my.com.fotia.osdec.feedback.model.UserFeedback;

@Transactional
public interface UserFeedbackDao extends JpaRepository<UserFeedback, Long>{

	UserFeedback findById(String id);
	
	UserFeedback findByFeedbacktraxid(String id);
	
	UserFeedback findByType(String type);
	
	List<UserFeedback> findAll();
}
