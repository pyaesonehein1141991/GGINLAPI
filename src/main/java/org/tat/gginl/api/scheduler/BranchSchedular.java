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
import org.tat.gginl.api.domains.Branch;
import org.tat.gginl.api.domains.repository.BranchRepository;
import org.tat.gginl.api.domains.services.FileService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Component
public class BranchSchedular {
	

	@Autowired
	private BranchRepository branchRepo;
	
	@Value("${fileDir}")
	private String fileDir;
	
	@Scheduled(cron = "0 0 0 * * ?")
	public void createAgentFolder() throws Exception {
		
		Date startDate =FileService.resetStartDate(new Date());
		startDate =FileService.minusDays(startDate, 2);
		Date endDate =FileService.resetEndDate(new Date());
		
		List<Branch> branchList = branchRepo.findAll();
		
		if(branchList.size()>0) {
			
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			
			File agentsFile = new File("Agents.csv");
			
			FileService.writesCsvFromBean(Paths.get(agentsFile.getPath()),branchList);
			
			FileOutputStream fos = new FileOutputStream("Branch.zip");
			ZipOutputStream zipOs = new ZipOutputStream(fos);

			FileService.writeToZipFile(agentsFile, zipOs);

			zipOs.close();
			fos.close();
			
			File toCheckSumFile = new File("Branch.zip");

			MessageDigest md5Digest = MessageDigest.getInstance("MD5");

			// Get the checksum
			String checksum = FileService.getFileChecksum(md5Digest, toCheckSumFile);
			File checksumFile = new File("BranchInfoChecksum".concat(".md5"));
			
			objectMapper.writeValue(checksumFile,checksum);
			String tempDir= fileDir.concat(":\\AceApi\\BranchInfo").concat(FileService.getDateToString(new Date()));
			
			Path filePath = Paths.get(tempDir.concat("\\Branch.zip"));
			Files.createDirectories(filePath.getParent());
			
			Files.move(Paths.get(toCheckSumFile.getPath()),Paths.get(tempDir.concat("\\Branch.zip")),StandardCopyOption.REPLACE_EXISTING);
			Files.move(Paths.get(checksumFile.getPath()),Paths.get(tempDir.concat("\\BranchInfoChecksum.md5")),StandardCopyOption.REPLACE_EXISTING);
			
			
			
			Files.deleteIfExists(Paths.get(agentsFile.getPath()));
			Files.deleteIfExists(Paths.get("BranchTest.zip"));
			Files.deleteIfExists(Paths.get("BranchInfochecksum.md5"));


		}
		
	}
    

}
