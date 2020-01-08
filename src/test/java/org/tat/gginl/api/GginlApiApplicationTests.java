package org.tat.gginl.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.tat.gginl.api.domain.Customer;
import org.tat.gginl.api.services.CustomerService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

@SpringBootTest
class GginlApiApplicationTests {
	
	@Autowired
	private CustomerService customerService;

	@Test
	void contextLoads() {
	}
	
	public static <T> void writesCsvFromBean(Path path, List<T> objectList) throws Exception{
		Writer writer = new FileWriter(path.toString());
		
		StatefulBeanToCsv<Object> sbc = new StatefulBeanToCsvBuilder<>(writer)
				.withSeparator(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
				.build();
		
		sbc.write(objectList);
		writer.close();
		
	}
	
	public static void writeToZipFile(File file, ZipOutputStream zipStream) throws FileNotFoundException, IOException {

		File aFile = new File(file.getPath());
		FileInputStream fis = new FileInputStream(aFile);
		ZipEntry zipEntry = new ZipEntry(file.getPath());
		zipStream.putNextEntry(zipEntry);

		byte[] bytes = new byte[1024];
		int length;
		while ((length = fis.read(bytes)) >= 0) {
			zipStream.write(bytes, 0, length);
		}

		zipStream.closeEntry();
		fis.close();
	}

	
	public static Date resetStartDate(Date startDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	public static Date resetEndDate(Date endDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(endDate);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}
	
	public Date minusDays(Date date, int day) {
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().minusDays(day); 
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}
	
	
	@BeforeEach
	public void createCustomerFolder() throws Exception {
		
		Date startDate = resetStartDate(new Date());
		Date endDate = resetEndDate(new Date());
		
		List<Customer> customerList = customerService.findByRecorderCreatedDateBetweenOrRecorderUpdatedDateBetween(startDate, endDate);
		
		
		if(customerList.size()>0) {
			
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			
			File customerFile = new File("Customer.csv");
			
			writesCsvFromBean(Paths.get(customerFile.getPath()),customerList);
			
			FileOutputStream fos = new FileOutputStream("Customer.zip");
			ZipOutputStream zipOs = new ZipOutputStream(fos);

			writeToZipFile(customerFile, zipOs);

			zipOs.close();
			fos.close();
			
			File toCheckSumFile = new File("Customer.zip");

			MessageDigest md5Digest = MessageDigest.getInstance("MD5");

			// Get the checksum
			String checksum = getFileChecksum(md5Digest, toCheckSumFile);
			File checksumFile = new File("CustomerInfoChecksum".concat(".md5"));
			
			objectMapper.writeValue(checksumFile,checksum);
			String tempDir= "D:\\tempCustomer\\CustomerInfo".concat(getDateToString(new Date()));
			
			Path filePath = Paths.get(tempDir.concat("\\Customer.zip"));
			Files.createDirectories(filePath.getParent());
			
			Files.move(Paths.get(toCheckSumFile.getPath()),Paths.get(tempDir.concat("\\Customer.zip")),StandardCopyOption.REPLACE_EXISTING);
			Files.move(Paths.get(checksumFile.getPath()),Paths.get(tempDir.concat("\\CustomerInfoChecksum.md5")),StandardCopyOption.REPLACE_EXISTING);
			
			
			
			Files.deleteIfExists(Paths.get(customerFile.getPath()));
			Files.deleteIfExists(Paths.get("Test.zip"));
			Files.deleteIfExists(Paths.get("CustomerInfochecksum.md5"));


		}
		
	}
	
	private static String getFileChecksum(MessageDigest digest, File file) throws IOException {
		// Get file input stream for reading the file content
		FileInputStream fis = new FileInputStream(file);

		// Create byte array to read data in chunks
		byte[] byteArray = new byte[1024];
		int bytesCount = 0;

		// Read file data and update in message digest
		while ((bytesCount = fis.read(byteArray)) != -1) {
			digest.update(byteArray, 0, bytesCount);
		}
		

		// close the stream; We don't need it now.
		fis.close();

		// Get the hash's bytes
		byte[] bytes = digest.digest();

		// This bytes[] has bytes in decimal format;
		// Convert it to hexadecimal format
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		}

		// return complete hash
		return sb.toString();
	}
	
	public String getDateToString(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-YYYY-HH-mm");
		return simpleDateFormat.format(date);
	}


}
