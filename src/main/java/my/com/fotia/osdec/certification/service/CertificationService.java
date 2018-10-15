package my.com.fotia.osdec.certification.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import my.com.fotia.osdec.certification.model.Certification;
import my.com.fotia.osdec.user.model.User;

public interface CertificationService {
	
	Certification findByTitle(String title);
	Certification findById(String id);
	List<Certification> findAll();
	List<Certification> certificationList();
	List<Certification> findByStatusAndStartDateGreaterThanEqual(String status, Date startDate);
	Certification save(Certification certification);
	void deleteById(String id);
	void deleteByCertificationCoachId(String tcid);
	void deleteByCertificationUserId(String txid);
	void deleteByMandayTransactionId(String mtid);
	long countByTitleContainingIgnoreCaseOrTechnology_NameContainingIgnoreCaseOrDurationContainingIgnoreCaseOrPlaceContainingIgnoreCaseOrLevelContainingIgnoreCaseOrStatusContainingIgnoreCase
	(String title, String technology, String duration, String place, String level, String status);
	List<Certification> findByTitleContainingIgnoreCaseOrTechnology_NameContainingIgnoreCaseOrDurationContainingIgnoreCaseOrPlaceContainingIgnoreCaseOrLevelContainingIgnoreCaseOrStatusContainingIgnoreCase
	(String title, String technology, String duration, String place, String level, String status, Pageable pageable);

}
