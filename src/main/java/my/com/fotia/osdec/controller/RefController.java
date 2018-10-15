package my.com.fotia.osdec.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import my.com.fotia.osdec.general.agency.model.Agency;
import my.com.fotia.osdec.references.model.City;
import my.com.fotia.osdec.references.model.State;
import my.com.fotia.osdec.references.service.CityService;
import my.com.fotia.osdec.references.service.StateService;
import my.com.fotia.osdec.user.model.Authority;
import my.com.fotia.osdec.user.model.User;
import my.com.fotia.osdec.user.service.AuthorityService;
import my.com.fotia.osdec.user.service.UserService;
import my.com.fotia.osdec.utilities.CustomErrorType;

@RestController
@RequestMapping("/api/ref")
public class RefController {

	public static final Logger logger = LoggerFactory.getLogger(RefController.class);
	
	@Autowired
	StateService stateService;
	
	@Autowired
	CityService cityService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	AuthorityService authorityService;
	
	
	@RequestMapping(value = "/states", method = RequestMethod.GET)
	public ResponseEntity<List<State>> getAllStates() {
		List<State> list = stateService.findAll();
		if(list.isEmpty()){
            return new ResponseEntity<List<State>>(HttpStatus.NOT_FOUND);
        }
		return new ResponseEntity<List<State>>(list, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/city", method = RequestMethod.GET)
	public ResponseEntity<List<City>> getAllCity() {
		
		List<City> list = new ArrayList<City>();
		
		try {
			list = cityService.findAll();
			if(list.isEmpty()){
	            return new ResponseEntity<List<City>>(HttpStatus.NOT_FOUND);
	        }
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		return new ResponseEntity<List<City>>(list, HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/states/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getState(@PathVariable("id") String id) {
        logger.info("Fetching agency with id {}", id);
        State obj = stateService.findById(id);
        if (obj == null) {
            logger.error("State with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("State with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<State>(obj, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/city/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<City>> getAllCityByState(@PathVariable("id") String id) {
		
		List<City> list = new ArrayList<City>();
		
		try {
			list = cityService.findByState_Id(id);
			if(list.isEmpty()){
	            return new ResponseEntity<List<City>>(HttpStatus.NOT_FOUND);
	        }
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		return new ResponseEntity<List<City>>(list, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/role", method = RequestMethod.GET)
	public ResponseEntity<List<Authority>> getAllRole() {
		
		List<Authority> list = new ArrayList();
		
		try {
			list = authorityService.findAll();
			if(list.isEmpty()){
	            return new ResponseEntity<List<Authority>>(HttpStatus.NOT_FOUND);
	        }
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		
		return new ResponseEntity<List<Authority>>(list, HttpStatus.OK);
	}
	
}
