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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;


import my.com.fotia.osdec.general.infrastructure.model.Infrastructure;
import my.com.fotia.osdec.general.infrastructure.service.InfrastructureService;
import my.com.fotia.osdec.technology.model.Technology;
import my.com.fotia.osdec.user.model.User;
import my.com.fotia.osdec.user.service.UserService;
import my.com.fotia.osdec.utilities.CustomErrorType;
import my.com.fotia.osdec.utilities.UUIDGenerator;

@RestController
@RequestMapping("/api")
public class InfrastructureController {
	
	public static final Logger logger = LoggerFactory.getLogger(InfrastructureController.class);
	
	@Autowired
	InfrastructureService infrastructureService;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/infrastructure/{id}", method = RequestMethod.POST, produces = "application/json")
    public String infraListByUser(@PathVariable("id")String id,
    		@RequestParam ("datatable[pagination][page]") int page,
    		@RequestParam ("datatable[pagination][perpage]") int perpage,
    		@RequestParam ("datatable[sort][sort]") String sort,
    		@RequestParam ("datatable[sort][field]") String sortField,
    		@RequestParam (value= "datatable[search]", required=false) String search
    		) throws Exception {
		
		String result = "";
		int pages = 1;
		
		User user = userService.findById(id);
		
		List<Infrastructure> ls = infrastructureService.findByUser(user);
		
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
			
			Sort sortObj = new Sort(new Sort.Order(sortDirection, sortField));
			Pageable pageable = new PageRequest(page-1, perpage, sortObj);
			
			ls = infrastructureService.findByUser_IdAndAgency_CodeContainingIgnoreCaseOrUser_IdAndOsContainingIgnoreCaseOrUser_IdAndTypeContainingIgnoreCase(id, search, id, search, id, search, pageable);
					
			ObjectMapper mapper = new ObjectMapper();
			String jsonRes = mapper.writeValueAsString(ls);
			
			Long totalL = infrastructureService.countByUser_IdAndAgency_CodeContainingIgnoreCaseOrUser_IdAndOsContainingIgnoreCaseOrUser_IdAndTypeContainingIgnoreCase(id, search, id, search, id, search);
					
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
	
	@RequestMapping(value = "/infras", method = RequestMethod.POST, produces = "application/json")
    public String infraList(
    		@RequestParam ("datatable[pagination][page]") int page,
    		@RequestParam ("datatable[pagination][perpage]") int perpage,
    		@RequestParam ("datatable[sort][sort]") String sort,
    		@RequestParam ("datatable[sort][field]") String sortField,
    		@RequestParam (value= "datatable[search]", required=false) String search
    		) throws Exception {
		
		String result = "";
		int pages = 1;
		
		List<Infrastructure> ls = new ArrayList<Infrastructure>();
		
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
			
			Sort sortObj = new Sort(new Sort.Order(sortDirection, sortField));
			Pageable pageable = new PageRequest(page-1, perpage, sortObj);
			
			ls = infrastructureService.findByUser_NameContainingIgnoreCaseOrAgency_CodeContainingIgnoreCaseOrOsContainingIgnoreCaseOrTypeContainingIgnoreCase(search, search, search, search, pageable);
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonRes = mapper.writeValueAsString(ls);
			
			Long totalL = infrastructureService.countByUser_NameContainingIgnoreCaseOrAgency_CodeContainingIgnoreCaseOrOsContainingIgnoreCaseOrTypeContainingIgnoreCase(search, search, search, search);
					
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
	
	//	Audit Trail
	@RequestMapping(value = "/infraAudit", method = RequestMethod.POST, produces = "application/json")
    public String infraAudit(
    		@RequestParam ("datatable[pagination][page]") int page,
    		@RequestParam ("datatable[pagination][perpage]") int perpage,
    		@RequestParam ("datatable[sort][sort]") String sort,
    		@RequestParam ("datatable[sort][field]") String sortField,
    		@RequestParam (value= "datatable[search]", required=false) String search
    		) throws Exception {
		
		String result = "";
		int pages = 1;
		
		List<Infrastructure> ls = new ArrayList<Infrastructure>();
		
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
			
			if(sortField.equals("createdBy")) {
				sortField = "CreatedBy_Name";
			}else if(sortField.equals("modifiedBy")) {
				sortField = "ModifiedBy_Name";
			}
			
			Sort sortObj = new Sort(new Sort.Order(sortDirection, sortField));
			Pageable pageable = new PageRequest(page-1, perpage, sortObj);
			String statusnot = "1";
			ls = infrastructureService.findByStatusNotAndUser_NameContainingIgnoreCaseOrStatusNotAndCreatedBy_NameContainingIgnoreCaseOrStatusNotAndModifiedBy_NameContainingIgnoreCase
					(statusnot, search, statusnot, search, statusnot, search, pageable);
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonRes = mapper.writeValueAsString(ls);
			
			Long totalL = infrastructureService.countByStatusNotAndUser_NameContainingIgnoreCaseOrStatusNotAndCreatedBy_NameContainingIgnoreCaseOrStatusNotAndModifiedBy_NameContainingIgnoreCase
					(statusnot, search, statusnot, search, statusnot, search);
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
	@RequestMapping(value = "/infrastructure/edit/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getInfrastructure(@PathVariable("id") String id) {
        logger.info("Fetching infrastructure with id {}", id);
        Infrastructure infrastructure = infrastructureService.findById(id);
        if (infrastructure == null) {
            logger.error("technology with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("User with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Infrastructure>(infrastructure, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/infrastructure/create", method = RequestMethod.POST)
    public ResponseEntity<?> createInfrastructure(@RequestBody Infrastructure infrastructure, UriComponentsBuilder ucBuilder) {
        logger.info("Creating User : {}", infrastructure);
 
        /*if (userService.isUserExist(user)) {
            logger.error("Unable to create. A User with name {} already exist", user.getName());
            return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " + 
            user.getName() + " already exist."),HttpStatus.CONFLICT);
        }*/
        infrastructure.setId(UUIDGenerator.getInstance().getId());
        
        infrastructure.setCreatedBy(infrastructure.getCreatedBy());
        infrastructure.setCreatedDate(new Date());
        
        infrastructureService.save(infrastructure);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(infrastructure.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/infrastructure/edit/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateInfrastructure(@PathVariable("id") String id, @RequestBody Infrastructure infrastructure) {
        logger.info("Updating infrastructure with id {}", id);
        
        Infrastructure currentInfrastructure = new Infrastructure();
 
        if(id !=null && !"".equals(id)) {
        	currentInfrastructure = infrastructureService.findById(id);
            
            if(currentInfrastructure != null) {
            	currentInfrastructure.setStatus(infrastructure.getStatus());
            	currentInfrastructure.setAdminRemarks(infrastructure.getAdminRemarks());
            	currentInfrastructure.setModifiedBy(infrastructure.getModifiedBy());
            	currentInfrastructure.setModifiedDate(new Date());
            	infrastructureService.save(currentInfrastructure);
            }else {
            	 logger.error("Unable to update. infrastructure with id {} not found.", id);
                 return new ResponseEntity(new CustomErrorType("Unable to upate. infrastructure with id " + id + " not found."),
                         HttpStatus.NOT_FOUND);
            }
            
        }
        
        return new ResponseEntity<Infrastructure>(currentInfrastructure, HttpStatus.OK);
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/infrastructure/edit2/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateInfrastructure2(@PathVariable("id") String id, @RequestBody Infrastructure infrastructure) {
        logger.info("Updating infrastructure with id {}", id);
        
        Infrastructure currentInfrastructure = new Infrastructure();
 
        if(id !=null && !"".equals(id)) {
        	currentInfrastructure = infrastructureService.findById(id);
            
            if(currentInfrastructure != null) {
            	currentInfrastructure.setRemarks(infrastructure.getRemarks());
            	currentInfrastructure.setOs(infrastructure.getOs());
            	currentInfrastructure.setVcpu(infrastructure.getVcpu());
            	currentInfrastructure.setMemori(infrastructure.getMemori());
            	currentInfrastructure.setRootDisk(infrastructure.getRootDisk());
            	currentInfrastructure.setEphemeralDisk(infrastructure.getEphemeralDisk());
            	currentInfrastructure.setSwapDisk(infrastructure.getSwapDisk());
            	currentInfrastructure.setWebServer(infrastructure.getWebServer());
            	currentInfrastructure.setFramework(infrastructure.getFramework());
            	currentInfrastructure.setType(infrastructure.getType());
            	currentInfrastructure.setPersistentDisk(infrastructure.getPersistentDisk());
            	currentInfrastructure.setDatabase(infrastructure.getDatabase());
            	currentInfrastructure.setLanguage(infrastructure.getLanguage());
            	
            	
            	infrastructureService.save(currentInfrastructure);
            }else {
            	 logger.error("Unable to update. infrastructure with id {} not found.", id);
                 return new ResponseEntity(new CustomErrorType("Unable to upate. infrastructure with id " + id + " not found."),
                         HttpStatus.NOT_FOUND);
            }
            
        }
        
        return new ResponseEntity<Infrastructure>(currentInfrastructure, HttpStatus.OK);
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/infrastructure/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteInfrastructure(@PathVariable("id") String id) {
        logger.info("Fetching & Deleting Infrastructure with id {}", id);
 
        Infrastructure infrastructure = infrastructureService.findById(id);
        if (infrastructure == null) {
            logger.error("Unable to delete. infrastructure with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        infrastructureService.deleteById(id);
        return new ResponseEntity<Infrastructure>(HttpStatus.NO_CONTENT);
    }

}
