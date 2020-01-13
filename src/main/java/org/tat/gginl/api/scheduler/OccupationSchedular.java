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
import org.tat.gginl.api.domains.Occupation;
import org.tat.gginl.api.domains.repository.OccupationRepository;
import org.tat.gginl.api.domains.services.FileService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
@Component
public class OccupationSchedular {

	@Autowired
	private OccupationRepository occupationRepo;

	
	@Scheduled(cron = "0 0 0 * * ?")
	 public void createSalePointFolder() throws Exception {
			
			Date startDate =FileService.resetStartDate(new Date());
			startDate = FileService.minusDays(startDate, 2);
			Date endDate = FileService.resetEndDate(new Date());
			
			List<Occupation> occupationList = occupationRepo.findAll();
			
			if(occupationList.size()>0) {
				
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
				
				File salePointsFile = new File("Occupation.csv");
				
				FileService.writesCsvFromBean(Paths.get(salePointsFile.getPath()),occupationList);
				
				FileOutputStream fos = new FileOutputStream("Occupation.zip");
				ZipOutputStream zipOs = new ZipOutputStream(fos);

				FileService.writeToZipFile(salePointsFile, zipOs);

				zipOs.close();
				fos.close();
				
				File toCheckSumFile = new File("Occupation.zip");

				MessageDigest md5Digest = MessageDigest.getInstance("MD5");

				// Get the checksum
				String checksum =FileService.getFileChecksum(md5Digest, toCheckSumFile);
				File checksumFile = new File("OccupationInfoChecksum".concat(".md5"));
				
				objectMapper.writeValue(checksumFile,checksum);
				String tempDir= "D:\\AceApi\\OccupationInfo".concat(FileService.getDateToString(new Date()));
				
				Path filePath = Paths.get(tempDir.concat("\\Occupation.zip"));
				Files.createDirectories(filePath.getParent());
				
				Files.move(Paths.get(toCheckSumFile.getPath()),Paths.get(tempDir.concat("\\Occupation.zip")),StandardCopyOption.REPLACE_EXISTING);
				Files.move(Paths.get(checksumFile.getPath()),Paths.get(tempDir.concat("\\OccupationInfoChecksum.md5")),StandardCopyOption.REPLACE_EXISTING);
				
				
				Files.deleteIfExists(Paths.get(salePointsFile.getPath()));
				Files.deleteIfExists(Paths.get("Occupation.zip"));
				Files.deleteIfExists(Paths.get("OccupationInfochecksum.md5"));


			}
			
		}

}
