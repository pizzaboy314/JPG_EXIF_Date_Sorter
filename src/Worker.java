import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import com.drew.imaging.ImageMetadataReader;


public class Worker {
	private static JFileChooser fc;
	public ArrayList<File> files;
	public static String find;
	public static String replace;
	
	public static void main(String[] args) {
		File headDir = null;
		headDir = getPath();
		
//		ImageMetadataReader test;
	}
	
	public static File getPath(){
		File returnFile = null;
		File resultPath = new File(System.getProperty("user.dir"));
		fc = new JFileChooser(resultPath);
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File f = fc.getSelectedFile();

			if(f.isDirectory()){
				returnFile = f;
			}
		}
		
		return returnFile;
	}

}
