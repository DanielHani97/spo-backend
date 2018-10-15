package my.com.fotia.osdec.general.agency.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import my.com.fotia.osdec.general.agency.model.Agency;

@Transactional
public interface AgencyDao extends JpaRepository<Agency, Long> {

	Agency findByName(String name);
	Agency findById(String id);
	List<Agency> findAll();
	
	List<Agency> findByNameContainingIgnoreCaseOrCodeContainingIgnoreCaseOrPhoneNoContainingIgnoreCaseOrState_NameContainingIgnoreCaseOrCity_NameContainingIgnoreCase
	(String name, String code, String phoneNo, String state, String city, Pageable pageable);
	
	@Modifying
	@Query("DELETE from Agency where id = :id")
	void deleteById(@Param("id") String id);
	
	long countByNameContainingIgnoreCaseOrCodeContainingIgnoreCaseOrPhoneNoContainingIgnoreCaseOrState_NameContainingIgnoreCaseOrCity_NameContainingIgnoreCase
	(String name, String code, String phoneNo, String state, String city);
}
