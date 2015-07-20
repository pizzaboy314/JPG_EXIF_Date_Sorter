import java.io.File;


public class QueueFile {
	private File image;
	private File destDir;
	
	public QueueFile(File f, String folderName){
		this.image = f;
		this.destDir = new File(Worker.currDir.getAbsoluteFile().toString() + "\\" + folderName);
		
	}

	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public File getDestDir() {
		return destDir;
	}

	public void setDestDir(File destDir) {
		this.destDir = destDir;
	}
}
