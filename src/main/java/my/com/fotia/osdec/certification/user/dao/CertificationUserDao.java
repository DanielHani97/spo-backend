package my.com.fotia.osdec.certification.user.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import my.com.fotia.osdec.certification.user.model.CertificationUser;
import my.com.fotia.osdec.user.model.User;

@Transactional
public interface CertificationUserDao extends CrudRepository<CertificationUser, Long> {
	
	List<CertificationUser> findByUser(User user);
	CertificationUser findById(String id);
	List<CertificationUser> findAll();
	List<CertificationUser> findByUser_Id(String id);
	
	@Modifying
	List<CertificationUser> findByCertification_id(String certification_id);
	
	@Modifying
	@Query("DELETE from Certification Transaction where id = :id")
	void deleteById(@Param("id") String id);
	
	List<CertificationUser> findByUser_NameContainingIgnoreCaseOrUser_Agency_NameContainingIgnoreCaseOrCertification_TitleContainingIgnoreCase
	(String name, String name2, String title, Pageable pageable);
	
	long countByUser_NameContainingIgnoreCaseOrUser_Agency_NameContainingIgnoreCaseOrCertification_TitleContainingIgnoreCase
	(String name, String name2, String title);

	
	List<CertificationUser> findByUser_IdAndCertification_TitleContainingIgnoreCaseOrUser_IdAndCertification_Technology_NameContainingIgnoreCaseOrUser_IdAndCertification_DurationContainingIgnoreCaseOrUser_IdAndCertification_PlaceContainingIgnoreCase
	(String userid, String title, String userid2, String technology, String userid3, String duration, String userid4, String place, Pageable pageable);
	
	long countByUser_IdAndCertification_TitleContainingIgnoreCaseOrUser_IdAndCertification_Technology_NameContainingIgnoreCaseOrUser_IdAndCertification_DurationContainingIgnoreCaseOrUser_IdAndCertification_PlaceContainingIgnoreCase
	(String user, String title, String user2, String technology, String user3, String duration, String user4, String place);

	boolean existsByUser_IdAndCertification_IdAndStatus(String userId, String certificationId, String status);
	
	List<CertificationUser> findByStatusInAndCreatedBy_NameContainingIgnoreCaseOrStatusInAndEvaluatedBy_NameContainingIgnoreCaseOrStatusInAndApprovedBy_NameContainingIgnoreCase( 
			Collection<String> collectiona, String searcha, Collection<String> collection, String search, Collection<String> collection2, String search2, Pageable pageable); 

    Long countByStatusInAndCreatedBy_NameContainingIgnoreCaseOrStatusInAndEvaluatedBy_NameContainingIgnoreCaseOrStatusInAndApprovedBy_NameContainingIgnoreCase( 
    		Collection<String> collectiona, String searcha, Collection<String> collection, String search, Collection<String> collection2, String search2); 
    
    List<CertificationUser> findByStatusInAndUser_NameContainingIgnoreCaseOrStatusInAndUser_Agency_NameContainingIgnoreCaseOrStatusInAndUser_EmailContainingIgnoreCaseOrStatusInAndUser_PositionContainingIgnoreCaseOrStatusInAndCertification_TitleContainingIgnoreCase( 
			Collection<String> collectiona, String searcha, Collection<String> collection, String search, Collection<String> collection2, String search2, Collection<String> collection3, String search3, Collection<String> collection4, String search4, Pageable pageable); 

    Long countByStatusInAndUser_NameContainingIgnoreCaseOrStatusInAndUser_Agency_NameContainingIgnoreCaseOrStatusInAndUser_EmailContainingIgnoreCaseOrStatusInAndUser_PositionContainingIgnoreCaseOrStatusInAndCertification_TitleContainingIgnoreCase( 
    		Collection<String> collectiona, String searcha, Collection<String> collection, String search, Collection<String> collection2, String search2, Collection<String> collection3, String search3, Collection<String> collection4, String search4); 
  
    
}
