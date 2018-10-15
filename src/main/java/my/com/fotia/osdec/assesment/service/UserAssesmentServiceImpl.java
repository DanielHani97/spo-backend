package my.com.fotia.osdec.assesment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.assesment.dao.UserAssesmentDao;
import my.com.fotia.osdec.assesment.model.UserAssesment;

@Service
public class UserAssesmentServiceImpl implements UserAssesmentService{

	@Autowired
	UserAssesmentDao userAssesmentDao;
	
	@Override
	public UserAssesment save(UserAssesment userAssesment) {
		return userAssesmentDao.save(userAssesment);
	}

	@Override
	public UserAssesment findById(String id) {
		return userAssesmentDao.findById(id);
	}

	@Override
	public UserAssesment findByUserId(String userId) {
		return userAssesmentDao.findByUserId(userId);
	}

	@Override
	public List<UserAssesment> findAll() {
		return userAssesmentDao.findAll();
	}

	@Override
	public void deleteById(String id) {
		userAssesmentDao.deleteById(id);
	}

	@Override
	public boolean existsByTechnology_IdAndLevelAndUserId(String technology, String level, String userId) {
		return userAssesmentDao.existsByTechnology_IdAndLevelAndUserId(technology, level, userId);
	}
}
