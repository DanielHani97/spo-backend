package my.com.fotia.osdec.capability.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import my.com.fotia.osdec.capability.model.Capability;
import my.com.fotia.osdec.training.model.Training;

public interface CapabilityService {
	
	Capability findByName(String name);
	Capability findById(String id);
	List<Capability> capabilityList();
	List<Capability> findAll();
	Capability save(Capability capability);
	void deleteById(String id);
	
	List<Capability> findByNameContainingIgnoreCaseOrKepakaran_NameContainingIgnoreCaseOrDurationContainingIgnoreCaseOrStatusContainingIgnoreCase
	(String name, String kepakaran, String duration, String status, Pageable pageable);
	
	long countByNameContainingIgnoreCaseOrKepakaran_NameContainingIgnoreCaseOrDurationContainingIgnoreCaseOrStatusContainingIgnoreCase
	(String name, String kepakaran, String duration, String status);
	List<Capability> findByStatusAndNameContainingIgnoreCaseOrStatusAndKepakaran_NameContainingIgnoreCase(String status,
			String search, String status2, String search2, Pageable pageable);
	Long countByStatusAndNameContainingIgnoreCaseOrStatusAndKepakaran_NameContainingIgnoreCase(String status,
			String search, String status2, String search2);
}
