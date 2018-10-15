package my.com.fotia.osdec.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import my.com.fotia.osdec.references.model.City;
import my.com.fotia.osdec.references.model.Filestorage;
import my.com.fotia.osdec.references.service.FilestorageService;
import my.com.fotia.osdec.user.model.User;
import my.com.fotia.osdec.utilities.CustomErrorType;
import my.com.fotia.osdec.utilities.UUIDGenerator;

@RestController
@RequestMapping("/api/file")
public class FileStorageController {

	public static final Logger logger = LoggerFactory.getLogger(FileStorageController.class);
	
	@Autowired
	FilestorageService filestorageService;
	
	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes="multipart/form-data")
    public void createTraining(@RequestPart("file") MultipartFile file, @RequestPart("info") String id, UriComponentsBuilder ucBuilder) {

        byte [] byteArr = null;
        
        try {
        	 try {
         		if (file.getSize() > 0) {
         			byteArr = file.getBytes();
         			
         			Filestorage obj = new Filestorage();
         			obj.setId(UUIDGenerator.getInstance().getId());
         			obj.setContent(byteArr);
         			obj.setInstanceid(id);
         			obj.setName(file.getOriginalFilename());
         			obj.setType(file.getContentType());
         			
         			filestorageService.save(obj);
     			}
     			
     		} catch (IOException e) {
     			e.printStackTrace();
     		}
        	 
        }catch(Exception e) {
        	logger.error(e.getMessage());
        }
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/file/save/{id}").buildAndExpand(id).toUri());
    }
	
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Filestorage>> getUser(@PathVariable("id") String instanceId) {
		
		List<Filestorage> ls = new ArrayList<Filestorage>();
		
		try {
			//ls = filestorageService.findByInstanceid(instanceId);
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		
		return new ResponseEntity<List<Filestorage>>(ls, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/download/{id}", method = RequestMethod.GET)
    public ResponseEntity<Filestorage> getDownloadFile(@PathVariable("id") String instanceId) {
		
		Filestorage obj = new Filestorage();
		
		try {
			
			obj = filestorageService.findByInstanceid(instanceId);
			
		}catch(Exception e) {
			logger.error(e.getMessage());
		}
		
		return new ResponseEntity<Filestorage>(obj, HttpStatus.OK);
    }
}
