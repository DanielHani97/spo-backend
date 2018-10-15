package my.com.fotia.osdec.general.certificate.service;

import java.util.List;

import my.com.fotia.osdec.general.certificate.model.Certificate;

public interface CertificateService {
	
	Certificate findByName(String name);
	Certificate findById(String id);
	List<Certificate> certificateList();
	void save(Certificate certificate);
	void deleteById(String id);
	List<Certificate> findByUser_Id(String userId);

}
