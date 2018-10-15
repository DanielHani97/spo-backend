package my.com.fotia.osdec.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import my.com.fotia.osdec.capability.model.CapabilityCoach;
import my.com.fotia.osdec.capability.service.CapabilityCoachService;
import my.com.fotia.osdec.coaching.coach.model.CoachingCoach;
import my.com.fotia.osdec.trainingCoach.model.TrainingCoach;
import my.com.fotia.osdec.user.model.User;
import my.com.fotia.osdec.user.service.UserService;
import my.com.fotia.osdec.utilities.CustomErrorType;
import my.com.fotia.osdec.utilities.UUIDGenerator;

@RestController
@RequestMapping("/api")
public class CapabilityCoachController {
	
	public static final Logger logger = LoggerFactory.getLogger(CapabilityCoachController.class);
	
	@Autowired
	CapabilityCoachService capabilityCoachService;
	
	@Autowired
	UserService userService;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/capabilityCoach/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<CapabilityCoach>> getCapabilityCoach(@PathVariable("id") String id) {
        logger.info("Fetching capabilityCoach with id {}", id);
        List<CapabilityCoach> ls = new ArrayList<CapabilityCoach>();
        ls = capabilityCoachService.findByCapability_Id(id);
        if (ls.isEmpty()) {
            logger.error("capabilityCoach with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("capabilityCoach with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<CapabilityCoach>>(ls, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/capability/coach/list/{id}", method = RequestMethod.POST, produces = "application/json")
    public String capabilityList (@PathVariable("id") String id,
		@RequestParam ("datatable[pagination][page]") int page,
		@RequestParam ("datatable[pagination][perpage]") int perpage,
		@RequestParam ("datatable[sort][sort]") String sort,
		@RequestParam ("datatable[sort][field]") String sortField,
		@RequestParam (value= "datatable[search]", required=false) String search
		) throws Exception {
	
		String result = "";
		User coach = userService.findById(id);
		int pages = 0;
					
		List<CapabilityCoach> ls = new ArrayList<CapabilityCoach>();
		
		try {
		
		//calculate page number and number of record perpage
		int limit = perpage;
		int offset = page * perpage - perpage ;
		
		//set direction and sorting
		Direction sortDirection = Direction.ASC;
		if(sort.toUpperCase().equals("DESC")) {
			sortDirection = Direction.DESC;
		}
		
		if(search == null || search.equals(null)) {
			search = "";
		}
		
		if(sortField.equals("name")) {
			sortField = "Capability_Name";
		}else if(sortField.equals("kepakaran")) {
			sortField = "Capability_Kepakaran_Name";
		}else if(sortField.equals("duration")) {
			sortField = "Capability_Duration";
		}
		
		Sort sortObj = new Sort(new Sort.Order(sortDirection, sortField));
		Pageable pageable = new PageRequest(page-1, perpage, sortObj);
		
		ls = capabilityCoachService.findByCoach_IdAndCapability_NameContainingIgnoreCaseOrCoach_IdAndCapability_Kepakaran_NameContainingIgnoreCaseOrCoach_IdAndCapability_DurationContainingIgnoreCase(id, search, id, search, id, search, pageable);
	
		ObjectMapper mapper = new ObjectMapper();
		String jsonRes = mapper.writeValueAsString(ls);
		
		Long totalL = capabilityCoachService.countByCoach_IdAndCapability_NameContainingIgnoreCaseOrCoach_IdAndCapability_Kepakaran_NameContainingIgnoreCaseOrCoach_IdAndCapability_DurationContainingIgnoreCase(id, search, id, search, id, search);
				
		int total = 0;
		double perpageDb = 0.0;
	
		try {
			total = totalL.intValue();
			perpageDb = (double) perpage;
		}catch(Exception e) {}
		
		pages = (int) Math.ceil(total / perpageDb);
		
		result += "{";
		result += "\"meta\":{";
		result += "\"page\":"+page+",";
		result += "\"pages\":"+pages+",";
		result += "\"perpage\":"+perpage+",";
		result += "\"total\":"+total+",";
		result += "\"sort\":\""+sort+"\",";
		result += "\"field\":\"id\"";
		result += "},";
		result += "\"data\":"+jsonRes+"}";
		
	} catch (Exception e) {
		logger.error(e.getMessage());
	}
	
    return result;
}
	
	
	
	@RequestMapping(value = "/capabilityCoach", method = RequestMethod.GET, produces = "application/json")
    public String getCoach() throws Exception {
		
		List<CapabilityCoach> ls = new ArrayList<CapabilityCoach>();
		ls = capabilityCoachService.findAll();
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonRes = mapper.writeValueAsString(ls);
		
        return jsonRes;
    }
	@RequestMapping(value = "/capabilityCoach/create", method = RequestMethod.POST)
    public ResponseEntity<?> createCapabilityCoach(@RequestBody CapabilityCoach capabilityCoach, UriComponentsBuilder ucBuilder) {
        logger.info("Creating capabilityCoach : {}", capabilityCoach);
 
        
        capabilityCoach.setId(UUIDGenerator.getInstance().getId());
        
        capabilityCoachService.save(capabilityCoach);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/capabilityCoach/{id}").buildAndExpand(capabilityCoach.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/capabilityCoach/edit/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCapabilityCoach(@PathVariable("id") String id, @RequestBody CapabilityCoach capabilityCoach) {
        logger.info("Updating capabilityCoach with id {}", id);
        
        CapabilityCoach currentCapabilityCoach = new CapabilityCoach();
        
        
        if(id !=null && !"".equals(id)) {
        	currentCapabilityCoach = capabilityCoachService.findById(id);
            
            if(currentCapabilityCoach != null) {
            	
            }else {
            	 logger.error("Unable to update. capabilityCoach with id {} not found.", id);
                 return new ResponseEntity(new CustomErrorType("Unable to upate. capabilityCoach with id " + id + " not found."),
                         HttpStatus.NOT_FOUND);
            }
            
        }
        capabilityCoachService.save(currentCapabilityCoach);
        return new ResponseEntity<CapabilityCoach>(currentCapabilityCoach, HttpStatus.OK);
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/capabilityCoach/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCapabilityCoach(@PathVariable("id") String id) {
        logger.info("Fetching & Deleting capabilityCoach with id {}", id);
 
        CapabilityCoach capabilityCoach = capabilityCoachService.findById(id);
        if (capabilityCoach == null) {
            logger.error("Unable to delete. capabilityCoach with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. capabilityCoach with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        capabilityCoachService.deleteById(id);
        return new ResponseEntity<CapabilityCoach>(HttpStatus.NO_CONTENT);
    }
}
