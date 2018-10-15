package my.com.fotia.osdec.general.manday.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.general.manday.model.Manday;
import my.com.fotia.osdec.general.manday.model.dao.MandayDao;

@Service
public class MandayServiceImpl implements MandayService {

	@Autowired
	MandayDao mandayDao;
	
	@Override
	public Manday findById(String id) {
		
		return mandayDao.findById(id);
	}

	@Override
	public List<Manday> mandayList() {
		
		return mandayDao.findAll();
	}

	@Override
	public List<Manday> findAll() {
		
		return mandayDao.findAll();
	}


	@Override
	public void deleteById(String id) {

		mandayDao.deleteById(id);
	}

	@Override
	public void save(Manday obj) {
		mandayDao.save(obj);
		
	}

}
