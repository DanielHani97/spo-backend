package my.com.fotia.osdec.assesment.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import my.com.fotia.osdec.assesment.model.AssesmentAnswer;

public interface AssesmentAnswerService {

	AssesmentAnswer findById(String id);
	List<AssesmentAnswer> findAll();
	void deleteById(String id);
	void save(List<AssesmentAnswer> ls);
	void deleteByAssesId(String assesid);
}
