package my.com.fotia.osdec.coaching.activities.service;

import java.util.List;

import my.com.fotia.osdec.coaching.activities.model.CoachingActivities;

public interface CoachingActivitiesService {

	CoachingActivities findByName(String name);
	CoachingActivities findById(String id);
	List<CoachingActivities> findAll();
	List<CoachingActivities> coachingActivitiesList();
	void save(CoachingActivities coachingActivities);
	void deleteById(String id);
	List<CoachingActivities> findByCoaching_IdAndAttendanceAndKelulusan(String id, String status, String kelulusan);
	List<CoachingActivities> findByCoaching_IdAndKelulusan(String id, String string);
	List<CoachingActivities> findByCoaching_Id(String id);
	
}
