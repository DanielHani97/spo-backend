package my.com.fotia.osdec.general.infrastructure.service;

import java.util.List;
import org.springframework.data.domain.Pageable;

import my.com.fotia.osdec.general.infrastructure.model.Infrastructure;
import my.com.fotia.osdec.user.model.User;

public interface InfrastructureService {
	Infrastructure findById(String id);
	List<Infrastructure> infrastructureList();
	List<Infrastructure> findByUser(User user);
	void save(Infrastructure infrastructure);
	void deleteById(String id);
	
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
