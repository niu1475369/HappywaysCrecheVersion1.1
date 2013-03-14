package lee.won.hcv1.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import lee.won.hcv1.abs.Person;
import lee.won.hcv1.impl.Child;
import lee.won.hcv1.impl.IdBackup;
import lee.won.hcv1.impl.Parent;

import org.jdom2.Comment;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.ProcessingInstruction;
import org.jdom2.output.XMLOutputter;

/**
 * 
 * @author Won Lee
 * @version 1.0 b242015
 * b242015:	basic methods are implemented (reference "Java & XML FOR DUMMIES")
 *
 */
public class PersonsXMLwriter {
	private Document doc;
	private Element rootElt;
	Date date = new Date();

	public PersonsXMLwriter() {
		// TODO Auto-generated constructor stub
	}


	public void write(List<Person> list, File file){
		makeRootElt();
		makeJDOMdoc();
		addProcessingInstruction();
		addComment();
		addAttribute();
		addElements(list);
		outputFile(file);
	}
	
	private void makeRootElt(){
		rootElt = new Element("Persons_XML");
	}
	
	private void makeJDOMdoc(){
		doc = new Document(rootElt);
	}
	
	private void addProcessingInstruction(){
		ProcessingInstruction pi = new ProcessingInstruction("xml-stylesheet","type=\"text/xsl\"");
		doc.addContent(pi);
	}
	
	private void addComment(){
		Comment comment = new Comment("ID Backup on "+date.toString());
		doc.addContent(comment);
	}
	
	private void addElements(List<Person> list){
		Element elt;
		rootElt.addContent("\n");
		for(Person ps: list){
			elt = new Element("Person");
			elt.setAttribute("Person_ID",String.valueOf(ps.getID()));
			elt.setAttribute("First_Name", ps.getFirstname());
			elt.setAttribute("Sur_Name", ps.getSurname());
			elt.setAttribute("Gender", ps.getGender());
			if(ps instanceof Parent){
				elt.setName("Parent");
			}else if(ps instanceof Child){
				addChildElement(elt,(Child)ps);
				elt.setName("Child");
			}
			rootElt.addContent(elt);
			rootElt.addContent("\n");
		}
	}
	
	private Element addChildElement(Element elt, Child child){
		elt.setAttribute("Fee", String.valueOf(child.getFee()));
		elt.setAttribute("Parent_ID", String.valueOf(child.getParentId()));
		return elt;
	}
	
	private void addAttribute(){
		
	}
	
	private void outputFile(File file){
		try{
			FileOutputStream out = new FileOutputStream(file);
			
			XMLOutputter outputter = new XMLOutputter();
			outputter.output(doc, out);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
