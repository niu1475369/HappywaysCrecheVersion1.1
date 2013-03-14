package lee.won.hcv1.gui;

import java.io.File;

import javax.swing.ImageIcon;

import com.alee.extended.filefilter.DefaultFileFilter;
import com.alee.extended.filefilter.DirectoriesFilter;

/**
 * Web Look and Feel package cannot capable of JFileChoose and it has own one.
 * The WebFileChooser's UI is very good but it lack of function.
 * This is the one to cover it. This class checks extension of the file and 
 * it decides it can be chosen or not by accept method.
 * DefaultFileFilter is abstract method provided by the package.
 */
public class FileExtensionFilter extends DefaultFileFilter {
	private String extension;
	private static final ImageIcon ICON =
            new ImageIcon ( DirectoriesFilter.class.getResource ( "icons/file.png" ));

	public FileExtensionFilter(String extension) {
		// TODO Auto-generated constructor stub
		this.extension = extension.toLowerCase();
	}

	@Override
	/**
	 * @return true, then the file clicked on WebFileChooser can be chosen.
	 */
	public boolean accept(File arg0) {
		// TODO Auto-generated method stub
		if(arg0.getName().toLowerCase().endsWith("."+extension)){
			return true;
		}
		return false;
	}

	@Override
	/**
	 * @return the message shown in the ComboBox at the bottom of WebFileChooser
	 */
	public String getDescription() {
		// TODO Auto-generated method stub
		return extension + " File Only";
	}

	@Override
	/**
	 * @return the icon shown in the ComboBox at the bottom of WebFileChooser
	 */
	public ImageIcon getIcon() {
		// TODO Auto-generated method stub
		return ICON;
	}

}
