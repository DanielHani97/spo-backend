package my.com.fotia.osdec.coaching.coach.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.coaching.coach.dao.CoachingCoachDao;
import my.com.fotia.osdec.coaching.coach.model.CoachingCoach;
import my.com.fotia.osdec.user.model.User;

@Service
public class CoachingCoachServiceImpl implements CoachingCoachService {
	
	@Autowired
	CoachingCoachDao coachingCoachDao;

	@Override
	public CoachingCoach findById(String id) {
		return coachingCoachDao.findById(id);
	}

	@Override
	public List<CoachingCoach> coachingCoachList() {
		return coachingCoachDao.findAll();
	}

	@Override
	public void save(CoachingCoach coachingCoach) {
		coachingCoachDao.save(coachingCoach);
	}

	@Override
	public void deleteById(String id) {
		coachingCoachDao.deleteById(id);
	}

	@Override
	public List<CoachingCoach> findByCoach(User coach) {
		return coachingCoachDao.findByCoach(coach);
	}

	@Override
	public List<CoachingCoach> findByCoach_IdAndCoaching_NameContainingIgnoreCaseOrCoach_IdAndCoaching_Agency_CodeContainingIgnoreCase(
			String userid, String name, String userid2, String agency, Pageable pageable) {
		return coachingCoachDao.findByCoach_IdAndCoaching_NameContainingIgnoreCaseOrCoach_IdAndCoaching_Agency_CodeContainingIgnoreCase(userid, name, userid2, agency, pageable);
	}

	@Override
	public long countByCoach_IdAndCoaching_NameContainingIgnoreCaseOrCoach_IdAndCoaching_Agency_CodeContainingIgnoreCase(
			String user, String name, String user2, String agency) {
		return coachingCoachDao.countByCoach_IdAndCoaching_NameContainingIgnoreCaseOrCoach_IdAndCoaching_Agency_CodeContainingIgnoreCase(user, name, user2, agency);
	}

	@Override
	public List<CoachingCoach> findAll() {
		
		return coachingCoachDao.findAll();
	}

	@Override
	public List<CoachingCoach> findByCoaching_Id(String id) {
		return coachingCoachDao.findByCoaching_Id(id);
	}

	@Override
	public boolean existsByCoaching_IdAndCoach_Id(String coachingId, String userId) {
		// TODO Auto-generated method stub
		return coachingCoachDao.existsByCoaching_IdAndCoach_Id(coachingId, userId);
	}

	
}
