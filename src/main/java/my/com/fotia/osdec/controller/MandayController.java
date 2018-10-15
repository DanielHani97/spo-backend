package my.com.fotia.osdec.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import my.com.fotia.osdec.general.manday.model.Manday;
import my.com.fotia.osdec.general.manday.model.service.MandayService;
import my.com.fotia.osdec.utilities.CustomErrorType;
import my.com.fotia.osdec.utilities.UUIDGenerator;

@RestController
@RequestMapping("/api")
public class MandayController {
	
	public static final Logger logger = LoggerFactory.getLogger(MandayController.class);
	
	
	@Autowired
	MandayService mandayService;
	
	@RequestMapping(value = "/manday/all", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Manday>>mandayList(){
		
		List<Manday>list = mandayService.findAll();
		if(list.isEmpty()) {
			return new ResponseEntity<List<Manday>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Manday>>(list, HttpStatus.OK);
		
	
	}
	
	@RequestMapping(value = "/manday/create", method = RequestMethod.POST)
//	public void action(@RequestParam("param") String[] param)
	public ResponseEntity<?> createManday(@RequestBody List<Manday> ls, UriComponentsBuilder ucBuilder){
		logger.info("Creating Manday : {}", ls);
		
		
		
		Iterator<Manday> itr = ls.iterator();
		while(itr.hasNext()) {
			Manday obj = new Manday();
			
			obj = itr.next();
			obj.setId(UUIDGenerator.getInstance().getId());
			
			mandayService.save(obj);
				
			}				
		
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/manday/{id}").buildAndExpand("mandaycreate").toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/manday/edit/", method = RequestMethod.POST)
	public void updateManday(@RequestBody List<Manday> ls){
		logger.info("Updating Manday with id{}");
													
						
			Iterator<Manday> itr = ls.iterator();
			while(itr.hasNext()) {
				Manday obj = new Manday();
				
				obj = itr.next();
							
				mandayService.save(obj);
				
			}
			
		}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/mandayUsed/edit/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updatemandayUsed(@PathVariable("id") String id, @RequestBody Manday manday) {
        logger.info("Updating manday used with id {}", id);
 
        Manday currentManday = new Manday();
        
        if (id !=null && !"".equals(id)) {
        	currentManday = mandayService.findById(id);
        	
        	if (currentManday !=null) {
        		//currentAttendance.setImage(attendance.getImage());
        		currentManday.setMandayUsed(manday.getMandayUsed());
        		  
        		mandayService.save(currentManday);
        		
        	}else {
        		
        	
            logger.error("Unable to update. Manday with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to update. Attendance with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        	}
        }
        
        return new ResponseEntity<Manday>(currentManday, HttpStatus.OK);
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/manday/updateReserved/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateMandayReserved(@PathVariable("id") String id, @RequestBody Manday manday) {
        logger.info("Updating manday used with id {}", id);
 
        Manday currentManday = new Manday();
        
        if (id !=null && !"".equals(id)) {
        	currentManday = mandayService.findById(id);
        	
        	if (currentManday !=null) {
        		currentManday.setMandayReserved(manday.getMandayReserved());
        		
        		
        		mandayService.save(currentManday);
        		
        	}else {
        		
        	
            logger.error("Unable to update. Manday with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to update. Attendance with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        	}
        }
        
        return new ResponseEntity<Manday>(currentManday, HttpStatus.OK);
    }
		
		
		
	}

