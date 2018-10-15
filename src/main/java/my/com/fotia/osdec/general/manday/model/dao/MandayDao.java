package my.com.fotia.osdec.general.manday.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import my.com.fotia.osdec.general.manday.model.Manday;

@Transactional
public interface MandayDao extends CrudRepository<Manday, Long> {
	
	Manday findById(String id);
	List<Manday>findAll();
	
	@Modifying
	@Query("DELETE from Manday where id =:id")
	void deleteById(@Param("id") String id);

}
