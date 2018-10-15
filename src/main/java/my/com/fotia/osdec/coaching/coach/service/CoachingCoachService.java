package my.com.fotia.osdec.coaching.coach.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import my.com.fotia.osdec.coaching.coach.model.CoachingCoach;
import my.com.fotia.osdec.user.model.User;

public interface CoachingCoachService {
	
	List<CoachingCoach> findByCoach(User coach);
	CoachingCoach findById(String id);
	List<CoachingCoach> coachingCoachList();
	void save(CoachingCoach coachingCoach);
	void deleteById(String id);
	
	List<CoachingCoach> findAll();
	
	List<CoachingCoach> findByCoach_IdAndCoaching_NameContainingIgnoreCaseOrCoach_IdAndCoaching_Agency_CodeContainingIgnoreCase
	(String userid, String name, String userid2, String agency, Pageable pageable);
	
	long countByCoach_IdAndCoaching_NameContainingIgnoreCaseOrCoach_IdAndCoaching_Agency_CodeContainingIgnoreCase
	(String user, String name,String user2, String agency);
	List<CoachingCoach> findByCoaching_Id(String id);
	
	boolean existsByCoaching_IdAndCoach_Id(String coachingId, String userId);


}
