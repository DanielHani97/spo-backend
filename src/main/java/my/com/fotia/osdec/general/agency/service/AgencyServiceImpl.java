package my.com.fotia.osdec.general.agency.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.general.agency.dao.AgencyDao;
import my.com.fotia.osdec.general.agency.model.Agency;

@Service
public class AgencyServiceImpl implements AgencyService{
	
	@Autowired
	AgencyDao agencyDao;

	public Agency findByName(String name) {
		return agencyDao.findByName(name);
	}

	public Agency findById(String id) {
		return agencyDao.findById(id);
	}

	@Override
	public void save(Agency agency) {
		agencyDao.save(agency);
	}

	@Override
	public void deleteById(String id) {
		agencyDao.deleteById(id);
	}

	@Override
	public List<Agency> agencyList() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Agency> findAll(){
		return agencyDao.findAll();
	}

	@Override
	public long countByNameContainingIgnoreCaseOrCodeContainingIgnoreCaseOrPhoneNoContainingIgnoreCaseOrState_NameContainingIgnoreCaseOrCity_NameContainingIgnoreCase(
			String name, String code, String phoneNo, String state, String city) {
		return agencyDao.countByNameContainingIgnoreCaseOrCodeContainingIgnoreCaseOrPhoneNoContainingIgnoreCaseOrState_NameContainingIgnoreCaseOrCity_NameContainingIgnoreCase(name, code, phoneNo, state, city);
	}

	@Override
	public List<Agency> findByNameContainingIgnoreCaseOrCodeContainingIgnoreCaseOrPhoneNoContainingIgnoreCaseOrState_NameContainingIgnoreCaseOrCity_NameContainingIgnoreCase(
			String name, String code, String phoneNo, String state, String city, Pageable pageable) {
		return agencyDao.findByNameContainingIgnoreCaseOrCodeContainingIgnoreCaseOrPhoneNoContainingIgnoreCaseOrState_NameContainingIgnoreCaseOrCity_NameContainingIgnoreCase(name, code, phoneNo, state, city, pageable);
	}

}
