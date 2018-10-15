package my.com.fotia.osdec.references.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.references.dao.FilestorageDao;
import my.com.fotia.osdec.references.model.Filestorage;

@Service
public class FileStorageServiceImpl implements FilestorageService{
	
	@Autowired
	FilestorageDao filestorageDao;

	@Override
	public Filestorage findById(String id) {
		return filestorageDao.findById(id);
	}

	@Override
	public void deleteById(String id) {
		filestorageDao.deleteById(id);;
	}

	@Override
	public void save(Filestorage filestorage) {
		filestorageDao.save(filestorage);
	}

	@Override
	public Filestorage findByInstanceid(String instanceid) {
		return filestorageDao.findByInstanceid(instanceid);
	}

	@Override
	public void deleteByInstanceId(String instanceid) {
		filestorageDao.deleteByInstanceId(instanceid);
	}

}
