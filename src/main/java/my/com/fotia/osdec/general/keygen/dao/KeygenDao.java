package my.com.fotia.osdec.general.keygen.dao;


import java.util.List;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import my.com.fotia.osdec.general.keygen.model.Keygen;

@Transactional
public interface KeygenDao extends CrudRepository<Keygen, Long>{

	Keygen findById(String id);
	List<Keygen> findAll();
	Keygen findByInstanceId(String id);
	
	@Modifying
	@Query("DELETE from Keygen where id = :id")
	void deleteById(@Param("id") String id);
	
}
