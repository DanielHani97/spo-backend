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

import my.com.fotia.osdec.technology.model.Technology;
import my.com.fotia.osdec.technology.service.TechnologyService;
import my.com.fotia.osdec.utilities.CustomErrorType;
import my.com.fotia.osdec.utilities.UUIDGenerator;

@RestController
@RequestMapping("/api")
public class TechnologyController {
	
public static final Logger logger = LoggerFactory.getLogger(TechnologyController.class);
	
	@Autowired
	TechnologyService technologyService;

	@RequestMapping(value = "/technology", method = RequestMethod.POST, produces = "application/json")
    public String technologyList(
    		@RequestParam ("datatable[pagination][page]") int page,
    		@RequestParam ("datatable[pagination][perpage]") int perpage,
    		@RequestParam ("datatable[sort][sort]") String sort,
    		@RequestParam ("datatable[sort][field]") String sortField,
    		@RequestParam (value= "datatable[search]", required=false) String search
    		) throws Exception {
		
		String result = "";
		int pages = 0;
		
		
		List<Technology> ls = new ArrayList<Technology>();
		
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
			
			ls = technologyService.findByNameContainingIgnoreCaseOrTypeContainingIgnoreCaseOrLanguageContainingIgnoreCase
					(search, search, search, pageable);
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonRes = mapper.writeValueAsString(ls);
			
			Long totalL = technologyService.countByNameContainingIgnoreCaseOrTypeContainingIgnoreCaseOrLanguageContainingIgnoreCase
					(search, search, search);
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
	
	@RequestMapping(value = "/teknologi/senarai", method = RequestMethod.GET, produces = "application/json")
    public String teknologi() throws Exception {
		String status = "1";
		List<Technology> ls = new ArrayList<Technology>();
		ls = technologyService.findAllTeknologi(status);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonRes = mapper.writeValueAsString(ls);
		
        return jsonRes;
    }
	
	@RequestMapping(value = "/technology/list", method = RequestMethod.GET, produces = "application/json")
    public String technology() throws Exception {
		String typeF = "Frontend";
		String statusF = "1";
		List<Technology> ls = new ArrayList<Technology>();
		ls = technologyService.findFrontend(typeF, statusF);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonRes = mapper.writeValueAsString(ls);
		
        return jsonRes;
    }
	
	@RequestMapping(value = "/technology2/list", method = RequestMethod.GET, produces = "application/json")
    public String technology2() throws Exception {
		String typeF = "Backend";
		String statusF = "1";
		List<Technology> ls = new ArrayList<Technology>();
		ls = technologyService.findBackend(typeF, statusF);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonRes = mapper.writeValueAsString(ls);
		
        return jsonRes;
    }
	
	@RequestMapping(value = "/technology3/list", method = RequestMethod.GET, produces = "application/json")
    public String technology3() throws Exception {
		String typeF = "Database";
		String statusF = "1";
		List<Technology> ls = new ArrayList<Technology>();
		ls = technologyService.findDatabase(typeF, statusF);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonRes = mapper.writeValueAsString(ls);
		
        return jsonRes;
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/technology/edit/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getTechnology(@PathVariable("id") String id) {
        logger.info("Fetching technology with id {}", id);
        Technology technology = technologyService.findById(id);
        if (technology == null) {
            logger.error("technology with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("User with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Technology>(technology, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/technology/create", method = RequestMethod.POST)
    public ResponseEntity<?> createTechnology(@RequestBody Technology technology, UriComponentsBuilder ucBuilder) {
        logger.info("Creating User : {}", technology);
 
        /*if (userService.isUserExist(user)) {
            logger.error("Unable to create. A User with name {} already exist", user.getName());
            return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " + 
            user.getName() + " already exist."),HttpStatus.CONFLICT);
        }*/
        technology.setId(UUIDGenerator.getInstance().getId());
        
        technology.setCreated_date(new Date());
        
        technologyService.save(technology);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(technology.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/technology/edit/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateTechnology(@PathVariable("id") String id, @RequestBody Technology technology) {
        logger.info("Updating Technology with id {}", id);
        
        Technology currentTechnology = new Technology();
 
        if(id !=null && !"".equals(id)) {
        	currentTechnology = technologyService.findById(id);
            
            if(currentTechnology != null) {
            	currentTechnology.setName(technology.getName());
            	currentTechnology.setType(technology.getType());
            	currentTechnology.setLanguage(technology.getLanguage());
            	currentTechnology.setStatus(technology.getStatus());
            	currentTechnology.setModified_by(technology.getModified_by());
            	currentTechnology.setModified_date(new Date());
            	technologyService.save(currentTechnology);
            }else {
            	 logger.error("Unable to update. Technology with id {} not found.", id);
                 return new ResponseEntity(new CustomErrorType("Unable to upate. Technology with id " + id + " not found."),
                         HttpStatus.NOT_FOUND);
            }
            
        }
        
        return new ResponseEntity<Technology>(currentTechnology, HttpStatus.OK);
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/technology/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteTechnology(@PathVariable("id") String id) {
        logger.info("Fetching & Deleting Technology with id {}", id);
 
        Technology technology = technologyService.findById(id);
        if (technology == null) {
            logger.error("Unable to delete. Technology with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        try {
        	technologyService.deleteById(id);
            return new ResponseEntity<Technology>(HttpStatus.NO_CONTENT);
        }catch(Exception e) {
        	logger.error(e.getMessage());
        	return new ResponseEntity(new CustomErrorType("Technology In Use"),
                    HttpStatus.CONFLICT);
        }
        
    }

}
