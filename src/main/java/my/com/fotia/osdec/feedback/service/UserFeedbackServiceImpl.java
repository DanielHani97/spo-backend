package my.com.fotia.osdec.feedback.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.feedback.dao.FeedbackDao;
import my.com.fotia.osdec.feedback.dao.UserFeedbackDao;
import my.com.fotia.osdec.feedback.model.Feedback;
import my.com.fotia.osdec.feedback.model.UserFeedback;

@Service
public class UserFeedbackServiceImpl implements UserFeedbackService{

	@Autowired
	UserFeedbackDao userFeedbackDao;

	@Override
	public UserFeedback findById(String id) {
		return userFeedbackDao.findById(id);
	}

	@Override
	public UserFeedback findByType(String type) {
		return userFeedbackDao.findByType(type);
	}

	@Override
	public List<UserFeedback> findAll() {
		return userFeedbackDao.findAll();
	}

	@Override
	public void save(UserFeedback userFeedback) {
		userFeedbackDao.save(userFeedback);
	}

	@Override
	public void save(List<UserFeedback> ls) {
		userFeedbackDao.save(ls);
	}

	@Override
	public long count() {
		return userFeedbackDao.count();
	}

	@Override
	public UserFeedback findByFeedbacktraxid(String id) {
		return userFeedbackDao.findByFeedbacktraxid(id);
	}

}
