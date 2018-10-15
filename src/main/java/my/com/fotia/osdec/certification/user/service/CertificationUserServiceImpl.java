package my.com.fotia.osdec.certification.user.service;

import java.util.List;
import java.util.Collection; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.certification.user.dao.CertificationUserDao;
import my.com.fotia.osdec.certification.user.model.CertificationUser;
import my.com.fotia.osdec.user.model.User;

@Service
public class CertificationUserServiceImpl implements CertificationUserService {
	
	@Autowired
	CertificationUserDao certificationUserDao;
	
	public CertificationUser findById(String id) {
		return certificationUserDao.findById(id);
	}

	public List<CertificationUser> certificationUserList() {
		return certificationUserDao.findAll();
	}

	@Override
	public void save(CertificationUser certificationUser) {
		certificationUserDao.save(certificationUser);
	}

	@Override
	public void deleteById(String id) {
		certificationUserDao.deleteById(id);
	}
	@Override
	public List<CertificationUser> findByUser(User user) {
		// TODO Auto-generated method stub
		return certificationUserDao.findByUser(user);
	}
	
	@Override
	public List<CertificationUser> findAll() {
		// TODO Auto-generated method stub
		return certificationUserDao.findAll();
	}
	
	@Override
	public List<CertificationUser> findByCertification_id(String id) {
		// TODO Auto-generated method stub
		return certificationUserDao.findByCertification_id(id);
	}
	
	@Override
	public List<CertificationUser> findByUser_NameContainingIgnoreCaseOrUser_Agency_NameContainingIgnoreCaseOrCertification_TitleContainingIgnoreCase(String name, String name2, String title, Pageable pageable) {
		
		return certificationUserDao.findByUser_NameContainingIgnoreCaseOrUser_Agency_NameContainingIgnoreCaseOrCertification_TitleContainingIgnoreCase(name,  name2, title, pageable);
	}
	
	@Override
	public List<CertificationUser> findByUser_IdAndCertification_TitleContainingIgnoreCaseOrUser_IdAndCertification_Technology_NameContainingIgnoreCaseOrUser_IdAndCertification_DurationContainingIgnoreCaseOrUser_IdAndCertification_PlaceContainingIgnoreCase(
			String userid, String title, String userid2, String technology, String userid3, String duration, String userid4, String place, Pageable pageable) {
		
		return certificationUserDao.findByUser_IdAndCertification_TitleContainingIgnoreCaseOrUser_IdAndCertification_Technology_NameContainingIgnoreCaseOrUser_IdAndCertification_DurationContainingIgnoreCaseOrUser_IdAndCertification_PlaceContainingIgnoreCase(userid, title, userid2, technology, userid3, duration, userid4, place, pageable);
	}

	@Override
	public long countByUser_IdAndCertification_TitleContainingIgnoreCaseOrUser_IdAndCertification_Technology_NameContainingIgnoreCaseOrUser_IdAndCertification_DurationContainingIgnoreCaseOrUser_IdAndCertification_PlaceContainingIgnoreCase(
			String user, String title, String user2, String technology, String user3, String duration, String user4, String place) {
		
		return certificationUserDao.countByUser_IdAndCertification_TitleContainingIgnoreCaseOrUser_IdAndCertification_Technology_NameContainingIgnoreCaseOrUser_IdAndCertification_DurationContainingIgnoreCaseOrUser_IdAndCertification_PlaceContainingIgnoreCase(user, title, user2, technology, user3, duration, user4, place);
	}

	@Override
	public long countByUser_NameContainingIgnoreCaseOrUser_Agency_NameContainingIgnoreCaseOrCertification_TitleContainingIgnoreCase( String name, String name2, String title) {
		
		return certificationUserDao.countByUser_NameContainingIgnoreCaseOrUser_Agency_NameContainingIgnoreCaseOrCertification_TitleContainingIgnoreCase(name, name2, title);
	}

	@Override
	public boolean existsByUser_IdAndCertification_IdAndStatus(String userId, String certificationId, String status) {
		return certificationUserDao.existsByUser_IdAndCertification_IdAndStatus(userId, certificationId, status);
	}

	@Override
	public List<CertificationUser> findByUser_Id(String id) {
		return certificationUserDao.findByUser_Id(id);
	}
	
	@Override 
	  public List<CertificationUser> findByStatusInAndCreatedBy_NameContainingIgnoreCaseOrStatusInAndEvaluatedBy_NameContainingIgnoreCaseOrStatusInAndApprovedBy_NameContainingIgnoreCase( 
	      Collection<String> collectiona, String searcha, Collection<String> collection, String search, 
	      Collection<String> collection2, String search2, 
	      Pageable pageable) { 
	    return certificationUserDao.findByStatusInAndCreatedBy_NameContainingIgnoreCaseOrStatusInAndEvaluatedBy_NameContainingIgnoreCaseOrStatusInAndApprovedBy_NameContainingIgnoreCase(collectiona, searcha, collection, search, collection2, search2, pageable); 
	  } 
	 
	  @Override 
	  public Long countByStatusInAndCreatedBy_NameContainingIgnoreCaseOrStatusInAndEvaluatedBy_NameContainingIgnoreCaseOrStatusInAndApprovedBy_NameContainingIgnoreCase( 
	      Collection<String> collectiona, String searcha, Collection<String> collection, String search, 
	      Collection<String> collection2, String search2) { 
	    return certificationUserDao.countByStatusInAndCreatedBy_NameContainingIgnoreCaseOrStatusInAndEvaluatedBy_NameContainingIgnoreCaseOrStatusInAndApprovedBy_NameContainingIgnoreCase(collectiona, searcha, collection, search, collection2, search2); 
	  }
	  
	  @Override 
	  public List<CertificationUser> findByStatusInAndUser_NameContainingIgnoreCaseOrStatusInAndUser_Agency_NameContainingIgnoreCaseOrStatusInAndUser_EmailContainingIgnoreCaseOrStatusInAndUser_PositionContainingIgnoreCaseOrStatusInAndCertification_TitleContainingIgnoreCase( 
	      Collection<String> collectiona, String searcha, Collection<String> collection, String search, 
	      Collection<String> collection2, String search2, Collection<String> collection3, String search3, Collection<String> collection4, String search4, 
	      Pageable pageable) { 
	    return certificationUserDao.findByStatusInAndUser_NameContainingIgnoreCaseOrStatusInAndUser_Agency_NameContainingIgnoreCaseOrStatusInAndUser_EmailContainingIgnoreCaseOrStatusInAndUser_PositionContainingIgnoreCaseOrStatusInAndCertification_TitleContainingIgnoreCase(collectiona, searcha, collection, search, collection2, search2, collection3, search3, collection4, search4, pageable); 
	  } 
	 
	  @Override 
	  public Long countByStatusInAndUser_NameContainingIgnoreCaseOrStatusInAndUser_Agency_NameContainingIgnoreCaseOrStatusInAndUser_EmailContainingIgnoreCaseOrStatusInAndUser_PositionContainingIgnoreCaseOrStatusInAndCertification_TitleContainingIgnoreCase( 
	      Collection<String> collectiona, String searcha, Collection<String> collection, String search, 
	      Collection<String> collection2, String search2, Collection<String> collection3, String search3, Collection<String> collection4, String search4) { 
	    return certificationUserDao.countByStatusInAndUser_NameContainingIgnoreCaseOrStatusInAndUser_Agency_NameContainingIgnoreCaseOrStatusInAndUser_EmailContainingIgnoreCaseOrStatusInAndUser_PositionContainingIgnoreCaseOrStatusInAndCertification_TitleContainingIgnoreCase(collectiona, searcha, collection, search, collection2, search2, collection3, search3, collection4, search4); 
	  }
}
