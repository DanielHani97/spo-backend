package my.com.fotia.osdec.capability.dao;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import my.com.fotia.osdec.capability.model.CapabilityCoach;

@Transactional
public interface CapabilityCoachDao extends JpaRepository<CapabilityCoach, Long>{
	
	CapabilityCoach findById(String id);
	List<CapabilityCoach> findAll();
	
	@Modifying
	@Query("DELETE from CapabilityCoach where id = :id")
	void deleteById(@Param("id") String id);

	@Modifying
	@Query("DELETE from CapabilityCoach where capability_id = :id")
	void deleteByCapability_Id(@Param("id") String id);
	
	List<CapabilityCoach>findByCoach_IdAndCapability_NameContainingIgnoreCaseOrCoach_IdAndCapability_Kepakaran_NameContainingIgnoreCaseOrCoach_IdAndCapability_DurationContainingIgnoreCase
	(String id, String name, String id2, String kepakaran, String id3, String duration, Pageable pageable);
	
	long countByCoach_IdAndCapability_NameContainingIgnoreCaseOrCoach_IdAndCapability_Kepakaran_NameContainingIgnoreCaseOrCoach_IdAndCapability_DurationContainingIgnoreCase
	(String id, String name, String id2, String kepakaran, String id3, String duration);
	List<CapabilityCoach> findByCapability_Id(String id);
	
	boolean existsByCapability_IdAndCoach_Id(String apabId, String coachId);
}
