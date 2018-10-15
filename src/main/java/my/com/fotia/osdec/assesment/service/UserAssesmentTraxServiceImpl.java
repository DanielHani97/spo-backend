package my.com.fotia.osdec.assesment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.assesment.dao.UserAssesmentTraxDao;
import my.com.fotia.osdec.assesment.model.UserAssesmentTrax;

@Service
public class UserAssesmentTraxServiceImpl implements UserAssesmentTraxService{
	
	@Autowired
	UserAssesmentTraxDao userAssesmentTraxDao;

	@Override
	public UserAssesmentTrax findById(String id) {
		return userAssesmentTraxDao.findById(id);
	}

	@Override
	public List<UserAssesmentTrax> findAll() {
		return userAssesmentTraxDao.findAll();
	}

	@Override
	public long countByMark(int mark) {
		return userAssesmentTraxDao.countByMark(mark);
	}

	@Override
	public List<UserAssesmentTrax> findByUserAssesId(String userAssesId) {
		return userAssesmentTraxDao.findByQuestionId(userAssesId);
	}

	@Override
	public List<UserAssesmentTrax> findByAnswerId(String answerId) {
		return userAssesmentTraxDao.findByAnswerId(answerId);
	}

	@Override
	public List<UserAssesmentTrax> findByQuestionId(String questionId) {
		return userAssesmentTraxDao.findByQuestionId(questionId);
	}

	@Override
	public void save(UserAssesmentTrax userAssesmentTrax) {
		userAssesmentTraxDao.save(userAssesmentTrax);
	}

	@Override
	public void save(List<UserAssesmentTrax> ls) {
		userAssesmentTraxDao.save(ls);
	}

}
