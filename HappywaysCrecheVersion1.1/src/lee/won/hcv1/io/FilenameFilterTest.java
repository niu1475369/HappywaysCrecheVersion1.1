package lee.won.hcv1.io;

import java.io.File;
import java.io.FilenameFilter;

public class FilenameFilterTest implements FilenameFilter {

	public FilenameFilterTest() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean accept(File arg0, String arg1) {
		// TODO Auto-generated method stub
		File file = new File(arg0.toString()+File.separator	+arg1);
		System.out.println(file.toString());
		if(file.isDirectory()){
		//	return false;
		}
		return true;
	}

}
