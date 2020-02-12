
public class AppendNode {
	
	private String content;
	private AppendNode next;
	
	public AppendNode(String content)
	{
		this.content = content;
		next = null;
	}

	public String getContent() { return content; }
	public AppendNode getNext() { return next; }
	
	public void setContent(String content) { this.content = content; }
	public void setNext(AppendNode next) { this.next = next; }

}
