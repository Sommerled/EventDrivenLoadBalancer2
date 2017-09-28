package context;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ContextLoader {
	private static List<ConnectionContext> contexts;
	
	public static List<ConnectionContext> getLoadedContexts(){
		return contexts;
	}
	
	public static void loadContexts(String fileName, String keystorePath) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException{
		File inputFile = new File(fileName);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();

		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
        XPathExpression expr = xpath.compile("/" +  ContextNodes.CONNECTIONS + "/" + ContextNodes.CONNECTION);
        
        NodeList connections = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        
        for(int i = 0; i < connections.getLength(); i++){
        	Node n = connections.item(i);
        	processContextNode(n, keystorePath);
        }
	}
	
	public static ConnectionContext processContextNode(Node n, String keystorePath){
		ConnectionContext context = new ConnectionContext();
		NodeList children = n.getChildNodes();
		
		for(int i = 0; i < children.getLength(); i++){
			Node child = children.item(i);
			switch(child.getNodeName()){
			case ContextNodes.CONNECTIONS:
				NodeList connections = child.getChildNodes();
				for(int j = 0; j < connections.getLength();j++){
					if(ContextNodes.CONNECTION.equals(connections.item(j).getNodeName())){
						ConnectionContext childContext = processContextNode(connections.item(j), keystorePath);
						childContext.setListensFor(context);
					}
				}
				break;
			case ContextNodes.ALGORITHM:
				context.setAlgorithm(child.getChildNodes().item(0).getNodeValue());
				break;
			case ContextNodes.HOST:
				context.setHost(child.getChildNodes().item(0).getNodeValue());
				break;
			case ContextNodes.LISTENING:
				context.setListening("true".equals(child.getChildNodes().item(0).getNodeValue()));
				break;
			case ContextNodes.PORT:
				context.setPort(Integer.parseInt(child.getChildNodes().item(0).getNodeValue()));
				break;
			case ContextNodes.PROTOCOL:
				context.setProtocol(child.getChildNodes().item(0).getNodeValue());
				break;
			case ContextNodes.TIMEOUT:
				context.setTimeout(Integer.parseInt(child.getChildNodes().item(0).getNodeValue()));
				break;
			case ContextNodes.HTTP:
				context.setHttpContext(new HTTPContext());
				break;
			case ContextNodes.KEYSTORE:
				if(child.hasChildNodes()){
					NodeList keystore = child.getChildNodes();
					
					for(int j = 0; j < keystore.getLength(); j++){
						Node item = keystore.item(j);
						
						switch(item.getNodeName()){
							case ContextNodes.STORE:
								context.setKeystorePath(keystorePath + "\\" + item.getChildNodes().item(0).getNodeValue());
								break;
							case ContextNodes.PASSWORD:
								context.setKeystorePassword(item.getChildNodes().item(0).getNodeValue());
								break;
							case ContextNodes.TYPE:
								context.setKeystoreType(item.getChildNodes().item(0).getNodeValue());
								break;
							case ContextNodes.KEYALGORITHM:
								context.setKeyAlgorithm(item.getChildNodes().item(0).getNodeValue());
								break;
						}
					}
				}
				
				break;
			}
		}
		
		if(contexts == null){
			contexts = new ArrayList<ConnectionContext>();
		}
		
		contexts.add(context);
		return context;
		
	}
	
}
