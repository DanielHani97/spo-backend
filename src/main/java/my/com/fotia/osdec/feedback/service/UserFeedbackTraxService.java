package my.com.fotia.osdec.feedback.service;

import java.util.List;

import my.com.fotia.osdec.feedback.model.UserFeedback;
import my.com.fotia.osdec.feedback.model.UserFeedbackTrax;

public interface UserFeedbackTraxService {

	void save(UserFeedbackTrax userFeedbackTrax);
	
	void save(List<UserFeedbackTrax> ls);
	
	long count();
	
	UserFeedbackTrax findById(String id);
	
	UserFeedbackTrax findByType(String type);
	
	List<UserFeedbackTrax> findAll();
	
	boolean existsByParentidAndInstanceidAndUser_Id(String parentId, String instanceId, String userId);
	
	List<UserFeedbackTrax> findByParentidAndInstanceidAndUser_IdAndType(String parentId, String instanceId, String userId, String type);
	
	boolean existsByParentidAndUser_IdAndType(String parentId, String userId, String type);
	
	List<UserFeedbackTrax> findByParentidAndUser_IdAndType(String parentId, String userId, String type);
	
	boolean existsByParentidAndUser_IdAndCreatedby_IdAndType(String parentId, String userId, String createdby, String type);
}
