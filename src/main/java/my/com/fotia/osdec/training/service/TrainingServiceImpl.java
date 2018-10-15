package my.com.fotia.osdec.training.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.training.dao.TrainingDao;
import my.com.fotia.osdec.training.model.Training;
import my.com.fotia.osdec.user.model.User;

@Service
public class TrainingServiceImpl implements TrainingService {
	
	@Autowired
	TrainingDao trainingDao;
	
	public Training findByTitle(String title) {
		return trainingDao.findByTitle(title);
	}
	
	public Training findById(String id) {
		return trainingDao.findById(id);
	}
	
	@Autowired
	public List<Training> findAll() {
		return trainingDao.findAll();
	}

	@Override
	public List<Training> trainingList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Training save(Training training) {
		return trainingDao.save(training);
	}

	@Override
	public void deleteById(String id) {
		trainingDao.deleteById(id);
	}
	
	@Override
	public void deleteByTrainingCoachId(String tcid) {
		trainingDao.deleteByTrainingCoachId(tcid);
	}
	
	@Override
	public void deleteByTrainingTxId(String txid) {
		trainingDao.deleteByTrainingTxId(txid);
	}
	
	@Override
	public void deleteByMandayTransactionId(String mtid) {
		trainingDao.deleteByMandayTransactionId(mtid);
	}
	
	@Override
	public long countByTitleContainingIgnoreCaseOrTechnology_NameContainingIgnoreCaseOrDurationContainingIgnoreCaseOrPlaceContainingIgnoreCaseOrLevelContainingIgnoreCaseOrStatusContainingIgnoreCase(
			String title, String technology, String duration, String place, String level, String status) {
		return trainingDao.countByTitleContainingIgnoreCaseOrTechnology_NameContainingIgnoreCaseOrDurationContainingIgnoreCaseOrPlaceContainingIgnoreCaseOrLevelContainingIgnoreCaseOrStatusContainingIgnoreCase(title, technology, duration, place, level, status);
	}

	@Override
	public List<Training> findByTitleContainingIgnoreCaseOrTechnology_NameContainingIgnoreCaseOrDurationContainingIgnoreCaseOrPlaceContainingIgnoreCaseOrLevelContainingIgnoreCaseOrStatusContainingIgnoreCase(
			String title, String technology, String duration, String place, String level, String status, Pageable pageable) {
		return trainingDao.findByTitleContainingIgnoreCaseOrTechnology_NameContainingIgnoreCaseOrDurationContainingIgnoreCaseOrPlaceContainingIgnoreCaseOrLevelContainingIgnoreCaseOrStatusContainingIgnoreCase(title, technology, duration, place, level, status, pageable);
	}
	
	@Override
	public List<Training> findByStatusAndStartDateGreaterThanEqual(String status, Date startDate) {
		return trainingDao.findByStatusAndStartDateGreaterThanEqual(status, startDate);
	}
	
}
