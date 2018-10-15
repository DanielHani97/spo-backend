package my.com.fotia.osdec.coaching.activities.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import my.com.fotia.osdec.coaching.activities.model.CoachingActivities;

@Transactional
public interface CoachingActivitiesDao extends CrudRepository<CoachingActivities, Long>{
	
	CoachingActivities findByName(String name);
	CoachingActivities findById(String id);
	List<CoachingActivities> findAll();
	
	@Modifying
	@Query("DELETE from CoachingActivities where id = :id")
	void deleteById(@Param("id") String id);
	
	List<CoachingActivities> findByCoaching_IdAndAttendanceAndKelulusan(String id, String status, String kelulusan);
	List<CoachingActivities> findByCoaching_IdAndKelulusan(String id, String string);
	List<CoachingActivities> findByCoaching_Id(String id);
}
