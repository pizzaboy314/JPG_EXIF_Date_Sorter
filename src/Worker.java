import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.commons.io.FilenameUtils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifSubIFDDirectory;


public class Worker {
	private static SimpleDateFormat dateFormat;
	private static JFileChooser fc;
	public static File currDir;
	public static ArrayList<File> files;
	public static ArrayList<QueueFile> fileQueue;
	public static String find;
	public static String replace;
	
	public static void main(String[] args) {
		dateFormat = new SimpleDateFormat("yyyy_MM_dd");
		currDir = null;
		currDir = getPath();
		if(currDir == null){
			System.exit(1);
		} else {
			files = getJPGs(currDir);
			grabTakenDatesAndQueueFiles();
//			printFilenames();
			moveAllFiles();
		}
		
//		System.out.println("breakpoint");
		System.exit(0);
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
	
	public static void grabTakenDatesAndQueueFiles(){
		fileQueue = new ArrayList<QueueFile>();
		for(File f : files){
			try {
				Metadata metadata = ImageMetadataReader.readMetadata(f);
				for (Directory directory : metadata.getDirectories()){
					for (Tag tag : directory.getTags()) {
						if(tag.toString().contains("Date/Time Original") && tag.toString().contains("Exif")){
							Date date = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
							
							QueueFile qf = new QueueFile(f, dateFormat.format(date));
							fileQueue.add(qf);
						}
				    }
					
				}
			} catch (ImageProcessingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void moveAllFiles(){
		JFrame frame = new JFrame("Sorting JPGs...");
		frame.setPreferredSize(new Dimension(300,120));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
//		JLabel label2 = new JLabel();
//		label2.setPreferredSize(new Dimension(250,40));
//		label2.setHorizontalAlignment(JLabel.CENTER);
//		label2.setText("High resolution images will be saved in:");
//		panel.add(label2);
//		
//		JLabel label3 = new JLabel();
//		label3.setPreferredSize(new Dimension(250,40));
//		label3.setHorizontalAlignment(JLabel.CENTER);
//		label3.setText("REMOVE");
//		panel.add(label3);
		
		JLabel label1 = new JLabel();
		label1.setPreferredSize(new Dimension(200,40));
		label1.setHorizontalAlignment(JLabel.CENTER);
		panel.add(label1);
		
		frame.add(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		for(QueueFile qf : fileQueue){
			label1.setText("Moving JPG " + (fileQueue.indexOf(qf)+1) + " of " + fileQueue.size());
			File image = qf.getImage();
			File destDir = qf.getDestDir();
			File destImage = new File(destDir.getAbsolutePath() + "\\" + image.getName());
			
			if(!destDir.exists()){
				destDir.mkdirs();
			}
			image.renameTo(destImage);
		}
		JOptionPane.showMessageDialog(null, "Finished!", "Finished", JOptionPane.PLAIN_MESSAGE);
		frame.setVisible(false);
	}
	
	public static void printFilenames(){
		for(QueueFile qf : fileQueue){
			System.out.println(qf.getImage().getName() + ": " + qf.getDestDir().getName());
		}
	}

}
