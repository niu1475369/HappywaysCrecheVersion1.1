package lee.won.hcv1.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lee.won.hcv1.abs.Person;
import lee.won.hcv1.exceptions.IdOverflowException;
import lee.won.hcv1.exceptions.InvalidInputException;
import lee.won.hcv1.impl.Child;
import lee.won.hcv1.impl.IdBackup;
import lee.won.hcv1.impl.Parent;
import lee.won.hcv1.impl.PersonID;
import lee.won.hcv1.impl.PersonList;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * 
 * @author Won Lee
 * @version 0.9 b280025
 * b242015:	basic methods are implemented (reference "Java & XML FOR DUMMIES")
 * 			but it is required to implement method that adjust currentID on PersonID class
 * 			the class that method will be implemented is not decided yet
 * 			Therefore there might be some change occur somewhere
 * b280025: It can set proper CurrentID of PersonID class by cMax, pMax, cNow and pNow;
 *
 */
public class PersonsXMLreader {

	public PersonsXMLreader() {
		// TODO Auto-generated constructor stub
	}

	public List<Person> read(File file) throws JDOMException, IOException, InvalidInputException, IdOverflowException{
		SAXBuilder builder = new SAXBuilder(false);
		Document doc = builder.build(file);
		return RootElementTraverser(doc.getRootElement());
	}

	public List<Person> read(String filePath) throws JDOMException, IOException, InvalidInputException, IdOverflowException{
		File file = new File(filePath);
		return read(filePath);
	}
	
	private List<Person> RootElementTraverser(Element rootElement) throws InvalidInputException, IdOverflowException, JDOMException{
		List<Element> children = rootElement.getChildren();
		PersonList list = new PersonList();
		int pMax = 0;
		int pNow = PersonID.getParentCurrent();
		int cMax = 0;
		int cNow = PersonID.getChildCurrent();
		for(int i=0; i<children.size(); i++){
			Element element = children.get(i);
			try{
				int id = Integer.valueOf(element.getAttributeValue("Person_ID"));
				String fName = element.getAttributeValue("First_Name");
				String sName = element.getAttributeValue("Sur_Name");
				String gender = element.getAttributeValue("Gender");
				if(element.getName().equals("Parent")){
					Parent parent = new Parent(fName,sName,gender);
					pMax = (id>pMax)? id: pMax;
					parent.setID(id);
					list.add(parent);
				}else if(element.getName().equals("Child")){
					double fee = Double.valueOf(element.getAttributeValue("Fee"));
					int pId = Integer.valueOf(element.getAttributeValue("Parent_ID"));
					Child child = new Child(fName,sName,gender,fee,pId);
					cMax = (id>cMax)? id: cMax;
					child.setID(id);
					list.add(child);
				}
			}catch (Exception e){
				//Restore Person ID Current using cache.
				PersonID.resetId();
				PersonID.setChildCurrent(cNow);
				PersonID.setParentCurrent(pNow);
				throw new JDOMException("Invalid XML File");
			}
		}
		PersonID.resetId();
		PersonID.setChildCurrent(cMax);
		PersonID.setParentCurrent(pMax);
		return list;
	}
}
