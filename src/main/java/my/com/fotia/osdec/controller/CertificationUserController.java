package my.com.fotia.osdec.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import my.com.fotia.osdec.user.model.User;
import my.com.fotia.osdec.user.service.UserService;
import my.com.fotia.osdec.attendance.model.Attendance;
import my.com.fotia.osdec.attendance.service.AttendanceService;
import my.com.fotia.osdec.certification.model.Certification;
import my.com.fotia.osdec.certification.user.model.CertificationUser;
import my.com.fotia.osdec.certification.user.service.CertificationUserService;
import my.com.fotia.osdec.coaching.model.Coaching;
import my.com.fotia.osdec.references.model.Filestorage;
import my.com.fotia.osdec.references.service.FilestorageService;
import my.com.fotia.osdec.training.model.Training;
import my.com.fotia.osdec.trainingTx.model.TrainingTx;
import my.com.fotia.osdec.utilities.CustomErrorType;
import my.com.fotia.osdec.utilities.UUIDGenerator;

@RestController
@RequestMapping("/api")
public class CertificationUserController {
	
public static final Logger logger = LoggerFactory.getLogger(CertificationUserController.class);
	
	@Autowired
	CertificationUserService certificationUserService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	AttendanceService attendanceService;
	
	@Autowired
	FilestorageService filestorageService;
	
	// AUDIT PERSIJILAN 
		  @RequestMapping(value = "/certTxAudit", method = RequestMethod.POST, produces = "application/json") 
		    public String certAuditList( 
		        @RequestParam ("datatable[pagination][page]") int page, 
		        @RequestParam ("datatable[pagination][perpage]") int perpage, 
		        @RequestParam ("datatable[sort][sort]") String sort, 
		        @RequestParam ("datatable[sort][field]") String sortField, 
		        @RequestParam (value= "datatable[search]", required=false) String search 
		        ) throws Exception { 
		     
		    String result = ""; 
		    int pages = 1; 
		     
		    List<CertificationUser> ls = new ArrayList<CertificationUser>(); 
		     
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
		      
		      if(sortField.equals("title")) {
					sortField = "Certification_Title";	
		      }else if(sortField.equals("name")) {
					sortField = "User_Name";	
		      }
		       
		      Collection<String> collection = new ArrayList<String>(Arrays.asList(new String[] { "3", "4", "5" })); 
		      Sort sortObj = new Sort(new Sort.Order(sortDirection, sortField)); 
		      Pageable pageable = new PageRequest(page-1, perpage, sortObj); 
		 
		      ls = certificationUserService.findByStatusInAndCreatedBy_NameContainingIgnoreCaseOrStatusInAndEvaluatedBy_NameContainingIgnoreCaseOrStatusInAndApprovedBy_NameContainingIgnoreCase 
		          (collection, search, collection, search, collection, search, pageable); 
		 
		      ObjectMapper mapper = new ObjectMapper(); 
		      String jsonRes = mapper.writeValueAsString(ls); 
		       
		      Long totalL = certificationUserService.countByStatusInAndCreatedBy_NameContainingIgnoreCaseOrStatusInAndEvaluatedBy_NameContainingIgnoreCaseOrStatusInAndApprovedBy_NameContainingIgnoreCase 
		          (collection, search, collection, search, collection, search); 
		           
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
		  
		//LAPORAN PERSIJILAN  
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@RequestMapping(value = "/persijilan/report", method = RequestMethod.POST)
	    public  String getPersijilanList(
	    		@RequestParam ("datatable[pagination][page]") int page,
	    		@RequestParam ("datatable[pagination][perpage]") int perpage,
	    		@RequestParam ("datatable[sort][sort]") String sort,
	    		@RequestParam ("datatable[sort][field]") String sortField,
	    		@RequestParam (value= "datatable[search]", required=false) String search
	    		) throws JsonProcessingException {
			
			String result = "";
			int pages = 1;
					
			List<CertificationUser> ls = new ArrayList<CertificationUser>();
		    
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
					sortField = "User_Name";
				}else if(sortField.equals("name2")) {
					sortField = "Agency_Name";
				}else if(sortField.equals("title")) {
					sortField = "Certification_Title";
				}
				
				Collection<String> collection = new ArrayList<String>(Arrays.asList(new String[] { "3"})); 
		        Sort sortObj = new Sort(new Sort.Order(sortDirection, sortField)); 
		        Pageable pageable = new PageRequest(page-1, perpage, sortObj); 
		 
		        ls = certificationUserService.findByStatusInAndUser_NameContainingIgnoreCaseOrStatusInAndUser_Agency_NameContainingIgnoreCaseOrStatusInAndUser_EmailContainingIgnoreCaseOrStatusInAndUser_PositionContainingIgnoreCaseOrStatusInAndCertification_TitleContainingIgnoreCase 
		          (collection, search, collection, search, collection, search, collection, search, collection, search, pageable); 
			
				ObjectMapper mapper = new ObjectMapper();
				String jsonRes = mapper.writeValueAsString(ls);
				
				Long totalL = certificationUserService.countByStatusInAndUser_NameContainingIgnoreCaseOrStatusInAndUser_Agency_NameContainingIgnoreCaseOrStatusInAndUser_EmailContainingIgnoreCaseOrStatusInAndUser_PositionContainingIgnoreCaseOrStatusInAndCertification_TitleContainingIgnoreCase( collection, search, collection, search, collection, search, collection, search, collection, search);
				
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
	@RequestMapping(value = "/certificationUser/all", method = RequestMethod.POST)
    public  String getCertificationUserList(
    		@RequestParam ("datatable[pagination][page]") int page,
    		@RequestParam ("datatable[pagination][perpage]") int perpage,
    		@RequestParam ("datatable[sort][sort]") String sort,
    		@RequestParam ("datatable[sort][field]") String sortField,
    		@RequestParam (value= "datatable[search]", required=false) String search
    		) throws JsonProcessingException {
		
		String result = "";
		int pages = 0;
				
		List<CertificationUser> ls = new ArrayList<CertificationUser>();
		
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
				sortField = "User_Name";
			}else if(sortField.equals("name2")) {
				sortField = "Agency_Name";
			}else if(sortField.equals("title")) {
				sortField = "Certification_Title";
			}
			
			Sort sortObj = new Sort(new Sort.Order(sortDirection, sortField));
			Pageable pageable = new PageRequest(page-1, perpage, sortObj);
			
			ls = certificationUserService.findByUser_NameContainingIgnoreCaseOrUser_Agency_NameContainingIgnoreCaseOrCertification_TitleContainingIgnoreCase(search, search, search, pageable);
		
		
			ObjectMapper mapper = new ObjectMapper();
			String jsonRes = mapper.writeValueAsString(ls);
			
			Long totalL = certificationUserService.countByUser_NameContainingIgnoreCaseOrUser_Agency_NameContainingIgnoreCaseOrCertification_TitleContainingIgnoreCase( search, search, search);
			
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
	
	@RequestMapping(value = "/certTxall", method = RequestMethod.GET)
	public ResponseEntity<List<CertificationUser>> certTxLs() {
		
		List<CertificationUser> ls = new ArrayList<CertificationUser>();
		ls = certificationUserService.findAll();
		
		if(ls.isEmpty()){
            return new ResponseEntity<List<CertificationUser>>(HttpStatus.NOT_FOUND);
        }
		return new ResponseEntity<List<CertificationUser>>(ls, HttpStatus.OK);
	}
	
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@RequestMapping(value = "/certificationUser/{id}", method = RequestMethod.GET)
	    public ResponseEntity<?> getCertificationUSer(@PathVariable("id") String id) {
	        logger.info("Fetching certification transaction with id {}", id);
	        CertificationUser certificationUser = certificationUserService.findById(id);
	        if (certificationUser == null) {
	            logger.error("certification transaction with id {} not found.", id);
	            return new ResponseEntity(new CustomErrorType("User with id " + id 
	                    + " not found"), HttpStatus.NOT_FOUND);
	        }
	        return new ResponseEntity<CertificationUser>(certificationUser, HttpStatus.OK);
	    }
		
		@RequestMapping(value = "/certificationUser/list/{id}", method = RequestMethod.GET, produces = "application/json")
	    public String certificationUser(@PathVariable("id") String id) throws Exception {
			
			List<CertificationUser> ls = new ArrayList<CertificationUser>();
			ls = certificationUserService.findByCertification_id(id);
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonRes = mapper.writeValueAsString(ls);
			
	        return jsonRes;
	    }
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@RequestMapping(value = "/certification/user/list/{id}", method = RequestMethod.POST)
	    public  String getCertificationList( @PathVariable("id") String id,
	    		@RequestParam ("datatable[pagination][page]") int page,
	    		@RequestParam ("datatable[pagination][perpage]") int perpage,
	    		@RequestParam ("datatable[sort][sort]") String sort,
	    		@RequestParam ("datatable[sort][field]") String sortField,
	    		@RequestParam (value= "datatable[search]", required=false) String search
	    		) throws JsonProcessingException {
		
		String result = "";
		int pages = 0;

		User user = userService.findById(id);
		
		List<CertificationUser> ls = certificationUserService.findByUser(user);
		
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
			
			if(sortField.equals("title")) {
				sortField = "Certification_Title";
			}else if(sortField.equals("technology")) {
				sortField = "Certification_Technology_Name";
			}else if(sortField.equals("place")) {
				sortField = "Certification_Place";
			}else if(sortField.equals("status")) {
				sortField = "Certification_Status";
			}
			
			Sort sortObj = new Sort(new Sort.Order(sortDirection, sortField));
			Pageable pageable = new PageRequest(page-1, perpage, sortObj);
			
			ls = certificationUserService.findByUser_IdAndCertification_TitleContainingIgnoreCaseOrUser_IdAndCertification_Technology_NameContainingIgnoreCaseOrUser_IdAndCertification_DurationContainingIgnoreCaseOrUser_IdAndCertification_PlaceContainingIgnoreCase(id, search, id, search, id, search, id, search, pageable);
					
			ObjectMapper mapper = new ObjectMapper();
			String jsonRes = mapper.writeValueAsString(ls);
			
			Long totalL = certificationUserService.countByUser_IdAndCertification_TitleContainingIgnoreCaseOrUser_IdAndCertification_Technology_NameContainingIgnoreCaseOrUser_IdAndCertification_DurationContainingIgnoreCaseOrUser_IdAndCertification_PlaceContainingIgnoreCase(id, search, id, search, id, search, id, search);
					
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
		
	@RequestMapping(value = "/certificationUser/create", method = RequestMethod.POST)
    public ResponseEntity<?> createCertificationUser(@RequestBody CertificationUser certificationUser, UriComponentsBuilder ucBuilder) {
        logger.info("Creating User : {}", certificationUser);
 
        /*if (userService.isUserExist(user)) {
            logger.error("Unable to create. A User with name {} already exist", user.getName());
            return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " + 
            user.getName() + " already exist."),HttpStatus.CONFLICT);
        }*/
        
        //User objUser = new User();
		//objUser = userService.findById(userid);
        
        //Training objtra = new Training();
		//objtra = trainingTxService.findById(trainingid);
        
        /*if(objUser != null && objtra != null) {
            trainingTx.setTraining(objtra);
            trainingTx.setUser(objUser);            
        }*/
        certificationUser.setId(UUIDGenerator.getInstance().getId());
        certificationUser.setCreatedBy(certificationUser.getCreatedBy());
        certificationUser.setCreatedDate(new Date());
        certificationUserService.save(certificationUser);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(certificationUser.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/certificationUser/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCertificationUser(@PathVariable("id") String id, @RequestBody CertificationUser certificationUser) {
        logger.info("Updating Certification transaction with id {}", id);
 
        CertificationUser currentCertificationUser = new CertificationUser();
        
        if (id !=null && !"".equals(id)) {
        	currentCertificationUser = certificationUserService.findById(id);
        	
        	if (currentCertificationUser !=null) {
        		String status = certificationUser.getStatus();
            	
            	if ("2".equals(status)) {
            		
            		currentCertificationUser.setCoach_remarks(certificationUser.getCoach_remarks());
            		currentCertificationUser.setStatus(certificationUser.getStatus());
            		currentCertificationUser.setEvaluatedBy(certificationUser.getEvaluatedBy());
            		currentCertificationUser.setEvaluatedDate(new Date());
            		certificationUserService.save(currentCertificationUser);
            		
            	}else if ("3".equals(status)) {
            		
            		currentCertificationUser.setAdmin_remarks(certificationUser.getAdmin_remarks());
            		currentCertificationUser.setStatus(certificationUser.getStatus());
            		currentCertificationUser.setStatusResult(certificationUser.getStatusResult());
            		currentCertificationUser.setRemarks(certificationUser.getRemarks());
            		currentCertificationUser.setApprovedBy(certificationUser.getApprovedBy());
            		currentCertificationUser.setApprovedDate(new Date());
            		certificationUserService.save(currentCertificationUser);

            	}else if ("4".equals(status)) {
            		
            		currentCertificationUser.setAdmin_remarks(certificationUser.getAdmin_remarks());
            		currentCertificationUser.setStatus(certificationUser.getStatus());
            		currentCertificationUser.setApprovedBy(certificationUser.getApprovedBy());
            		currentCertificationUser.setApprovedDate(new Date());
            		certificationUserService.save(currentCertificationUser);
            		
            	}else if ("5".equals(status)) {
            		
            		currentCertificationUser.setStatus(certificationUser.getStatus());
            		currentCertificationUser.setApprovedBy(certificationUser.getApprovedBy());
            		currentCertificationUser.setApprovedDate(new Date());
            		certificationUserService.save(currentCertificationUser);
            		
            	}       	
            	
            }else {
            	 logger.error("Unable to update. CertUser with id {} not found.", id);
                 return new ResponseEntity(new CustomErrorType("Unable to update. CertUser with id " + id + " not found."),
                         HttpStatus.NOT_FOUND);
            }
            
        }
        certificationUserService.save(currentCertificationUser);
        return new ResponseEntity<CertificationUser>(currentCertificationUser, HttpStatus.OK);
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/certificationUser/update", method = RequestMethod.POST, consumes="multipart/form-data")
    public ResponseEntity<?> updateCertificationUserFile(
    		@RequestPart(value="fileCert", required=false) MultipartFile file,
    		@RequestPart("info") CertificationUser certificationUser) {
 
		String id = certificationUser.getId();
        CertificationUser currentCertificationUser = new CertificationUser();
        
        if (id !=null && !"".equals(id)) {
        	currentCertificationUser = certificationUserService.findById(id);
        	
        	Filestorage storage = new Filestorage();
			byte [] byteFile = null;
        	
        	if (currentCertificationUser !=null) {
        		String status = certificationUser.getStatusResult();
            		
        		currentCertificationUser.setRemarks(certificationUser.getRemarks());
        		currentCertificationUser.setStatusResult(status);
        		
        		try {
        			
            		if (file.getSize() > 0) {
            			byteFile = file.getBytes();
            			
            			storage.setId(UUIDGenerator.getInstance().getId());
            			storage.setType(file.getContentType());
            			storage.setName(file.getOriginalFilename());
            			storage.setContent(byteFile);
            			storage.setInstanceid(id);
            			
            			currentCertificationUser.setCert(storage);
            			
        			}
        			
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
        		
        		certificationUserService.save(currentCertificationUser);
        		if (file.getSize() > 0) {
        			filestorageService.save(storage);
        		}
            	
            }else {
            	 logger.error("Unable to update. CertUser with id {} not found.", id);
                 return new ResponseEntity(new CustomErrorType("Unable to update. CertUser with id " + id + " not found."),
                         HttpStatus.NOT_FOUND);
            }
            
        }
        certificationUserService.save(currentCertificationUser);
        return new ResponseEntity<CertificationUser>(currentCertificationUser, HttpStatus.OK);
    }
        
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/certificationUser/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCertificationUser(@PathVariable("id") String id) {
        logger.info("Fetching & Deleting Certification transaction with id {}", id);
 
        CertificationUser certificationUser = certificationUserService.findById(id);
        if (certificationUser == null) {
            logger.error("Unable to delete. Certification transaction with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        certificationUserService.deleteById(id);
        return new ResponseEntity<CertificationUser>(HttpStatus.NO_CONTENT);
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/certificationUser/isExist", method = RequestMethod.POST)
    public ResponseEntity<?> capUserIsExist(@RequestBody CertificationUser certificationUser, UriComponentsBuilder ucBuilder) {
		
		String certId = certificationUser.getCertification().getId();
	    String userId = certificationUser.getUser().getId();
	    String status = "1";
	    
	    boolean isExist = certificationUserService.existsByUser_IdAndCertification_IdAndStatus(userId, certId, status);

	    if (isExist) {
	        logger.error("Unable to create Certification. A Certification for User with name {} already exist", certificationUser.getUser().getName());
	        return new ResponseEntity(new CustomErrorType("Unable to create Certification. A Certification for User with name " + 
	        		certificationUser.getUser().getName() + " already exist."),HttpStatus.CONFLICT);
	    }
        
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(certificationUser.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

}
