package my.com.fotia.osdec.certification.coach.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import my.com.fotia.osdec.certification.coach.model.CertificationCoach;
import my.com.fotia.osdec.user.model.User;

public interface CertificationCoachService {
	
	List<CertificationCoach> findByCoach(User coach);
	CertificationCoach findById(String id);
	List<CertificationCoach> certificationCoachList();
	List<CertificationCoach> findByCertification_id (String id);
	void save(CertificationCoach certificationCoach);
	void deleteById(String id);
	long countByCoach_IdAndCertification_TitleContainingIgnoreCaseOrCoach_IdAndCertification_Technology_NameContainingIgnoreCaseOrCoach_IdAndCertification_DurationContainingIgnoreCaseOrCoach_IdAndCertification_PlaceContainingIgnoreCase
	(String user, String title, String user2, String technology, String user3, String duration, String user4, String place);
	List<CertificationCoach> findByCoach_IdAndCertification_TitleContainingIgnoreCaseOrCoach_IdAndCertification_Technology_NameContainingIgnoreCaseOrCoach_IdAndCertification_DurationContainingIgnoreCaseOrCoach_IdAndCertification_PlaceContainingIgnoreCase
	(String coachid, String title, String coachid2, String technology, String coachid3, String duration, String coachid4, String place, Pageable pageable);

}
