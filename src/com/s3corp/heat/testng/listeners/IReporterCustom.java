package com.s3corp.heat.testng.listeners;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.FileUtils;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.xml.XmlSuite;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.s3corp.heat.config.Config;
import com.s3corp.heat.config.ConfigVar;
import com.s3corp.heat.core.appium.base.App;
import com.s3corp.heat.core.appium.base.AppiumCapabilities;
import com.s3corp.heat.data.Data;
import com.s3corp.heat.data.DataVar;
import com.s3corp.heat.log.Log;
import com.s3corp.heat.testng.report.ConstantReport;
import com.s3corp.heat.testng.var.ReportVar;

public class IReporterCustom implements IReporter{
	
	List<Map<String, Object>> lst = ReportVar.lst;
	private SimpleDateFormat sdf = new SimpleDateFormat(ConstantReport.DATE_FORMAT);
	private String outputDirectory;
	private long totalTime = 0;
	private String suiteName;
	
	private Document doc;
	private Element header;
	private Element body;
	private Element device;
	private Element app;
	
	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites,
			String outputDirectory) {
		this.outputDirectory = outputDirectory;
		
		ISuite iSuite = suites.get(0);
		suiteName = iSuite.getName();
		
		Map<String, ISuiteResult> suiteResults = iSuite.getResults();
	    for (ISuiteResult sr : suiteResults.values()) {
	        ITestContext tc = sr.getTestContext();
	        totalTime += tc.getEndDate().getTime() - tc.getStartDate().getTime(); 
	    }
	    
	    String xmlSummaryReport = outputDirectory + "\\" + ConstantReport.XML_OUTPUT_NAME;
	    String htmlOutput = outputDirectory + "\\" + ConstantReport.HTML_OUTPUT_NAME;
	    //Transfer HTML Test Case
	    generateTestCaseHTMLReport();
		
		//Create HTML Summary Report
		generateSummaryHTMLReport(xmlSummaryReport, htmlOutput);
		
		//Send mail report automation
		if(Boolean.valueOf(Config.getConfigProperties(ConfigVar.ENABLE_SEND_MAIL_AUTO))){
			try {
				sendMailReport(xmlSummaryReport);
			} catch (AddressException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
		
		//Open report HTML file
	    File htmlFile = new File(htmlOutput);
	    try {
			Desktop.getDesktop().browse(htmlFile.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Send automation report
	 * @throws MessagingException 
	 */
	private void sendMailReport(String xmlData) throws MessagingException{
		String htmlOutput = outputDirectory + "\\" + "text.html";
		createHmltFile(xmlData, htmlOutput, ConstantReport.XSL_SEND_MAIL_REPORT);
		
		String htmlMessageContent = "";
		try {
			htmlMessageContent = FileUtils.readFileToString(new File(htmlOutput));
		} catch (IOException e) {
			Log.error(e.getMessage());
		}
		
		//Get all information email
		String username = Data.getConfigProperties(DataVar.EMAIL_FROM);
		String password = Data.getConfigProperties(DataVar.EMAIL_PASSWORD);
		String to = Data.getConfigProperties(DataVar.EMAIL_TO);
		
		String host = Data.getConfigProperties(DataVar.EMAIL_HOST);
		String port = Data.getConfigProperties(DataVar.EMAIL_PORT);
		
		Properties props = null;
		if (props == null) {
			props = new Properties();

			// Use mail server s3corp: mail.s3corp.com.vn
			props.put("mail.smtp.host", host);

			// Use port 578
			props.put("mail.smtp.port", port);
			props.put("mail.smtp.user", username);
			props.put("mail.smtp.pwd", password);
			props.put("mail.smtp.auth", "true"); 
		}
        
    	Session session = Session.getInstance(props, null);
//      session.setDebug(true);
    	Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(username));
		
		msg.setSubject(Data.getConfigProperties(DataVar.EMAIL_SUBJECT));
		
		BodyPart messageBodyPath = new MimeBodyPart();
		messageBodyPath.setContent(htmlMessageContent, "text/html");

		Multipart multipart = new MimeMultipart();

		// Set text message part
		multipart.addBodyPart(messageBodyPath);

		// Part two is attachment
		messageBodyPath = new MimeBodyPart();
		File file = new File("log//logging.log");

		DataSource source = new FileDataSource(file);
		messageBodyPath.setDataHandler(new DataHandler(source));
		messageBodyPath.setFileName(file.getName());
		multipart.addBodyPart(messageBodyPath);

		// Send the complete message parts
		msg.setContent(multipart);
		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
		
		Transport transport = session.getTransport("smtp");
		transport.connect(host, 587, username, password);
		transport.sendMessage(msg, msg.getAllRecipients());

		transport.close();
		
	}
	
	/**
	 * Transfer To TestCase
	 */
	private void generateTestCaseHTMLReport() {
		String folder = outputDirectory + "\\" + ConstantReport.DIR_HTML_REPORT;
		File f = new File(folder);
		if(!f.exists())
			f.mkdir();
	
		for (Map<String, Object> map : lst) {
			String name = (String) map.get(ConstantReport.NAME_TC_KEY);
			String htmlOutput = folder + "\\" + name.replace(" ", "_") + ".html";
			String xslFile = ConstantReport.XSL_TESTCASE_REPORT;
			createHmltFile((String) map.get(ConstantReport.SRC_TC_KEY), htmlOutput, xslFile);
			
			//Put URL TestCase Into List
			map.put(ConstantReport.URL_TC, outputDirectory + "/" + ConstantReport.DIR_HTML_REPORT + "/" + name.replace(" ", "_") + ".html");
		}
		
	}

	/**
	 * Generate HTML Test Case
	 */
	public void generateSummaryHTMLReport(String xmlSummaryReport, String htmlOutput){
		
		//Generate XML Summary Report
		createXmlSummaryReport(xmlSummaryReport);
		
		//Generate HTML Summary Report
		createHTMLSummaryReport(xmlSummaryReport, htmlOutput);
	}
	
	/**
	 * Transformer HTML Summary Report
	 */
	private void createHTMLSummaryReport(String xmlSource, String htmlOutput){
		
		String xslFile = ConstantReport.XSL_TESTSUITE_REPORT;
		createHmltFile(xmlSource, htmlOutput, xslFile);
	}

	/**
	 * Generate XML Summary Report
	 */
	private void createXmlSummaryReport(String fileReport){
		
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.newDocument();

			
			Element rootElement = doc.createElement(ConstantReport.TEST_SUITE);
			doc.appendChild(rootElement);
			
			//Create XML Summary Report Header
			header = doc.createElement(ConstantReport.TEST_SUITE_HEADER);
			createXMSummaryHeaderReport();
			rootElement.appendChild(header);
			
			//Create XML Summary Device Information
			device = doc.createElement(ConstantReport.DEVICE);
			createXMLSummaryDeviceInfo();
			header.appendChild(device);
			
			//Create XMLSummary Application Information
			app = doc.createElement("App");
			createXMLSummaryAppInfo();
			header.appendChild(app);
			
			//Create XML Summary Report Body
			body = doc.createElement(ConstantReport.TEST_SUITE_BODY);
			createXMLBodyReport();
			rootElement.appendChild(body);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult resultOutput = new StreamResult(new File(fileReport));

			transformer.transform(source, resultOutput);
		} catch (ParserConfigurationException e) {
			Log.error(e.getMessage());
		}catch(TransformerException e){
			Log.error(e.getMessage());
		}
	}
	
	/**
	 * Create XML Summary Report about application information
	 */
	private void createXMLSummaryAppInfo() {
//		String name = App.getApp();
		String name = Data.getConfigProperties(DataVar.EMAIL_CONTENT_APP);
		
		String mdm = Data.getConfigProperties(DataVar.MAIL_CONTENT_MDM);
		
		Element mdmNode = doc.createElement("MDM");
		mdmNode.appendChild(doc.createTextNode(mdm));
		app.appendChild(mdmNode);
		
		Element appName = doc.createElement("Name");
		appName.appendChild(doc.createTextNode(name));
		app.appendChild(appName);
		
	}

	/**
	 * Create XML Summary Report about information device
	 */
	private void createXMLSummaryDeviceInfo() {
		AppiumCapabilities inforDevice = App.getInforDevice();
		if(inforDevice == null)
			return;
		
		Element deviceName = doc.createElement("Name");
		deviceName.appendChild(doc.createTextNode(inforDevice.getDeviceName()));
		device.appendChild(deviceName);
		
		Element os = doc.createElement("OS");
		os.appendChild(doc.createTextNode(inforDevice.getOsPlatform()));
		device.appendChild(os);
		
		Element osVersion = doc.createElement("Version");
		osVersion.appendChild(doc.createTextNode(inforDevice.getOsVersion()));
		device.appendChild(osVersion);
		
		Element udid = doc.createElement("UDID");
		udid.appendChild(doc.createTextNode(inforDevice.getUdid()));
		device.appendChild(udid);
	}

	/**
	 * Generate XML Body Summary Report
	 */
	private void createXMLBodyReport(){
		for (Map<String, Object> map : lst) {
			String src = (String) map.get(ConstantReport.SRC_TC_KEY);
			String name = (String) map.get(ConstantReport.NAME_TC_KEY);
			boolean rs = (Boolean) map.get(ConstantReport.RESULT_TC_KEY);
			String url = (String) map.get(ConstantReport.URL_TC);
			
			Element testCase = doc.createElement(ConstantReport.TEST_CASE);
			
			Element testName = doc.createElement(ConstantReport.TEST_NAME);
			testName.appendChild(doc.createTextNode(name));
			testCase.appendChild(testName);
			
			Element eleSrc = doc.createElement(ConstantReport.SRC);
			eleSrc.appendChild(doc.createTextNode(src));
			testCase.appendChild(eleSrc);
			
			Element eleRs = doc.createElement(ConstantReport.RESULT);
			eleRs.appendChild(doc.createTextNode(String.valueOf(rs)));
			testCase.appendChild(eleRs);
			
			Element eleUrl = doc.createElement(ConstantReport.URL_TC);
			eleUrl.appendChild(doc.createTextNode(url));
			testCase.appendChild(eleUrl);
			
			body.appendChild(testCase);
		}
	}
	
	/**
	 * Generate Header XML Summary Report
	 * @param header
	 */
	private void createXMSummaryHeaderReport(){
		
		//Create Node Reporter
		createNodeIntoHeader(ConstantReport.REPORTER, System.getProperty("user.name").toUpperCase());
		
		//Create Node Suite Name
		createNodeIntoHeader(ConstantReport.TEST_SUITE_NAME, suiteName);
		
		//Create Node Time Duration
		createNodeIntoHeader(ConstantReport.DURATION, String.valueOf(totalTime));
		
		//Create Node Date
		Date now = new Date();
		String strNow = sdf.format(now);
		createNodeIntoHeader("Date", strNow);
		
		int countPass = countCasePass(lst);
		int countFail = countCaseFail(lst);
		
		//Create Node Total TCs Passed
		String totalTcPassed = String.valueOf(countPass);
		createNodeIntoHeader(ConstantReport.PASSED, totalTcPassed);
		
		//Create Node Total TCs failed
		String totalTcFailed = String.valueOf(countFail);
		createNodeIntoHeader(ConstantReport.FAILED, totalTcFailed);
		
		if((countFail + countPass) != 0){
			float currentPass = countPass / (countFail + countPass) * 100;
			String currentPassed = String.valueOf(currentPass);
			createNodeIntoHeader(ConstantReport.CURRET_PASSED, currentPassed);
		}
	}
	
	/**
	 * Create Element Node into Header
	 */
	private void createNodeIntoHeader(String nodeName, String value){
		Element ele = doc.createElement(nodeName);
		ele.appendChild(doc.createTextNode(value));
		header.appendChild(ele);
	}
	
	/**
	 * Create HTML report file using JAXP
	 * @author toan.nguyenp
	 */
	private static void createHmltFile(String xmlSource, String htmlOutput, String xslFile){
		
		try {
			TransformerFactory factory = TransformerFactory.newInstance();
			Templates templates = factory.newTemplates(new StreamSource(IReporterCustom.class.getClassLoader().getResourceAsStream(xslFile)));
			
			Transformer transformer = templates.newTransformer();//factory.newTransformer(new StreamSource(IReporterCustom.class.getClassLoader().getResourceAsStream(xslFile)));
				
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			documentFactory.setNamespaceAware(true);

			
			DocumentBuilder builder = documentFactory.newDocumentBuilder();
			Document doc = builder.parse(xmlSource);
			DOMSource source = new DOMSource(doc);
			
			File fileOutput = new File(htmlOutput);
			StreamResult result = new StreamResult(fileOutput);
			
			transformer.transform(source, result);
		} catch (ParserConfigurationException e) {
			Log.error(e.getMessage());
		} catch(SAXException e){
			Log.error(e.getMessage());
		} catch(IOException e){
			Log.error(e.getMessage());
		} catch(TransformerException e){
			Log.error(e.getMessage());
		}
		
	}
	
	private int countCasePass(List<Map<String, Object>> maps){
		int count = 0;
		for (Map<String, Object> map : maps) {
			if ((Boolean) map.get(ConstantReport.RESULT_TC_KEY)) {
				count++;
			}
		}
		return count;
	}

	
	private int countCaseFail(List<Map<String, Object>> maps){
		return maps.size() - countCasePass(maps);
	}
}
