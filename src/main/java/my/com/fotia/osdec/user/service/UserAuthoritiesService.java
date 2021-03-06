package my.com.fotia.osdec.user.service;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Pageable;

import my.com.fotia.osdec.user.model.UserAuthorities;

public interface UserAuthoritiesService {
		
	List<UserAuthorities> findByAuthority_Id(String authId);
	List<UserAuthorities> findByUser_Id(String userId);
	void save(UserAuthorities userAuthorities);
	List<UserAuthorities> findByAuthority_IdAndUser_NameContainingIgnoreCaseOrAuthority_IdAndUser_EmailContainingIgnoreCase(
			String string, String search, String string2, String search2, Pageable pageable);
	long countByAuthority_IdAndUser_NameContainingIgnoreCaseOrAuthority_IdAndUser_EmailContainingIgnoreCase(
			String string, String search, String string2, String search2);
	List<UserAuthorities> findByUser_IdNotInAndAuthority_IdAndUser_NameContainingIgnoreCaseOrUserIdNotInAndAuthority_IdAndUser_EmailContainingIgnoreCase(
			Collection<String> exceptId, String string, String search, Collection<String> exceptId2, String string2,
			String search2, Pageable pageable);
	Long countByUser_IdNotInAndAuthority_IdAndUser_NameContainingIgnoreCaseOrUser_IdNotInAndAuthority_IdAndUser_EmailContainingIgnoreCase(
			Collection<String> exceptId, String string, String search, Collection<String> exceptId2, String string2,
			String search2);
	
	
}
