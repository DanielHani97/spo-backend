package my.com.fotia.osdec.technology.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import my.com.fotia.osdec.technology.model.Technology;

@Transactional
public interface TechnologyDao extends JpaRepository<Technology, Long>{
	

	Technology findByName(String name);
	Technology findById(String id);
	List<Technology> findAll();
	
	
	@Modifying
	@Query("select f from Technology f where f.status = ?1")
	List<Technology> findAllTeknologi(String status);

	
	@Modifying
	@Query("select f from Technology f where f.type = ?1 and f.status = ?2")
	List<Technology> findFrontend(String type, String status);
	
	@Modifying
	@Query("select f from Technology f where f.type = ?1 and f.status = ?2")
	List<Technology> findBackend(String type, String status);
	
	@Modifying
	@Query("select f from Technology f where f.type = ?1 and f.status = ?2")
	List<Technology> findDatabase(String type, String status);
	
	@Modifying
	@Query("DELETE from Technology where id = :id")
	void deleteById(@Param("id") String id);
	
	List<Technology> findByNameContainingIgnoreCaseOrTypeContainingIgnoreCaseOrLanguageContainingIgnoreCase
	(String name, String type, String language, Pageable pageable);
	
	long countByNameContainingIgnoreCaseOrTypeContainingIgnoreCaseOrLanguageContainingIgnoreCase
	(String name, String type, String language);
	
}
