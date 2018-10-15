package my.com.fotia.osdec.controller;

import java.util.ArrayList;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import my.com.fotia.osdec.user.model.User;
import my.com.fotia.osdec.user.service.UserService;
import my.com.fotia.osdec.certification.coach.model.CertificationCoach;
import my.com.fotia.osdec.certification.coach.service.CertificationCoachService;
import my.com.fotia.osdec.utilities.CustomErrorType;
import my.com.fotia.osdec.utilities.UUIDGenerator;

@RestController
@RequestMapping("/api")
public class CertificationCoachController {
	
public static final Logger logger = LoggerFactory.getLogger(CertificationCoachController.class);
	
	@Autowired
	CertificationCoachService certificationCoachService;
	@Autowired
	UserService userService;	
	
	@RequestMapping(value = "/certificationCoach/all", method = RequestMethod.POST, produces = "application/json")
    public String certificationCoachList() throws Exception {
		
		String result = "";
		
		List<CertificationCoach> ls = new ArrayList<CertificationCoach>();
		ls = certificationCoachService.certificationCoachList();
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonRes = mapper.writeValueAsString(ls);
		
		/*"meta": {
	        "page": 1,
	        "pages": 1,
	        "perpage": -1,
	        "total": 350,
	        "sort": "asc",
	        "field": "RecordID"
	    },*/
		
		result += "{";
		result += "\"meta\":{";
		result += "\"page\":"+1+",";
		result += "\"pages\":"+1+",";
		result += "\"perpage\":"+-1+",";
		result += "\"total\":"+10+",";
		result += "\"sort\":\"asc\""+",";
		result += "\"field\":\"id\"";
		result += "},";
		result += "\"data\":"+jsonRes+"}";
		
        return result;
    }

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/certificationCoach/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getCertificationCoach(@PathVariable("id") String id) {
	    logger.info("Fetching certification coach with id {}", id);
	    CertificationCoach certificationCoach = certificationCoachService.findById(id);
	    if (certificationCoach == null) {
	        logger.error("certification coach with id {} not found.", id);
	        return new ResponseEntity(new CustomErrorType("User with id " + id 
	                + " not found"), HttpStatus.NOT_FOUND);
	    }
	    return new ResponseEntity<CertificationCoach>(certificationCoach, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/certificationCoach/list/{id}", method = RequestMethod.GET, produces = "application/json")
	public String certificationUser(@PathVariable("id") String id) throws Exception {
		
		List<CertificationCoach> ls = new ArrayList<CertificationCoach>();
		ls = certificationCoachService.findByCertification_id(id);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonRes = mapper.writeValueAsString(ls);
		
	    return jsonRes;
	}
	
	@RequestMapping(value = "/certification/coach/list/{id}", method = RequestMethod.POST, produces = "application/json")
    public String certificationList (@PathVariable("id") String id,
		@RequestParam ("datatable[pagination][page]") int page,
		@RequestParam ("datatable[pagination][perpage]") int perpage,
		@RequestParam ("datatable[sort][sort]") String sort,
		@RequestParam ("datatable[sort][field]") String sortField,
		@RequestParam (value= "datatable[search]", required=false) String search
		) throws Exception {
	
		String result = "";
		User coach = userService.findById(id);
		int pages = 0;
					
		List<CertificationCoach> ls = certificationCoachService.findByCoach(coach);
		
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
		
		if(sortField.equals("technology")) {
			sortField = "Certification_Technology_Name";
		}else if(sortField.equals("title")) {
			sortField = "Certification_Title";
		}else if(sortField.equals("duration")) {
			sortField = "Certification_Duration";
		}else if(sortField.equals("place")) {
			sortField = "Certification_Place";
		}
		
		Sort sortObj = new Sort(new Sort.Order(sortDirection, sortField));
		Pageable pageable = new PageRequest(page-1, perpage, sortObj);
		
		ls = certificationCoachService.findByCoach_IdAndCertification_TitleContainingIgnoreCaseOrCoach_IdAndCertification_Technology_NameContainingIgnoreCaseOrCoach_IdAndCertification_DurationContainingIgnoreCaseOrCoach_IdAndCertification_PlaceContainingIgnoreCase
				(id, search, id, search, id, search, id, search, pageable);
	
		ObjectMapper mapper = new ObjectMapper();
		String jsonRes = mapper.writeValueAsString(ls);
		
		Long totalL = certificationCoachService.countByCoach_IdAndCertification_TitleContainingIgnoreCaseOrCoach_IdAndCertification_Technology_NameContainingIgnoreCaseOrCoach_IdAndCertification_DurationContainingIgnoreCaseOrCoach_IdAndCertification_PlaceContainingIgnoreCase
				(id, search, id, search, id, search, id, search);
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
	@RequestMapping(value = "/certificationCoach/create", method = RequestMethod.POST)
    public ResponseEntity<?> createCertificationCoach(@RequestBody CertificationCoach certificationCoach, UriComponentsBuilder ucBuilder) {
        logger.info("Creating User : {}", certificationCoach);
 
        /*if (userService.isUserExist(user)) {
            logger.error("Unable to create. A User with name {} already exist", user.getName());
            return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " + 
            user.getName() + " already exist."),HttpStatus.CONFLICT);
        }*/
        certificationCoach.setId(UUIDGenerator.getInstance().getId());
        
        certificationCoachService.save(certificationCoach);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(certificationCoach.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/certificationCoach/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCertificationCoach(@PathVariable("id") String id, @RequestBody CertificationCoach certificationCoach) {
        logger.info("Updating Certification coach with id {}", id);
 
        CertificationCoach currentCertificationCoach = new CertificationCoach();
        
        if (id !=null && !"".equals(id)) {
        	currentCertificationCoach = certificationCoachService.findById(id);
        	
        	if (currentCertificationCoach !=null) {
                
        		certificationCoachService.save(currentCertificationCoach);
        		
        	}else {
        		
        	
            logger.error("Unable to update. Certification coach with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to upate. Certification coach with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        	}
        }
        
        return new ResponseEntity<CertificationCoach>(currentCertificationCoach, HttpStatus.OK);
    }
        
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/certificationCoach/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCertificationCoach(@PathVariable("id") String id) {
        logger.info("Fetching & Deleting Certification coach with id {}", id);
 
        CertificationCoach certificationCoach = certificationCoachService.findById(id);
        if (certificationCoach == null) {
            logger.error("Unable to delete. Certification coach with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        certificationCoachService.deleteById(id);
        return new ResponseEntity<CertificationCoach>(HttpStatus.NO_CONTENT);
    }

}

	


