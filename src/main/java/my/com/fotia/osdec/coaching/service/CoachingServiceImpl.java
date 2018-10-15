package my.com.fotia.osdec.coaching.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.coaching.dao.CoachingDao;
import my.com.fotia.osdec.coaching.model.Coaching;

@Service
public class CoachingServiceImpl implements CoachingService{
	
	@Autowired
	CoachingDao coachingDao;
	
	public Coaching findByName(String name) {
		return coachingDao.findByName(name);
	}

	public Coaching findById(String id) {
		return coachingDao.findById(id);
	}

	public List<Coaching> coachingList() {
		return coachingDao.findAll();
	}

	@Override
	public Coaching save(Coaching coaching) {
		return coachingDao.save(coaching);

	}

	@Override
	public void deleteById(String id) {
		coachingDao.deleteById(id);
	}

	@Override
	public List<Coaching> findByNameContainingIgnoreCaseOrAgency_CodeContainingIgnoreCaseOrStatusContainingIgnoreCase(
			String name, String agency, String status, Pageable pageable) {
		return coachingDao.findByNameContainingIgnoreCaseOrAgency_CodeContainingIgnoreCaseOrStatusContainingIgnoreCase(name, agency, status, pageable);
	}

	@Override
	public long countByNameContainingIgnoreCaseOrAgency_CodeContainingIgnoreCaseOrStatusContainingIgnoreCase(
			String name, String agency, String status) {
		return coachingDao.countByNameContainingIgnoreCaseOrAgency_CodeContainingIgnoreCaseOrStatusContainingIgnoreCase(name, agency, status);
	}

	@Override
	public void updateStatus(String status,String id) {
		coachingDao.updateStatus(status, id);
	}

	@Override
	public List<Coaching> findByStatusInAndNameContainingIgnoreCaseOrStatusInAndCreatedBy_NameContainingIgnoreCaseOrStatusInAndModifiedBy_NameContainingIgnoreCaseOrStatusInAndEvaluatedBy_NameContainingIgnoreCaseOrStatusInAndApprovedBy_NameContainingIgnoreCaseOrStatusInAndVerifiedBy_NameContainingIgnoreCase(
			Collection<String> collectiona, String searcha, Collection<String> collection, String search,
			Collection<String> collection2, String search2, Collection<String> collection3, String search3,
			Collection<String> collection4, String search4, Collection<String> collection5, String search5,
			Pageable pageable) {
		return coachingDao.findByStatusInAndNameContainingIgnoreCaseOrStatusInAndCreatedBy_NameContainingIgnoreCaseOrStatusInAndModifiedBy_NameContainingIgnoreCaseOrStatusInAndEvaluatedBy_NameContainingIgnoreCaseOrStatusInAndApprovedBy_NameContainingIgnoreCaseOrStatusInAndVerifiedBy_NameContainingIgnoreCase(collectiona, searcha, collection, search, collection2, search2, collection3, search3, collection4, search4, collection5, search5, pageable);
	}

	@Override
	public Long countByStatusInAndNameContainingIgnoreCaseOrStatusInAndCreatedBy_NameContainingIgnoreCaseOrStatusInAndModifiedBy_NameContainingIgnoreCaseOrStatusInAndEvaluatedBy_NameContainingIgnoreCaseOrStatusInAndApprovedBy_NameContainingIgnoreCaseOrStatusInAndVerifiedBy_NameContainingIgnoreCase(
			Collection<String> collectiona, String searcha, Collection<String> collection, String search,
			Collection<String> collection2, String search2, Collection<String> collection3, String search3,
			Collection<String> collection4, String search4, Collection<String> collection5, String search5) {
		return coachingDao.countByStatusInAndNameContainingIgnoreCaseOrStatusInAndCreatedBy_NameContainingIgnoreCaseOrStatusInAndModifiedBy_NameContainingIgnoreCaseOrStatusInAndEvaluatedBy_NameContainingIgnoreCaseOrStatusInAndApprovedBy_NameContainingIgnoreCaseOrStatusInAndVerifiedBy_NameContainingIgnoreCase(collectiona, searcha, collection, search, collection2, search2, collection3, search3, collection4, search4, collection5, search5);
	}

	@Override
	public List<Coaching> findByStatusAndNameContainingIgnoreCaseOrStatusAndAgency_CodeContainingIgnoreCase(
			String status, String search, String status2, String search2, Pageable pageable) {
		return coachingDao.findByStatusAndNameContainingIgnoreCaseOrStatusAndAgency_CodeContainingIgnoreCase(status, search, status2, search2, pageable);
	}

	@Override
	public Long countByStatusAndNameContainingIgnoreCaseOrStatusAndAgency_CodeContainingIgnoreCase(String status,
			String search, String status2, String search2) {
		return coachingDao.countByStatusAndNameContainingIgnoreCaseOrStatusAndAgency_CodeContainingIgnoreCase(status, search, status2, search2);
	}

	@Override
	public Long countByStatusInAndNameContainingIgnoreCaseOrStatusInAndAgency_CodeContainingIgnoreCase(
			Collection<String> collection, String search, Collection<String> collection2, String search2) {
		return coachingDao.countByStatusInAndNameContainingIgnoreCaseOrStatusInAndAgency_CodeContainingIgnoreCase(collection, search, collection2, search2);
	}

	@Override
	public List<Coaching> findByStatusInAndNameContainingIgnoreCaseOrStatusInAndAgency_CodeContainingIgnoreCase(
			Collection<String> collection, String search, Collection<String> collection2, String search2,
			Pageable pageable) {
		return coachingDao.findByStatusInAndNameContainingIgnoreCaseOrStatusInAndAgency_CodeContainingIgnoreCase(collection, search, collection2, search2, pageable);
	}

	

}
