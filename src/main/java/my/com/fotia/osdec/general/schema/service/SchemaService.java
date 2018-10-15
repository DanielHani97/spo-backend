package my.com.fotia.osdec.general.schema.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import my.com.fotia.osdec.general.schema.model.Schema;

public interface SchemaService {


	Schema findByName(String name);
	Schema findById(String id);
	List<Schema> schemaList();
	List<Schema> findAll();
	List<Schema> schemaCnList();
	
	void save(Schema schema);
	void deleteById(String id);
	
	long countByNameContainingIgnoreCaseOrStatusContainingIgnoreCase
	(String name,  String status);
	
	List<Schema> findByNameContainingIgnoreCaseOrStatusContainingIgnoreCase
	(String name, String status, Pageable pageable);
}
