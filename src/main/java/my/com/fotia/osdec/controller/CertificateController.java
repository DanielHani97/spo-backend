package my.com.fotia.osdec.controller;


import java.util.ArrayList;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import my.com.fotia.osdec.general.certificate.model.Certificate;
import my.com.fotia.osdec.general.certificate.service.CertificateService;
import my.com.fotia.osdec.references.service.FilestorageService;
import my.com.fotia.osdec.utilities.CustomErrorType;
import my.com.fotia.osdec.utilities.UUIDGenerator;

@RestController
@RequestMapping("/api")
public class CertificateController {
	
public static final Logger logger = LoggerFactory.getLogger(CertificateController.class);
	
	@Autowired
	CertificateService certificateService;
	
	@Autowired
	FilestorageService filestorageService;

	@RequestMapping(value = "/certificate/all", method = RequestMethod.POST, produces = "application/json")
    public String certificateList() throws Exception {
		
		String result = "";
		
		List<Certificate> ls = new ArrayList<Certificate>();
		ls = certificateService.certificateList();
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonRes = mapper.writeValueAsString(ls);
		
		/*"meta": {
	        "page": 1,
	        "pages": 1,
	        "perpage": -1,
	        "total": 350,
	        "sort": "asc",http://localhost:8080/api/certificate
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
	@RequestMapping(value = "/certificate/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getCertificate(@PathVariable("id") String id) {
        logger.info("Fetching certificate with id {}", id);
        Certificate certificate = certificateService.findById(id);
        if (certificate == null) {
            logger.error("certificate with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("User with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Certificate>(certificate, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/certificate/create", method = RequestMethod.POST)
    public ResponseEntity<?> createCertificate(@RequestBody Certificate certificate, UriComponentsBuilder ucBuilder) {
        logger.info("Creating User : {}", certificate);
 
        /*if (userService.isUserExist(user)) {
            logger.error("Unable to create. A User with name {} already exist", user.getName());
            return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " + 
            user.getName() + " already exist."),HttpStatus.CONFLICT);
        }*/
        certificate.setId(UUIDGenerator.getInstance().getId());
        
        certificateService.save(certificate);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(certificate.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/certificate/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCertificate(@PathVariable("id") String id, @RequestBody Certificate certificate) {
        logger.info("Updating Certificate with id {}", id);
 
        //Agency currentAgency = agencyService.findById(id);
        Certificate currentCertificate = new Certificate();
        if(id !=null && !"".equals(id)) {
        		currentCertificate = certificateService.findById(id);
        		if(currentCertificate != null) {
        			currentCertificate.setName(certificate.getName());
        			currentCertificate.setCategory(certificate.getCategory());
        			certificateService.save(currentCertificate);
        	
        } else  {
            logger.error("Unable to update. Certificate with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to upate. Certificate with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        	}
        }
        
        return new ResponseEntity<Certificate>(currentCertificate, HttpStatus.OK);
    }
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/certificate/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCertificate(@PathVariable("id") String id) {
        logger.info("Fetching & Deleting Certificate with id {}", id);
 
        Certificate certificate = certificateService.findById(id);
        if (certificate == null) {
            logger.error("Unable to delete. Certificate with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        
        try {
        	certificateService.deleteById(id);
        }catch(Exception e){}
        
        try {
        	filestorageService.deleteByInstanceId(id);
        }catch(Exception e){}
        
        
        return new ResponseEntity<Certificate>(HttpStatus.NO_CONTENT);
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/certificate/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getCertificateuser(@PathVariable("id") String userId) {
        List <Certificate> ls = certificateService.findByUser_Id(userId);
        /*if (ls.size() < 1) {
            return new ResponseEntity(new CustomErrorType("certificateUser with userid " + userId 
                    + " not found"), HttpStatus.NOT_FOUND);
        }*/
        return new ResponseEntity<List<Certificate>>(ls, HttpStatus.OK);
    }
}
