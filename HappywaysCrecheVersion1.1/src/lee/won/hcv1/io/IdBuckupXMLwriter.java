package lee.won.hcv1.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.jdom2.Comment;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.ProcessingInstruction;
import org.jdom2.output.XMLOutputter;

import lee.won.hcv1.impl.IdBackup;
import lee.won.hcv1.impl.PersonID;

/**
 * 
 * @author Won Lee
 * @version 1.0 b280840
 * b242015:	basic methods are implemented (reference "Java & XML FOR DUMMIES")
 * b280840:	it records ID Length now => disabled
 *
 */
public class IdBuckupXMLwriter {
	private Document doc;
	private Element rootElt;
	Date date = new Date();

	public IdBuckupXMLwriter() {
		// TODO Auto-generated constructor stub
	}

	public void write(List<IdBackup> list, File file){
		makeRootElt();
		makeJDOMdoc();
		addProcessingInstruction();
		addComment();
		addAttribute();
		addElements(list);
		outputFile(file);
	}
	
	private void makeRootElt(){
		rootElt = new Element("ID_Backup_XML");
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
	
	private void addElements(List<IdBackup> list){
		Element elt;
		rootElt.addContent("\n");
		//elt = new Element("ID_Length");
		//elt.setAttribute("P_Length", String.valueOf(PersonID.getParentLength()));
		//elt.setAttribute("C_Length", String.valueOf(PersonID.getChildLength()));
		//rootElt.addContent(elt);
		//rootElt.addContent("\n");
		for(IdBackup ib: list){
			elt = new Element("Person_ID_Backup");
			elt.setAttribute("Old_ID",String.valueOf(ib.getOldId()));
			elt.setAttribute("New_ID", String.valueOf(ib.getNewId()));
			rootElt.addContent(elt);
			rootElt.addContent("\n");
		}
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
	
	private void outputFile(){
		File file = new File("Setting/ID_Backup.xml");
		outputFile(file);
	}
}
