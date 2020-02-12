public class FileNode {

	private String filename;
	private int diskstart;
	private int size;
	private FileNode letterDown;
	private FileNode numberDown;
	private AppendNode right;
	
	public FileNode(String filename,int diskstart)
	{
		this.filename = filename;
		diskstart = 0;
		size = 0;
		letterDown = null;
		numberDown = null;
		right = null;
	}
	public String getFilename() { return filename; }
	public int getDiskstart() { return diskstart; }
	public int getSize() { return size; }
	public FileNode getLetterDown() { return letterDown; }
 	public FileNode getNumberDown() { return numberDown;}
 	public AppendNode getRight() { return right; }
	
	public void setFilename(String filename) { this.filename = filename; }
	public void setDiskstart(int diskstart) { this.diskstart = diskstart; }
	public void setSize(int size) { this.size = size; }
	public void setLetterDown(FileNode letterDown) { this.letterDown = letterDown; 	}
	public void setNumberDown(FileNode numberDown) { this.numberDown = numberDown; }
	public void setRight(AppendNode right) { this.right = right;}
}
