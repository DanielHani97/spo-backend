package my.com.fotia.osdec.references.service;

import java.util.List;

import my.com.fotia.osdec.references.model.State;

public interface StateService {

	List<State> findAll();
	State findById(String id);
	void save(State state);
}
