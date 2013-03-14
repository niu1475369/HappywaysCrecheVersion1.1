package lee.won.hcv1.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.jdom2.JDOMException;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import lee.won.hcv1.abs.Person;
import lee.won.hcv1.exceptions.IdOverflowException;
import lee.won.hcv1.exceptions.InvalidInputException;
import lee.won.hcv1.impl.IdBackup;

/**
 * 
 * @author Won Lee
 * @version 1.0 b3012050
 * b151348:	File input and output were implemented by using Object-I/O-Stream
 * b220750:	preparation for IdBackup 
 * b242015: implement XML file handling method except readFromXML
 * b251321: readFromXML method was implemented
 * b270850: all read methods accept only File parameter except IdBackupXML
 * b3012050:hcv1.dat records List<Person> and Config File
 */
public class ObjectFileHandler {

	public ObjectFileHandler() {
		// TODO Auto-generated constructor stub
	}

	public void writeIntoFile(Object[] list) throws IOException{
		FileOutputStream fileOut = new FileOutputStream("Setting/hcv1.dat");
		ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
		objOut.writeObject(list);
		objOut.close();
	}
	
	public Object[] readFromFile() throws IOException, ClassNotFoundException{
		//There is way to read from inside the package but
		//no way to write into the package.
		//InputStream inst = this.getClass().getResourceAsStream("hcv1.dat");
		//ObjectInputStream objIn = new ObjectInputStream(inst);
		FileInputStream fileIn = new FileInputStream("Setting/hcv1.dat");
		ObjectInputStream objIn = new ObjectInputStream(fileIn);
		Object[] list = (Object[]) objIn.readObject();
		objIn.close();
		return list;
	}
	
	public void writeIntoXML(List<Person> list, File file){
		PersonsXMLwriter psxml = new PersonsXMLwriter();
		psxml.write(list, file);
	}
	
	public List<Person> readFromXML(File file) throws JDOMException, IOException, InvalidInputException, IdOverflowException{
		PersonsXMLreader psxml = new PersonsXMLreader();
		return psxml.read(file);
	}

	public List<Person> readFromXML(String path) throws JDOMException, IOException, InvalidInputException, IdOverflowException{
		File file = new File(path);
		return readFromXML(file);
	}
	
	public void writeIdBackupXML(List<IdBackup> list, File file){
		IdBuckupXMLwriter ibxml = new IdBuckupXMLwriter();
		ibxml.write(list, file);
	}
	
	public List<IdBackup> readIdBackupXML(File file) throws JDOMException, IOException{
		IdBuckupXMLreader ibxml = new IdBuckupXMLreader();
		return ibxml.read(file);
	}
	
	public List<IdBackup> readIdBackupXML(String path) throws JDOMException, IOException{
		File file = new File(path);
		return readIdBackupXML(file);
	}
}
