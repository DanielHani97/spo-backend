package my.com.fotia.osdec.user.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import my.com.fotia.osdec.user.model.AppAuthority;

public interface AppAuthorityService {

	List<AppAuthority> findAll();
	List<AppAuthority> findByStatus(String status);
	void save(AppAuthority appAuthority);
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
