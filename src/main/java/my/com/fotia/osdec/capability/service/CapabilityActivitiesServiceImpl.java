package my.com.fotia.osdec.capability.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.capability.dao.CapabilityActivitiesDao;
import my.com.fotia.osdec.capability.model.CapabilityActivities;

@Service
public class CapabilityActivitiesServiceImpl implements CapabilityActivitiesService{
	
	@Autowired
	CapabilityActivitiesDao capabilityActivitiesDao;
	
	public CapabilityActivities findByName(String name) {
		return capabilityActivitiesDao.findByName(name);
	}

	public CapabilityActivities findById(String id) {
		return capabilityActivitiesDao.findById(id);
	}

	public List<CapabilityActivities> capabilityActivitiesList() {
		return capabilityActivitiesDao.findAll();
	}

	@Override
	public void save(CapabilityActivities capabilityActivities) {
		capabilityActivitiesDao.save(capabilityActivities);
	}

	@Override
	public void deleteById(String id) {
		capabilityActivitiesDao.deleteById(id);
	}

	@Override
	public List<CapabilityActivities> findAll() {
		return capabilityActivitiesDao.findAll();
	}

	@Override
	public List<CapabilityActivities> findByCapability_IdAndAttendance(String id, String string) {
		
		return capabilityActivitiesDao.findByCapability_IdAndAttendance(id, string);
	}

	@Override
	public List<CapabilityActivities> findByCapability_Id(String id) {
		return capabilityActivitiesDao.findByCapability_Id(id);
	}

	@Override
	public void deleteByCapability_Id(String id) {
		capabilityActivitiesDao.deleteByCapability_Id(id);
		
	}

}
