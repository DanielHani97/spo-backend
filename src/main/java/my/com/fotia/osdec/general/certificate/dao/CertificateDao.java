package my.com.fotia.osdec.general.certificate.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import my.com.fotia.osdec.general.certificate.model.Certificate;


@Transactional
public interface CertificateDao extends CrudRepository<Certificate, Long> {
	
	Certificate findByName(String name);
	Certificate findById(String id);
	List<Certificate> findAll();
	
	@Modifying
	@Query("DELETE from Certificate where id = :id")
	void deleteById(@Param("id") String id);
	
	List<Certificate> findByUser_Id(String userId);

}
