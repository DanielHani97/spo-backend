package my.com.fotia.osdec.references.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.com.fotia.osdec.references.dao.StateDao;
import my.com.fotia.osdec.references.model.State;

@Service
public class StateServiceImpl implements StateService{
	
	@Autowired
	StateDao stateDao;

	@Override
	public List<State> findAll() {
		return stateDao.findAll();
	}

	@Override
	public State findById(String id) {
		return stateDao.findById(id);
	}

	@Override
	public void save(State state) {
		stateDao.save(state);
	}

}
