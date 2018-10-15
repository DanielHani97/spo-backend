package my.com.fotia.osdec.references.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.references.dao.CityDao;
import my.com.fotia.osdec.references.model.City;

@Service
public class CityServiceImpl implements CityService{
	
	@Autowired
	CityDao cityDao;

	@Override
	public List<City> findAll() {
		return cityDao.findAll();
	}

	@Override
	public City findById(String id) {
		return cityDao.findById(id);
	}

	@Override
	public List<City> findByState_Id(String stateId) {
		return cityDao.findByState_Id(stateId);
	}

}
