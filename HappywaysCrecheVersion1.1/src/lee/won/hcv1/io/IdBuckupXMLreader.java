package lee.won.hcv1.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import lee.won.hcv1.impl.IdBackup;
import lee.won.hcv1.impl.PersonID;

/**
 * 
 * @author Won Lee
 * @version 1.0 b280830
 * b242015:	basic methods are implemented (reference "Java & XML FOR DUMMIES")
 * b280830: it check element name, only matched element will be processed.
 *
 */
public class IdBuckupXMLreader {

	public IdBuckupXMLreader() {
		// TODO Auto-generated constructor stub
	}
	
	public List<IdBackup> read(File file) throws JDOMException, IOException{
		SAXBuilder builder = new SAXBuilder(false);
		Document doc = builder.build(file);
		return RootElementTraverser(doc.getRootElement());
	}

	public List<IdBackup> read(String filePath) throws JDOMException, IOException{
		File file = new File(filePath);
		return read(file);
	}
	
	/**
	 * 
	 * @param rootElement
	 * @return return list only there was no exception. 
	 * @throws JDOMException 
	 * 
	 */
	private List<IdBackup> RootElementTraverser(Element rootElement) throws JDOMException{
		List<Element> children = rootElement.getChildren();
		List<IdBackup> list = new ArrayList<IdBackup>();
		//int pLen = PersonID.getParentLength();
		//int cLen = PersonID.getChildLength();
		try{
			for(int i=0; i<children.size(); i++){
				Element element = children.get(i);
				if(element.getName().equals("Person_ID_Backup")){
					int oldId;
					int newId;
						oldId = Integer.valueOf(element.getAttribute("Old_ID").getValue());
						newId = Integer.valueOf(element.getAttribute("New_ID").getValue());
						IdBackup ib = new IdBackup(oldId,newId);
						list.add(ib);
				//}else if(element.getName().equals("ID_Length")){
					//pLen = Integer.valueOf(element.getAttributeValue("P_Length"));
					//cLen = Integer.valueOf(element.getAttributeValue("C_Length"));
				}
			}
		}catch (NullPointerException e){
			throw new JDOMException("Invalid XML File, which has no Old_ID atribute and/or " + 
					"New_ID attribute in it.");
		}
		//PersonID.setParentLength(pLen);
		//PersonID.setChildLength(cLen);
		return list;
	}

}
