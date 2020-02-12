public class VirtualMachine {
	
	//RAM GÖREVÝ
	MultiLinkedList mll = new MultiLinkedList();
	
	public VirtualMachine()
	{
		
	}
	
	public void createFile(String filename,int diskstart,Vdisk vd)
	{
		char[] file = filename.toCharArray();
		if(vd.createFile(filename))
			mll.addFileByLetter(filename, diskstart);	
	}
	
	public void appendFile(String filename,String content)
	{
		int counter=0;
		String a = "";
		for (int i = 0; i < content.length(); i++) {
			if(content.charAt(i)!='"'){a+=content.charAt(i);}
		}
		content = a;
		counter = (int) Math.ceil((double) content.length() / 10);
		
		
		for (int i = 0; i < counter; i++) {
			if(i==counter-1){mll.addAppend(filename, content.substring(i*10));}
			else
			mll.addAppend(filename, content.substring(i*10, (i+1)*10));
		}		
	}
	public void deleteFile(String filename)
	{
		mll.deleteFile(filename);
	}
	
	public void insertAppend(String filename,String content,int place,VirtualMachine vm)
	{		
		mll.insertAppend(filename, content, place, vm);	
	}
	
	public void deleteContent(String filename,int start,int end,VirtualMachine vm)
	{	
		mll.deleteContent(filename, start, end);
	}
	
	public void clearMll()
	{
		mll=new MultiLinkedList();
	}
	
}