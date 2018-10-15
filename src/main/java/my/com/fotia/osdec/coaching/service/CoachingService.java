package my.com.fotia.osdec.coaching.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;

import my.com.fotia.osdec.coaching.model.Coaching;

public interface CoachingService {
	
	
	Coaching findByName(String name);
	Coaching findById(String id);
	List<Coaching> coachingList();
	List<Coaching> findByNameContainingIgnoreCaseOrAgency_CodeContainingIgnoreCaseOrStatusContainingIgnoreCase
	(String name, String agency, String status, Pageable pageable);
	long countByNameContainingIgnoreCaseOrAgency_CodeContainingIgnoreCaseOrStatusContainingIgnoreCase
	(String name, String agency, String status);
	Coaching save(Coaching coaching);
	void deleteById(String id);

	void updateStatus(String status, String id);
	List<Coaching> findByStatusInAndNameContainingIgnoreCaseOrStatusInAndCreatedBy_NameContainingIgnoreCaseOrStatusInAndModifiedBy_NameContainingIgnoreCaseOrStatusInAndEvaluatedBy_NameContainingIgnoreCaseOrStatusInAndApprovedBy_NameContainingIgnoreCaseOrStatusInAndVerifiedBy_NameContainingIgnoreCase(
			Collection<String> collectiona, String searcha, Collection<String> collection, String search, Collection<String> collection2, String search2,
			Collection<String> collection3, String search3, Collection<String> collection4, String search4,
			Collection<String> collection5, String search5, Pageable pageable);
	Long countByStatusInAndNameContainingIgnoreCaseOrStatusInAndCreatedBy_NameContainingIgnoreCaseOrStatusInAndModifiedBy_NameContainingIgnoreCaseOrStatusInAndEvaluatedBy_NameContainingIgnoreCaseOrStatusInAndApprovedBy_NameContainingIgnoreCaseOrStatusInAndVerifiedBy_NameContainingIgnoreCase(
			Collection<String> collectiona, String searcha, Collection<String> collection, String search, Collection<String> collection2, String search2,
			Collection<String> collection3, String search3, Collection<String> collection4, String search4,
			Collection<String> collection5, String search5);
	List<Coaching> findByStatusAndNameContainingIgnoreCaseOrStatusAndAgency_CodeContainingIgnoreCase(String status,
			String search, String status2, String search2, Pageable pageable);
	Long countByStatusAndNameContainingIgnoreCaseOrStatusAndAgency_CodeContainingIgnoreCase(String status,
			String search, String status2, String search2);
	Long countByStatusInAndNameContainingIgnoreCaseOrStatusInAndAgency_CodeContainingIgnoreCase(
			Collection<String> collection, String search, Collection<String> collection2, String search2);
	List<Coaching> findByStatusInAndNameContainingIgnoreCaseOrStatusInAndAgency_CodeContainingIgnoreCase(
			Collection<String> collection, String search, Collection<String> collection2, String search2,
			Pageable pageable);

	
}
