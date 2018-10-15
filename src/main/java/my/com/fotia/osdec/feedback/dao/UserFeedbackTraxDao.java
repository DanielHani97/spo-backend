package my.com.fotia.osdec.feedback.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import my.com.fotia.osdec.feedback.model.Feedback;
import my.com.fotia.osdec.feedback.model.UserFeedbackTrax;
import my.com.fotia.osdec.general.agency.model.Agency;

@Transactional
public interface UserFeedbackTraxDao extends JpaRepository<UserFeedbackTrax, Long>{

	UserFeedbackTrax findById(String id);
	
	UserFeedbackTrax findByType(String type);
	
	List<UserFeedbackTrax> findAll();
	
	boolean existsByParentidAndInstanceidAndUser_Id(String parentId, String instanceId, String userId);
	
	List<UserFeedbackTrax> findByParentidAndInstanceidAndUser_IdAndType(String parentId, String instanceId, String userId, String type);
	
	boolean existsByParentidAndUser_IdAndType(String parentId, String userId, String type);
	
	List<UserFeedbackTrax> findByParentidAndUser_IdAndType(String parentId, String userId, String type);
	
	boolean existsByParentidAndUser_IdAndCreatedby_IdAndType(String parentId, String userId, String createdby, String type);
	
}
