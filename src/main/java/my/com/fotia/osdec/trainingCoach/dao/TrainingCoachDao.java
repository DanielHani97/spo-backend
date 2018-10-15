package my.com.fotia.osdec.trainingCoach.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import my.com.fotia.osdec.trainingCoach.model.*;
import my.com.fotia.osdec.user.model.User;

@Transactional
public interface TrainingCoachDao extends JpaRepository<TrainingCoach, Long> {
	
	List<TrainingCoach> findByCoach(User coach);
	TrainingCoach findById(String id);
	List<TrainingCoach> findAll();
	
	List<TrainingCoach> findByCoach_IdAndTraining_TitleContainingIgnoreCaseOrCoach_IdAndTraining_Technology_NameContainingIgnoreCaseOrCoach_IdAndTraining_DurationContainingIgnoreCaseOrCoach_IdAndTraining_PlaceContainingIgnoreCase
	(String coachid, String title, String coachid2, String technology, String coachid3, String duration, String coachid4, String place, Pageable pageable);
	
	@Modifying
	List<TrainingCoach> findByTraining_id(String training_id);
	
	@Modifying
	@Query("DELETE from TrainingCoach where id = :id")
	void deleteById(@Param("id") String id);
	
	long countByCoach_IdAndTraining_TitleContainingIgnoreCaseOrCoach_IdAndTraining_Technology_NameContainingIgnoreCaseOrCoach_IdAndTraining_DurationContainingIgnoreCaseOrCoach_IdAndTraining_PlaceContainingIgnoreCase
	(String user, String title, String user2, String technology, String user3, String duration, String user4, String place);

	boolean existsByTraining_IdAndCoach_Id(String trainingId, String userId);
}
