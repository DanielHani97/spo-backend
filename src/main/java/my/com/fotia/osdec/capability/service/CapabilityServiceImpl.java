package my.com.fotia.osdec.capability.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.capability.dao.CapabilityDao;
import my.com.fotia.osdec.capability.model.Capability;
import my.com.fotia.osdec.training.model.Training;

@Service
public class CapabilityServiceImpl implements CapabilityService{
	
	@Autowired
	CapabilityDao capabilityDao;

	@Override
	public Capability findByName(String name) {
		return capabilityDao.findByName(name);
	}

	@Override
	public Capability findById(String id) {
		return capabilityDao.findById(id);
	}

	@Override
	public List<Capability> capabilityList() {
		return capabilityDao.findAll();
	}

	@Override
	public Capability save(Capability capability) {
		return capabilityDao.save(capability);
	}

	@Override
	public void deleteById(String id) {
		capabilityDao.deleteById(id);
		
	}

	@Override
	public List<Capability> findByNameContainingIgnoreCaseOrKepakaran_NameContainingIgnoreCaseOrDurationContainingIgnoreCaseOrStatusContainingIgnoreCase(
			String name, String kepakaran, String duration, String status, Pageable pageable) {
		return capabilityDao.findByNameContainingIgnoreCaseOrKepakaran_NameContainingIgnoreCaseOrDurationContainingIgnoreCaseOrStatusContainingIgnoreCase(name, kepakaran, duration, status, pageable);
	}

	@Override
	public long countByNameContainingIgnoreCaseOrKepakaran_NameContainingIgnoreCaseOrDurationContainingIgnoreCaseOrStatusContainingIgnoreCase(
			String name, String kepakaran, String duration, String status) {
		return capabilityDao.countByNameContainingIgnoreCaseOrKepakaran_NameContainingIgnoreCaseOrDurationContainingIgnoreCaseOrStatusContainingIgnoreCase(name, kepakaran, duration, status);
	}

	@Override
	public List<Capability> findAll() {
		return capabilityDao.findAll();
	}

	@Override
	public List<Capability> findByStatusAndNameContainingIgnoreCaseOrStatusAndKepakaran_NameContainingIgnoreCase(
			String status, String search, String status2, String search2, Pageable pageable) {
		return capabilityDao.findByStatusAndNameContainingIgnoreCaseOrStatusAndKepakaran_NameContainingIgnoreCase(status, search, status2, search2, pageable);
	}

	@Override
	public Long countByStatusAndNameContainingIgnoreCaseOrStatusAndKepakaran_NameContainingIgnoreCase(String status,
			String search, String status2, String search2) {
		return capabilityDao.countByStatusAndNameContainingIgnoreCaseOrStatusAndKepakaran_NameContainingIgnoreCase(status, search, status2, search2);
	}
}
