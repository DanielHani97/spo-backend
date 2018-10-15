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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import my.com.fotia.osdec.general.grade.model.Grade;
import my.com.fotia.osdec.general.schema.model.Schema;
import my.com.fotia.osdec.general.schema.service.SchemaService;
import my.com.fotia.osdec.utilities.CustomErrorType;
import my.com.fotia.osdec.utilities.UUIDGenerator;

@RestController
@RequestMapping("/api")
public class SchemaController {
	
public static final Logger logger = LoggerFactory.getLogger(SchemaController.class);
	
	@Autowired
	SchemaService schemaService;
	
	@RequestMapping(value = "/schema", method = RequestMethod.POST, produces = "application/json")
    public String schemaList(
    		@RequestParam ("datatable[pagination][page]") int page,
    		@RequestParam ("datatable[pagination][perpage]") int perpage,
    		@RequestParam ("datatable[sort][sort]") String sort,
    		@RequestParam ("datatable[sort][field]") String sortField,
    		@RequestParam (value= "datatable[search]", required=false) String search
    		) throws Exception {
		
		String result = "";
		int pages = 0;
		
		List<Schema> ls = new ArrayList<Schema>();
		
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
			
			ls = schemaService.findByNameContainingIgnoreCaseOrStatusContainingIgnoreCase
					(search, search, pageable);
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonRes = mapper.writeValueAsString(ls);
			
			Long totalL = schemaService.countByNameContainingIgnoreCaseOrStatusContainingIgnoreCase
					(search, search);
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

	@RequestMapping(value = "schema/count", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Integer>> schemaCnList(){
		
			Long countall = schemaService.countByNameContainingIgnoreCaseOrStatusContainingIgnoreCase
					("", "");
			
			int count = 0;
			try {
				count = countall.intValue();
			} catch(Exception e) {}
			
			List<Integer> ls = new ArrayList<>();
			for(int i = 0; i < count+1 ; i++ ) {
				ls.add(i+1);
			}
			
		
		
		return new ResponseEntity<List<Integer>>(ls, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/schema/all", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Schema>> schemaList() {
		List<Schema> list = schemaService.findAll();
		if(list.isEmpty()){
            return new ResponseEntity<List<Schema>>(HttpStatus.NOT_FOUND);
        }
		return new ResponseEntity<List<Schema>>(list, HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/schema/edit/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getSchema(@PathVariable("id") String id) {
        logger.info("Fetching schema with id {}", id);
        Schema schema = schemaService.findById(id);
        if (schema == null) {
            logger.error("schema with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("schema with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Schema>(schema, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/schema/create", method = RequestMethod.POST)
    public ResponseEntity<?> createSchema(@RequestBody Schema schema, UriComponentsBuilder ucBuilder) {
        logger.info("Creating User : {}", schema);
 
        
        schema.setId(UUIDGenerator.getInstance().getId());
        
        schemaService.save(schema);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(schema.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/schema/edit/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateSchema(@PathVariable("id") String id, @RequestBody Schema schema) {
        logger.info("Updating Schema with id {}", id);
 
        Schema currentSchema = schemaService.findById(id);
        
        if(currentSchema == null) {
        	logger.error("Unable to update. Schema with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to update. Schema with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        			
        		
        } 
        
        currentSchema.setName(schema.getName());
        currentSchema.setSeniority(schema.getSeniority());
        currentSchema.setStatus(schema.getStatus());
        	
        	schemaService.save(currentSchema);
        	return new ResponseEntity<Schema>(currentSchema, HttpStatus.OK);
        	
        }
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/schema/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteSchema(@PathVariable("id") String id) {
        logger.info("Fetching & Deleting Schema with id {}", id);
 
        Schema schema = schemaService.findById(id);
        if (schema == null) {
            logger.error("Unable to delete. Schema with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. Schema with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        schemaService.deleteById(id);
        return new ResponseEntity<Schema>(HttpStatus.NO_CONTENT);
    }
}
