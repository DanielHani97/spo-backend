package my.com.fotia.osdec.coaching.user.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import my.com.fotia.osdec.coaching.user.model.CoachingUser;
import my.com.fotia.osdec.user.model.User;

public interface CoachingUserService {
	
	List<CoachingUser> findByUser(User user);
	CoachingUser findById(String id);
	List<CoachingUser> coachingUserList();
	List<CoachingUser> findByCoaching_id(String id);
	void save(CoachingUser coachingUser);
	void deleteById(String id);
	List<CoachingUser> findByUser_Id(String id);
	
	long countByUser_IdAndCoaching_NameContainingIgnoreCaseOrUser_IdAndCoaching_Agency_CodeContainingIgnoreCase
	(String user, String name,String user2, String agency);
	
	List<CoachingUser> findByUser_IdAndCoaching_NameContainingIgnoreCaseOrUser_IdAndCoaching_Agency_CodeContainingIgnoreCase
	(String userid, String name, String userid2, String agency, Pageable pageable);
	
	CoachingUser findByCoaching_IdAndUser_Id(String coachingId, String userId);
	
	boolean existsByCoaching_IdAndUser_Id(String coachingId, String userId);
}
