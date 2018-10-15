package my.com.fotia.osdec.general.infrastructure.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import my.com.fotia.osdec.general.infrastructure.model.Infrastructure;
import my.com.fotia.osdec.user.model.User;

@Transactional
public interface InfrastructureDao extends JpaRepository<Infrastructure, Long>{
	
	Infrastructure findById(String id);
	List<Infrastructure> findAll();
	List<Infrastructure> findByUser(User user);
	
	@Modifying
	@Query("DELETE from Infrastructure where id = :id")
	void deleteById(@Param("id") String id);
	
	List<Infrastructure> findByUser_NameContainingIgnoreCaseOrAgency_CodeContainingIgnoreCaseOrOsContainingIgnoreCaseOrTypeContainingIgnoreCase(
			String search, String search2, String search3, String search4, Pageable pageable);
	Long countByUser_NameContainingIgnoreCaseOrAgency_CodeContainingIgnoreCaseOrOsContainingIgnoreCaseOrTypeContainingIgnoreCase(
			String search, String search2, String search3, String search4);
	List<Infrastructure> findByUser_IdAndAgency_CodeContainingIgnoreCaseOrUser_IdAndOsContainingIgnoreCaseOrUser_IdAndTypeContainingIgnoreCase(
			String id, String search, String id2, String search2, String id3, String search3, Pageable pageable);
	Long countByUser_IdAndAgency_CodeContainingIgnoreCaseOrUser_IdAndOsContainingIgnoreCaseOrUser_IdAndTypeContainingIgnoreCase(
			String id, String search, String id2, String search2, String id3, String search3);
	List<Infrastructure> findByStatusNotAndUser_NameContainingIgnoreCaseOrStatusNotAndCreatedBy_NameContainingIgnoreCaseOrStatusNotAndModifiedBy_NameContainingIgnoreCase(
			String statusnot, String search, String statusnot2, String search2, String statusnot3, String search3,
			Pageable pageable);
	Long countByStatusNotAndUser_NameContainingIgnoreCaseOrStatusNotAndCreatedBy_NameContainingIgnoreCaseOrStatusNotAndModifiedBy_NameContainingIgnoreCase(
			String statusnot, String search, String statusnot2, String search2, String statusnot3, String search3);

}
