package my.com.fotia.osdec.general.agency.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import my.com.fotia.osdec.general.agency.model.Company;

@Transactional
public interface CompanyDao extends JpaRepository<Company, Long> {

	Company findByName(String name);
	Company findById(String id);
	List<Company> findAll();
	
	List<Company> findByNameContainingIgnoreCaseOrPhoneNoContainingIgnoreCaseOrState_NameContainingIgnoreCaseOrCity_NameContainingIgnoreCase
	(String name, String phoneNo, String state, String city, Pageable pageable);
	
	@Modifying
	@Query("DELETE from Company where id = :id")
	void deleteById(@Param("id") String id);
	
	long countByNameContainingIgnoreCaseOrPhoneNoContainingIgnoreCaseOrState_NameContainingIgnoreCaseOrCity_NameContainingIgnoreCase
	(String name, String phoneNo, String state, String city);
}
