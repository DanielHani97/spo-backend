package my.com.fotia.osdec.references.service;

import java.util.List;

import my.com.fotia.osdec.references.model.City;

public interface CityService {

	List<City> findAll();
	City findById(String id);
	List<City> findByState_Id(String stateId);
}
