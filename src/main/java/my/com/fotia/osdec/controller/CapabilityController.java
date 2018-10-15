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

import my.com.fotia.osdec.capability.model.Capability;
import my.com.fotia.osdec.capability.model.CapabilityActivities;
import my.com.fotia.osdec.capability.model.CapabilityCoach;
import my.com.fotia.osdec.capability.model.CapabilityUser;
import my.com.fotia.osdec.capability.service.CapabilityActivitiesService;
import my.com.fotia.osdec.capability.service.CapabilityCoachService;
import my.com.fotia.osdec.capability.service.CapabilityService;
import my.com.fotia.osdec.capability.service.CapabilityUserService;
import my.com.fotia.osdec.general.manday.transaction.service.MandayTransactionService;
import my.com.fotia.osdec.training.model.Training;
import my.com.fotia.osdec.utilities.CustomErrorType;
import my.com.fotia.osdec.utilities.UUIDGenerator;

@RestController
@RequestMapping("/api")
public class CapabilityController {
	
	public static final Logger logger = LoggerFactory.getLogger(CapabilityController.class);
	
	@Autowired
	CapabilityService capabilityService;
	
	@Autowired
	CapabilityCoachService capabilityCoachService;
	
	@Autowired
	CapabilityUserService capabilityUserService;
	
	@Autowired
	CapabilityActivitiesService capabilityActivitiesService;
	
	@Autowired
	MandayTransactionService mandayTransactionService;
	
	@RequestMapping(value = "/capability", method = RequestMethod.GET)
	public ResponseEntity<List<Capability>> capabilityList() {
		List<Capability> list = capabilityService.findAll();
		if(list.isEmpty()){
            return new ResponseEntity<List<Capability>>(HttpStatus.NOT_FOUND);
        }
		return new ResponseEntity<List<Capability>>(list, HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/capability/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getCapability(@PathVariable("id") String id) {
        logger.info("Fetching capability with id {}", id);
        Capability capability = capabilityService.findById(id);
        if (capability == null) {
            logger.error("capability with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("User with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Capability>(capability, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/capability", method = RequestMethod.POST, produces = "application/json")
    public String trainingList (
		@RequestParam ("datatable[pagination][page]") int page,
		@RequestParam ("datatable[pagination][perpage]") int perpage,
		@RequestParam ("datatable[sort][sort]") String sort,
		@RequestParam ("datatable[sort][field]") String sortField,
		@RequestParam (value= "datatable[search]", required=false) String search
		) throws Exception {
		
		String result = "";
		int pages = 0;
		
		List<Capability> ls = new ArrayList<Capability>();
				
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
			
			if(sortField.equals("kepakaran")) {
				sortField = "Kepakaran_Name";
			}
			
			Sort sortObj = new Sort(new Sort.Order(sortDirection, sortField));
			Pageable pageable = new PageRequest(page-1, perpage, sortObj);
			
			ls = capabilityService.findByNameContainingIgnoreCaseOrKepakaran_NameContainingIgnoreCaseOrDurationContainingIgnoreCaseOrStatusContainingIgnoreCase(search, search, search, search, pageable);
		
			ObjectMapper mapper = new ObjectMapper();
			String jsonRes = mapper.writeValueAsString(ls);
			
			Long totalL = capabilityService.countByNameContainingIgnoreCaseOrKepakaran_NameContainingIgnoreCaseOrDurationContainingIgnoreCaseOrStatusContainingIgnoreCase(search, search, search, search);
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
	
	// REPORT HERE
	@RequestMapping(value = "/capability/report", method = RequestMethod.POST, produces = "application/json")
    public String reportCapList (
		@RequestParam ("datatable[pagination][page]") int page,
		@RequestParam ("datatable[pagination][perpage]") int perpage,
		@RequestParam ("datatable[sort][sort]") String sort,
		@RequestParam ("datatable[sort][field]") String sortField,
		@RequestParam (value= "datatable[search]", required=false) String search
		) throws Exception {
		
		String result = "";
		int pages = 0;
		
		List<Capability> ls = new ArrayList<Capability>();
				
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
			
			if(sortField.equals("kepakaran")) {
				sortField = "Kepakaran_Name";
			}
			
			Sort sortObj = new Sort(new Sort.Order(sortDirection, sortField));
			Pageable pageable = new PageRequest(page-1, perpage, sortObj);
			String status = "1";
			
			ls = capabilityService.findByStatusAndNameContainingIgnoreCaseOrStatusAndKepakaran_NameContainingIgnoreCase
					(status, search, status, search, pageable);

			ObjectMapper mapper = new ObjectMapper();
			String jsonRes = mapper.writeValueAsString(ls);
			
			Long totalL = capabilityService.countByStatusAndNameContainingIgnoreCaseOrStatusAndKepakaran_NameContainingIgnoreCase
					(status, search, status, search);
					
			
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
	
	@RequestMapping(value = "/capability/create", method = RequestMethod.POST)
    public Capability createCapability(@RequestBody Capability capability, UriComponentsBuilder ucBuilder) {
        logger.info("Creating capability : {}", capability);

        Capability ch = new Capability(); 

        capability.setId(UUIDGenerator.getInstance().getId());
        
        capability.setCreated_date(new Date());
        
        
        ch = capabilityService.save(capability);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/capability/{id}").buildAndExpand(capability.getId()).toUri());
        return ch;
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/capability/edit/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCapability(@PathVariable("id") String id, @RequestBody Capability capability) {
        logger.info("Updating capability with id {}", id);
        
        Capability currentCapability = new Capability();
        
        
        if(id !=null && !"".equals(id)) {
        	currentCapability = capabilityService.findById(id);
            
            if(currentCapability != null) {
            	currentCapability.setName(capability.getName());
            	currentCapability.setKepakaran(capability.getKepakaran());
            	currentCapability.setLimitation(capability.getLimitation());
            	currentCapability.setStarting_date(capability.getStarting_date());
            	currentCapability.setEnding_date(capability.getEnding_date());
            	currentCapability.setDuration(capability.getDuration());
            	currentCapability.setRemarks(capability.getRemarks());
            	currentCapability.setStatus(capability.getStatus());
            	currentCapability.setLimitation_used(capability.getLimitation_used());
            	currentCapability.setModified_by(capability.getModified_by());
            	currentCapability.setModified_date(new Date());
            	
            	capabilityService.save(currentCapability);
            	
            }else {
            	 logger.error("Unable to update. capability with id {} not found.", id);
                 return new ResponseEntity(new CustomErrorType("Unable to upate. capability with id " + id + " not found."),
                         HttpStatus.NOT_FOUND);
            }
            
        }
        capabilityService.save(currentCapability);
        return new ResponseEntity<Capability>(currentCapability, HttpStatus.OK);
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/capability/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCapability(@PathVariable("id") String id) {
        logger.info("Fetching & Deleting capability with id {}", id);
 
        Capability capability = capabilityService.findById(id);
        List<CapabilityUser> capUser = capabilityUserService.findByCapability_id(id);
        List<CapabilityCoach> capCoach = capabilityCoachService.findByCapability_Id(id);
        List<CapabilityActivities> capAct = capabilityActivitiesService.findByCapability_Id(id);
        
        if (capability == null) {
            logger.error("Unable to delete. capability with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. capability with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }else if(capUser.isEmpty()) {
        	
        	if(!capCoach.isEmpty()) {
        		capabilityCoachService.deleteByCapability_Id(id);
        	}
        	if(!capAct.isEmpty()) {
        		capabilityActivitiesService.deleteByCapability_Id(id);
        	}
        	mandayTransactionService.deleteByInstanceId(id);
        	capabilityService.deleteById(id);
            return new ResponseEntity<Capability>(HttpStatus.NO_CONTENT);
        }else {
        	logger.error("Unable to delete. User already exist");
        	return new ResponseEntity(new CustomErrorType("Unable to delete. User already exist"),HttpStatus.NOT_FOUND);
        }
        
    }
	
	public String getUserRole(String capabId, String userId) {
		String roleResult = "ROLE_USER";
		
		boolean isCoach = capabilityCoachService.existsByCapability_IdAndCoach_Id(capabId, userId);
		if(isCoach) {
			roleResult = "ROLE_COACH";
		}
		
		return roleResult;
	}
	
	@RequestMapping(value = "/capability/role/{capabilityid}/{userid}", method = RequestMethod.GET)
    public ResponseEntity<?> getFeedback(@PathVariable("userid") String userid, @PathVariable("capabilityid") String capabid) {
        logger.info("Fetching capability with capabilityid {}", capabid);
        
        String role = getUserRole(capabid, userid);
        
        return new ResponseEntity<String>(role, HttpStatus.OK);
    }
}
