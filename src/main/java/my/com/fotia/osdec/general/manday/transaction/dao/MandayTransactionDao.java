package my.com.fotia.osdec.general.manday.transaction.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import my.com.fotia.osdec.general.manday.transaction.model.MandayTransaction;

@Transactional
public interface MandayTransactionDao extends CrudRepository<MandayTransaction, Long> {
	
	MandayTransaction findById(String id);
	List<MandayTransaction>findAll();
	List<MandayTransaction> findByTypeContainingIgnoreCase(String string);

	
	@Modifying
	@Query("DELETE from MandayTransaction where id =:id")
	void deleteById(@Param("id") String id);
	
	@Modifying
	@Query("DELETE from MandayTransaction where instanceId =:id")
	void deleteByInstanceId(@Param("id") String id);
	
	List<MandayTransaction> findByInstanceIdAndInstanceDate(String id, Date d2);
//	List<MandayTransaction> findBy_type(String string);

}
