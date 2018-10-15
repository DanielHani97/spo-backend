package my.com.fotia.osdec.assesment.service;

import java.util.List;

import my.com.fotia.osdec.assesment.model.UserAssesmentQuestion;


public interface UserAssesmentQuestionService {

	UserAssesmentQuestion findById(String id);
	
	List<UserAssesmentQuestion> findAll();
	
	void deleteById(String id);
	
	void save(List<UserAssesmentQuestion> ls);
	
	void save(UserAssesmentQuestion userAssesmentQuestion);
	
}
