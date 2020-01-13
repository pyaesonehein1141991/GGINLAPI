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
import org.tat.gginl.api.domains.SaleMan;
import org.tat.gginl.api.domains.repository.SaleManRepository;
import org.tat.gginl.api.domains.services.FileService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Component
public class SaleManSchedular {
	@Autowired
	private SaleManRepository saleManRepo;

	
	@Scheduled(cron = "0 0 0 * * ?")
	 public void createSalePointFolder() throws Exception {
			
			Date startDate =FileService.resetStartDate(new Date());
			startDate = FileService.minusDays(startDate, 2);
			Date endDate = FileService.resetEndDate(new Date());
			
			List<SaleMan> saleManList = saleManRepo.findAll();
			
			if(saleManList.size()>0) {
				
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
				
				File salePointsFile = new File("SaleMan.csv");
				
				FileService.writesCsvFromBean(Paths.get(salePointsFile.getPath()),saleManList);
				
				FileOutputStream fos = new FileOutputStream("SaleMan.zip");
				ZipOutputStream zipOs = new ZipOutputStream(fos);

				FileService.writeToZipFile(salePointsFile, zipOs);

				zipOs.close();
				fos.close();
				
				File toCheckSumFile = new File("SaleMan.zip");

				MessageDigest md5Digest = MessageDigest.getInstance("MD5");

				// Get the checksum
				String checksum =FileService.getFileChecksum(md5Digest, toCheckSumFile);
				File checksumFile = new File("SaleManInfoChecksum".concat(".md5"));
				
				objectMapper.writeValue(checksumFile,checksum);
				String tempDir= "D:\\AceApi\\SaleManInfo".concat(FileService.getDateToString(new Date()));
				
				Path filePath = Paths.get(tempDir.concat("\\SaleMan.zip"));
				Files.createDirectories(filePath.getParent());
				
				Files.move(Paths.get(toCheckSumFile.getPath()),Paths.get(tempDir.concat("\\SaleMan.zip")),StandardCopyOption.REPLACE_EXISTING);
				Files.move(Paths.get(checksumFile.getPath()),Paths.get(tempDir.concat("\\SaleManInfoChecksum.md5")),StandardCopyOption.REPLACE_EXISTING);
				
				
				Files.deleteIfExists(Paths.get(salePointsFile.getPath()));
				Files.deleteIfExists(Paths.get("SaleMan.zip"));
				Files.deleteIfExists(Paths.get("SaleManInfochecksum.md5"));


			}
			
		}
		


}
