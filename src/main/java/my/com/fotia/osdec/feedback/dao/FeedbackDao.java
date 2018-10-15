package my.com.fotia.osdec.feedback.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import my.com.fotia.osdec.feedback.model.Feedback;
import my.com.fotia.osdec.general.agency.model.Agency;

@Transactional
public interface FeedbackDao extends JpaRepository<Feedback, Long>{

	Feedback findById(String id);
	
	Feedback findByType(String type);
	
	List<Feedback> findAll();
}
