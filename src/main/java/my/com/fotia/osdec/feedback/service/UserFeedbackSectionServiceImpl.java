package my.com.fotia.osdec.feedback.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.feedback.dao.FeedbackDao;
import my.com.fotia.osdec.feedback.dao.FeedbackSectionDao;
import my.com.fotia.osdec.feedback.dao.UserFeedbackSectionDao;
import my.com.fotia.osdec.feedback.model.Feedback;
import my.com.fotia.osdec.feedback.model.FeedbackSection;
import my.com.fotia.osdec.feedback.model.UserFeedbackSection;

@Service
public class UserFeedbackSectionServiceImpl implements UserFeedbackSectionService{

	@Autowired
	UserFeedbackSectionDao userFeedbackSectionDao;

	@Override
	public void save(UserFeedbackSection feedbackSection) {
		userFeedbackSectionDao.save(feedbackSection);
	}

	@Override
	public void save(List<UserFeedbackSection> ls) {
		userFeedbackSectionDao.save(ls);
	}

	@Override
	public UserFeedbackSection findById(String id) {
		return userFeedbackSectionDao.findById(id);
	}

	@Override
	public List<UserFeedbackSection> findByUserFeedback_Id(String feedbackId) {
		return userFeedbackSectionDao.findByUserFeedback_Id(feedbackId);
	}

	@Override
	public boolean existsByUserFeedback_Id(String id) {
		return userFeedbackSectionDao.existsByUserFeedback_Id(id);
	}

	@Override
	public boolean existsById(String id) {
		return userFeedbackSectionDao.existsById(id);
	}
}
