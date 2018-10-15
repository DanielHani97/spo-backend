package my.com.fotia.osdec.references.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import my.com.fotia.osdec.references.model.Filestorage;

@Transactional
public interface FilestorageDao extends JpaRepository<Filestorage, Long>{

	Filestorage findById(String id);
	
	@Modifying
	@Query("DELETE from Filestorage where id = :id")
	void deleteById(@Param("id") String id);
	
	Filestorage findByInstanceid(String instanceid);
	
	@Modifying
	@Query("DELETE from Filestorage where instanceid = :instanceid")
	void deleteByInstanceId(@Param("instanceid") String instanceid);
}
