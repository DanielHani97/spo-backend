package my.com.fotia.osdec.coaching.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import my.com.fotia.osdec.coaching.model.Coaching;

@Transactional
public interface CoachingDao extends JpaRepository<Coaching, Long>{
	
	Coaching findByName(String name);
	Coaching findById(String id);
	List<Coaching> findAll();
	
	@Modifying
	@Query("DELETE from Coaching where id = :id")
	void deleteById(@Param("id") String id);
	
	@Modifying
	@Query("SELECT count(*) from Coaching where name = :name")
	Boolean existsByName(@Param("name") String name);
	
	List<Coaching> findByNameContainingIgnoreCaseOrAgency_CodeContainingIgnoreCaseOrStatusContainingIgnoreCase
	(String name, String agency, String status, Pageable pageable);
	
	long countByNameContainingIgnoreCaseOrAgency_CodeContainingIgnoreCaseOrStatusContainingIgnoreCase
	(String name, String agency, String status);
	
	@Modifying
	@Query("UPDATE Coaching set status = ?1 where id = ?2")
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
