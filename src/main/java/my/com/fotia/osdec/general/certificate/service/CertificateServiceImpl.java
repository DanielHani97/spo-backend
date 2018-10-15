package my.com.fotia.osdec.general.certificate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.general.certificate.dao.CertificateDao;
import my.com.fotia.osdec.general.certificate.model.Certificate;

@Service
public class CertificateServiceImpl implements CertificateService{
	
	@Autowired
	CertificateDao certificateDao;

	public Certificate findByName(String name) {
		return certificateDao.findByName(name);
	}

	public Certificate findById(String id) {
		return certificateDao.findById(id);
	}

	public List<Certificate> certificateList() {
		return certificateDao.findAll();
	}

	@Override
	public void save(Certificate certificate) {
		certificateDao.save(certificate);
	}

	@Override
	public void deleteById(String id) {
		certificateDao.deleteById(id);
	}

	@Override
	public List<Certificate> findByUser_Id(String userId) {
		return certificateDao.findByUser_Id(userId);
	}

}
