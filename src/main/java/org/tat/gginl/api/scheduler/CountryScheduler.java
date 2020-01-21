package org.tat.gginl.api.scheduler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.tat.gginl.api.common.CSVUtils;
import org.tat.gginl.api.domains.services.CountryService;
import org.tat.gginl.api.domains.services.FileService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Component
public class CountryScheduler {
	
	@Autowired
	private CountryService countryService;
	
	@Value("${fileDir}")
	private String fileDir;
	
	@Scheduled(cron = "0 */5 * ? * *")
	public void createBankFolder() throws Exception {
		
		Date startDate =FileService.resetStartDate(new Date());
		startDate =FileService.minusDays(startDate, 2);
		Date endDate =FileService.resetEndDate(new Date());
		
	//	List<Bank> agentList = bankService.findAll();
		

		List<Object> columnNameList = countryService.findAllColumnName();
		List<Object[]> dataList = countryService.findAllNativeObject();
		
		
		if(dataList.size()>0) {
			
			List<String> columnString = columnNameList.stream().map(String::valueOf).collect(Collectors.toList()); 
			
			
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			
			File agentsFile = new File("Countrys.csv");
			FileWriter writer = new FileWriter(agentsFile);
			
//			writesCsvFromBean(Paths.get(agentsFile.getPath()),agentList);
			columnString.add("[)~=_(]");
			CSVUtils.writeLine(writer, columnString, "[)!|;(]");
			
			for(Object[] object : dataList) {
				
				List<String> stringList = Stream.of(object).map(String::valueOf).collect(Collectors.toList());
				stringList.add("[)~=_(]");
				CSVUtils.writeLine(writer, stringList, "[)!|;(]");
			}
			
			FileOutputStream fos = new FileOutputStream("Countrys.zip");
			ZipOutputStream zipOs = new ZipOutputStream(fos);

			FileService.writeToZipFile(agentsFile, zipOs);

			zipOs.close();
			fos.close();
			writer.close();
			
			File toCheckSumFile = new File("Countrys.zip");

			MessageDigest md5Digest = MessageDigest.getInstance("MD5");

			// Get the checksum
			String checksum = FileService.getFileChecksum(md5Digest, toCheckSumFile);
			File checksumFile = new File("CountrysInfoChecksum".concat(".md5"));
			
			objectMapper.writeValue(checksumFile,checksum);
			String tempDir= fileDir.concat(":\\AceSharedFolder\\CountrysInfo").concat(FileService.getDateToString(new Date()));
			
			Path filePath = Paths.get(tempDir.concat("\\Countrys.zip"));
			Files.createDirectories(filePath.getParent());
			
			Files.move(Paths.get(toCheckSumFile.getPath()),Paths.get(tempDir.concat("\\Countrys.zip")),StandardCopyOption.REPLACE_EXISTING);
			Files.move(Paths.get(checksumFile.getPath()),Paths.get(tempDir.concat("\\CountrysInfoChecksum.md5")),StandardCopyOption.REPLACE_EXISTING);
			
			
			
			Files.deleteIfExists(Paths.get("Countrys.zip"));
			Files.deleteIfExists(Paths.get("CountrysInfoChecksum.md5"));
			Files.deleteIfExists(Paths.get(agentsFile.getPath()));


		}
		
	}

}
