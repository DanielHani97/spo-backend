package my.com.fotia.osdec.general.keygen.service;

import java.util.List;
import org.springframework.data.repository.query.Param;

import my.com.fotia.osdec.general.keygen.model.Keygen;

public interface KeygenService {
	
	Keygen findById(String id);
	List<Keygen> findAll();
	Keygen findByInstanceId(String id);
	
	void save(Keygen keygen);
	void deleteById(@Param("id") String id);
	void deleteTen(String id);
}
