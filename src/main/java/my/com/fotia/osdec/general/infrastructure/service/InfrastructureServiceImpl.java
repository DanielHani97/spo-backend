package my.com.fotia.osdec.general.infrastructure.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import my.com.fotia.osdec.general.infrastructure.dao.InfrastructureDao;
import my.com.fotia.osdec.general.infrastructure.model.Infrastructure;
import my.com.fotia.osdec.user.model.User;

@Service
public class InfrastructureServiceImpl implements InfrastructureService{
	
	@Autowired
	InfrastructureDao infrastructureDao;

	@Override
	public Infrastructure findById(String id) {
		return infrastructureDao.findById(id);
	}

	@Override
	public List<Infrastructure> infrastructureList() {
		return infrastructureDao.findAll();
	}

	@Override
	public void save(Infrastructure infrastructure) {
		infrastructureDao.save(infrastructure);
		
	}

	@Override
	public void deleteById(String id) {
		infrastructureDao.deleteById(id);
		
	}

	@Override
	public List<Infrastructure> findByUser(User user) {
		return infrastructureDao.findByUser(user);
	}

	@Override
	public List<Infrastructure> findByUser_NameContainingIgnoreCaseOrAgency_CodeContainingIgnoreCaseOrOsContainingIgnoreCaseOrTypeContainingIgnoreCase(
			String search, String search2, String search3, String search4, Pageable pageable) {
		return infrastructureDao.findByUser_NameContainingIgnoreCaseOrAgency_CodeContainingIgnoreCaseOrOsContainingIgnoreCaseOrTypeContainingIgnoreCase(search, search2, search3, search4, pageable);
	}

	@Override
	public Long countByUser_NameContainingIgnoreCaseOrAgency_CodeContainingIgnoreCaseOrOsContainingIgnoreCaseOrTypeContainingIgnoreCase(
			String search, String search2, String search3, String search4) {
		return infrastructureDao.countByUser_NameContainingIgnoreCaseOrAgency_CodeContainingIgnoreCaseOrOsContainingIgnoreCaseOrTypeContainingIgnoreCase(search, search2, search3, search4);
	}

	@Override
	public List<Infrastructure> findByUser_IdAndAgency_CodeContainingIgnoreCaseOrUser_IdAndOsContainingIgnoreCaseOrUser_IdAndTypeContainingIgnoreCase(
			String id, String search, String id2, String search2, String id3, String search3, Pageable pageable) {
		return infrastructureDao.findByUser_IdAndAgency_CodeContainingIgnoreCaseOrUser_IdAndOsContainingIgnoreCaseOrUser_IdAndTypeContainingIgnoreCase(id, search, id2, search2, id3, search3, pageable);
	}

	@Override
	public Long countByUser_IdAndAgency_CodeContainingIgnoreCaseOrUser_IdAndOsContainingIgnoreCaseOrUser_IdAndTypeContainingIgnoreCase(
			String id, String search, String id2, String search2, String id3, String search3) {
		return infrastructureDao.countByUser_IdAndAgency_CodeContainingIgnoreCaseOrUser_IdAndOsContainingIgnoreCaseOrUser_IdAndTypeContainingIgnoreCase(id, search, id2, search2, id3, search3);
	}

	@Override
	public List<Infrastructure> findByStatusNotAndUser_NameContainingIgnoreCaseOrStatusNotAndCreatedBy_NameContainingIgnoreCaseOrStatusNotAndModifiedBy_NameContainingIgnoreCase(
			String statusnot, String search, String statusnot2, String search2, String statusnot3, String search3,
			Pageable pageable) {
		return infrastructureDao.findByStatusNotAndUser_NameContainingIgnoreCaseOrStatusNotAndCreatedBy_NameContainingIgnoreCaseOrStatusNotAndModifiedBy_NameContainingIgnoreCase(statusnot, search, statusnot2, search2, statusnot3, search3, pageable);
	}

	@Override
	public Long countByStatusNotAndUser_NameContainingIgnoreCaseOrStatusNotAndCreatedBy_NameContainingIgnoreCaseOrStatusNotAndModifiedBy_NameContainingIgnoreCase(
			String statusnot, String search, String statusnot2, String search2, String statusnot3, String search3) {
		return infrastructureDao.countByStatusNotAndUser_NameContainingIgnoreCaseOrStatusNotAndCreatedBy_NameContainingIgnoreCaseOrStatusNotAndModifiedBy_NameContainingIgnoreCase(statusnot, search, statusnot2, search2, statusnot3, search3);
	}
	

}
