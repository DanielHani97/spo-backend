package my.com.fotia.osdec.capability.service;

import java.util.List;


import my.com.fotia.osdec.capability.model.CapabilityActivities;

public interface CapabilityActivitiesService {

	CapabilityActivities findByName(String name);
	CapabilityActivities findById(String id);
	List<CapabilityActivities> findAll();
	List<CapabilityActivities> capabilityActivitiesList();
	void save(CapabilityActivities capabilityActivities);
	void deleteById(String id);
	void deleteByCapability_Id(String id);
	List<CapabilityActivities> findByCapability_IdAndAttendance(String id, String string);
	
	List<CapabilityActivities> findByCapability_Id(String id);
	
}
