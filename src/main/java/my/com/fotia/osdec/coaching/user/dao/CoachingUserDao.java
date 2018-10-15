package my.com.fotia.osdec.coaching.user.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import my.com.fotia.osdec.coaching.user.model.CoachingUser;
import my.com.fotia.osdec.user.model.User;

@Transactional
public interface CoachingUserDao extends CrudRepository<CoachingUser, Long>{

	List<CoachingUser> findByUser(User user);
	CoachingUser findById(String id);
	List<CoachingUser> findAll();
	List<CoachingUser> findByUser_Id(String id);
	
	@Modifying
	@Query("DELETE from CoachingUser where id = :id")
	void deleteById(@Param("id") String id);
	
	List<CoachingUser> findByCoaching_id(String coaching_id);
	
	List<CoachingUser> findByUser_IdAndCoaching_NameContainingIgnoreCaseOrUser_IdAndCoaching_Agency_CodeContainingIgnoreCase
	(String userid, String name, String userid2, String agency, Pageable pageable);
	
	long countByUser_IdAndCoaching_NameContainingIgnoreCaseOrUser_IdAndCoaching_Agency_CodeContainingIgnoreCase
	(String user, String name,String user2, String agency);
	
	CoachingUser findByCoaching_IdAndUser_Id(String coachingId, String userId);
	
	boolean existsByCoaching_IdAndUser_Id(String coachingId, String userId);
}
