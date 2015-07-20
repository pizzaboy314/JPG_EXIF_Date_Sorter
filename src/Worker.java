import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import org.apache.commons.io.FilenameUtils;

import com.drew.imaging.ImageMetadataReader;


public class Worker {
	private static JFileChooser fc;
	public static ArrayList<File> files;
	public static String find;
	public static String replace;
	
	public static void main(String[] args) {
		File currDir = null;
		currDir = getPath();
		files = getJPGs(currDir);
		printFilenames();
		
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
	public static ArrayList<File> getJPGs (File dir){
		ArrayList<File> list = new ArrayList<File>();
		File[] allFiles = dir.listFiles();
		for(File f : allFiles){
			if(!f.isDirectory() && FilenameUtils.getExtension(f.getAbsolutePath()).equalsIgnoreCase("jpg")){
				list.add(f);
			}
		}
		
		return list;
	}
	public static void printFilenames(){
		for(File f : files){
			System.out.println(f.getName());
			
		}
	}

}
