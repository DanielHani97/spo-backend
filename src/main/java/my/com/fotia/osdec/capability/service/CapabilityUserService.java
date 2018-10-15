package my.com.fotia.osdec.capability.service;

import java.util.Collection;
import java.util.List;
import org.springframework.data.domain.Pageable;

import my.com.fotia.osdec.capability.model.CapabilityUser;

public interface CapabilityUserService {
	
	CapabilityUser findById(String id);
	List<CapabilityUser> CapabilityUserList();
	
	CapabilityUser save(CapabilityUser capabilityUser);
	void deleteById(String id);
	
	List<CapabilityUser> findByCapability_id(String id);
	
	List<CapabilityUser> findByUser_Id(String id);
	
	List<CapabilityUser> findByUser_NameContainingIgnoreCaseOrUser_Agency_NameContainingIgnoreCaseOrCapability_NameContainingIgnoreCase
	(String pemohon, String agency, String name, Pageable pageable);
	
	long countByUser_NameContainingIgnoreCaseOrUser_Agency_NameContainingIgnoreCaseOrCapability_NameContainingIgnoreCase
	(String pemohon, String agency, String name);
	
	List<CapabilityUser> findByUser_IdAndCapability_NameContainingIgnoreCaseOrUser_IdAndCapability_Kepakaran_NameContainingIgnoreCaseOrUser_IdAndCapability_DurationContainingIgnoreCase
	(String id, String name, String id2, String kepakaran, String id3, String duration, Pageable pageable);
	
	long countByUser_IdAndCapability_NameContainingIgnoreCaseOrUser_IdAndCapability_Kepakaran_NameContainingIgnoreCaseOrUser_IdAndCapability_DurationContainingIgnoreCase
	(String id, String name, String id2, String kepakaran, String id3, String duration);
	boolean existsByUser_IdAndCapability_IdAndStatus(String userId, String capId, String status);
	List<CapabilityUser> findByStatusInAndCapability_NameContainingIgnoreCaseOrStatusInAndCreatedBy_NameContainingIgnoreCaseOrStatusInAndEvaluatedBy_NameContainingIgnoreCaseOrStatusInAndApprovedBy_NameContainingIgnoreCase(
			Collection<String> collection, String search, Collection<String> collection2, String search2,
			Collection<String> collection3, String search3, Collection<String> collection4, String search4,
			Pageable pageable);
	Long countByStatusInAndCapability_NameContainingIgnoreCaseOrStatusInAndCreatedBy_NameContainingIgnoreCaseOrStatusInAndEvaluatedBy_NameContainingIgnoreCaseOrStatusInAndApprovedBy_NameContainingIgnoreCase(
			Collection<String> collection, String search, Collection<String> collection2, String search2,
			Collection<String> collection3, String search3, Collection<String> collection4, String search4);
	
	boolean existsByCapability_IdAndUser_Id(String apabId, String userId);
	List<CapabilityUser> findByCapability_idAndStatus(String id, String string);
	
}
