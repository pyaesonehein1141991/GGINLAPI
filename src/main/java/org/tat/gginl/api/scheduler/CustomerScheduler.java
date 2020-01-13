package org.tat.gginl.api.scheduler;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.tat.gginl.api.domains.Customer;
import org.tat.gginl.api.domains.services.CustomerService;
import org.tat.gginl.api.domains.services.FileService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Component
public class CustomerScheduler {
	
	
	@Autowired
	private CustomerService customerService;
	
	
	@Scheduled(cron = "0 0 0 * * ?")
	public void createCustomerFolder() throws Exception {
		
		Date startDate =FileService.resetStartDate(new Date());
		startDate =FileService.minusDays(startDate, 2);
		Date endDate =FileService.resetEndDate(new Date());
		
		List<Customer> customerList = customerService.findByRecorderCreatedDateBetweenOrRecorderUpdatedDateBetween(startDate, endDate);
		
		if(customerList.size()>0) {
			
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			
			File customerFile = new File("Customer.csv");
			
			FileService.writesCsvFromBean(Paths.get(customerFile.getPath()),customerList);
			
			FileOutputStream fos = new FileOutputStream("Customer.zip");
			ZipOutputStream zipOs = new ZipOutputStream(fos);

			FileService.writeToZipFile(customerFile, zipOs);

			zipOs.close();
			fos.close();
			
			File toCheckSumFile = new File("Customer.zip");

			MessageDigest md5Digest = MessageDigest.getInstance("MD5");

			// Get the checksum
			String checksum = FileService.getFileChecksum(md5Digest, toCheckSumFile);
			File checksumFile = new File("CustomerInfoChecksum".concat(".md5"));
			
			objectMapper.writeValue(checksumFile,checksum);
			String tempDir= "D:\\tempCustomer\\CustomerInfo".concat(FileService.getDateToString(new Date()));
			
			Path filePath = Paths.get(tempDir.concat("\\Customer.zip"));
			Files.createDirectories(filePath.getParent());
			
			Files.move(Paths.get(toCheckSumFile.getPath()),Paths.get(tempDir.concat("\\Customer.zip")),StandardCopyOption.REPLACE_EXISTING);
			Files.move(Paths.get(checksumFile.getPath()),Paths.get(tempDir.concat("\\CustomerInfoChecksum.md5")),StandardCopyOption.REPLACE_EXISTING);
			
			
			
			Files.deleteIfExists(Paths.get(customerFile.getPath()));
			Files.deleteIfExists(Paths.get("CustomerTest.zip"));
			Files.deleteIfExists(Paths.get("CustomerInfochecksum.md5"));


		}
		
	}
    
    
  
}
