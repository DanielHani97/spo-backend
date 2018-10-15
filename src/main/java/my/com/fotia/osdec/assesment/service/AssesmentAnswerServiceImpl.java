package my.com.fotia.osdec.assesment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.assesment.dao.AssesmentAnswerDao;
import my.com.fotia.osdec.assesment.model.AssesmentAnswer;

@Service
public class AssesmentAnswerServiceImpl implements AssesmentAnswerService{
	
	@Autowired
	AssesmentAnswerDao assesmentAnswerDao;

	@Override
	public AssesmentAnswer findById(String id) {
		return assesmentAnswerDao.findById(id);
	}

	@Override
	public List<AssesmentAnswer> findAll() {
		return assesmentAnswerDao.findAll();
	}

	@Override
	public void deleteById(String id) {
		assesmentAnswerDao.deleteById(id);
	}

	@Override
	public void save(List<AssesmentAnswer> ls) {
		assesmentAnswerDao.save(ls);
	}

	@Override
	public void deleteByAssesId(String assesid) {
		assesmentAnswerDao.deleteByAssesId(assesid);
	}
}
