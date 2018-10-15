package my.com.fotia.osdec.feedback.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.feedback.dao.FeedbackDao;
import my.com.fotia.osdec.feedback.model.Feedback;

@Service
public class FeedbackServiceImpl implements FeedbackService{

	@Autowired
	FeedbackDao feedbackDao;
	
	@Override
	public Feedback findById(String id) {
		return feedbackDao.findById(id);
	}

	@Override
	public Feedback findByType(String type) {
		return feedbackDao.findByType(type);
	}

	@Override
	public List<Feedback> findAll() {
		return feedbackDao.findAll();
	}

	@Override
	public void save(Feedback feedback) {
		feedbackDao.save(feedback);
		
	}

	@Override
	public void save(List<Feedback> ls) {
		feedbackDao.save(ls);
	}

	@Override
	public long count() {
		return feedbackDao.count();
	}

}
