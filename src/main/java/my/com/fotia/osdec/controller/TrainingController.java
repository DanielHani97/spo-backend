package my.com.fotia.osdec.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import my.com.fotia.osdec.attendance.model.Attendance;
import my.com.fotia.osdec.attendance.service.AttendanceService;
import my.com.fotia.osdec.general.agency.model.Agency;
import my.com.fotia.osdec.general.agency.model.Company;
import my.com.fotia.osdec.training.model.Training;
import my.com.fotia.osdec.training.service.TrainingService;
import my.com.fotia.osdec.trainingTx.model.TrainingTx;
import my.com.fotia.osdec.trainingTx.service.TrainingTxService;
import my.com.fotia.osdec.user.model.User;
import my.com.fotia.osdec.utilities.CustomErrorType;
import my.com.fotia.osdec.utilities.UUIDGenerator;

@RestController
@RequestMapping("/api")
public class TrainingController {
	
public static final Logger logger = LoggerFactory.getLogger(TrainingController.class);
	
	@Autowired
	TrainingService trainingService;
	
	@Autowired
	TrainingTxService trainingTxService;
	
	@Autowired
	AttendanceService attendanceService;
	
	//	-----------USER CALENDAR------------
	@RequestMapping(value = "/training/getuserAll/{id}", method = RequestMethod.POST , produces = "application/json" )
	public String trainingList(@PathVariable("id") String id) throws Exception{
		String result = "";
		List<TrainingTx> ls2 = trainingTxService.findByUser_Id(id);
		
//		TimeZone tz = TimeZone.getTimeZone("Asia/Kuala_Lumpur"); 
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"); 
//	    df.setTimeZone(tz); 
	    
	    Iterator <TrainingTx> iterator = ls2.iterator();
	    
	    result +="[";
	    
	    while(iterator.hasNext()) {
	    	Training train = iterator.next().getTraining();
	    	DateTime dateEnd = new DateTime(df.format(train.getEndDate()));
		      
		      dateEnd = dateEnd.plusDays(1);
		      Date d2 = dateEnd.toDate();
	    				    
			    result += "{";
				result += "\"allDay\": true,";
				result += "\"type\": \"training\","; 
				result += "\"title\": "+"\""+train.getTitle()+"\",";
				result += "\"id\": "+"\""+train.getId()+"\",";				
				result += "\"description\": "+"\""+train.getPlace()+"\",";				
				result += "\"start\": "+"\""+train.getStartDate()+"\",";
				result += "\"endDate\": "+"\""+train.getEndDate()+"\",";
		        result += "\"end\": "+"\""+dateEnd+"\",";  
				result += "\"technology\": "+"\""+train.getTechnology().getName()+"\",";
				result += "\"level\": "+"\""+train.getLevel()+"\",";
				result += "\"editable\": false";
				result += "},";		
			
			
		
	    }
			result = removeLastChar(result);
			result += "]";
		
		return result;
	}
	
	//	-----------ADMIN CALENDAR------------
	@RequestMapping(value = "/training/getall", method = RequestMethod.POST, produces = "application/json")
	public String trainingListing() throws Exception{
		
		String result ="";
		
		List <Training> ls = new ArrayList<Training>();
		ls = trainingService.findAll();
		
//		TimeZone tz = TimeZone.getTimeZone("Asia/Kuala_Lumpur");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
//		df.setTimeZone(tz);
		
		Iterator<Training> iterator = ls.iterator();		
		result += "[";
		
		while (iterator.hasNext()) {
		
			Training obj = new Training();
			
			obj = iterator.next();
			DateTime dateEnd = new DateTime(df.format(obj.getEndDate()));
		      
		      dateEnd = dateEnd.plusDays(1);
		      Date d2 = dateEnd.toDate();
		    
			    result += "{";
				result += "\"allDay\": true,";
				result += "\"type\": \"training\",";
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
	
	@RequestMapping(value = "/traininggetall", method = RequestMethod.GET)
	public ResponseEntity<List<Training>> trainingList() {
		List<Training> list = trainingService.findAll();
		if(list.isEmpty()){
            return new ResponseEntity<List<Training>>(HttpStatus.NOT_FOUND);
        }
		return new ResponseEntity<List<Training>>(list, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/trainingall", method = RequestMethod.GET)
	public ResponseEntity<List<Training>> trainingLs() {
		
		//DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		List<Training> ls = new ArrayList<Training>();
		ls = trainingService.findByStatusAndStartDateGreaterThanEqual("1", date);
		
		if(ls.isEmpty()){
            return new ResponseEntity<List<Training>>(HttpStatus.NOT_FOUND);
        }
		return new ResponseEntity<List<Training>>(ls, HttpStatus.OK);
	}
	
		// LAPORAN KEHADIRAN
		  @RequestMapping(value = "/training/laporan/{id}", method = RequestMethod.GET, produces = "application/json")
		    public String getReport(@PathVariable("id") String id) throws Exception {
		    		
			String result = "";
			Training tr = new Training();
			
			tr = trainingService.findById(id);
		    
		    TimeZone tz = TimeZone.getTimeZone("Asia/Kuala_Lumpur");
		    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
		    df.setTimeZone(tz);    
		    
		    result += "[";		    			
			
			      result += "{";
			      result += "\"title\": "+"\""+tr.getTitle()+"\",";
			      result += "\"id\": "+"\""+tr.getId()+"\",";
			      result += "\"laporan\": "+"[";
			      
			      List<TrainingTx> tu = trainingTxService.findByTraining_idAndStatus(id, "3");
			      Iterator<TrainingTx> iterate = tu.iterator();
			      while(iterate.hasNext()) {
			        TrainingTx tx = new TrainingTx();
			        tx = iterate.next();
		          
			      result += "{";
			      
			      User user = tx.getUser();

		          result += "\"peserta\": "+"\""+user.getName()+"\",";	
		          
		          String jawatan = "";
	        	          	
			      if (!(user.equals(null))) {
			    	  String position = user.getPosition();
			      	if (position==null) {
			      		jawatan = "-";
			      	} else {
			      		jawatan = position;
			      	}

			      } else {

			      	jawatan = "-";

			      }
			      
			      result += "\"jawatan\": "+"\""+jawatan+"\",";
		          
		          String agensi = "";
	        	  String type = user.getType();
	        	  
		          if (!(user.equals(null))) {

		        		if (type.equals("GOV")) {
		        			Agency agency = user.getAgency();
		        			if (agency==null) {
		        				agensi = "-";
		        				
		        			} else {
		        				String code = agency.getCode();
		        				if (!(code.equals(null))) {
		        					agensi = code;
		        				} else {
		        					agensi = "-";
		        				}
		        			}
		        		}

		        		else {
		        			Company company = user.getCompany();
		        			if(!(company==null)) {
		        				String name = company.getName();
		        				if(!(name.equals(null))) {
		        					agensi = name;
		        				} else {
		        					agensi = "-";
		        				}
		        			} else {
		        				agensi = "-";
		        			}
		        		}

		        	} else {

		        		agensi = "-";

		        	}
		          
		          result += "\"agensi\": "+"\""+agensi+"\",";
		          
		          DateTime dateStart = new DateTime(df.format(tr.getStartDate()));
		          DateTime dateEnd = new DateTime(df.format(tr.getEndDate()));
		          result += "\"report\": "+"[";
		          int days = Days.daysBetween(dateStart, dateEnd).getDays()+1;
		          for (int i=0; i < days; i++) {
		            DateTime d = dateStart.withFieldAdded(DurationFieldType.days(), i);
		            
		            d = d.plusHours(8);
		            Date d2 = d.toDate();
		            
		            result += "{";
		            
		            Calendar myDate = Calendar.getInstance();
		            myDate.setTime(d2);
		            int dow = myDate.get (Calendar.DAY_OF_WEEK);
		            
		            String day="";
		            
		            if(dow == Calendar.MONDAY) {
		              day = "Isnin";
		            }else if(dow == Calendar.TUESDAY) {
		              day = "Selasa";
		            }else if(dow == Calendar.WEDNESDAY) {
		              day = "Rabu";
		            }else if(dow == Calendar.THURSDAY) {
		              day = "Khamis";
		            }else if(dow == Calendar.FRIDAY) {
		              day = "Jumaat";
		            }else if(dow == Calendar.SATURDAY) {
		              day = "Sabtu";
		            }else if(dow == Calendar.SUNDAY) {
		              day = "Ahad";
		            }
		            
		            SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy");
		            String strDate = dt1.format(d2);
		            
		          result += "\"date\": "+"\""+strDate+" ("+day+") "+"\",";
		          List<Attendance> existAttendance = new ArrayList<Attendance>();
		          existAttendance = attendanceService.findByUser_IdAndDateAndInstanceId(tx.getUser().getId(), d2 , tx.getId());
		          if(existAttendance.isEmpty()) {
		            result += "\"kehadiran\": \"Tidak Hadir\"";
		          }else {
		            result += "\"kehadiran\": \"Hadir\"";
		          }
		          result += "},";
		          
		          }
		        result = removeLastChar(result);
		        result += "]";
		          result += "},";
		        
		      }
		      result = removeLastChar(result);
		      result += "]";
		      result += "},";
		      
		    result = removeLastChar(result);
		    result += "]";
		    
		    
		        return result;
		    }
	
	  @RequestMapping(value = "/trainingCoachAttend/all/{id}/{id2}", method = RequestMethod.POST, produces = "application/json")
	    public String trainingTranscList(@PathVariable("id") String id,@PathVariable("id2") String id2) throws Exception {
			
			String result = "";
			
			Training ls = new Training();
			ls = trainingService.findById(id);
							
			TimeZone tz = TimeZone.getTimeZone("Asia/Kuala_Lumpur");
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
			df.setTimeZone(tz);
			
			
			result += "[";
						
				Training train = new Training();
				train = ls;
				DateTime dateStart = new DateTime(df.format(train.getStartDate()));
				DateTime dateEnd = new DateTime(df.format(train.getEndDate()));
				
				int days = Days.daysBetween(dateStart, dateEnd).getDays()+1;
				for (int i=0; i < days; i++) {
				    DateTime d = dateStart.withFieldAdded(DurationFieldType.days(), i);
				    d = d.plusHours(8);

				    Date d2 = d.toDate();
				    List<Attendance> existAttendance = new ArrayList<Attendance>();
				    existAttendance = attendanceService.findByUser_IdAndDateAndInstanceId(id2, d2 , train.getId());
				    
				    result += "{";
					result += "\"allDay\": true,";
					result += "\"title\": "+"\""+train.getTitle()+"\",";
					result += "\"id\": "+"\""+train.getId()+"\",";				
					result += "\"description\": "+"\""+train.getPlace()+"\",";
					result += "\"start\": "+"\""+d+"\",";	
					if (existAttendance.isEmpty()) {
						result += "\"isExist\": false"+",";
						result += "\"borderColor\": \"#716ACA\""+",";
						result += "\"className\": \"m-fc-event--solid-info m-fc-event--light\""+",";
					}else {
						result += "\"isExist\": true"+",";
						result += "\"borderColor\": \"#34BFA3\""+",";
				        result += "\"className\": \"m-fc-event--solid-success m-fc-event--light\""+","; 
					}
					result += "\"editable\": false";
					result += "},";		

			}
			result = removeLastChar(result);
			result += "]";
			
			
	        return result;
	    }
		  
	@RequestMapping(value = "/training", method = RequestMethod.POST, produces = "application/json")
    public String trainingList (
		@RequestParam ("datatable[pagination][page]") int page,
		@RequestParam ("datatable[pagination][perpage]") int perpage,
		@RequestParam ("datatable[sort][sort]") String sort,
		@RequestParam ("datatable[sort][field]") String sortField,
		@RequestParam (value= "datatable[search]", required=false) String search
		) throws Exception {
		
		String result = "";
		int pages = 0;
		
		List<Training> ls = new ArrayList<Training>();
				
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
			
			ls = trainingService.findByTitleContainingIgnoreCaseOrTechnology_NameContainingIgnoreCaseOrDurationContainingIgnoreCaseOrPlaceContainingIgnoreCaseOrLevelContainingIgnoreCaseOrStatusContainingIgnoreCase
					(search, search, search, search, search, search, pageable);
		
			ObjectMapper mapper = new ObjectMapper();
			String jsonRes = mapper.writeValueAsString(ls);
			
			Long totalL = trainingService.countByTitleContainingIgnoreCaseOrTechnology_NameContainingIgnoreCaseOrDurationContainingIgnoreCaseOrPlaceContainingIgnoreCaseOrLevelContainingIgnoreCaseOrStatusContainingIgnoreCase
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
	@RequestMapping(value = "/training/edit/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getTraining(@PathVariable("id") String id) {
        logger.info("Fetching training with id {}", id);
        Training training = trainingService.findById(id);
        if (training == null) {
            logger.error("training with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("training with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Training>(training, HttpStatus.OK);
    }	
	
	@RequestMapping(value = "/training/create", method = RequestMethod.POST, consumes="multipart/form-data")
    public Training createTraining(@RequestPart(value="avatar", required=false) MultipartFile file, @RequestPart("info") Training training, UriComponentsBuilder ucBuilder) {
        logger.info("Creating Training : {}", training);
        
        Training tr = new Training();
        byte [] byteArr = null;
        
		TimeZone tz = TimeZone.getTimeZone("Asia/Kuala_Lumpur");
        
        /*if (userService.isUserExist(user)) {
            logger.error("Unable to create. A User with name {} already exist", user.getName());
            return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " + 
            user.getName() + " already exist."),HttpStatus.CONFLICT);
        }*/
        try {
        	training.setId(UUIDGenerator.getInstance().getId());
            training.setCreated_by(training.getCreated_by());
            training.setCreated_date(new Date());
            
            try {
            	if(file != null) {
            		if (file.getSize() > 0) {
            			byteArr = file.getBytes();
        				// Byte[]
        				//String filename = file.getOriginalFilename();// Get attached
        				// logo name
        				training.setImage(byteArr);
        			}
            	}
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
            
            tr = trainingService.save(training);
        }catch(Exception e) {
        	logger.error(e.getMessage());
        }
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(training.getId()).toUri());
        return tr;
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/training/edit", method = RequestMethod.POST, consumes="multipart/form-data")
    public ResponseEntity<?> updateTraining(@RequestPart(value="avatar", required=false) MultipartFile file, @RequestPart("info") Training training) {
        Training currentTraining = new Training();
        byte [] byteArr = null;
        
        if (training != null) {
        	
        	String id = training.getId();
        	logger.info("Updating Training with id {}", id);
        	
        	currentTraining = trainingService.findById(id);
        	
        	if (currentTraining !=null) {
        		currentTraining.setTitle(training.getTitle());
        		currentTraining.setTechnology(training.getTechnology());
                currentTraining.setCategory(training.getCategory());
                currentTraining.setDuration(training.getDuration());
                currentTraining.setPlace(training.getPlace());
                currentTraining.setRemark(training.getRemark());
                currentTraining.setLevel(training.getLevel());
                currentTraining.setStartDate(training.getStartDate());
                currentTraining.setEndDate(training.getEndDate());
                currentTraining.setStatus(training.getStatus());
                currentTraining.setModified_date(new Date());
                currentTraining.setLimitation(training.getLimitation());
                currentTraining.setModified_by(training.getModified_by());                
                try {
                	if(file != null) {
                		if (file.getSize() > 0) {
                			byteArr = file.getBytes();
                			currentTraining.setImage(byteArr);
            			}
                	}
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
                
        		trainingService.save(currentTraining);
        		
        	}else {
        		
            logger.error("Unable to update. Training with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to update. Training with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        	}
        }
        
        return new ResponseEntity<Training>(currentTraining, HttpStatus.OK);
    }
        
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/training/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteTraining(@PathVariable("id") String id) {
        logger.info("Fetching & Deleting Training with id {}", id);
 
        Training training= trainingService.findById(id);
        List<TrainingTx> trainingTx= trainingTxService.findByTraining_id(id);
       
        if (training == null) {

            logger.error("Unable to delete. Training with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. Training with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
            
        }else if( trainingTx.isEmpty()) {
        	trainingService.deleteByMandayTransactionId(id);
            trainingService.deleteByTrainingCoachId(id);
            //trainingService.deleteByTrainingTxId(id);
            trainingService.deleteById(id);
            return new ResponseEntity<Training>(HttpStatus.NO_CONTENT);
        }else {
        	logger.error("Unable to delete. Training tx exist");
        	return new ResponseEntity(new CustomErrorType("Unable to delete. Training tx exist"),HttpStatus.NOT_FOUND);
        }

    }
	
	public String removeLastChar(String str) {
	    if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == ',') {
	        str = str.substring(0, str.length() - 1);
	    }
	    return str;
}
	
}
