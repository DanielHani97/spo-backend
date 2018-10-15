package my.com.fotia.osdec.assesment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.assesment.dao.UserAssesmentAnswerDao;
import my.com.fotia.osdec.assesment.model.UserAssesmentAnswer;

@Service
public class UserAssesmentAnswerServiceImpl implements UserAssesmentAnswerService{

	@Autowired
	UserAssesmentAnswerDao userAssesmentAnswerDao;
	
	@Override
	public UserAssesmentAnswer findById(String id) {
		return userAssesmentAnswerDao.findById(id);
	}

	@Override
	public List<UserAssesmentAnswer> findAll() {
		return userAssesmentAnswerDao.findAll();
	}

	@Override
	public void deleteById(String id) {
		userAssesmentAnswerDao.deleteById(id);
	}

	@Override
	public void save(List<UserAssesmentAnswer> ls) {
		userAssesmentAnswerDao.save(ls);
	}

	@Override
	public void deleteByAssesId(String assesid) {
		userAssesmentAnswerDao.deleteByAssesId(assesid);
	}

}
