package my.com.fotia.osdec.references.service;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import my.com.fotia.osdec.references.model.Filestorage;

public interface FilestorageService {

	Filestorage findById(String id);
	void deleteById(String id);
	Filestorage findByInstanceid(String instanceid);
	void save(Filestorage filestorage);
	void deleteByInstanceId(String instanceid);
}
