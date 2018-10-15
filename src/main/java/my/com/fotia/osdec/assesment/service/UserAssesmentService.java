package my.com.fotia.osdec.assesment.service;

import java.util.List;

import my.com.fotia.osdec.assesment.model.UserAssesment;

public interface UserAssesmentService {

	UserAssesment save(UserAssesment userAssesment);
	
	UserAssesment findById(String id);
	
	UserAssesment findByUserId(String userId);
	
	List<UserAssesment> findAll();
	
	void deleteById(String id);
	
	boolean existsByTechnology_IdAndLevelAndUserId(String technology, String level, String userId);
}
