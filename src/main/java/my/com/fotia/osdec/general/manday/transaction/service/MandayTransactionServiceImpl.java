package my.com.fotia.osdec.general.manday.transaction.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.general.manday.transaction.dao.MandayTransactionDao;
import my.com.fotia.osdec.general.manday.transaction.model.MandayTransaction;

@Service
public class MandayTransactionServiceImpl implements MandayTransactionService{
	
	@Autowired
	MandayTransactionDao mandayTransactionDao;
	
	@Override
	public MandayTransaction findById(String id) {
		
		return mandayTransactionDao.findById(id);
	}

	@Override
	public List<MandayTransaction> mandayTransList() {
		
		return mandayTransactionDao.findAll();
	}

	@Override
	public List<MandayTransaction> findAll() {
		
		return mandayTransactionDao.findAll();
	}

		

	@Override
	public void deleteById(String id) {

		mandayTransactionDao.deleteById(id);
	}

	@Override
	public void save(MandayTransaction obj) {
		mandayTransactionDao.save(obj);
		
	}

	@Override
	public List<MandayTransaction> findByTypeContainingIgnoreCase(String string) {
		return mandayTransactionDao.findByTypeContainingIgnoreCase(string);
	}

	@Override
	public List<MandayTransaction> findByInstanceIdAndInstanceDate(String id, Date d2) {
		return mandayTransactionDao.findByInstanceIdAndInstanceDate(id, d2);
	}

	@Override
	public void deleteByInstanceId(String id) {
		mandayTransactionDao.deleteByInstanceId(id);
	}



}
