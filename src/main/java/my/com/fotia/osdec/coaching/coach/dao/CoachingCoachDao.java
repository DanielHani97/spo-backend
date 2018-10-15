package my.com.fotia.osdec.coaching.coach.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import my.com.fotia.osdec.coaching.coach.model.CoachingCoach;
import my.com.fotia.osdec.coaching.user.model.CoachingUser;
import my.com.fotia.osdec.user.model.User;

@Transactional
public interface CoachingCoachDao extends JpaRepository<CoachingCoach, Long>{

	List<CoachingCoach> findByCoach(User coach);
	CoachingCoach findById(String id);
	List<CoachingCoach> findAll();
	List<CoachingCoach> findByCoaching_Id(String id);
	@Modifying
	@Query("DELETE from CoachingCoach where id = :id")
	void deleteById(@Param("id") String id);
	
	List<CoachingCoach> findByCoach_IdAndCoaching_NameContainingIgnoreCaseOrCoach_IdAndCoaching_Agency_CodeContainingIgnoreCase
	(String userid, String name, String userid2, String agency, Pageable pageable);
	
	long countByCoach_IdAndCoaching_NameContainingIgnoreCaseOrCoach_IdAndCoaching_Agency_CodeContainingIgnoreCase
	(String user, String name,String user2, String agency);
	
	boolean existsByCoaching_IdAndCoach_Id(String coachingId, String userId);
}
