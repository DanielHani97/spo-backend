package my.com.fotia.osdec.feedback.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.feedback.dao.FeedbackDao;
import my.com.fotia.osdec.feedback.dao.FeedbackSectionDao;
import my.com.fotia.osdec.feedback.model.Feedback;
import my.com.fotia.osdec.feedback.model.FeedbackSection;

@Service
public class FeedbackSectionServiceImpl implements FeedbackSectionService{

	@Autowired
	FeedbackSectionDao feedbackSectionDao;

	@Override
	public FeedbackSection findById(String id) {
		return feedbackSectionDao.findById(id);
	}

	@Override
	public List<FeedbackSection> findByFeedback_Id(String feedbackId) {
		return feedbackSectionDao.findByFeedback_Id(feedbackId);
	}

	@Override
	public void save(FeedbackSection feedbackSection) {
		feedbackSectionDao.save(feedbackSection);
	}

	@Override
	public void save(List<FeedbackSection> ls) {
		feedbackSectionDao.save(ls);
	}

	@Override
	public void deleteByFeedbackId(String id) {
		feedbackSectionDao.deleteByFeedbackId(id);
	}

	@Override
	public boolean existsByFeedback_Id(String id) {
		return feedbackSectionDao.existsByFeedback_Id(id);
	}

	@Override
	public boolean existsById(String id) {
		return feedbackSectionDao.existsById(id);
	}

	@Override
	public void deleteById(String id) {
		feedbackSectionDao.deleteById(id);
	}
	

}
