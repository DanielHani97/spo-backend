package my.com.fotia.osdec.general.schema.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.general.grade.model.Grade;
import my.com.fotia.osdec.general.schema.dao.SchemaDao;
import my.com.fotia.osdec.general.schema.model.Schema;


@Service
public class SchemaServiceImpl implements SchemaService {
	
	@Autowired
	SchemaDao schemaDao;

	public Schema findByName(String name) {
		return schemaDao.findByName(name);
	}

	public Schema findById(String id) {
		return schemaDao.findById(id);
	}

	public List<Schema> schemaList() {
		return schemaDao.findAll();
	}
	
	public List<Schema> schemaCnList(){
		return schemaDao.findAll();
	}

	@Override
	public void save(Schema schema) {
		schemaDao.save(schema);
	}

	@Override
	public void deleteById(String id) {
		schemaDao.deleteById(id);
	}
	
	@Override
	public List<Schema> findAll(){
		return schemaDao.findAll();

	}

	@Override
	public long countByNameContainingIgnoreCaseOrStatusContainingIgnoreCase(String name, String status) {
		return schemaDao.countByNameContainingIgnoreCaseOrStatusContainingIgnoreCase(name, status);
	}

	@Override
	public List<Schema> findByNameContainingIgnoreCaseOrStatusContainingIgnoreCase(String name, String status,
			Pageable pageable) {
		return schemaDao.findByNameContainingIgnoreCaseOrStatusContainingIgnoreCase(name, status, pageable);
	}

	

	
	

	
	
	
}