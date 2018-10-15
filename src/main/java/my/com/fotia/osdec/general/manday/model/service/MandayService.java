package my.com.fotia.osdec.general.manday.model.service;

import java.util.List;

import my.com.fotia.osdec.general.manday.model.Manday;

public interface MandayService {
	
	Manday findById(String id);
	List<Manday> mandayList();
	List<Manday> findAll();
	
	void save(Manday obj);
	void deleteById(String id);
}
