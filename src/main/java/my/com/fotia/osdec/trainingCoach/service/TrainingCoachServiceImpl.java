package my.com.fotia.osdec.trainingCoach.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.trainingCoach.dao.TrainingCoachDao;
import my.com.fotia.osdec.trainingCoach.model.TrainingCoach;
import my.com.fotia.osdec.user.model.User;

@Service
public class TrainingCoachServiceImpl implements TrainingCoachService {
	
	@Autowired
	TrainingCoachDao trainingCoachDao;
	
	public TrainingCoach findById(String id) {
		return trainingCoachDao.findById(id);
	}

	@Override
	public List<TrainingCoach> trainingCoachList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(TrainingCoach trainingCoach) {
		trainingCoachDao.save(trainingCoach);
	}

	@Override
	public void deleteById(String id) {
		trainingCoachDao.deleteById(id);
	}
	
	@Override
	public List<TrainingCoach> findByCoach(User coach) {
		// TODO Auto-generated method stub
		return trainingCoachDao.findByCoach(coach);
	}
	
	@Override
	public List<TrainingCoach> findByTraining_id(String id) {
		// TODO Auto-generated method stub
		return trainingCoachDao.findByTraining_id(id);
	}
	
	@Override
	public long countByCoach_IdAndTraining_TitleContainingIgnoreCaseOrCoach_IdAndTraining_Technology_NameContainingIgnoreCaseOrCoach_IdAndTraining_DurationContainingIgnoreCaseOrCoach_IdAndTraining_PlaceContainingIgnoreCase(
			String user, String title, String user2, String technology, String user3, String duration, String user4, String place) {
		return trainingCoachDao.countByCoach_IdAndTraining_TitleContainingIgnoreCaseOrCoach_IdAndTraining_Technology_NameContainingIgnoreCaseOrCoach_IdAndTraining_DurationContainingIgnoreCaseOrCoach_IdAndTraining_PlaceContainingIgnoreCase(user, title, user2, technology, user3, duration, user4, place);
	}

	@Override
	public List<TrainingCoach> findByCoach_IdAndTraining_TitleContainingIgnoreCaseOrCoach_IdAndTraining_Technology_NameContainingIgnoreCaseOrCoach_IdAndTraining_DurationContainingIgnoreCaseOrCoach_IdAndTraining_PlaceContainingIgnoreCase(
			String coachid, String title, String coachid2, String technology, String coachid3, String duration, String coachid4, String place, Pageable pageable) {
		return trainingCoachDao.findByCoach_IdAndTraining_TitleContainingIgnoreCaseOrCoach_IdAndTraining_Technology_NameContainingIgnoreCaseOrCoach_IdAndTraining_DurationContainingIgnoreCaseOrCoach_IdAndTraining_PlaceContainingIgnoreCase(coachid, title, coachid2, technology, coachid3, duration, coachid4, place, pageable);
	}

	@Override
	public boolean existsByTraining_IdAndCoach_Id(String trainingId, String userId) {
		return trainingCoachDao.existsByTraining_IdAndCoach_Id(trainingId, userId);
	}

}
