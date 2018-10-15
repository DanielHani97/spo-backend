package my.com.fotia.osdec.general.agency.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import my.com.fotia.osdec.general.agency.model.Agency;

public interface AgencyService {

	Agency findByName(String name);
	Agency findById(String id);
	List<Agency> agencyList();
	List<Agency> findAll();
	
	void save(Agency agency);
	void deleteById(String id);
	long countByNameContainingIgnoreCaseOrCodeContainingIgnoreCaseOrPhoneNoContainingIgnoreCaseOrState_NameContainingIgnoreCaseOrCity_NameContainingIgnoreCase
	(String name, String code, String phoneNo, String state, String city);
	List<Agency> findByNameContainingIgnoreCaseOrCodeContainingIgnoreCaseOrPhoneNoContainingIgnoreCaseOrState_NameContainingIgnoreCaseOrCity_NameContainingIgnoreCase
	(String name, String code, String phoneNo, String state, String city, Pageable pageable);
	
}
