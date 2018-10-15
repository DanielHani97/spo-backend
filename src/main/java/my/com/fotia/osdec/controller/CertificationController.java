package my.com.fotia.osdec.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.Interval;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import my.com.fotia.osdec.certification.model.Certification;
import my.com.fotia.osdec.certification.service.CertificationService;
import my.com.fotia.osdec.certification.user.model.CertificationUser;
import my.com.fotia.osdec.certification.user.service.CertificationUserService;
import my.com.fotia.osdec.general.manday.transaction.service.MandayTransactionService;
import my.com.fotia.osdec.trainingTx.model.TrainingTx;
import my.com.fotia.osdec.user.model.User;
import my.com.fotia.osdec.utilities.CustomErrorType;
import my.com.fotia.osdec.utilities.UUIDGenerator;

@RestController
@RequestMapping("/api")
public class CertificationController {
	
public static final Logger logger = LoggerFactory.getLogger(CertificationController.class);
	
	@Autowired
	CertificationService certificationService;
	
	@Autowired 
	CertificationUserService certificationUserService;
	
	@Autowired
	MandayTransactionService mandayTransactionService;
	
//	--------- ADMIN CALENDAR -----------
	@RequestMapping (value = "/certification/getall", method = RequestMethod.POST, produces = "application/json")
	public String CertListing() throws Exception{
		String result="";
		
		List <Certification> ls =new ArrayList<Certification>();
		ls = certificationService.findAll();
		
//		TimeZone tz = TimeZone.getTimeZone("Asia/Kuala_Lumpur");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
//		df.setTimeZone(tz);
		
		Iterator<Certification> iterator = ls.iterator();
		result += "[";
		
		while (iterator.hasNext()) {
			Certification obj = new Certification();
			
			obj = iterator.next();
			DateTime dateEnd = new DateTime(df.format(obj.getEndDate()));
		      
		      dateEnd = dateEnd.plusDays(1);
		      Date d2 = dateEnd.toDate();
					   
			    result += "{";
				result += "\"allDay\": true,";
				result += "\"type\": \"certification\",";
				result += "\"title\": "+"\""+obj.getTitle()+"\",";	
				result += "\"id\": "+"\""+obj.getId()+"\",";
				result += "\"description\": "+"\""+obj.getPlace()+"\",";
				result += "\"start\": "+"\""+obj.getStartDate()+"\",";
				result += "\"endDate\": "+"\""+obj.getEndDate()+"\",";
		        result += "\"end\": "+"\""+dateEnd+"\",";  
				result += "\"technology\": "+"\""+obj.getTechnology().getName()+"\",";
				result += "\"level\": "+"\""+obj.getLevel()+"\",";
				result += "\"editable\": false";
				result += "},";		
		
				
	}
	result = removeLastChar(result);
	result += "]";
		
		return result;
	}
	
//	------------ USER CALENDAR -----------
	@RequestMapping(value = "/certification/getuserAll/{id}", method = RequestMethod.POST, produces = "application/json")
	public String certList(@PathVariable("id") String id) throws Exception{
		
		String result = "";
		List<CertificationUser> ls2 = certificationUserService.findByUser_Id(id);
		
//		TimeZone tz = TimeZone.getTimeZone("Asia/Kuala_Lumpur"); 
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"); 
//	    df.setTimeZone(tz); 
	    
	    Iterator <CertificationUser> iterator = ls2.iterator();
	    
	    result +="[";
	    
	    while(iterator.hasNext()) {
	    	Certification cert = iterator.next().getCertification();
	    	DateTime dateEnd = new DateTime(df.format(cert.getEndDate()));
		      
		      dateEnd = dateEnd.plusDays(1);
		      Date d2 = dateEnd.toDate();
	    	
			    
			    result += "{";
				result += "\"allDay\": true,";
				result += "\"type\": \"certification\",";
				result += "\"title\": "+"\""+cert.getTitle()+"\",";
				result += "\"id\": "+"\""+cert.getId()+"\",";				
				result += "\"description\": "+"\""+cert.getPlace()+"\",";
				result += "\"start\": "+"\""+cert.getStartDate()+"\",";
				result += "\"endDate\": "+"\""+cert.getEndDate()+"\",";
		        result += "\"end\": "+"\""+dateEnd+"\",";  
				result += "\"technology\": "+"\""+cert.getTechnology().getName()+"\",";
				result += "\"level\": "+"\""+cert.getLevel()+"\",";
				result += "},";		
			
	    }
	    
	    result = removeLastChar(result);
		result += "]";
				
		return result;
	}
	
	@RequestMapping(value = "/certificationgetall", method = RequestMethod.GET)
	public ResponseEntity<List<Certification>> certificationList() {
		List<Certification> list = certificationService.findAll();
		if(list.isEmpty()){
            return new ResponseEntity<List<Certification>>(HttpStatus.NOT_FOUND);
        }
		return new ResponseEntity<List<Certification>>(list, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/certificationall", method = RequestMethod.GET)
	public ResponseEntity<List<Certification>> certLs() {
		
		//DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		List<Certification> list = new ArrayList<Certification>();
		list = certificationService.findByStatusAndStartDateGreaterThanEqual("Aktif", date);
		
		if(list.isEmpty()){
            return new ResponseEntity<List<Certification>>(HttpStatus.NOT_FOUND);
        }
		return new ResponseEntity<List<Certification>>(list, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/certification", method = RequestMethod.POST, produces = "application/json")
    public String certificationList (
		@RequestParam ("datatable[pagination][page]") int page,
		@RequestParam ("datatable[pagination][perpage]") int perpage,
		@RequestParam ("datatable[sort][sort]") String sort,
		@RequestParam ("datatable[sort][field]") String sortField,
		@RequestParam (value= "datatable[search]", required=false) String search
		) throws Exception {
		
		String result = "";
		int pages = 0;
		
		List<Certification> ls = new ArrayList<Certification>();
				
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
				sortField = "Technology_Name";
			}
			
			Sort sortObj = new Sort(new Sort.Order(sortDirection, sortField));
			Pageable pageable = new PageRequest(page-1, perpage, sortObj);
			
			ls = certificationService.findByTitleContainingIgnoreCaseOrTechnology_NameContainingIgnoreCaseOrDurationContainingIgnoreCaseOrPlaceContainingIgnoreCaseOrLevelContainingIgnoreCaseOrStatusContainingIgnoreCase
					(search, search, search, search, search, search, pageable);
		
			ObjectMapper mapper = new ObjectMapper();
			String jsonRes = mapper.writeValueAsString(ls);
			
			Long totalL = certificationService.countByTitleContainingIgnoreCaseOrTechnology_NameContainingIgnoreCaseOrDurationContainingIgnoreCaseOrPlaceContainingIgnoreCaseOrLevelContainingIgnoreCaseOrStatusContainingIgnoreCase
					(search, search, search, search, search, search);
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
	@RequestMapping(value = "/certification/edit/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getCertification(@PathVariable("id") String id) {
        logger.info("Fetching certification with id {}", id);
        Certification certification = certificationService.findById(id);
        if (certification == null) {
            logger.error("certification with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("User with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Certification>(certification, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/certification/create", method = RequestMethod.POST, consumes="multipart/form-data")
    public Certification createCertification(@RequestPart(value="avatar", required=false) MultipartFile file, @RequestPart("info") Certification certification, UriComponentsBuilder ucBuilder) {
        logger.info("Creating User : {}", certification);
        
        Certification tr = new Certification();
        byte [] byteArr = null;
        /*if (userService.isUserExist(user)) {
            logger.error("Unable to create. A User with name {} already exist", user.getName());
            return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " + 
            user.getName() + " already exist."),HttpStatus.CONFLICT);
        }*/
        try {
        	certification.setId(UUIDGenerator.getInstance().getId());
        	certification.setCreated_by(certification.getCreated_by());
        	certification.setCreated_date(new Date());
            
            try {
            	if(file != null) {
        		if (file.getSize() > 0) {
        			byteArr = file.getBytes();
    				// Byte[]
    				//String filename = file.getOriginalFilename();// Get attached
    				// logo name
        			certification.setImage(byteArr);
    			}
            }
    			
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        
        tr = certificationService.save(certification);
        
        }catch(Exception e) {
        	logger.error(e.getMessage());
        }
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(certification.getId()).toUri());
        return tr;
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/certification/edit", method = RequestMethod.POST, consumes="multipart/form-data")
    public ResponseEntity<?> updateTraining(@RequestPart(value="avatar", required=false) MultipartFile file, @RequestPart("info") Certification certification) {     
        Certification currentCertification = new Certification();
    	byte [] byteArr = null;

        if (certification !=null) {
          
        	String id = certification.getId();
            logger.info("Updating Certification with id {}", id);
            	
            	currentCertification = certificationService.findById(id);
            	
        	if (currentCertification !=null) {
        		currentCertification.setTitle(certification.getTitle());
        		currentCertification.setTechnology(certification.getTechnology());
                currentCertification.setCategory(certification.getCategory());
                currentCertification.setDuration(certification.getDuration());
                currentCertification.setPlace(certification.getPlace());
                currentCertification.setRemark(certification.getRemark());
                currentCertification.setLevel(certification.getLevel());
                //currentCertification.setImage(certification.getImage());
                currentCertification.setStartDate(certification.getStartDate());
                currentCertification.setEndDate(certification.getEndDate());
                currentCertification.setStatus(certification.getStatus());
                currentCertification.setModified_date(certification.getModified_date());
                currentCertification.setModified_by(certification.getModified_by());
                currentCertification.setLimitation(certification.getLimitation());

                try {
                	if(file != null) {
                		if (file.getSize() > 0) {
                			byteArr = file.getBytes();
                			currentCertification.setImage(byteArr);
            			}
                	}
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
                
                certificationService.save(currentCertification);
        		
        	}else {
        		
        	
            logger.error("Unable to update. Certification with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to update. Certification with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        	}
        }
        
        return new ResponseEntity<Certification>(currentCertification, HttpStatus.OK);
    }
        
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/certification/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCertification(@PathVariable("id") String id) {
        logger.info("Fetching & Deleting Certification with id {}", id);
 
        Certification certification = certificationService.findById(id);
        List<CertificationUser> certTx= certificationUserService.findByCertification_id(id);

        if (certification == null) {
            logger.error("Unable to delete. Certification with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
            
        }else if( certTx.isEmpty()) {
        	
        mandayTransactionService.deleteByInstanceId(id); 	
        //certificationService.deleteByCertificationCoachId(id);
        //certificationService.deleteByCertificationUserId(id);
        certificationService.deleteById(id);
        return new ResponseEntity<Certification>(HttpStatus.NO_CONTENT);
        
        }else {
        	logger.error("Unable to delete. Cert tx exist");
        	return new ResponseEntity(new CustomErrorType("Unable to delete. Cert tx exist"),HttpStatus.NOT_FOUND);
        }
    }
	
	public String removeLastChar(String str) {
	    if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == ',') {
	        str = str.substring(0, str.length() - 1);
	    }
	    return str;
}

}
