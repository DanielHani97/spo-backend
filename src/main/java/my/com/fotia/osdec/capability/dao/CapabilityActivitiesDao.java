package my.com.fotia.osdec.capability.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import my.com.fotia.osdec.capability.model.CapabilityActivities;

@Transactional
public interface CapabilityActivitiesDao extends CrudRepository<CapabilityActivities, Long>{
	
	CapabilityActivities findByName(String name);
	CapabilityActivities findById(String id);
	List<CapabilityActivities> findAll();
	
	@Modifying
	@Query("DELETE from CapabilityActivities where id = :id")
	void deleteById(@Param("id") String id);
	
	@Modifying
	@Query("DELETE from CapabilityActivities where capability_id = :id")
	void deleteByCapability_Id(@Param("id") String id);
	
	List<CapabilityActivities> findByCapability_IdAndAttendance(String id, String string);
	List<CapabilityActivities> findByCapability_Id(String id);
}
