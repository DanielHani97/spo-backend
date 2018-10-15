package my.com.fotia.osdec.user.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.user.dao.AppAuthorityDao;
import my.com.fotia.osdec.user.model.AppAuthority;

@Service
public class AppAuthorityServiceImpl implements AppAuthorityService{

	@Autowired
	AppAuthorityDao appAuthorityDao;
	
	@Override
	public List<AppAuthority> findAll() {
		return appAuthorityDao.findAll();
	}

	@Override
	public List<AppAuthority> findByStatus(String status) {
		return appAuthorityDao.findByStatus(status);
	}

	@Override
	public void save(AppAuthority appAuthority) {
		appAuthorityDao.save(appAuthority);
	}

	@Override
	public void updateStatus(String status, String userId, Date modifiedon, String id) {
		appAuthorityDao.updateStatus(status, userId, modifiedon, id);
	}

	@Override
	public List<AppAuthority> findByUser_NameContainingOrUser_EmailOrAuthority_RolenameAllIgnoreCase(String name,
			String email, String role, Pageable pageable) {
		return appAuthorityDao.findByUser_NameContainingOrUser_EmailOrAuthority_RolenameAllIgnoreCase(name, email, role, pageable);
	}

	@Override
	public long countByUser_NameContainingOrUser_EmailOrAuthority_RolenameAllIgnoreCase(String name, String email,
			String role) {
		return appAuthorityDao.countByUser_NameContainingOrUser_EmailOrAuthority_RolenameAllIgnoreCase(name, email, role);
	}

	@Override
	public AppAuthority findById(String id) {
		return appAuthorityDao.findById(id);
	}

	@Override
	public List<AppAuthority> findByStatusNotAndUser_NameContainingIgnoreCaseOrStatusNotAndCreatedby_NameContainingIgnoreCaseOrStatusNotAndModifiedby_NameContainingIgnoreCase(
			String status, String search, String status2, String search2, String status3, String search3,
			Pageable pageable) {
		return appAuthorityDao.findByStatusNotAndUser_NameContainingIgnoreCaseOrStatusNotAndCreatedby_NameContainingIgnoreCaseOrStatusNotAndModifiedby_NameContainingIgnoreCase(status, search, status2, search2, status3, search3, pageable);
	}

	@Override
	public Long countByStatusNotAndUser_NameContainingIgnoreCaseOrStatusNotAndCreatedby_NameContainingIgnoreCaseOrStatusNotAndModifiedby_NameContainingIgnoreCase(
			String status, String search, String status2, String search2, String status3, String search3) {
		return appAuthorityDao.countByStatusNotAndUser_NameContainingIgnoreCaseOrStatusNotAndCreatedby_NameContainingIgnoreCaseOrStatusNotAndModifiedby_NameContainingIgnoreCase(status, search, status2, search2, status3, search3);
	}
}
