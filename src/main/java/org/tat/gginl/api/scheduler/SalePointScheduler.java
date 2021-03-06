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
import org.tat.gginl.api.domains.SalePoint;
import org.tat.gginl.api.domains.services.FileService;
import org.tat.gginl.api.domains.services.SalePointService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Component
public class SalePointScheduler {
	
	@Autowired
	private SalePointService salePointService;
	
	@Value("${fileDir}")
	private String fileDir;
	
	@Scheduled(cron = "0 */5 * ? * *")
	 public void createSalePointFolder() throws Exception {
			
			Date startDate =FileService.resetStartDate(new Date());
			startDate = FileService.minusDays(startDate, 2);
			Date endDate = FileService.resetEndDate(new Date());
			
			List<SalePoint> salePointList = salePointService.findAll();
			
			List<Object> columnNameList = salePointService.findAllColumnName();
			List<Object[]> dataList = salePointService.findAllNativeObject();
			
			if(dataList.size()>0) {
				
				List<String> columnString = columnNameList.stream().map(String::valueOf).collect(Collectors.toList()); 
				
				
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
				
				File salePointsFile = new File("SalePoint.csv");
				FileWriter writer = new FileWriter(salePointsFile);
				
//				writesCsvFromBean(Paths.get(agentsFile.getPath()),agentList);
				columnString.add("[)~=_(]");
				CSVUtils.writeLine(writer, columnString, "[)!|;(]");
				
				for(Object[] object : dataList) {
					
					List<String> stringList = Stream.of(object).map(String::valueOf).collect(Collectors.toList());
					stringList.add("[)~=_(]");
					CSVUtils.writeLine(writer, stringList, "[)!|;(]");
				}
				
				FileOutputStream fos = new FileOutputStream("SalePoints.zip");
				ZipOutputStream zipOs = new ZipOutputStream(fos);

				FileService.writeToZipFile(salePointsFile, zipOs);

				zipOs.close();
				fos.close();
				writer.close();
				
				File toCheckSumFile = new File("SalePoints.zip");

				MessageDigest md5Digest = MessageDigest.getInstance("MD5");

				// Get the checksum
				String checksum =FileService.getFileChecksum(md5Digest, toCheckSumFile);
				File checksumFile = new File("SalePointsInfoChecksum".concat(".md5"));
				
				objectMapper.writeValue(checksumFile,checksum);
				String tempDir= fileDir.concat(":\\AceSharedFolder\\SalePointInfo").concat(FileService.getDateToString(new Date()));
				
				Path filePath = Paths.get(tempDir.concat("\\SalePoints.zip"));
				Files.createDirectories(filePath.getParent());
				
				Files.move(Paths.get(toCheckSumFile.getPath()),Paths.get(tempDir.concat("\\SalePoints.zip")),StandardCopyOption.REPLACE_EXISTING);
				Files.move(Paths.get(checksumFile.getPath()),Paths.get(tempDir.concat("\\SalePointsInfoChecksum.md5")),StandardCopyOption.REPLACE_EXISTING);
				
				
				Files.deleteIfExists(Paths.get("SalePoints.zip"));
				Files.deleteIfExists(Paths.get("SalePointsInfochecksum.md5"));
				Files.deleteIfExists(Paths.get(salePointsFile.getPath()));


			}
			
		}
		

}
