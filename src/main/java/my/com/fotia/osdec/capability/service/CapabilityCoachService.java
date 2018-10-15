package my.com.fotia.osdec.capability.service;

import java.util.List;
import org.springframework.data.domain.Pageable;

import my.com.fotia.osdec.capability.model.CapabilityCoach;

public interface CapabilityCoachService {
	
	CapabilityCoach findById(String id);
	List<CapabilityCoach> CapabilityCoachList();
	
	CapabilityCoach save(CapabilityCoach capabilityCoach);
	void deleteById(String id);
	void deleteByCapability_Id(String id);
	List<CapabilityCoach> findAll();
	
	List<CapabilityCoach>findByCoach_IdAndCapability_NameContainingIgnoreCaseOrCoach_IdAndCapability_Kepakaran_NameContainingIgnoreCaseOrCoach_IdAndCapability_DurationContainingIgnoreCase
	(String id, String name, String id2, String kepakaran, String id3, String duration, Pageable pageable);
	
	long countByCoach_IdAndCapability_NameContainingIgnoreCaseOrCoach_IdAndCapability_Kepakaran_NameContainingIgnoreCaseOrCoach_IdAndCapability_DurationContainingIgnoreCase
	(String id, String name, String id2, String kepakaran, String id3, String duration);
	List<CapabilityCoach> findByCapability_Id(String id);
	
	boolean existsByCapability_IdAndCoach_Id(String apabId, String coachId);
}
