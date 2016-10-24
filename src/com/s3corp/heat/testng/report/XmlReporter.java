package com.s3corp.heat.testng.report;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.s3corp.heat.config.Config;
import com.s3corp.heat.config.ConfigVar;
import com.s3corp.heat.log.Log;
import com.s3corp.heat.testng.var.ReportVar;

public class XmlReporter {
	
	private static Document doc;
	private static Element rootElement;
	
	/**
	 * Create XML node
	 * @param nodeName
	 * @param value
	 */
	public static Element appentNodeIntoRoot(String nodeName, String value){
		Element ele = doc.createElement(nodeName);
		if(value != null){
			ele.appendChild(doc.createTextNode(value));
		}
		rootElement.appendChild(ele);
		return ele;
	}
	
	/**
	 * Create XML file test case report
	 * @param tc
	 */
	public static void createXmlTestCaseReport(TestCaseXmlReport tc){
		
		String xmlReportDir = Config.getConfigProperties(ConfigVar.REPORT_OUTPUT_DIR) + "\\" + ConstantReport.DIR_XML_REPORT;
		File dir = new File(xmlReportDir);
		if(!dir.exists()){
			dir.mkdir();
		}
		
		//Create XML report file name
		String testName = tc.getName();
		String fileReport = xmlReportDir + "\\" + testName.replace(" ", "_") + ".xml";

		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.newDocument();
			
			//Create node root TestCase
			rootElement = doc.createElement(ConstantReport.TEST_CASE);
			doc.appendChild(rootElement);
			
			//Create node test name
			appentNodeIntoRoot(ConstantReport.TEST_NAME, testName);
			
			//Create node result
			appentNodeIntoRoot(ConstantReport.RESULT, String.valueOf(tc.isResult()));
			
			//Create node time start
			appentNodeIntoRoot(ConstantReport.START_TIME, tc.getTimeStart());
			
			//Create node time end
			appentNodeIntoRoot(ConstantReport.END_TIME, tc.getTimeEnd());
			
			//Create node time duration
			appentNodeIntoRoot(ConstantReport.DURATION, String.valueOf(tc.getTimeDurration() / 1000) + "s");
			
			//Create node test step
			Element testStep = appentNodeIntoRoot(ConstantReport.TEST_STEPS, null);
			
			List<TestStepXmlReport> ts = tc.getTestStep();
			for (TestStepXmlReport obj : ts) {
				Element ele = doc.createElement(ConstantReport.STEP);
				
				//Create attr line
				Attr attrLine = doc.createAttribute(ConstantReport.LINE);
				attrLine.setValue(String.valueOf(obj.getTestStep().getLine()));
				ele.setAttributeNode(attrLine);
				
				//Create attr action name
				Attr attrAction = doc.createAttribute(ConstantReport.DO_ACTION);
				attrAction.setValue(obj.getTestStep().getActionName());
				ele.setAttributeNode(attrAction);
				
				//Create attr argument
				Attr attrArgment = doc.createAttribute(ConstantReport.AGRUMENT);
				attrArgment.setValue(obj.getTestStep().getArgument());
				ele.setAttributeNode(attrArgment);
				
				testStep.appendChild(ele);
				
				//Create node result test step
				Element eleResult = doc.createElement(ConstantReport.RESULT);
				eleResult.appendChild(doc.createTextNode(String.valueOf(obj.isResult())));
				ele.appendChild(eleResult);
				
				//Create node time duration
				Element eleDuration = doc.createElement(ConstantReport.DURATION);
				eleDuration.appendChild(doc.createTextNode(String.valueOf(obj.getTimeDuration())));
				ele.appendChild(eleDuration);
				
				//Create node error message
				String message = obj.getErr();
				if(message != null){
					Element eleError = doc.createElement(ConstantReport.ERROR_MESSAGE);
					eleError.appendChild(doc.createTextNode(message));
					ele.appendChild(eleError);
				}
				
				//Create node image URL
				String img = obj.getImg();
				if(img != null){
					Element eleImg = doc.createElement(ConstantReport.IMAGE_URL);
					eleImg.appendChild(doc.createTextNode(img));
					ele.appendChild(eleImg);
				}
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult resultOutput = new StreamResult(new File(fileReport));

			transformer.transform(source, resultOutput);
			
			//Create hast map test case reporter
			ReportVar.lst.add(generateHastMapTestCaseReporter(tc.getName(), fileReport, tc.isResult()));
			
		} catch (ParserConfigurationException e) {
			Log.error(e.getMessage());
		} catch(TransformerException e){
			Log.error(e.getMessage());
		}
	}
	
	public static Map<String, Object> generateHastMapTestCaseReporter(String name, String src, boolean rs){
		Map<String, Object> testCaseReporter = new HashMap<String, Object>();
		testCaseReporter.put(ConstantReport.NAME_TC_KEY, name);
		testCaseReporter.put(ConstantReport.SRC_TC_KEY, src);
		testCaseReporter.put(ConstantReport.RESULT_TC_KEY, rs);
		return testCaseReporter;
	}
}
