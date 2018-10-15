package my.com.fotia.osdec.certification.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.certification.dao.CertificationDao;
import my.com.fotia.osdec.certification.model.Certification;
import my.com.fotia.osdec.user.model.User;

@Service
public class CertificationServiceImpl  implements CertificationService {
	
	@Autowired
	CertificationDao certificationDao;
	
	public Certification findByTitle(String title) {
		return certificationDao.findByTitle(title);
	}
	
	public Certification findById(String id) {
		return certificationDao.findById(id);
	}
	
	@Override
	public void deleteByCertificationCoachId(String tcid) {
		certificationDao.deleteByCertificationCoachId(tcid);
	}
	
	@Override
	public void deleteByCertificationUserId(String txid) {
		certificationDao.deleteByCertificationUserId(txid);
	}
	
	@Override
	public void deleteByMandayTransactionId(String mtid) {
		certificationDao.deleteByCertificationUserId(mtid);
	}
	
	@Autowired
	public List<Certification> findAll() {
		return certificationDao.findAll();
	}

	@Override
	public List<Certification> certificationList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Certification save(Certification certification) {
		return certificationDao.save(certification);
	}

	@Override
	public void deleteById(String id) {
		certificationDao.deleteById(id);
	}
	
	@Override
	public long countByTitleContainingIgnoreCaseOrTechnology_NameContainingIgnoreCaseOrDurationContainingIgnoreCaseOrPlaceContainingIgnoreCaseOrLevelContainingIgnoreCaseOrStatusContainingIgnoreCase(
			String title, String technology, String duration, String place, String level, String status) {
		return certificationDao.countByTitleContainingIgnoreCaseOrTechnology_NameContainingIgnoreCaseOrDurationContainingIgnoreCaseOrPlaceContainingIgnoreCaseOrLevelContainingIgnoreCaseOrStatusContainingIgnoreCase(title, technology, duration, place, level, status);
	}

	@Override
	public List<Certification> findByTitleContainingIgnoreCaseOrTechnology_NameContainingIgnoreCaseOrDurationContainingIgnoreCaseOrPlaceContainingIgnoreCaseOrLevelContainingIgnoreCaseOrStatusContainingIgnoreCase(
			String title, String technology, String duration, String place, String level, String status, Pageable pageable) {
		return certificationDao.findByTitleContainingIgnoreCaseOrTechnology_NameContainingIgnoreCaseOrDurationContainingIgnoreCaseOrPlaceContainingIgnoreCaseOrLevelContainingIgnoreCaseOrStatusContainingIgnoreCase(title, technology, duration, place, level, status, pageable);
	}
	
	@Override
	public List<Certification> findByStatusAndStartDateGreaterThanEqual(String status, Date startDate) {
		return certificationDao.findByStatusAndStartDateGreaterThanEqual(status, startDate);
	}

}
