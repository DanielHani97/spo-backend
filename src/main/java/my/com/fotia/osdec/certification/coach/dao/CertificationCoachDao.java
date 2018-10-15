package my.com.fotia.osdec.certification.coach.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import my.com.fotia.osdec.certification.coach.model.CertificationCoach;
import my.com.fotia.osdec.user.model.User;

@Transactional
public interface CertificationCoachDao extends JpaRepository<CertificationCoach, Long> {
	
	List<CertificationCoach> findByCoach(User coach);
	CertificationCoach findById(String id);
	List<CertificationCoach> findAll();
	
	List<CertificationCoach> findByCoach_IdAndCertification_TitleContainingIgnoreCaseOrCoach_IdAndCertification_Technology_NameContainingIgnoreCaseOrCoach_IdAndCertification_DurationContainingIgnoreCaseOrCoach_IdAndCertification_PlaceContainingIgnoreCase
	(String coachid, String title, String coachid2, String technology, String coachid3, String duration, String coachid4, String place, Pageable pageable);
	
	@Modifying
	List<CertificationCoach> findByCertification_id(String certification_id);
	
	@Modifying
	@Query("DELETE from CertificationCoach where id = :id")
	void deleteById(@Param("id") String id);
	
	long countByCoach_IdAndCertification_TitleContainingIgnoreCaseOrCoach_IdAndCertification_Technology_NameContainingIgnoreCaseOrCoach_IdAndCertification_DurationContainingIgnoreCaseOrCoach_IdAndCertification_PlaceContainingIgnoreCase
	(String user, String title, String user2, String technology, String user3, String duration, String user4, String place);

}
