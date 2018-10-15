package my.com.fotia.osdec.assesment.service;

import java.util.List;

import my.com.fotia.osdec.assesment.model.UserAssesmentAnswer;

public interface UserAssesmentAnswerService {

	UserAssesmentAnswer findById(String id);
	List<UserAssesmentAnswer> findAll();
	void deleteById(String id);
	void save(List<UserAssesmentAnswer> ls);
	void deleteByAssesId(String assesid);
}
