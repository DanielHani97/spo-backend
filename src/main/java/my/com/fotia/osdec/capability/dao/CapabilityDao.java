package my.com.fotia.osdec.capability.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import my.com.fotia.osdec.capability.model.Capability;
import my.com.fotia.osdec.training.model.Training;

@Transactional
public interface CapabilityDao extends JpaRepository<Capability, Long>{
	
	Capability findByName(String name);
	Capability findById(String id);
	List<Capability> findAll();
	
	@Modifying
	@Query("DELETE from Capability where id = :id")
	void deleteById(@Param("id") String id);
	
	List<Capability> findByNameContainingIgnoreCaseOrKepakaran_NameContainingIgnoreCaseOrDurationContainingIgnoreCaseOrStatusContainingIgnoreCase
	(String name, String kepakaran, String duration, String status, Pageable pageable);
	
	long countByNameContainingIgnoreCaseOrKepakaran_NameContainingIgnoreCaseOrDurationContainingIgnoreCaseOrStatusContainingIgnoreCase
	(String name, String kepakaran, String duration, String status);
	List<Capability> findByStatusAndNameContainingIgnoreCaseOrStatusAndKepakaran_NameContainingIgnoreCase(String status,
			String search, String status2, String search2, Pageable pageable);
	Long countByStatusAndNameContainingIgnoreCaseOrStatusAndKepakaran_NameContainingIgnoreCase(String status,
			String search, String status2, String search2);
}
