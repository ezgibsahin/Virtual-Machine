public class Block {
	
	public char[] point = new char[10];
	public int link ;
	
	public Block()
	{ 
		for(int i= 0;i<point.length;i++)
		{
			point[i]='.';
		}
		link = 0;
	}
	
	public char[] getPoint() { return point; }
	public int getLink() { return link; }
	
	public void setPoint(char[] block) { this.point = block; }
	public void setLink(int link) { this.link = link; }

}