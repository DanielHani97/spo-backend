package my.com.fotia.osdec.user.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import my.com.fotia.osdec.general.agency.model.Agency;
import my.com.fotia.osdec.user.model.AppAuthority;

@Transactional
public interface AppAuthorityDao extends JpaRepository<AppAuthority,Long>{

	List<AppAuthority> findAll();
	List<AppAuthority> findByStatus(String status);
	
	@Modifying
	@Query("UPDATE AppAuthority set status = ?1, modifiedby = ?2, modifiedon = ?3 where id = ?4")
	void updateStatus(String status, String userId, Date modifiedon, String id);
	
	List<AppAuthority> findByUser_NameContainingOrUser_EmailOrAuthority_RolenameAllIgnoreCase
	(String name, String email, String role, Pageable pageable);
	
	long countByUser_NameContainingOrUser_EmailOrAuthority_RolenameAllIgnoreCase
	(String name, String email, String role);
	
	AppAuthority findById(String id);
	
	List<AppAuthority> findByStatusNotAndUser_NameContainingIgnoreCaseOrStatusNotAndCreatedby_NameContainingIgnoreCaseOrStatusNotAndModifiedby_NameContainingIgnoreCase(
			String status, String search, String status2, String search2, String status3, String search3,
			Pageable pageable);
	Long countByStatusNotAndUser_NameContainingIgnoreCaseOrStatusNotAndCreatedby_NameContainingIgnoreCaseOrStatusNotAndModifiedby_NameContainingIgnoreCase(
			String status, String search, String status2, String search2, String status3, String search3);
}
