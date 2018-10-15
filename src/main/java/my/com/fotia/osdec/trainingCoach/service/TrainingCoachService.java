package my.com.fotia.osdec.trainingCoach.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import my.com.fotia.osdec.trainingCoach.model.TrainingCoach;
import my.com.fotia.osdec.user.model.User;

public interface TrainingCoachService {
	
	List<TrainingCoach> findByCoach(User coach);
	TrainingCoach findById(String id);
	List<TrainingCoach> trainingCoachList();
	List<TrainingCoach> findByTraining_id (String id);
	void save(TrainingCoach trainingCoach);
	void deleteById(String id);
	long countByCoach_IdAndTraining_TitleContainingIgnoreCaseOrCoach_IdAndTraining_Technology_NameContainingIgnoreCaseOrCoach_IdAndTraining_DurationContainingIgnoreCaseOrCoach_IdAndTraining_PlaceContainingIgnoreCase
	(String user, String title, String user2, String technology, String user3, String duration, String user4, String place);
	List<TrainingCoach> findByCoach_IdAndTraining_TitleContainingIgnoreCaseOrCoach_IdAndTraining_Technology_NameContainingIgnoreCaseOrCoach_IdAndTraining_DurationContainingIgnoreCaseOrCoach_IdAndTraining_PlaceContainingIgnoreCase
	(String coachid, String title, String coachid2, String technology, String coachid3, String duration, String coachid4, String place, Pageable pageable);

	boolean existsByTraining_IdAndCoach_Id(String trainingId, String userId);
}
