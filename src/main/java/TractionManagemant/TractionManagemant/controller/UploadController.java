package TractionManagemant.TractionManagemant.controller;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

//import TractionManagemant.TractionManagemant.repository.UploadRepository;
import TractionManagemant.TractionManagemant.model.*;
import TractionManagemant.TractionManagemant.repository.UploadRepository;
import TractionManagemant.TractionManagemant.service.UploadService;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.time.LocalDate;

import java.time.ZoneId;

import java.util.*;



@RestController
@CrossOrigin(origins="http://localhost:4200")
@RequestMapping("/transaction")
public class UploadController {
	private static Logger logger = LoggerFactory.getLogger(UploadController.class); 
	String reason=" - ";
	String dont=" - ";
	@Autowired
	UploadRepository uploadRepository;                        
	
    private BufferedReader reader;	
	private String line;
		
	@Autowired
	UploadService uploadService;
	
	//Uploading a file from the UI
	@PostMapping("/upload/{fileName}")
	public void GetUploadTransaction(@PathVariable (value="fileName") String filename) throws IOException  {
		
		logger.info("upload file is called");
	String fn="D:\\"+filename;
	logger.info("The file path is "+fn);
	
	try {
	reader = new BufferedReader(new FileReader(
	fn));
	line = reader.readLine(); 
	}
	catch(Exception e) {
		logger.info("could not find file");
	}

	while(line != null) {
	int flag=1;
	reason=" ";

	String a =line;

	 //Validation 1 ------------------------- Transaction Reference
	String tempone= a.substring(0,12);
	String transactionRef=tempone.trim();
	//String one =a.substring(56,68);
	System.out.println(transactionRef);
	
	if(transactionRef.matches("^[a-zA-Z0-9]*$") && transactionRef.length()==12)
	{
		logger.info("Transaction ref id is valid");
	}
	else 
	{
	flag=0;
	logger.info("Transaction ref id is invalid");
      reason= reason + " Transaction Reference ";
	}

	//validation 2 ---------------- current date
	String day= "03";
	String month="08";
	String year="2020";
	String date= "03-08-2020";
	
	ZoneId z = ZoneId.of( "Asia/Kolkata" );
	LocalDate today = LocalDate.now( z );
	String tdate=today.toString();
	String[] checkDate=tdate.split("-");
	
	if(year.equals(checkDate[0]) && month.equals(checkDate[1]) && day.equals(checkDate[2])) {

		logger.info("valid date");
	}
	else {
	flag=0;
		logger.info(" invalid date");
		reason= reason + "   Date ";
	}
	

    //Validation 3 ------------------------- Payer Name
	String temp=a.substring(20, 55);
	
	String payer=temp.trim();
	System.out.println(payer);
	
	if(payer.matches("^[a-zA-Z0-9]*$")) {
		logger.info("String(Payer) is alpha numeric");
	}
	else {
		logger.info("String(Payer) is invalid");
      reason= reason + "  Payer Name ";

	flag=0;
	}
	
	 //Validation 4 ------------------------- Payer Amount
	String tempfour=a.substring(55, 67);
	String four=tempfour.trim();
	System.out.println(four);
	
	if(four.matches("^[a-zA-Z0-9]*$") && four.length()==12) {
		logger.info("String is valid");
	}
	else {
	flag=0;
		logger.info("String is invalid");
     reason= reason + "  Payer Amount ";
	}

	
	 //Validation 5 ------------------------- Payee Name
	String temp1=a.substring(67, 102);
	
	String payee=temp1.trim();
	System.out.println(payee);
	
	if(payee.matches("^[a-zA-Z0-9]*$")) {
		logger.info("String is alpha numeric");
	}
	else {
		logger.info("String is invalid");
	flag=0;
      reason= reason + "   Payee Name ";
	}

	 //Validation 6 ------------------------- Payee Amount
	String tempsix=a.substring(102, 114);
	String six=tempsix.trim();
	System.out.println(six);
	
	if(six.matches("^[a-zA-Z0-9]*$") && six.length()==12) {
		logger.info("String is valid");
	}
	else {
	flag=0;
		logger.info("String is invalid");
reason= reason + "   Payer Account ";
	}

	 //Validation 3 ------------------------- Amount 
	String seven1 =a.substring(114, 124);
	String seven2 =a.substring(125);
	String nseven1 = seven1.trim();
	String nseven2= seven2.trim();
	String p =  nseven1.concat(".");
	String amount = p.concat(nseven2);
	int x=Integer.parseInt(nseven1);
	int y=Integer.parseInt(nseven2);
	System.out.println(x);
	System.out.println(y);

	if(x<0 || nseven2.length()!=2) {
	flag=0;
	logger.info("String is invalid");
      reason= reason + "  Amount ";

	}
	else 
	{
		logger.info("String is valid");
	}

    //Dumping the transactions into the invalid database based on the validation and unique transaction ref
	if(flag==0)
	{

	  
	   if(uploadRepository.findByTransactionRef(transactionRef)==null) {
		   UploadModel transaction= new UploadModel(date,  payer,  payee,amount, 
					"invalid",  transactionRef, reason);
		   logger.info("dump to invalid database");
				uploadRepository.save(transaction);
			 
	   }	
	   else
	   {
		   logger.info("transaction with this Reference id exists");
	   }

	}
	else
	{
	
	 if(uploadRepository.findByTransactionRef(transactionRef)==null) {
		 UploadModel transaction= new UploadModel(date,  payer,  payee, amount, 
					"valid",  transactionRef, dont);
		 logger.info("dump to valid database");
			uploadRepository.save(transaction);
		 
	 }
	 else
	   {
		   logger.info("transaction with this Reference id exists");
	   }
	
	}

	try {
	line=reader.readLine();
	} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	
	}
	}

	try
	{
	reader.close();
	logger.info("close file");
	} 
	catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	}

	archivefile(filename);

	}

	//function for archiving the file that is uploaded
	public void archivefile(String fn) throws IOException
	{
	logger.info("Archive file is called");
	String newfn="D:\\archive\\archived"+fn;
	fn="D:\\"+fn;
	Path archivefile = Files.move 
	       (Paths.get(fn),  
	       Paths.get(newfn)); 
	 
	       if(archivefile != null) 
	       { 
	    	   logger.info("File renamed and moved successfully"); 
	       } 
	       else
	       { 
	    	   logger.info("Failed to move the file"); 
	       } 
	}

	
	//Display transactions by status 
	  @GetMapping("/getStatus/{status}")
	    public List<UploadModel> getByStatus(
	    		@PathVariable(value="status") String status)
	  {
		  logger.info("get transactions by status is called ");
		   return uploadRepository.findByStatus(status);
	  }
	  
	  //Display all valid and invalid transactions
	  @GetMapping("/get")
	    public List<UploadModel> getAll()
	  {
		  logger.info("get all transactions");
		   return uploadRepository.findAll();
	  }
	  
	 
	  
	  
	  //generation a feed file in the local host after uploading
	  @GetMapping("/generatefeedfile")
	  public int generateFeedFile() throws IOException 
	  {
		  List <UploadModel> l= new ArrayList<UploadModel>();
		  logger.info("  generate feed file is called");
		  l= uploadRepository.findByFeedgenerated("false");
		  String text="";
		  int cnt=0;
		  Iterator itr = l.iterator();
	      while(itr.hasNext()) {
	    	  UploadModel um=(UploadModel) itr.next();
	    	  if(um.getStatus().equals("valid"))
	    	  {
	    		  cnt++;
	    		  text=um.feedformat();
	    		  BufferedWriter out = new BufferedWriter( 
	    	                new FileWriter("D:\\feedfile.txt", true)); 
	    	         out.write(text); 
	    	         out.close(); 	
	    	      update(um);
	    	  }
	    	  
	      }
	      logger.info(cnt+" transactions were added to the feed file");
	      return cnt;
	  }
	  
	  //updating the transactions after generating the feed file
	  public UploadModel update(UploadModel transaction) {
		  logger.info("updating transaction after generating feed");
			UploadModel um = uploadRepository.findByTransactionRef(transaction.getTransactionRef());
			um.setAmount(um.getAmount());
			um.setDate(um.getDate());
			um.setFeedgenerated("true");
			um.setPayee(um.getPayee());
			um.setPayer(um.getPayer());
			um.setStatus(um.getStatus());
			um.setTransactionRef(um.getTransactionRef());
			
			return uploadRepository.save(um);
				//return um;
	  }
	  
	  // downloading the generated feed file
	  @GetMapping("/downloadfeed/{feedgenerated}")
	  public ResponseEntity<byte[]> downloadFile(String feedgenerated) throws Exception{
		  List<UploadModel> em = uploadService.getFeedGenerated(feedgenerated);
		  ObjectMapper obj = new ObjectMapper();
		  String json = obj.writeValueAsString(em);
		  byte[] isr= json.getBytes();
		  String fileName = "em.json";
		  HttpHeaders respHeaders =new HttpHeaders();
		  respHeaders.setContentLength(isr.length);
		  respHeaders.setContentType(new MediaType("text", json));
		  respHeaders.setCacheControl("must-revalidate, post-check=0, precheck=0");
		  respHeaders.set(HttpHeaders.CONTENT_DISPOSITION, " attachment; filename=" + fileName);
		  return new ResponseEntity<byte[]>(isr, respHeaders, HttpStatus.OK);
		  
	  }
	  
	  
	  
	
	
	

}