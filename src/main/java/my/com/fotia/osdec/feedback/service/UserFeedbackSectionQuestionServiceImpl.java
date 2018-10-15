package my.com.fotia.osdec.feedback.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.feedback.dao.FeedbackDao;
import my.com.fotia.osdec.feedback.dao.FeedbackSectionDao;
import my.com.fotia.osdec.feedback.dao.FeedbackSectionQuestionDao;
import my.com.fotia.osdec.feedback.dao.UserFeedbackSectionQuestionDao;
import my.com.fotia.osdec.feedback.model.Feedback;
import my.com.fotia.osdec.feedback.model.FeedbackSection;
import my.com.fotia.osdec.feedback.model.FeedbackSectionQuestion;
import my.com.fotia.osdec.feedback.model.UserFeedbackSectionQuestion;

@Service
public class UserFeedbackSectionQuestionServiceImpl implements UserFeedbackSectionQuestionService{

	@Autowired
	UserFeedbackSectionQuestionDao userFeedbackSectionQuestionDao;

	@Override
	public void save(UserFeedbackSectionQuestion userFeedbackSectionQuestion) {
		userFeedbackSectionQuestionDao.save(userFeedbackSectionQuestion);
	}

	@Override
	public void save(List<UserFeedbackSectionQuestion> ls) {
		userFeedbackSectionQuestionDao.save(ls);
	}

	@Override
	public UserFeedbackSectionQuestion findById(String id) {
		return userFeedbackSectionQuestionDao.findById(id);
	}

	@Override
	public boolean existsByUserFeedbackSection_Id(String id) {
		return userFeedbackSectionQuestionDao.existsByUserFeedbackSection_Id(id);
	}

	@Override
	public boolean existsById(String id) {
		return userFeedbackSectionQuestionDao.existsById(id);
	}
}
