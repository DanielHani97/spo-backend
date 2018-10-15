package my.com.fotia.osdec.references.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import my.com.fotia.osdec.references.model.City;

public interface CityDao extends JpaRepository<City, Long>{

	List<City> findAll();
	City findById(String id);
	List<City> findByState_Id(String stateId);
}
