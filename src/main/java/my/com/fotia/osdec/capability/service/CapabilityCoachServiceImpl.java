package my.com.fotia.osdec.capability.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.capability.dao.CapabilityCoachDao;
import my.com.fotia.osdec.capability.model.CapabilityCoach;

@Service
public class CapabilityCoachServiceImpl implements CapabilityCoachService{
	
	@Autowired
	CapabilityCoachDao capabilityCoachDao;

	@Override
	public CapabilityCoach findById(String id) {
		return capabilityCoachDao.findById(id);
	}

	@Override
	public List<CapabilityCoach> CapabilityCoachList() {
		return capabilityCoachDao.findAll();
	}

	@Override
	public CapabilityCoach save(CapabilityCoach capabilityCoach) {
		return capabilityCoachDao.save(capabilityCoach);
	}

	@Override
	public void deleteById(String id) {
		capabilityCoachDao.deleteById(id);
	}

	@Override
	public List<CapabilityCoach> findAll() {
		return capabilityCoachDao.findAll();
	}

	@Override
	public List<CapabilityCoach> findByCoach_IdAndCapability_NameContainingIgnoreCaseOrCoach_IdAndCapability_Kepakaran_NameContainingIgnoreCaseOrCoach_IdAndCapability_DurationContainingIgnoreCase(
			String id, String name, String id2, String kepakaran, String id3, String duration, Pageable pageable) {
		return capabilityCoachDao.findByCoach_IdAndCapability_NameContainingIgnoreCaseOrCoach_IdAndCapability_Kepakaran_NameContainingIgnoreCaseOrCoach_IdAndCapability_DurationContainingIgnoreCase(id, name, id2, kepakaran, id3, duration, pageable);
	}

	@Override
	public long countByCoach_IdAndCapability_NameContainingIgnoreCaseOrCoach_IdAndCapability_Kepakaran_NameContainingIgnoreCaseOrCoach_IdAndCapability_DurationContainingIgnoreCase(
			String id, String name, String id2, String kepakaran, String id3, String duration) {
		return capabilityCoachDao.countByCoach_IdAndCapability_NameContainingIgnoreCaseOrCoach_IdAndCapability_Kepakaran_NameContainingIgnoreCaseOrCoach_IdAndCapability_DurationContainingIgnoreCase(id, name, id2, kepakaran, id3, duration);
	}

	@Override
	public List<CapabilityCoach> findByCapability_Id(String id) {
		return capabilityCoachDao.findByCapability_Id(id);
	}

	@Override
	public boolean existsByCapability_IdAndCoach_Id(String apabId, String coachId) {
		return capabilityCoachDao.existsByCapability_IdAndCoach_Id(apabId, coachId);
	}

	@Override
	public void deleteByCapability_Id(String id) {
		capabilityCoachDao.deleteByCapability_Id(id);
	}

}
