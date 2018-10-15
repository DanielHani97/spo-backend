package my.com.fotia.osdec.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import my.com.fotia.osdec.capability.model.CapabilityUser;
import my.com.fotia.osdec.capability.service.CapabilityUserService;
import my.com.fotia.osdec.trainingTx.model.TrainingTx;
import my.com.fotia.osdec.user.model.User;
import my.com.fotia.osdec.user.service.UserService;
import my.com.fotia.osdec.utilities.CustomErrorType;
import my.com.fotia.osdec.utilities.UUIDGenerator;

@RestController
@RequestMapping("/api")
public class CapabilityUserController {
	
	public static final Logger logger = LoggerFactory.getLogger(CapabilityUserController.class);
	
	@Autowired
	CapabilityUserService capabilityUserService;
	@Autowired
	UserService userService;
	
	@RequestMapping (value = "/capabilityUser/getall/{id}", method = RequestMethod.POST, produces = "application/json")
	public List <CapabilityUser> capabilityList(@PathVariable("id") String id) throws Exception{
		
		List <CapabilityUser> ls = new ArrayList <CapabilityUser>();
		ls = capabilityUserService.findByUser_Id(id);
		
		return ls;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/capabilityUser/all", method = RequestMethod.POST)
    public  String getCapabilityUserList(
    		@RequestParam ("datatable[pagination][page]") int page,
    		@RequestParam ("datatable[pagination][perpage]") int perpage,
    		@RequestParam ("datatable[sort][sort]") String sort,
    		@RequestParam ("datatable[sort][field]") String sortField,
    		@RequestParam (value= "datatable[search]", required=false) String search
    		) throws JsonProcessingException {
		
		String result = "";
		int pages = 0;
		
		List<CapabilityUser> ls = new ArrayList<CapabilityUser>();
		
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
			
			if(sortField.equals("pemohon")) {
				sortField = "User_Name";
			}else if(sortField.equals("agency")) {
				sortField = "User_Agency_Name";
			}else if(sortField.equals("name")) {
				sortField = "Capability_Name";
			}
			
			
			Sort sortObj = new Sort(new Sort.Order(sortDirection, sortField));
			Pageable pageable = new PageRequest(page-1, perpage, sortObj);
			
			ls = capabilityUserService.findByUser_NameContainingIgnoreCaseOrUser_Agency_NameContainingIgnoreCaseOrCapability_NameContainingIgnoreCase(search, search, search, pageable);
		
		
			ObjectMapper mapper = new ObjectMapper();
			String jsonRes = mapper.writeValueAsString(ls);
			
			Long totalL = capabilityUserService.countByUser_NameContainingIgnoreCaseOrUser_Agency_NameContainingIgnoreCaseOrCapability_NameContainingIgnoreCase(search, search, search);

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
	
	// AUDIT HERE
	@RequestMapping(value = "/capabilityUser/audit", method = RequestMethod.POST)
    public  String capUserAudit(
    		@RequestParam ("datatable[pagination][page]") int page,
    		@RequestParam ("datatable[pagination][perpage]") int perpage,
    		@RequestParam ("datatable[sort][sort]") String sort,
    		@RequestParam ("datatable[sort][field]") String sortField,
    		@RequestParam (value= "datatable[search]", required=false) String search
    		) throws JsonProcessingException {
		
		String result = "";
		int pages = 0;
		
		List<CapabilityUser> ls = new ArrayList<CapabilityUser>();
		
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
			}
			Collection<String> collection = new ArrayList<String>(Arrays.asList(new String[] { "3", "4", "5" }));
			Sort sortObj = new Sort(new Sort.Order(sortDirection, sortField));
			Pageable pageable = new PageRequest(page-1, perpage, sortObj);
			
			ls = capabilityUserService.findByStatusInAndCapability_NameContainingIgnoreCaseOrStatusInAndCreatedBy_NameContainingIgnoreCaseOrStatusInAndEvaluatedBy_NameContainingIgnoreCaseOrStatusInAndApprovedBy_NameContainingIgnoreCase
					(collection, search, collection, search, collection, search, collection, search, pageable);
		
		
			ObjectMapper mapper = new ObjectMapper();
			String jsonRes = mapper.writeValueAsString(ls);
			
			Long totalL = capabilityUserService.countByStatusInAndCapability_NameContainingIgnoreCaseOrStatusInAndCreatedBy_NameContainingIgnoreCaseOrStatusInAndEvaluatedBy_NameContainingIgnoreCaseOrStatusInAndApprovedBy_NameContainingIgnoreCase
					(collection, search, collection, search, collection, search, collection, search);

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
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/capabilityUser/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getCapabilityUser(@PathVariable("id") String id) {
        logger.info("Fetching capabilityUser with id {}", id);
        CapabilityUser capabilityUser = capabilityUserService.findById(id);
        if (capabilityUser == null) {
            logger.error("capabilityUser with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("capabilityUser with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<CapabilityUser>(capabilityUser, HttpStatus.OK);
    }
	
	
	@RequestMapping(value = "/capabilityUser/list/{id}", method = RequestMethod.GET, produces = "application/json")
    public String capabilityUser(@PathVariable("id") String id) throws Exception {
		
		List<CapabilityUser> ls = new ArrayList<CapabilityUser>();
		ls = capabilityUserService.findByCapability_id(id);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonRes = mapper.writeValueAsString(ls);
		
        return jsonRes;
    }
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/capability/user/list/{id}", method = RequestMethod.POST)
    public  String getTrainingList( @PathVariable("id") String id,
    		@RequestParam ("datatable[pagination][page]") int page,
    		@RequestParam ("datatable[pagination][perpage]") int perpage,
    		@RequestParam ("datatable[sort][sort]") String sort,
    		@RequestParam ("datatable[sort][field]") String sortField,
    		@RequestParam (value= "datatable[search]", required=false) String search
    		) throws JsonProcessingException {
	
	String result = "";
	int pages = 0;

	User user = userService.findById(id);
	
	List<CapabilityUser> ls = new ArrayList<CapabilityUser>();

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
		
		ls = capabilityUserService.findByUser_IdAndCapability_NameContainingIgnoreCaseOrUser_IdAndCapability_Kepakaran_NameContainingIgnoreCaseOrUser_IdAndCapability_DurationContainingIgnoreCase(id, search, id, search, id, search, pageable);
				
		ObjectMapper mapper = new ObjectMapper();
		String jsonRes = mapper.writeValueAsString(ls);
		
		Long totalL = capabilityUserService.countByUser_IdAndCapability_NameContainingIgnoreCaseOrUser_IdAndCapability_Kepakaran_NameContainingIgnoreCaseOrUser_IdAndCapability_DurationContainingIgnoreCase(id, search, id, search, id, search);
				
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
	@RequestMapping(value = "/capabilityUser/create", method = RequestMethod.POST)
    public ResponseEntity<?> createCapabilityUser(@RequestBody CapabilityUser capabilityUser, UriComponentsBuilder ucBuilder) {
        logger.info("Creating capabilityUser : {}", capabilityUser);
 
        
        capabilityUser.setId(UUIDGenerator.getInstance().getId());
        capabilityUser.setCreatedDate(new Date());
        capabilityUserService.save(capabilityUser);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/capabilityUser/{id}").buildAndExpand(capabilityUser.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/capabilityUser/edit/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCapabilityUser(@PathVariable("id") String id, @RequestBody CapabilityUser capabilityUser) {
        logger.info("Updating capabilityUser with id {}", id);
        
        CapabilityUser currentCapabilityUser = new CapabilityUser();
        
        
        if(id !=null && !"".equals(id)) {
        	currentCapabilityUser = capabilityUserService.findById(id);
            
            if(currentCapabilityUser != null) {
            	String status = capabilityUser.getStatus();
            	
            	if("2".equals(status)) {
            		
            		currentCapabilityUser.setCoach_remarks(capabilityUser.getCoach_remarks());
            		currentCapabilityUser.setStatus(capabilityUser.getStatus());
            		currentCapabilityUser.setEvaluatedBy(capabilityUser.getEvaluatedBy());
            		currentCapabilityUser.setEvaluatedDate(new Date());
            		capabilityUserService.save(currentCapabilityUser);
            		
            	}else if ("3".equals(status)) {
            		
            		currentCapabilityUser.setAdmin_remarks(capabilityUser.getAdmin_remarks());
            		currentCapabilityUser.setStatus(capabilityUser.getStatus());
            		currentCapabilityUser.setApprovedBy(capabilityUser.getApprovedBy());
            		currentCapabilityUser.setApprovedDate(new Date());
            		capabilityUserService.save(currentCapabilityUser);
            		
            	}else if ("4".equals(status)) {
            		
            		currentCapabilityUser.setAdmin_remarks(capabilityUser.getAdmin_remarks());
            		currentCapabilityUser.setStatus(capabilityUser.getStatus());
            		currentCapabilityUser.setApprovedBy(capabilityUser.getApprovedBy());
            		currentCapabilityUser.setApprovedDate(new Date());
            		capabilityUserService.save(currentCapabilityUser);
            		
            	}else if ("5".equals(status)) {
            		
            		currentCapabilityUser.setStatus(capabilityUser.getStatus());
            		currentCapabilityUser.setApprovedBy(capabilityUser.getApprovedBy());
            		currentCapabilityUser.setApprovedDate(new Date());
            		capabilityUserService.save(currentCapabilityUser);
            		
            	}
            	
            	
            	
            }else {
            	 logger.error("Unable to update. capabilityUser with id {} not found.", id);
                 return new ResponseEntity(new CustomErrorType("Unable to upate. capabilityUser with id " + id + " not found."),
                         HttpStatus.NOT_FOUND);
            }
            
        }
        capabilityUserService.save(currentCapabilityUser);
        return new ResponseEntity<CapabilityUser>(currentCapabilityUser, HttpStatus.OK);
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/capUser/isExist", method = RequestMethod.POST)
    public ResponseEntity<?> capUserIsExist(@RequestBody CapabilityUser capabilityUser, UriComponentsBuilder ucBuilder) {
		
		String capId = capabilityUser.getCapability().getId();
	    String userId = capabilityUser.getUser().getId();
	    String status = "1";
	    
	    boolean isExist = capabilityUserService.existsByUser_IdAndCapability_IdAndStatus(userId, capId, status);

	    if (isExist) {
	        logger.error("Unable to create Capability. A Capability for User with name {} already exist", capabilityUser.getUser().getName());
	        return new ResponseEntity(new CustomErrorType("Unable to create Capability. A Capability for User with name " + 
	        		capabilityUser.getUser().getName() + " already exist."),HttpStatus.CONFLICT);
	    }
        
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(capabilityUser.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/capabilityUser/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCapabilityUser(@PathVariable("id") String id) {
        logger.info("Fetching & Deleting capabilityUser with id {}", id);
 
        CapabilityUser capabilityUser = capabilityUserService.findById(id);
        if (capabilityUser == null) {
            logger.error("Unable to delete. capabilityUser with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. capabilityUser with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        capabilityUserService.deleteById(id);
        return new ResponseEntity<CapabilityUser>(HttpStatus.NO_CONTENT);
    }
}
