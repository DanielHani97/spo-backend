package my.com.fotia.osdec.controller;


import java.util.ArrayList;
import java.util.List;


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

import my.com.fotia.osdec.general.agency.model.Agency;
import my.com.fotia.osdec.general.agency.model.Company;
import my.com.fotia.osdec.general.agency.service.AgencyService;
import my.com.fotia.osdec.general.agency.service.CompanyService;
import my.com.fotia.osdec.general.grade.model.Grade;
import my.com.fotia.osdec.utilities.CustomErrorType;
import my.com.fotia.osdec.utilities.UUIDGenerator;

@RestController
@RequestMapping("/api")
public class AgencyController {
	
	public static final Logger logger = LoggerFactory.getLogger(AgencyController.class);
	
	@Autowired
	AgencyService agencyService;
	
	@Autowired
	CompanyService companyService;

	@RequestMapping(value = "/agency", method = RequestMethod.POST, produces = "application/json")
    public String agencyList(
    		@RequestParam ("datatable[pagination][page]") int page,
    		@RequestParam ("datatable[pagination][perpage]") int perpage,
    		@RequestParam ("datatable[sort][sort]") String sort,
    		@RequestParam ("datatable[sort][field]") String sortField,
    		@RequestParam (value= "datatable[search]", required=false) String search
    		) throws Exception {
		
		String result = "";
		int pages = 0;
		
		List<Agency> ls = new ArrayList<Agency>();
		
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
			
			ls = agencyService.findByNameContainingIgnoreCaseOrCodeContainingIgnoreCaseOrPhoneNoContainingIgnoreCaseOrState_NameContainingIgnoreCaseOrCity_NameContainingIgnoreCase
					(search, search, search, search, search, pageable);
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonRes = mapper.writeValueAsString(ls);
			
			Long totalL = agencyService.countByNameContainingIgnoreCaseOrCodeContainingIgnoreCaseOrPhoneNoContainingIgnoreCaseOrState_NameContainingIgnoreCaseOrCity_NameContainingIgnoreCase
					(search, search, search, search,search);
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
	
	@RequestMapping(value = "/agency/all", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Agency>> agencyList() {
		List<Agency> list = agencyService.findAll();
		if(list.isEmpty()){
            return new ResponseEntity<List<Agency>>(HttpStatus.NOT_FOUND);
        }
		return new ResponseEntity<List<Agency>>(list, HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/agency/edit/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getAgency(@PathVariable("id") String id) {
        logger.info("Fetching agency with id {}", id);
        Agency agency = agencyService.findById(id);
        if (agency == null) {
            logger.error("agency with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Agency with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Agency>(agency, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/agency/create", method = RequestMethod.POST)
    public ResponseEntity<?> createagency(@RequestBody Agency agency, UriComponentsBuilder ucBuilder) {
        logger.info("Creating Agency : {}", agency);
 
        /*if (userService.isUserExist(user)) {
            logger.error("Unable to create. A User with name {} already exist", user.getName());
            return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " + 
            user.getName() + " already exist."),HttpStatus.CONFLICT);
        }*/
        agency.setId(UUIDGenerator.getInstance().getId());
        
        agencyService.save(agency);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/agency/{id}").buildAndExpand(agency.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/agency/edit/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateAgency(@PathVariable("id") String id, @RequestBody Agency agency) {
        logger.info("Updating Agency with id {}", id);
 
        Agency currentAgency = agencyService.findById(id);
 
        if (currentAgency == null) {
            logger.error("Unable to update. Agency with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to update. Agency with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        
        currentAgency.setAddress(agency.getAddress());
        currentAgency.setCity(agency.getCity());
        currentAgency.setName(agency.getName());
        currentAgency.setPhoneNo(agency.getPhoneNo());
        currentAgency.setPostcode(agency.getPostcode());
        currentAgency.setCode(agency.getCode());
        currentAgency.setState(agency.getState());
 
        agencyService.save(currentAgency);
        return new ResponseEntity<Agency>(currentAgency, HttpStatus.OK);
    }
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/agency/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAgency(@PathVariable("id") String id) {
        logger.info("Fetching & Deleting Agency with id {}", id);
 
        Agency agency = agencyService.findById(id);
        if (agency == null) {
            logger.error("Unable to delete. agency with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. Agency with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        agencyService.deleteById(id);
        return new ResponseEntity<Agency>(HttpStatus.NO_CONTENT);
    }
	
	/*@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/company/edit/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCompany(@PathVariable("id") String id, @RequestBody Company company) {
        logger.info("Updating Agency with id {}", id);
 
        Company currentCompany = companyService.findById(id);
 
        if (currentCompany == null) {
            logger.error("Unable to update. Company with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to update. Company with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        
        currentCompany.setAddress(company.getAddress());
        currentCompany.setCity(company.getCity());
        currentCompany.setName(company.getName());
        currentCompany.setPhoneNo(company.getPhoneNo());
        currentCompany.setPostcode(company.getPostcode());
        currentCompany.setState(company.getState());
 
        companyService.save(currentCompany);
        return new ResponseEntity<Company>(currentCompany, HttpStatus.OK);
    }*/
	
	//END
}
