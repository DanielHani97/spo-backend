package my.com.fotia.osdec.general.keygen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.general.keygen.dao.KeygenDao;
import my.com.fotia.osdec.general.keygen.model.Keygen;

@Service
public class KeygenServiceImpl implements KeygenService{
	
	@Autowired
	KeygenDao keygenDao;
	
	@Async("processExecutor")
    @Override
    public void deleteTen(String id) {
        
        try {
            Thread.sleep(600000);
            deleteById(id);
        }
        catch (InterruptedException ie) {
        	
        }
    }
	
	@Override
	public Keygen findById(String id) {
		return keygenDao.findById(id);
	}

	@Override
	public List<Keygen> findAll() {
		return keygenDao.findAll();
	}

	@Override
	public Keygen findByInstanceId(String id) {
		return keygenDao.findByInstanceId(id);
	}

	@Override
	public void save(Keygen keygen) {
		keygenDao.save(keygen);
		
	}

	@Override
	public void deleteById(String id) {
		keygenDao.deleteById(id);
	}
}
