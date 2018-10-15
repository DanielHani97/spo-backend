package my.com.fotia.osdec.controller;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import my.com.fotia.osdec.general.keygen.model.Keygen;
import my.com.fotia.osdec.general.keygen.service.KeygenService;
import my.com.fotia.osdec.utilities.CustomErrorType;
import my.com.fotia.osdec.utilities.UUIDGenerator;

@RestController
@RequestMapping("/api")
public class KeygenController {
	
	public static final Logger logger = LoggerFactory.getLogger(GradeController.class);
	
	@Autowired
	KeygenService keygenService;
	
	@RequestMapping(value = "/keygen/create", method = RequestMethod.POST)
    public ResponseEntity<?> createKeygen(@RequestBody Keygen keygen, UriComponentsBuilder ucBuilder) {
        logger.info("Creating Grade : {}", keygen);
 
        
        keygen.setId(UUIDGenerator.getInstance().getId());
        keygen.setCreatedDate(new Date());
        DateTime dateTime = new DateTime(keygen.getCreatedDate());
        dateTime = dateTime.plusMinutes(10);
        keygen.setExpiredDate(dateTime.toDate());
        
        keygenService.save(keygen);
        keygenService.deleteTen(keygen.getId());
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(keygen.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
	
	@RequestMapping(value = "/keygen/all", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Keygen>> gradeList() {
		List<Keygen> list = keygenService.findAll();
		if(list.isEmpty()){
            return new ResponseEntity<List<Keygen>>(HttpStatus.NOT_FOUND);
        }
		return new ResponseEntity<List<Keygen>>(list, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/keygen/instance/{id}", method = RequestMethod.GET)
	public boolean instanceExist(@PathVariable("id") String id) {
		Keygen keygen = keygenService.findByInstanceId(id);
		if( keygen == null) {
			return false;
		}else {
			return true;
		}
	}
	
	@RequestMapping(value = "/keygen/getInstance/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Keygen> keygenByInstance(@PathVariable("id") String id) {
		Keygen keygen = keygenService.findByInstanceId(id);
		if(keygen == null){
            return new ResponseEntity<Keygen>(HttpStatus.NOT_FOUND);
        }
		return new ResponseEntity<Keygen>(keygen, HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/keygen/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteKeygen(@PathVariable("id") String id) {
        logger.info("Fetching & Deleting Keygen with id {}", id);
 
        Keygen keygen = keygenService.findById(id);
        if (keygen == null) {
            logger.error("Unable to delete. Keygen with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. Keygen with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        keygenService.deleteById(id);
        return new ResponseEntity<Keygen>(HttpStatus.NO_CONTENT);
    }

}
