package my.com.fotia.osdec.certification.coach.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.certification.coach.dao.CertificationCoachDao;
import my.com.fotia.osdec.certification.coach.model.CertificationCoach;
import my.com.fotia.osdec.user.model.User;

@Service
public class CertificationCoachServiceImpl implements CertificationCoachService {
	
	@Autowired
	CertificationCoachDao certificationCoachDao;
	
	public CertificationCoach findById(String id) {
		return certificationCoachDao.findById(id);
	}

	@Override
	public List<CertificationCoach> certificationCoachList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(CertificationCoach certificationCoach) {
		certificationCoachDao.save(certificationCoach);
	}

	@Override
	public void deleteById(String id) {
		certificationCoachDao.deleteById(id);
	}
	
	@Override
	public List<CertificationCoach> findByCoach(User coach) {
		// TODO Auto-generated method stub
		return certificationCoachDao.findByCoach(coach);
	}
	
	@Override
	public List<CertificationCoach> findByCertification_id(String id) {
		// TODO Auto-generated method stub
		return certificationCoachDao.findByCertification_id(id);
	}
	
	@Override
	public long countByCoach_IdAndCertification_TitleContainingIgnoreCaseOrCoach_IdAndCertification_Technology_NameContainingIgnoreCaseOrCoach_IdAndCertification_DurationContainingIgnoreCaseOrCoach_IdAndCertification_PlaceContainingIgnoreCase(
			String user, String title, String user2, String technology, String user3, String duration, String user4, String place) {
		return certificationCoachDao.countByCoach_IdAndCertification_TitleContainingIgnoreCaseOrCoach_IdAndCertification_Technology_NameContainingIgnoreCaseOrCoach_IdAndCertification_DurationContainingIgnoreCaseOrCoach_IdAndCertification_PlaceContainingIgnoreCase(user, title, user2, technology, user3, duration, user4, place);
	}

	@Override
	public List<CertificationCoach> findByCoach_IdAndCertification_TitleContainingIgnoreCaseOrCoach_IdAndCertification_Technology_NameContainingIgnoreCaseOrCoach_IdAndCertification_DurationContainingIgnoreCaseOrCoach_IdAndCertification_PlaceContainingIgnoreCase(
			String coachid, String title, String coachid2, String technology, String coachid3, String duration, String coachid4, String place, Pageable pageable) {
		return certificationCoachDao.findByCoach_IdAndCertification_TitleContainingIgnoreCaseOrCoach_IdAndCertification_Technology_NameContainingIgnoreCaseOrCoach_IdAndCertification_DurationContainingIgnoreCaseOrCoach_IdAndCertification_PlaceContainingIgnoreCase(coachid, title, coachid2, technology, coachid3, duration, coachid4, place, pageable);
	}
}
