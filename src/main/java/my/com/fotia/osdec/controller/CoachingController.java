package my.com.fotia.osdec.controller;

import java.io.IOException;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import my.com.fotia.osdec.coaching.activities.model.CoachingActivities;
import my.com.fotia.osdec.coaching.coach.service.CoachingCoachService;
import my.com.fotia.osdec.coaching.model.Coaching;
import my.com.fotia.osdec.coaching.service.CoachingService;
import my.com.fotia.osdec.feedback.model.Feedback;
import my.com.fotia.osdec.general.certificate.model.Certificate;
import my.com.fotia.osdec.references.model.Filestorage;
import my.com.fotia.osdec.references.service.FilestorageService;
import my.com.fotia.osdec.security.JwtUser;
import my.com.fotia.osdec.utilities.CustomErrorType;
import my.com.fotia.osdec.utilities.UUIDGenerator;

@RestController
@RequestMapping("/api")
public class CoachingController {
	
	public static final Logger logger = LoggerFactory.getLogger(CoachingController.class);
	
	@Autowired
	CoachingService coachingService;
	
	@Autowired
	CoachingCoachService coachingCoachService;
	
	@Autowired
	FilestorageService filestorageService;
	
	//ADMIN DATATABLE
	@RequestMapping(value = "/coaching", method = RequestMethod.POST, produces = "application/json")
    public String coachingList(
    		@RequestParam ("datatable[pagination][page]") int page,
    		@RequestParam ("datatable[pagination][perpage]") int perpage,
    		@RequestParam ("datatable[sort][sort]") String sort,
    		@RequestParam ("datatable[sort][field]") String sortField,
    		@RequestParam (value= "datatable[search]", required=false) String search
    		) throws Exception {
		
		String result = "";
		int pages = 1;
		
		List<Coaching> ls = new ArrayList<Coaching>();
		
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
			
			ls = coachingService.findByNameContainingIgnoreCaseOrAgency_CodeContainingIgnoreCaseOrStatusContainingIgnoreCase(search, search, search, pageable);
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonRes = mapper.writeValueAsString(ls);
			
			Long totalL = coachingService.countByNameContainingIgnoreCaseOrAgency_CodeContainingIgnoreCaseOrStatusContainingIgnoreCase(search, search, search);
					
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
	
	//AUDIT DATATABLE
	@RequestMapping(value = "/coachingAudit", method = RequestMethod.POST, produces = "application/json")
    public String coachingAuditList(
    		@RequestParam ("datatable[pagination][page]") int page,
    		@RequestParam ("datatable[pagination][perpage]") int perpage,
    		@RequestParam ("datatable[sort][sort]") String sort,
    		@RequestParam ("datatable[sort][field]") String sortField,
    		@RequestParam (value= "datatable[search]", required=false) String search
    		) throws Exception {
		
		String result = "";
		int pages = 1;
		
		List<Coaching> ls = new ArrayList<Coaching>();
		
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
			
			Collection<String> collection = new ArrayList<String>(Arrays.asList(new String[] { "4", "5", "6", "7" }));
			Sort sortObj = new Sort(new Sort.Order(sortDirection, sortField));
			Pageable pageable = new PageRequest(page-1, perpage, sortObj);

			ls = coachingService.findByStatusInAndNameContainingIgnoreCaseOrStatusInAndCreatedBy_NameContainingIgnoreCaseOrStatusInAndModifiedBy_NameContainingIgnoreCaseOrStatusInAndEvaluatedBy_NameContainingIgnoreCaseOrStatusInAndApprovedBy_NameContainingIgnoreCaseOrStatusInAndVerifiedBy_NameContainingIgnoreCase
					(collection, search, collection, search, collection, search, collection, search, collection, search, collection, search, pageable);

			ObjectMapper mapper = new ObjectMapper();
			String jsonRes = mapper.writeValueAsString(ls);
			
			Long totalL = coachingService.countByStatusInAndNameContainingIgnoreCaseOrStatusInAndCreatedBy_NameContainingIgnoreCaseOrStatusInAndModifiedBy_NameContainingIgnoreCaseOrStatusInAndEvaluatedBy_NameContainingIgnoreCaseOrStatusInAndApprovedBy_NameContainingIgnoreCaseOrStatusInAndVerifiedBy_NameContainingIgnoreCase
					(collection, search, collection, search, collection, search, collection, search, collection, search, collection, search);
					
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
	
	//REPORT AND COMPLETION DATATABLE
	@RequestMapping(value = "/coaching/report", method = RequestMethod.POST, produces = "application/json")
    public String coachingReport(
    		@RequestParam ("datatable[pagination][page]") int page,
    		@RequestParam ("datatable[pagination][perpage]") int perpage,
    		@RequestParam ("datatable[sort][sort]") String sort,
    		@RequestParam ("datatable[sort][field]") String sortField,
    		@RequestParam (value= "datatable[search]", required=false) String search
    		) throws Exception {
		
		String result = "";
		int pages = 1;
		
		List<Coaching> ls = new ArrayList<Coaching>();
		
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
			Collection<String> collection = new ArrayList<String>(Arrays.asList(new String[] { "4", "7" }));
			
			
			ls = coachingService.findByStatusInAndNameContainingIgnoreCaseOrStatusInAndAgency_CodeContainingIgnoreCase
					(collection, search, collection, search, pageable);
					
			ObjectMapper mapper = new ObjectMapper();
			String jsonRes = mapper.writeValueAsString(ls);
			
			Long totalL = coachingService.countByStatusInAndNameContainingIgnoreCaseOrStatusInAndAgency_CodeContainingIgnoreCase
					(collection, search, collection, search);
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
	@RequestMapping(value = "/coaching/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getCoaching(@PathVariable("id") String id) {
        logger.info("Fetching coaching with id {}", id);
        Coaching coaching = coachingService.findById(id);
        if (coaching == null) {
            logger.error("coaching with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("User with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Coaching>(coaching, HttpStatus.OK);
    }
	
	/*@RequestMapping(value = "/coaching/create", method = RequestMethod.POST)
    public Coaching createCoaching(@RequestBody Coaching coaching, UriComponentsBuilder ucBuilder) {
        logger.info("Creating User : {}", coaching);
        
        Coaching ch = new Coaching(); 
        
        coaching.setId(UUIDGenerator.getInstance().getId());
        coaching.setCreatedDate(new Date());
        ch = coachingService.save(coaching);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/coaching/create/{id}").buildAndExpand(coaching.getId()).toUri());
        return ch;
    }*/
	
	@RequestMapping(value = "/coaching/create", method = RequestMethod.POST, consumes="multipart/form-data")
    public Coaching createCert(@RequestPart(value="fileURS", required=false) MultipartFile fileURS, 
    		@RequestPart(value="fileSRS", required=false) MultipartFile fileSRS,
    		@RequestPart(value="fileSDS", required=false) MultipartFile fileSDS,
    		@RequestPart("info") Coaching coaching, 
    		UriComponentsBuilder ucBuilder) {

        byte [] byteURS = null;
        byte [] byteSRS = null;
        byte [] byteSDS = null;
        
        Coaching ch = new Coaching(); 
        
        try {
        	
        	if(coaching != null) {
        		
        		String ursId = UUIDGenerator.getInstance().getId();
        		String srsId = UUIDGenerator.getInstance().getId();
        		String sdsId = UUIDGenerator.getInstance().getId();
        		
                Filestorage ursStorage = new Filestorage();
                Filestorage srsStorage = new Filestorage();
                Filestorage sdsStorage = new Filestorage();
                
                try {
            		if (fileURS != null) {
            			byteURS = fileURS.getBytes();
            			
            			ursStorage.setId(UUIDGenerator.getInstance().getId());
            			ursStorage.setType(fileURS.getContentType());
            			ursStorage.setName(fileURS.getOriginalFilename());
            			ursStorage.setContent(byteURS);
            			ursStorage.setInstanceid(ursId);
            			
            			coaching.setUrs(ursStorage);
            			filestorageService.save(ursStorage);
        			}
            		
            		if (fileSRS != null) {
            			byteSRS = fileSRS.getBytes();
            			
            			srsStorage.setId(UUIDGenerator.getInstance().getId());
            			srsStorage.setType(fileSRS.getContentType());
            			srsStorage.setName(fileSRS.getOriginalFilename());
            			srsStorage.setContent(byteSRS);
            			srsStorage.setInstanceid(srsId);
            			
            			coaching.setSrs(srsStorage);
            			filestorageService.save(srsStorage);
        			}
            		
            		if (fileSDS != null) {
            			byteSDS = fileSDS.getBytes();
            			
            			sdsStorage.setId(UUIDGenerator.getInstance().getId());
            			sdsStorage.setType(fileSDS.getContentType());
            			sdsStorage.setName(fileSDS.getOriginalFilename());
            			sdsStorage.setContent(byteSDS);
            			sdsStorage.setInstanceid(sdsId);
            			
            			coaching.setUrs(sdsStorage);
            			filestorageService.save(sdsStorage);
        			}
            		
                    coaching.setId(UUIDGenerator.getInstance().getId());
                    coaching.setCreatedDate(new Date());
                    ch = coachingService.save(coaching);
        			
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
        	}
        }catch(Exception e) {
        	logger.error(e.getMessage());
        }
        
        return ch;
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/coaching/edit/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCoaching(@PathVariable("id") String id, @RequestBody Coaching coaching) {
        logger.info("Updating coaching with id {}", id);
        
        Coaching currentCoaching = new Coaching();
        
        
        if(id !=null && !"".equals(id)) {
        	currentCoaching = coachingService.findById(id);
            
            if(currentCoaching != null) {
            	
            	String status = coaching.getStatus();
            	
            	if("2".equals(status)) {
            		
            		currentCoaching.setStatus(coaching.getStatus());
            		coachingService.save(currentCoaching);
            		
            	}else if("3".equals(status)) {
            		
            		currentCoaching.setStatus(coaching.getStatus());
            		currentCoaching.setKelayakan(coaching.getKelayakan());
            		currentCoaching.setCoach_remarks(coaching.getCoach_remarks());
            		currentCoaching.setEvaluatedBy(coaching.getEvaluatedBy());
            		currentCoaching.setEvaluatedDate(new Date());
            		coachingService.save(currentCoaching);
            		
            	}else if("5".equals(status)) {
            		
            		currentCoaching.setStatus(coaching.getStatus());
            		currentCoaching.setAdmin_remarks(coaching.getAdmin_remarks());
            		currentCoaching.setApprovedBy(coaching.getApprovedBy());
            		currentCoaching.setApprovedDate(new Date());
            		coachingService.save(currentCoaching);
            		
            	}else if("6".equals(status)) {
            		
            		currentCoaching.setStatus(coaching.getStatus());
            		currentCoaching.setModifiedBy(coaching.getModifiedBy());
            		currentCoaching.setModifiedDate(new Date());
            		coachingService.save(currentCoaching);
            		
            	}else if("7".equals(status)) {
            		
            		currentCoaching.setStatus(coaching.getStatus());
            		currentCoaching.setModifiedBy(coaching.getModifiedBy());
            		currentCoaching.setModifiedDate(new Date());
            		coachingService.save(currentCoaching);
            		
            	}
            	
            }else {
            	 logger.error("Unable to update. Coaching with id {} not found.", id);
                 return new ResponseEntity(new CustomErrorType("Unable to upate. Coaching with id " + id + " not found."),
                         HttpStatus.NOT_FOUND);
            }
            
        }
        return new ResponseEntity<Coaching>(currentCoaching, HttpStatus.OK);
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/coaching/verified/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> verifiedCoaching(@PathVariable("id") String id, @RequestBody Coaching coaching) {
        logger.info("Updating coaching with id {}", id);
        
        Coaching currentCoaching = new Coaching();
        
        
        if(id !=null && !"".equals(id)) {
        	currentCoaching = coachingService.findById(id);
            
            if(currentCoaching != null) {
            	currentCoaching.setVerifiedBy(coaching.getVerifiedBy());
            	currentCoaching.setVerifiedDate(new Date());

                coachingService.save(currentCoaching);
            }else {
            	 logger.error("Unable to update. Coaching with id {} not found.", id);
                 return new ResponseEntity(new CustomErrorType("Unable to upate. Coaching with id " + id + " not found."),
                         HttpStatus.NOT_FOUND);
            }
            
        }
        return new ResponseEntity<Coaching>(currentCoaching, HttpStatus.OK);
    }
	
	/*@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/coaching/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> editCoaching(@PathVariable("id") String id, @RequestBody Coaching coaching) {
        logger.info("Updating coaching with id {}", id);
        
        Coaching currentCoaching = new Coaching();
        
        
        if(id !=null && !"".equals(id)) {
        	currentCoaching = coachingService.findById(id);
            
            if(currentCoaching != null) {
            	currentCoaching.setName(coaching.getName());
            	currentCoaching.setFrontend(coaching.getFrontend());
            	currentCoaching.setFrontendlevel(coaching.getFrontendlevel());
            	currentCoaching.setBackend(coaching.getBackend());
            	currentCoaching.setBackendlevel(coaching.getBackendlevel());
            	currentCoaching.setDatabase(coaching.getDatabase());
            	currentCoaching.setDatabaselevel(coaching.getDatabaselevel());
            	currentCoaching.setRemarks(coaching.getRemarks());
            	currentCoaching.setAdmin_remarks(coaching.getAdmin_remarks());
            	currentCoaching.setCoach_remarks(coaching.getCoach_remarks());
            	currentCoaching.setStarting_date(coaching.getStarting_date());
            	currentCoaching.setEnding_date(coaching.getEnding_date());
            	currentCoaching.setMandayReserved(coaching.getMandayReserved());
            	currentCoaching.setModifiedBy(coaching.getModifiedBy());
            	currentCoaching.setModifiedDate(new Date());

                coachingService.save(currentCoaching);
            }else {
            	 logger.error("Unable to update. Coaching with id {} not found.", id);
                 return new ResponseEntity(new CustomErrorType("Unable to upate. Coaching with id " + id + " not found."),
                         HttpStatus.NOT_FOUND);
            }
            
        }
        return new ResponseEntity<Coaching>(currentCoaching, HttpStatus.OK);
    }*/
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/coaching/update", method = RequestMethod.POST, consumes="multipart/form-data")
    public ResponseEntity<?> coachingUpdate(@RequestPart(value="fileURS", required=false) MultipartFile fileURS, 
    		@RequestPart(value="fileSRS", required=false) MultipartFile fileSRS,
    		@RequestPart(value="fileSDS", required=false) MultipartFile fileSDS,
    		@RequestPart("info") Coaching coaching,
    		UriComponentsBuilder ucBuilder) {

        byte [] byteURS = null;
        byte [] byteSRS = null;
        byte [] byteSDS = null;
        
        String id = coaching.getId();
        
        Coaching ch = new Coaching(); 
        Coaching currentCoaching = new Coaching();
        
        try {
        	
        	if(id !=null && !"".equals(id)) {
            	currentCoaching = coachingService.findById(id);
            	
            	if(currentCoaching != null) {
            		
                    Filestorage ursStorage = new Filestorage();
                    Filestorage srsStorage = new Filestorage();
                    Filestorage sdsStorage = new Filestorage();
                    
                    try {
                    	Filestorage urs = currentCoaching.getUrs();
                    	Filestorage srs = currentCoaching.getSrs();
                    	Filestorage sds = currentCoaching.getSds();
                    	
                    	String ursId = "";
                		String srsId = "";
                		String sdsId = "";
                		
                		if(urs!=null) {
                			ursId = urs.getId();
                		}else {
                			ursId = UUIDGenerator.getInstance().getId();
                		}
                		
                		if(srs!=null) {
                			srsId = srs.getId();
                		}else {
                			srsId = UUIDGenerator.getInstance().getId();
                		}
                		
                		if(sds!=null) {
                			sdsId = sds.getId();
                		}else {
                			sdsId = UUIDGenerator.getInstance().getId();
                		}
                    	
                		if (fileURS != null) {
                			byteURS = fileURS.getBytes();
                			
                			ursStorage.setId(ursId);
                			ursStorage.setType(fileURS.getContentType());
                			ursStorage.setName(fileURS.getOriginalFilename());
                			ursStorage.setContent(byteURS);
                			ursStorage.setInstanceid(id);
                			
                			currentCoaching.setUrs(null);
            			}
                		
                		if (fileSRS != null) {
                			byteSRS = fileSRS.getBytes();
                			
                			srsStorage.setId(srsId);
                			srsStorage.setType(fileSRS.getContentType());
                			srsStorage.setName(fileSRS.getOriginalFilename());
                			srsStorage.setContent(byteSRS);
                			srsStorage.setInstanceid(id);
                			
                			currentCoaching.setSrs(null);
            			}
                		
                		if (fileSDS != null) {
                			byteSDS = fileSDS.getBytes();
                			
                			sdsStorage.setId(sdsId);
                			sdsStorage.setType(fileSDS.getContentType());
                			sdsStorage.setName(fileSDS.getOriginalFilename());
                			sdsStorage.setContent(byteSDS);
                			sdsStorage.setInstanceid(id);
                			
                			currentCoaching.setSds(null);
            			}
                		
                		currentCoaching.setName(coaching.getName());
                    	currentCoaching.setFrontend(coaching.getFrontend());
                    	currentCoaching.setFrontendlevel(coaching.getFrontendlevel());
                    	currentCoaching.setBackend(coaching.getBackend());
                    	currentCoaching.setBackendlevel(coaching.getBackendlevel());
                    	currentCoaching.setDatabase(coaching.getDatabase());
                    	currentCoaching.setDatabaselevel(coaching.getDatabaselevel());
                    	currentCoaching.setRemarks(coaching.getRemarks());
                    	currentCoaching.setAdmin_remarks(coaching.getAdmin_remarks());
                    	currentCoaching.setCoach_remarks(coaching.getCoach_remarks());
                    	currentCoaching.setStarting_date(coaching.getStarting_date());
                    	currentCoaching.setEnding_date(coaching.getEnding_date());
                    	currentCoaching.setMandayReserved(coaching.getMandayReserved());
                    	currentCoaching.setModifiedBy(coaching.getModifiedBy());
                    	currentCoaching.setModifiedDate(new Date());
                    	
                    	coachingService.save(currentCoaching);
                    	
                    	//temporary deleted..will be added back at the end of method
                		/*try {
            				filestorageService.deleteByInstanceId(id);
            			}catch(Exception e) {
            				logger.error(e.getMessage());
            			}*/
                		
                		//add back
                        if(fileURS != null) {
                        	currentCoaching.setUrs(ursStorage);
                        }if(fileSRS != null) {
                        	currentCoaching.setSrs(srsStorage);
                        }if(fileSDS != null) {
                        	currentCoaching.setSds(sdsStorage);
                        }
                        
                        coachingService.save(currentCoaching);
                        
                        if(fileURS != null) {
                        	filestorageService.save(ursStorage);
                        }if(fileSRS != null) {
                        	filestorageService.save(srsStorage);
                        }if(fileSDS != null) {
                        	filestorageService.save(sdsStorage);
                        }
                        
            		} catch (IOException e) {
            			e.printStackTrace();
            		}
            	}
        	}else {
        		logger.error("Unable to update. Coaching with id {} not found.", id);
        		return new ResponseEntity(new CustomErrorType("Unable to upate. Coaching with id " + id + " not found."),
                        HttpStatus.NOT_FOUND);
        	}
        	
        	
        }catch(Exception e) {
        	logger.error(e.getMessage());
        	return new ResponseEntity(new CustomErrorType("Unable to upate. Coaching with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<Coaching>(currentCoaching, HttpStatus.OK);
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/coaching/edit2/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCoaching2(@PathVariable("id") String id, @RequestBody Coaching coaching) {
        logger.info("Updating coaching with id {}", id);
        
        Coaching currentCoaching = new Coaching();
        
        
        if(id !=null && !"".equals(id)) {
        	currentCoaching = coachingService.findById(id);
            
            if(currentCoaching != null) {
            	currentCoaching.setStatus(coaching.getStatus());
            	currentCoaching.setAdmin_remarks(coaching.getAdmin_remarks());
            	currentCoaching.setStarting_date(coaching.getStarting_date());
            	currentCoaching.setEnding_date(coaching.getEnding_date());
            	currentCoaching.setMandayUsed(coaching.getMandayUsed());
            	currentCoaching.setMandayReserved(coaching.getMandayReserved());
            	currentCoaching.setApprovedBy(coaching.getApprovedBy());
            	currentCoaching.setApprovedDate(new Date());

                coachingService.save(currentCoaching);
            }else {
            	 logger.error("Unable to update. Coaching with id {} not found.", id);
                 return new ResponseEntity(new CustomErrorType("Unable to upate. Coaching with id " + id + " not found."),
                         HttpStatus.NOT_FOUND);
            }
            
        }
        return new ResponseEntity<Coaching>(currentCoaching, HttpStatus.OK);
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/coaching/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCoaching(@PathVariable("id") String id) {
        logger.info("Fetching & Deleting Coaching with id {}", id);
 
        Coaching coaching = coachingService.findById(id);
        if (coaching == null) {
            logger.error("Unable to delete. Coaching with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        coachingService.deleteById(id);
        return new ResponseEntity<Coaching>(HttpStatus.NO_CONTENT);
    }
	
	public String getUserRole(String coachingId, String userId) {
		String roleResult = "ROLE_USER";
		
		boolean isCoach = coachingCoachService.existsByCoaching_IdAndCoach_Id(coachingId, userId);
		if(isCoach) {
			roleResult = "ROLE_COACH";
		}
		
		return roleResult;
	}
	
	@RequestMapping(value = "/coaching/role/{coachingid}/{userid}", method = RequestMethod.GET)
    public ResponseEntity<?> getFeedback(@PathVariable("userid") String userid, @PathVariable("coachingid") String coachingid) {
        logger.info("Fetching coaching with coachingid {}", coachingid);
        
        String role = getUserRole(coachingid, userid);
        
        return new ResponseEntity<String>(role, HttpStatus.OK);
    }
	
}
