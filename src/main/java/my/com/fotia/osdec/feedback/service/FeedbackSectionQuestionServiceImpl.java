package my.com.fotia.osdec.feedback.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.feedback.dao.FeedbackDao;
import my.com.fotia.osdec.feedback.dao.FeedbackSectionDao;
import my.com.fotia.osdec.feedback.dao.FeedbackSectionQuestionDao;
import my.com.fotia.osdec.feedback.model.Feedback;
import my.com.fotia.osdec.feedback.model.FeedbackSection;
import my.com.fotia.osdec.feedback.model.FeedbackSectionQuestion;

@Service
public class FeedbackSectionQuestionServiceImpl implements FeedbackSectionQuestionService{

	@Autowired
	FeedbackSectionQuestionDao feedbackSectionQuestionDao;

	@Override
	public FeedbackSectionQuestion findById(String id) {
		return feedbackSectionQuestionDao.findById(id);
	}

	@Override
	public void save(FeedbackSectionQuestion feedbackSectionQuestion) {
		feedbackSectionQuestionDao.save(feedbackSectionQuestion);
	}

	@Override
	public void save(List<FeedbackSectionQuestion> ls) {
		feedbackSectionQuestionDao.save(ls);
	}

	@Override
	public void deleteByFeedbackSectionId(String id) {
		feedbackSectionQuestionDao.deleteByFeedbackSectionId(id);
	}

	@Override
	public boolean existsByFeedbackSection_Id(String id) {
		return feedbackSectionQuestionDao.existsByFeedbackSection_Id(id);
	}

	@Override
	public boolean existsById(String id) {
		return feedbackSectionQuestionDao.existsById(id);
	}

	@Override
	public void deleteById(String id) {
		feedbackSectionQuestionDao.deleteById(id);
	}
}
