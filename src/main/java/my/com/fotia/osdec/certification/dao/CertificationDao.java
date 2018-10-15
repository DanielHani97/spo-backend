package my.com.fotia.osdec.certification.dao;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import my.com.fotia.osdec.certification.model.Certification;

@Transactional
public interface CertificationDao extends JpaRepository<Certification, Long> {
	
	Certification findByTitle(String title);
	Certification findById(String id);
	List<Certification> findAll();
	List<Certification> findByStatusAndStartDateGreaterThanEqual(String status, Date startDate);
	List<Certification> findByTitleContainingIgnoreCaseOrTechnology_NameContainingIgnoreCaseOrDurationContainingIgnoreCaseOrPlaceContainingIgnoreCaseOrLevelContainingIgnoreCaseOrStatusContainingIgnoreCase
	(String title, String technology, String duration, String place, String level, String status, Pageable pageable);
	
	@Modifying
	@Query("DELETE from Certification where id = :id")
	void deleteById(@Param("id") String id);
	
	@Modifying
	@Query("DELETE from CertificationCoach where certification_id = :id")
	void deleteByCertificationCoachId(@Param("id") String id);
	
	@Modifying
	@Query("DELETE from CertificationUser where certification_id = :id")
	void deleteByCertificationUserId(@Param("id") String id);
	
	@Modifying
	@Query("DELETE from MandayTransaction where instanceId = :id")
	void deleteByMandayTransactionId(@Param("id") String id);
	
	long countByTitleContainingIgnoreCaseOrTechnology_NameContainingIgnoreCaseOrDurationContainingIgnoreCaseOrPlaceContainingIgnoreCaseOrLevelContainingIgnoreCaseOrStatusContainingIgnoreCase
	(String title, String technology, String duration, String place, String level, String status);

}
