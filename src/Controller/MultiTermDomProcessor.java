package Controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MultiTermDomProcessor
{
	private Document document;
	
	public static void main (String args[]) throws Exception
	{
		try {
			MultiTermDomProcessor mtd = new MultiTermDomProcessor(args[0]);
			mtd.process();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public MultiTermDomProcessor(String file) 
			throws ParserConfigurationException, FileNotFoundException, SAXException, IOException 
	{
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		document = builder.parse(new FileInputStream(file));
	}
	
	
	public MTF process() {
		MTF mtf = new MTF();
		NodeList ns = document.getFirstChild().getChildNodes();
		for (int i=0; i<ns.getLength(); i++) {
			Node n = ns.item(i);
			if ("conceptGrp".equals(n.getNodeName())) {
				// System.out.println(n.getNodeName());
				this.processConcept(n, mtf.getConcepts());
			}
		}
		return mtf;
	}
	
	private void processConcept(Node conceptGrp, List<Concept> concepts)
	{
		Concept cpt = new Concept();
		concepts.add(cpt);

		NodeList ns = conceptGrp.getChildNodes();
		for (int i=0; i < ns.getLength(); i++) {
			Node n = ns.item(i);
			String nn = n.getNodeName();
			if ("descripGrp".equals(nn)) {
				// System.out.println(nn);
				this.processConceptDescriptionGrp(n, cpt);	
			}
			else if ("languageGrp".equals(nn)) {
				// System.out.println(nn);
				this.processLanguage(n, cpt.getLanguages());
			}
		}
	}
	
	private void processConceptDescriptionGrp(Node descripGrp, Concept concept)
	{
		NodeList ns = descripGrp.getChildNodes();
		for (int i=0; i < ns.getLength(); i++) {
			Node n = ns.item(i);
			String nn = n.getNodeName();
			if ("descrip".equals(nn)) {
				String type = n.getAttributes().getNamedItem("type").getNodeValue();
				String value = ns.item(1).getTextContent();
				if ("Subject Area".equals(type))
					concept.setSubjectArea(value);
				else if ("Categories".equals(type)) {
					concept.setCategories(value);
					//System.out.println("Cat:" + value);
				}
				// System.out.println(type);
				// System.out.println(value);
			}
		}
	}
	
	private void processLanguage(Node languageGrp, Map<String, Language> languages)
	{
		Language lang = new Language();
		
		NodeList ns = languageGrp.getChildNodes();
		for (int i=0; i < ns.getLength(); i++) {
			Node n = ns.item(i);
			String nn = n.getNodeName();
			if ("language".equals(nn)) { 
				String langId = n.getAttributes().getNamedItem("lang").getNodeValue();
				lang.setLanguage(langId);
				languages.put(langId, lang);
				// System.out.println(langId);
			}
			else if ("descripGrp".equals(nn)) {
				// System.out.println(nn);
				this.processLanguageDescriptionGrp(n, lang);
			}
			else if ("termGrp".equals(nn)) {
				// System.out.println(nn);
				this.processTerm(n, lang);
			}
		}
	}
	
	private void processLanguageDescriptionGrp(Node descripGrp, Language language)
	{
		NodeList ns = descripGrp.getChildNodes();
		for (int i=0; i < ns.getLength(); i++) {
			Node n = ns.item(i);
			String nn = n.getNodeName();
			if ("descrip".equals(nn)) {
				String type = n.getAttributes().getNamedItem("type").getNodeValue();
				String value = ns.item(1).getTextContent();
				if ("Definition".equals(type))
					language.setDefinition(value);
				else if ("Source".equals(type))
					language.setSource(value);;
				// System.out.println(type);
				// System.out.println(value);
			}
		}
	}

	private void processTerm(Node termGrp, Language language)
	{
		Term term = new Term();
		List<Term> terms = language.getTerms();
		terms.add(term);
		
		NodeList ns = termGrp.getChildNodes();
		for (int i=0; i < ns.getLength(); i++) {
			Node n = ns.item(i);
			String nn = ns.item(i).getNodeName();
			if ("term".equals(nn)) {
				String txt = n.getTextContent();
				term.setTerm(txt);
				// System.out.println(txt);
			}		
			else if ("descripGrp".equals(nn)) {
				// System.out.println(nn);
				this.processTermDescriptionGrp(n, term);			    	
			}
		}
	}
	
	private void processTermDescriptionGrp(Node descripGrp, Term term)
	{
		NodeList ns = descripGrp.getChildNodes();
		for (int i=0; i < ns.getLength(); i++) {
			Node n = ns.item(i);
			String nn = n.getNodeName();
			if ("descrip".equals(nn)) {
				String type = n.getAttributes().getNamedItem("type").getNodeValue();
				String value = ns.item(1).getTextContent();
				if ("Status".equals(type))
					term.setStatus(value);
				else if ("Lock Reason".equals(type)){
					//System.out.println(value);
					term.setLockReason(value);
				}
				else if ("Footnote 1".equals(type)) {
					// System.out.println("FN:" + value + ":/FN");
					term.setFootNote1(value);
				}
				else if ("Footnote 2".equals(type)) {
					// System.out.println("FN:" + value + ":/FN");
					term.setFootNote2(value);
				}
				// System.out.println(type);
				// System.out.println(value);
			}
		}
	}

}

