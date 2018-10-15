package my.com.fotia.osdec.assesment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.assesment.dao.AssesmentQuestionDao;
import my.com.fotia.osdec.assesment.model.AssesmentQuestion;

@Service
public class AssesmentQuestionServiceImpl implements AssesmentQuestionService{

	@Autowired
	AssesmentQuestionDao assesmentQuestionDao;
	
	@Override
	public AssesmentQuestion findById(String id) {
		return assesmentQuestionDao.findById(id);
	}

	@Override
	public List<AssesmentQuestion> findAll() {
		return assesmentQuestionDao.findAll();
	}

	@Override
	public void deleteById(String id) {
		assesmentQuestionDao.deleteById(id);
	}

	@Override
	public void save(List<AssesmentQuestion> ls) {
		assesmentQuestionDao.save(ls);
	}

	@Override
	public void save(AssesmentQuestion assesmentQuestion) {
		assesmentQuestionDao.save(assesmentQuestion);
	}

	@Override
	public List<AssesmentQuestion> findByAssesment_IdAndQuestionContainingIgnoreCase(String assesmentId,
			String question, Pageable pageable) {
		return assesmentQuestionDao.findByAssesment_IdAndQuestionContainingIgnoreCase(assesmentId, question, pageable);
	}

	@Override
	public long countByAssesment_IdAndQuestionContainingIgnoreCase(String assesmentId, String question) {
		return assesmentQuestionDao.countByAssesment_IdAndQuestionContainingIgnoreCase(assesmentId, question);
	}

	@Override
	public boolean existsByIdAndAssesmentAnswer_IdAndAssesmentAnswer_Answer(String questionId, String answerId,
			boolean isAnswer) {
		return assesmentQuestionDao.existsByIdAndAssesmentAnswer_IdAndAssesmentAnswer_Answer(questionId, answerId, isAnswer);
	}

}
