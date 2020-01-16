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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.tat.gginl.api.domains.Township;
import org.tat.gginl.api.domains.repository.TownshipRepository;
import org.tat.gginl.api.domains.services.FileService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Component
public class TowshipSchedular {
	@Autowired
	private TownshipRepository townshipRepo;

	@Value("${fileDir}")
	private String fileDir;
	
	
	@Scheduled(cron = "0 0 0 * * ?")
	 public void createSalePointFolder() throws Exception {
			
			Date startDate =FileService.resetStartDate(new Date());
			startDate = FileService.minusDays(startDate, 2);
			Date endDate = FileService.resetEndDate(new Date());
			
			List<Township> townshipList = townshipRepo.findAll();
			
			if(townshipList.size()>0) {
				
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
				
				File salePointsFile = new File("Township.csv");
				
				FileService.writesCsvFromBean(Paths.get(salePointsFile.getPath()),townshipList);
				
				FileOutputStream fos = new FileOutputStream("Township.zip");
				ZipOutputStream zipOs = new ZipOutputStream(fos);

				FileService.writeToZipFile(salePointsFile, zipOs);

				zipOs.close();
				fos.close();
				
				File toCheckSumFile = new File("Township.zip");

				MessageDigest md5Digest = MessageDigest.getInstance("MD5");

				// Get the checksum
				String checksum =FileService.getFileChecksum(md5Digest, toCheckSumFile);
				File checksumFile = new File("SalePointsInfoChecksum".concat(".md5"));
				
				objectMapper.writeValue(checksumFile,checksum);
				String tempDir= fileDir.concat(":\\AceApi\\TownshipInfo").concat(FileService.getDateToString(new Date()));
				
				Path filePath = Paths.get(tempDir.concat("\\Township.zip"));
				Files.createDirectories(filePath.getParent());
				
				Files.move(Paths.get(toCheckSumFile.getPath()),Paths.get(tempDir.concat("\\Township.zip")),StandardCopyOption.REPLACE_EXISTING);
				Files.move(Paths.get(checksumFile.getPath()),Paths.get(tempDir.concat("\\TownshipInfoChecksum.md5")),StandardCopyOption.REPLACE_EXISTING);
				
				
				Files.deleteIfExists(Paths.get(salePointsFile.getPath()));
				Files.deleteIfExists(Paths.get("Township.zip"));
				Files.deleteIfExists(Paths.get("TownshipInfochecksum.md5"));


			}
			
		}
		


}
