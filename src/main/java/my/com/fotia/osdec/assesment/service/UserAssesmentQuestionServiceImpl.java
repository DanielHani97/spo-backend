package my.com.fotia.osdec.assesment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.assesment.dao.UserAssesmentQuestionDao;
import my.com.fotia.osdec.assesment.model.UserAssesmentQuestion;

@Service
public class UserAssesmentQuestionServiceImpl implements UserAssesmentQuestionService{

	@Autowired
	UserAssesmentQuestionDao userAssesmentQuestionDao;

	@Override
	public UserAssesmentQuestion findById(String id) {
		return userAssesmentQuestionDao.findById(id);
	}

	@Override
	public List<UserAssesmentQuestion> findAll() {
		return userAssesmentQuestionDao.findAll();
	}

	@Override
	public void deleteById(String id) {
		userAssesmentQuestionDao.deleteById(id);
	}

	@Override
	public void save(List<UserAssesmentQuestion> ls) {
		userAssesmentQuestionDao.save(ls);
	}

	@Override
	public void save(UserAssesmentQuestion userAssesmentQuestion) {
		userAssesmentQuestionDao.save(userAssesmentQuestion);
	}
}
