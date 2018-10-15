package my.com.fotia.osdec.user.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.user.dao.UserAuthoritiesDao;
import my.com.fotia.osdec.user.model.UserAuthorities;

@Service
public class UserAuthoritiesServiceImpl implements UserAuthoritiesService{

	@Autowired
	UserAuthoritiesDao userAuthoritiesDao;
	
	@Override
	public List<UserAuthorities> findByAuthority_Id(String authId) {
		return userAuthoritiesDao.findByAuthority_Id(authId);
	}

	@Override
	public List<UserAuthorities> findByUser_Id(String userId) {
		return userAuthoritiesDao.findByUser_Id(userId);
	}

	@Override
	public void save(UserAuthorities userAuthorities) {
		userAuthoritiesDao.save(userAuthorities);
	}

	@Override
	public List<UserAuthorities> findByAuthority_IdAndUser_NameContainingIgnoreCaseOrAuthority_IdAndUser_EmailContainingIgnoreCase(
			String string, String search, String string2, String search2, Pageable pageable) {
		
		return userAuthoritiesDao.findByAuthority_IdAndUser_NameContainingIgnoreCaseOrAuthority_IdAndUser_EmailContainingIgnoreCase(string, search, string2, search2, pageable);
	}

	@Override
	public long countByAuthority_IdAndUser_NameContainingIgnoreCaseOrAuthority_IdAndUser_EmailContainingIgnoreCase(
			String string, String search, String string2, String search2) {
		return userAuthoritiesDao.countByAuthority_IdAndUser_NameContainingIgnoreCaseOrAuthority_IdAndUser_EmailContainingIgnoreCase(string, search, string2, search2);
	}

	@Override
	public List<UserAuthorities> findByUser_IdNotInAndAuthority_IdAndUser_NameContainingIgnoreCaseOrUserIdNotInAndAuthority_IdAndUser_EmailContainingIgnoreCase(
			Collection<String> exceptId, String string, String search, Collection<String> exceptId2, String string2,
			String search2, Pageable pageable) {
		return userAuthoritiesDao.findByUser_IdNotInAndAuthority_IdAndUser_NameContainingIgnoreCaseOrUserIdNotInAndAuthority_IdAndUser_EmailContainingIgnoreCase(exceptId, string, search, exceptId2, string2, search2, pageable);
	}

	@Override
	public Long countByUser_IdNotInAndAuthority_IdAndUser_NameContainingIgnoreCaseOrUser_IdNotInAndAuthority_IdAndUser_EmailContainingIgnoreCase(
			Collection<String> exceptId, String string, String search, Collection<String> exceptId2, String string2,
			String search2) {
		return userAuthoritiesDao.countByUser_IdNotInAndAuthority_IdAndUser_NameContainingIgnoreCaseOrUser_IdNotInAndAuthority_IdAndUser_EmailContainingIgnoreCase(exceptId, string, search, exceptId2, string2, search2);
	}

	

}
