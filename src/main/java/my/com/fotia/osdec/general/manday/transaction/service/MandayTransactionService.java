package my.com.fotia.osdec.general.manday.transaction.service;

import java.util.Date;
import java.util.List;

import my.com.fotia.osdec.general.manday.transaction.model.MandayTransaction;

public interface MandayTransactionService {

	MandayTransaction findById(String id);
	List<MandayTransaction> mandayTransList();
	List<MandayTransaction> findAll();
	
	List<MandayTransaction> findByTypeContainingIgnoreCase(String string);
	
	void save(MandayTransaction obj);
	void deleteById(String id);
	void deleteByInstanceId(String id);
	List<MandayTransaction> findByInstanceIdAndInstanceDate(String id, Date d2);
	
	
	
}
