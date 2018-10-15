package my.com.fotia.osdec.training.dao;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import my.com.fotia.osdec.training.model.Training;

@Transactional
public interface TrainingDao extends JpaRepository<Training, Long> {

	Training findByTitle(String title);
	Training findById(String id);
	List<Training> findAll();
	List<Training> findByStatusAndStartDateGreaterThanEqual(String status, Date startDate);
	List<Training> findByTitleContainingIgnoreCaseOrTechnology_NameContainingIgnoreCaseOrDurationContainingIgnoreCaseOrPlaceContainingIgnoreCaseOrLevelContainingIgnoreCaseOrStatusContainingIgnoreCase
	(String title, String technology, String duration, String place, String level, String status, Pageable pageable);
	
	@Modifying
	@Query("DELETE from Training where id = :id")
	void deleteById(@Param("id") String id);
	
	@Modifying
	@Query("DELETE from TrainingCoach where training_id = :id")
	void deleteByTrainingCoachId(@Param("id") String id);
	
	@Modifying
	@Query("DELETE from TrainingTx where training_id = :id")
	void deleteByTrainingTxId(@Param("id") String id);
	
	@Modifying
	@Query("DELETE from MandayTransaction where instanceId = :id")
	void deleteByMandayTransactionId(@Param("id") String id);
	
	long countByTitleContainingIgnoreCaseOrTechnology_NameContainingIgnoreCaseOrDurationContainingIgnoreCaseOrPlaceContainingIgnoreCaseOrLevelContainingIgnoreCaseOrStatusContainingIgnoreCase
	(String title, String technology, String duration, String place, String level, String status);
}
