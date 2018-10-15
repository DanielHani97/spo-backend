package my.com.fotia.osdec.capability.service;

import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.capability.dao.CapabilityUserDao;
import my.com.fotia.osdec.capability.model.CapabilityUser;

@Service
public class CapabilityUserServiceImpl implements CapabilityUserService{
	
	@Autowired
	CapabilityUserDao capabilityUserDao;

	@Override
	public CapabilityUser findById(String id) {
		return capabilityUserDao.findById(id);
	}

	@Override
	public List<CapabilityUser> CapabilityUserList() {
		return capabilityUserDao.findAll();
	}

	@Override
	public CapabilityUser save(CapabilityUser capabilityUser) {
		return capabilityUserDao.save(capabilityUser);
	}

	@Override
	public void deleteById(String id) {
		capabilityUserDao.deleteById(id);
	}

	@Override
	public List<CapabilityUser> findByUser_IdAndCapability_NameContainingIgnoreCaseOrUser_IdAndCapability_Kepakaran_NameContainingIgnoreCaseOrUser_IdAndCapability_DurationContainingIgnoreCase(
			String id, String name, String id2, String kepakaran, String id3, String duration, Pageable pageable) {
		return capabilityUserDao.findByUser_IdAndCapability_NameContainingIgnoreCaseOrUser_IdAndCapability_Kepakaran_NameContainingIgnoreCaseOrUser_IdAndCapability_DurationContainingIgnoreCase(id, name, id2, kepakaran, id3, duration, pageable);
	}

	@Override
	public long countByUser_IdAndCapability_NameContainingIgnoreCaseOrUser_IdAndCapability_Kepakaran_NameContainingIgnoreCaseOrUser_IdAndCapability_DurationContainingIgnoreCase(
			String id, String name, String id2, String kepakaran, String id3, String duration) {
		return capabilityUserDao.countByUser_IdAndCapability_NameContainingIgnoreCaseOrUser_IdAndCapability_Kepakaran_NameContainingIgnoreCaseOrUser_IdAndCapability_DurationContainingIgnoreCase(id, name, id2, kepakaran, id3, duration);
	}

	@Override
	public List<CapabilityUser> findByCapability_id(String id) {
		return capabilityUserDao.findByCapability_id(id);
	}

	@Override
	public List<CapabilityUser> findByUser_NameContainingIgnoreCaseOrUser_Agency_NameContainingIgnoreCaseOrCapability_NameContainingIgnoreCase(
			String pemohon, String agency, String name, Pageable pageable) {
		return capabilityUserDao.findByUser_NameContainingIgnoreCaseOrUser_Agency_NameContainingIgnoreCaseOrCapability_NameContainingIgnoreCase(pemohon, agency, name, pageable);
	}

	@Override
	public long countByUser_NameContainingIgnoreCaseOrUser_Agency_NameContainingIgnoreCaseOrCapability_NameContainingIgnoreCase(
			String pemohon, String agency, String name) {
		return capabilityUserDao.countByUser_NameContainingIgnoreCaseOrUser_Agency_NameContainingIgnoreCaseOrCapability_NameContainingIgnoreCase(pemohon, agency, name);
	}

	@Override
	public boolean existsByUser_IdAndCapability_IdAndStatus(String userId, String capId, String status) {
		return capabilityUserDao.existsByUser_IdAndCapability_IdAndStatus(userId, capId, status);
	}

	@Override
	public List<CapabilityUser> findByUser_Id(String id) {
		return capabilityUserDao.findByUser_Id(id);
	}

	@Override
	public List<CapabilityUser> findByStatusInAndCapability_NameContainingIgnoreCaseOrStatusInAndCreatedBy_NameContainingIgnoreCaseOrStatusInAndEvaluatedBy_NameContainingIgnoreCaseOrStatusInAndApprovedBy_NameContainingIgnoreCase(
			Collection<String> collection, String search, Collection<String> collection2, String search2,
			Collection<String> collection3, String search3, Collection<String> collection4, String search4,
			Pageable pageable) {
		return capabilityUserDao.findByStatusInAndCapability_NameContainingIgnoreCaseOrStatusInAndCreatedBy_NameContainingIgnoreCaseOrStatusInAndEvaluatedBy_NameContainingIgnoreCaseOrStatusInAndApprovedBy_NameContainingIgnoreCase(collection, search, collection2, search2, collection3, search3, collection4, search4, pageable);
	}

	@Override
	public Long countByStatusInAndCapability_NameContainingIgnoreCaseOrStatusInAndCreatedBy_NameContainingIgnoreCaseOrStatusInAndEvaluatedBy_NameContainingIgnoreCaseOrStatusInAndApprovedBy_NameContainingIgnoreCase(
			Collection<String> collection, String search, Collection<String> collection2, String search2,
			Collection<String> collection3, String search3, Collection<String> collection4, String search4) {
		return capabilityUserDao.countByStatusInAndCapability_NameContainingIgnoreCaseOrStatusInAndCreatedBy_NameContainingIgnoreCaseOrStatusInAndEvaluatedBy_NameContainingIgnoreCaseOrStatusInAndApprovedBy_NameContainingIgnoreCase(collection, search, collection2, search2, collection3, search3, collection4, search4);
	}

	@Override
	public boolean existsByCapability_IdAndUser_Id(String apabId, String userId) {
		return capabilityUserDao.existsByCapability_IdAndUser_Id(apabId, userId);
	}

	@Override
	public List<CapabilityUser> findByCapability_idAndStatus(String id, String string) {
		return capabilityUserDao.findByCapability_idAndStatus(id, string);
	}

}
