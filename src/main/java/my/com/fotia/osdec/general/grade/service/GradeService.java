package my.com.fotia.osdec.general.grade.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import my.com.fotia.osdec.general.grade.model.Grade;


public interface GradeService {
	
	
	Grade findByName(String name);
	Grade findById(String id);
	List<Grade> gradeList();
	List<Grade> findAll();
	List<Grade> gradeCnList();
	
	void save(Grade grade);
	void deleteById(String id);
	
	
	List<Grade> findByNameContainingIgnoreCaseOrStatusContainingIgnoreCase(String name,
			 String status, Pageable pageable);
	
	long countByNameContainingIgnoreCaseOrStatusContainingIgnoreCase(String name,
			 String status);
	

}
