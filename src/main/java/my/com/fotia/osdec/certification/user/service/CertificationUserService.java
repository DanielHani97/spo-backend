package my.com.fotia.osdec.certification.user.service;

import java.util.List;
import java.util.Collection; 

import org.springframework.data.domain.Pageable;

import my.com.fotia.osdec.certification.user.model.CertificationUser;
import my.com.fotia.osdec.user.model.User;

public interface CertificationUserService {
	
	List<CertificationUser> findByUser(User user);
	CertificationUser findById(String id);
	List<CertificationUser> certificationUserList();
	List<CertificationUser> findByCertification_id (String id);
	void save(CertificationUser certificationUser);
	void deleteById(String id);
	List<CertificationUser> findAll();
	
	List<CertificationUser> findByUser_Id(String id);
	
	long countByUser_NameContainingIgnoreCaseOrUser_Agency_NameContainingIgnoreCaseOrCertification_TitleContainingIgnoreCase
	(String name, String name2, String title);
	
	List<CertificationUser> findByUser_NameContainingIgnoreCaseOrUser_Agency_NameContainingIgnoreCaseOrCertification_TitleContainingIgnoreCase
	(String name, String name2, String title, Pageable pageable);

	
	long countByUser_IdAndCertification_TitleContainingIgnoreCaseOrUser_IdAndCertification_Technology_NameContainingIgnoreCaseOrUser_IdAndCertification_DurationContainingIgnoreCaseOrUser_IdAndCertification_PlaceContainingIgnoreCase
	(String user, String title, String user2, String technology, String user3, String duration, String user4, String place);
	
	List<CertificationUser> findByUser_IdAndCertification_TitleContainingIgnoreCaseOrUser_IdAndCertification_Technology_NameContainingIgnoreCaseOrUser_IdAndCertification_DurationContainingIgnoreCaseOrUser_IdAndCertification_PlaceContainingIgnoreCase
	(String userid, String title, String userid2, String technology, String userid3, String duration, String userid4, String place, Pageable pageable);

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
