package my.com.fotia.osdec.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;


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
import my.com.fotia.osdec.general.manday.transaction.model.MandayTransaction;
import my.com.fotia.osdec.general.manday.transaction.service.MandayTransactionService;
import my.com.fotia.osdec.utilities.CustomErrorType;
import my.com.fotia.osdec.utilities.UUIDGenerator;

@RestController
@RequestMapping("/api")
public class MandayTransactionController {
	
public static final Logger logger = LoggerFactory.getLogger(MandayController.class);
	
	
	@Autowired
	MandayTransactionService mandayTransactionService;
	
	@RequestMapping(value = "/mandayTrans/getTrain", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<MandayTransaction>> trainList(){
		
		List<MandayTransaction> ls =  mandayTransactionService.findByTypeContainingIgnoreCase("Latihan");
		if(ls.isEmpty()) {
			return new ResponseEntity<List<MandayTransaction>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<MandayTransaction>>(ls, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/mandayTrans/getCapab", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<MandayTransaction>> capabList(){
		
		List<MandayTransaction> ls =  mandayTransactionService.findByTypeContainingIgnoreCase("Kepakaran");
		if(ls.isEmpty()) {
			return new ResponseEntity<List<MandayTransaction>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<MandayTransaction>>(ls, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/mandayTrans/getCoach", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<MandayTransaction>> coachList(){
		
		List<MandayTransaction> ls =  mandayTransactionService.findByTypeContainingIgnoreCase("Coaching");
		if(ls.isEmpty()) {
			return new ResponseEntity<List<MandayTransaction>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<MandayTransaction>>(ls, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/mandayTrans/getCert", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<MandayTransaction>> certList(){
		
		List<MandayTransaction> ls =  mandayTransactionService.findByTypeContainingIgnoreCase("Persijilan");
		if(ls.isEmpty()) {
			return new ResponseEntity<List<MandayTransaction>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<MandayTransaction>>(ls, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/mandayTrans/all", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<MandayTransaction>>mandayTransList(){
		
		List<MandayTransaction> list = new ArrayList<MandayTransaction>();
		
		list = mandayTransactionService.findAll();
		
		return new ResponseEntity<List<MandayTransaction>>(list, HttpStatus.OK);
		
	
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/mandayTrans/coachingEdit/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> updateCoachingTrans(@PathVariable("id") String id){
		
		MandayTransaction mandayTx = mandayTransactionService.findById(id);
		
		if(mandayTx == null) {
			logger.error("Unable to update. MandayTx with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to update. MandayTx with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
		}
		
		mandayTx.setManday(mandayTx.getManday()+1);
		mandayTransactionService.save(mandayTx);
		return new ResponseEntity<MandayTransaction>(mandayTx, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/mandayTrans/create", method = RequestMethod.POST)
//	public void action(@RequestParam("param") String[] param)
	public ResponseEntity<?> createMandayTrans(@RequestBody MandayTransaction mandayTransaction, UriComponentsBuilder ucBuilder){
		logger.info("Creating Manday Trans : {}", mandayTransaction);
		
		
		
//		Iterator<MandayTransaction> itr = ls.iterator();
//		while(itr.hasNext()) {
//			MandayTransaction obj = new MandayTransaction();
//			
//			obj = itr.next();
			mandayTransaction.setId(UUIDGenerator.getInstance().getId());
			
			mandayTransactionService.save(mandayTransaction);			
		
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/mandayTrans/{id}").buildAndExpand(mandayTransaction.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/mandayTrans/edit/", method = RequestMethod.POST)
	public void updateMandayTrans(@RequestBody List<MandayTransaction> ls){
		logger.info("Updating Manday Trans with id{}");
		
		
//		Manday currentObj = mandayService.findById(id);
//		
//		if(currentObj == null) {
//			
//			logger.error("Unable to update. Manday with id{} not found.", id);
//			return new ResponseEntity(new CustomErrorType("Unable to update. Manday with id " + id + " not found"),
//				HttpStatus.NOT_FOUND);
//		}
						
			Iterator<MandayTransaction> itr = ls.iterator();
			while(itr.hasNext()) {
				MandayTransaction obj = new MandayTransaction();
				
				obj = itr.next();
				
				
				mandayTransactionService.save(obj);
				
			}
			
		}

}
