package my.com.fotia.osdec.general.grade.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.general.grade.dao.GradeDao;
import my.com.fotia.osdec.general.grade.model.Grade;


@Service
public class GradeServiceImpl implements GradeService {
	
	@Autowired
	GradeDao gradeDao;

	public Grade findByName(String name) {
		return gradeDao.findByName(name);
	}

	public Grade findById(String id) {
		return gradeDao.findById(id);
	}

	public List<Grade> gradeList() {
		return gradeDao.findAll();
	}
	
	public List<Grade> gradeCnList() {
		return gradeDao.findAll();
	}

	@Override
	public void save(Grade grade) {
		gradeDao.save(grade);
	}

	@Override
	public void deleteById(String id) {
		gradeDao.deleteById(id);
	}
	
	@Override
	public List<Grade> findAll() {
		return gradeDao.findAll();
	}

	@Override
	public List<Grade> findByNameContainingIgnoreCaseOrStatusContainingIgnoreCase(
			String name, String status, Pageable pageable) {
		return gradeDao.findByNameContainingIgnoreCaseOrStatusContainingIgnoreCase(name, status, pageable);
	}

	@Override
	public long countByNameContainingIgnoreCaseOrStatusContainingIgnoreCase(String name, String status) {
		return gradeDao.countByNameContainingIgnoreCaseOrStatusContainingIgnoreCase(name, status);
	}

	
	
	


}
