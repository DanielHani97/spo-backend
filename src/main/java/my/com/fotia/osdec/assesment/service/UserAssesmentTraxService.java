package my.com.fotia.osdec.assesment.service;

import java.util.List;

import my.com.fotia.osdec.assesment.model.UserAssesmentTrax;

public interface UserAssesmentTraxService {

	UserAssesmentTrax findById(String id);
	List<UserAssesmentTrax> findAll();
	long countByMark(int mark);
	List<UserAssesmentTrax> findByUserAssesId(String userAssesId);
	List<UserAssesmentTrax> findByAnswerId(String answerId);
	List<UserAssesmentTrax> findByQuestionId(String questionId);
	void save(UserAssesmentTrax userAssesmentTrax);
	void save(List<UserAssesmentTrax> ls);
}
