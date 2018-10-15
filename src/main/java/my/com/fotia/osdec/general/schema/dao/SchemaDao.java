package my.com.fotia.osdec.general.schema.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import my.com.fotia.osdec.general.schema.model.Schema;

@Transactional
public interface SchemaDao extends CrudRepository<Schema, Long> {
	
	Schema findByName(String name);
	Schema findById(String id);
	List<Schema> findAll();
	
	List<Schema> findByNameContainingIgnoreCaseOrStatusContainingIgnoreCase(String name,  String status, Pageable pageable);

	
	@Modifying
	@Query("DELETE from Schema where id = :id")
	void deleteById(@Param("id") String id);
	
	long countByNameContainingIgnoreCaseOrStatusContainingIgnoreCase
	(String name, String status);
	

}
