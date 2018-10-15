package my.com.fotia.osdec.general.agency.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import my.com.fotia.osdec.general.agency.model.Company;

public interface CompanyService {

	Company findByName(String name);
	Company findById(String id);
	List<Company> findAll();
	
	void save(Company agency);
	void deleteById(String id);
	List<Company> findByNameContainingIgnoreCaseOrPhoneNoContainingIgnoreCaseOrState_NameContainingIgnoreCaseOrCity_NameContainingIgnoreCase
	(String name, String phoneNo, String state, String city, Pageable pageable);

	long countByNameContainingIgnoreCaseOrPhoneNoContainingIgnoreCaseOrState_NameContainingIgnoreCaseOrCity_NameContainingIgnoreCase
	(String name, String phoneNo, String state, String city);
	
}
