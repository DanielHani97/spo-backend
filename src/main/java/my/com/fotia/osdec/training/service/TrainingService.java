package my.com.fotia.osdec.training.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import my.com.fotia.osdec.training.model.Training;
import my.com.fotia.osdec.user.model.User;

public interface TrainingService {
	
	Training findByTitle(String title);
	Training findById(String id);
	List<Training> findAll();
	List<Training> trainingList();
	List<Training> findByStatusAndStartDateGreaterThanEqual(String status, Date startDate);
	Training save(Training training);
	void deleteById(String id);
	void deleteByTrainingCoachId(String tcid);
	void deleteByTrainingTxId(String txid);
	void deleteByMandayTransactionId(String mtid);	
	long countByTitleContainingIgnoreCaseOrTechnology_NameContainingIgnoreCaseOrDurationContainingIgnoreCaseOrPlaceContainingIgnoreCaseOrLevelContainingIgnoreCaseOrStatusContainingIgnoreCase
	(String title, String technology, String duration, String place, String level, String status);
	List<Training> findByTitleContainingIgnoreCaseOrTechnology_NameContainingIgnoreCaseOrDurationContainingIgnoreCaseOrPlaceContainingIgnoreCaseOrLevelContainingIgnoreCaseOrStatusContainingIgnoreCase
	(String title, String technology, String duration, String place, String level, String status, Pageable pageable);

}
