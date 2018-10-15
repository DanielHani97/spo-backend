package my.com.fotia.osdec.coaching.activities.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.coaching.activities.dao.CoachingActivitiesDao;
import my.com.fotia.osdec.coaching.activities.model.CoachingActivities;

@Service
public class CoachingActivitiesServiceImpl implements CoachingActivitiesService{
	
	@Autowired
	CoachingActivitiesDao coachingActivitiesDao;
	
	public CoachingActivities findByName(String name) {
		return coachingActivitiesDao.findByName(name);
	}

	public CoachingActivities findById(String id) {
		return coachingActivitiesDao.findById(id);
	}

	public List<CoachingActivities> coachingActivitiesList() {
		return coachingActivitiesDao.findAll();
	}

	@Override
	public void save(CoachingActivities coachingActivities) {
		coachingActivitiesDao.save(coachingActivities);
	}

	@Override
	public void deleteById(String id) {
		coachingActivitiesDao.deleteById(id);
	}

	@Override
	public List<CoachingActivities> findAll() {
		return coachingActivitiesDao.findAll();
	}

	@Override
	public List<CoachingActivities> findByCoaching_IdAndAttendanceAndKelulusan(String id, String status, String kelulusan) {
		return coachingActivitiesDao.findByCoaching_IdAndAttendanceAndKelulusan(id, status, kelulusan);
	}

	@Override
	public List<CoachingActivities> findByCoaching_IdAndKelulusan(String id, String string) {
		return coachingActivitiesDao.findByCoaching_IdAndKelulusan(id,  string);
	}

	@Override
	public List<CoachingActivities> findByCoaching_Id(String id) {
		return coachingActivitiesDao.findByCoaching_Id(id);
	}

	


}
