package my.com.fotia.osdec.technology.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import my.com.fotia.osdec.technology.model.Technology;

public interface TechnologyService {
	
	Technology findByName(String name);
	Technology findById(String id);
	List<Technology> technologyList();
	List<Technology> findAllTeknologi(String Status);
	List<Technology> findFrontend(String type, String Status);
	List<Technology> findByNameContainingIgnoreCaseOrTypeContainingIgnoreCaseOrLanguageContainingIgnoreCase
	(String name, String type, String language, Pageable pageable);
	long countByNameContainingIgnoreCaseOrTypeContainingIgnoreCaseOrLanguageContainingIgnoreCase
	(String name, String type, String language);
	List<Technology> findBackend(String type, String Status);
	List<Technology> findDatabase(String type, String Status);
	void save(Technology technology);
	void deleteById(String id);

}
