import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Vdisk {
	private Block[] blocks = new Block[30];
	
	public Block[] getBlocks() { return blocks; }
	public void setBlocks(Block[] blocks) { this.blocks = blocks; }

	public Vdisk(Block[] blocks)
	{
		for (int i = 0; i < blocks.length; i++) 
		{
			blocks[i] = new Block();
		}
		this.blocks = blocks;
		
	}
	
	public void printDisk() 
	{
		for (int j = 0; j < 6; j++) 
		{
			for (int k = 0; k < 5; k++) 
			{	
				for (int i = 0; i < 10; i++)
				{
					//Block x = blocks[i];
					System.out.print(blocks[5*j+k].point[i]);		
				}			
				System.out.print(blocks[5*j+k].link);
				System.out.print("\t");	
			}
			System.out.println();
		}
	}

	public boolean createFile(String filename)
	{
		int empty = emptyBlock();
		boolean flag = true;
		if (filename.length() > 9) 
		{
			System.out.println("error");
		}
		
		for (int i = 0; i < blocks.length; i++) 
		{
			if (blocks[i].getPoint()[0] == '@') 
			{
				String a = "";
				for (int j = 0; j < filename.length(); j++) 
				{
					if (blocks[i].getPoint()[j + 1] != '.') 
					{
						a += blocks[i].getPoint()[j + 1];
					}
				}
				
				if (a.equals(filename)) 
				{
					flag = false;
					System.out.print(" Error: File Exist");
					System.out.println("");
					return flag;
				}
			}
		}

		if (flag)
		{
			blocks[empty].getPoint()[0] = '@';
			for (int i = 0; i < filename.length(); i++) 
			{
				blocks[empty].getPoint()[i + 1] = filename.charAt(i);
			}

			Block y = blocks[0];
			if (y.point[0] == '.') 
			{
				for (int j = 0; j < filename.length(); j++)
				{
					y.point[j] = filename.charAt(j);
				}
			}
		}
		return flag;
	}
	
	public void deleteFile(String filename) {

		for (int i = 0; i < blocks.length; i++) {
			if (blocks[i].getPoint()[0] == '@') {
				String a = "";
				for (int j = 0; j < filename.length(); j++) {
					if (blocks[i].getPoint()[j + 1] != '.') {
						a += blocks[i].getPoint()[j + 1];
					}
				}

				if (a.equals(filename)) {

					do {
						int temp = blocks[i].getLink();

						blocks[i] = new Block();
						i = temp - 1;
						if (i < 0)
							break;
						if (blocks[i].getLink() == 0)
							blocks[i] = new Block();
						if (i < 0)
							break;
					} while (blocks[i].getLink() != 0);
					break;
				}
			}
		}
	}
	
	public int emptyBlock()
	{
		int a = 0;
		for(int i = 0;i<30;i++)
		{
			if(blocks[i].getPoint()[0]=='.')
			{
				a=i;
				break;
			}
		}
		return a;
	}
	
	public void appendFile(String command) {
		String[] words = command.split(" ");
		String append = "";
		char[] com = command.toCharArray();
		for (int i = 0; i < com.length; i++) {
			if (com[i] == '"') {
				for (int j = i + 1; j < com.length - 1; j++) {
					append += com[j];
				}
				break;
			}
		}

		int counter = (int) Math.ceil((double) append.length() / 10);
		for (int k = 0; k < counter; k++) {

			int empty = emptyBlock();
			Boolean flag = false;

			for (int i = 0; i < blocks.length; i++) {
				if (blocks[i].getPoint()[0] == '@') {
					String a = "";
					for (int j = 0; j < words[1].length(); j++) {
						if (blocks[i].getPoint()[j + 1] != '.') {
							a += blocks[i].getPoint()[j + 1];
						}
					}

					if (a.equals(words[1])) {
						flag = true;
						while (true) {
							if (blocks[i].getLink() == 0) {
								blocks[i].setLink(emptyBlock() + 1);
								break;
							} 
							
							else
								i = blocks[i].link - 1;
						}
						break;
					}
				}
			}

			if (flag) {

				if (counter == 1) {

					for (int i = 0; i < append.length(); i++) {
						blocks[empty].getPoint()[i] = append.charAt(i);
					}
				} else if (k == counter - 1) {
					for (int i = 0; i < append.length() - 10 * (k); i++) {
						blocks[empty].getPoint()[i] = append.charAt((10 * (k)) + i);
					}
				} else {
					for (int i = 0; i < 10; i++) {
						blocks[empty].getPoint()[i] = append.charAt((10 * (k)) + i);
					}
				}
			}
		}
	}
	
	public void insert(String command)
	{
		String[] com = command.split(" ");
		String filename = com[1];
		int counter = Integer.parseInt(com[2]);
		

		for (int i = 0; i < blocks.length; i++) {
			if (blocks[i].getPoint()[0] == '@') {
				String a = "";
				for (int j = 0; j < filename.length(); j++) {
					if (blocks[i].getPoint()[j + 1] != '.') {
						a += blocks[i].getPoint()[j + 1];
					}
				}

				if (a.equals(filename)) {
					int filestart = i;
					for (int j = 0; j < counter; j++) {
						i = blocks[i].link - 1;
					}
					int temp = blocks[i].getLink();
					blocks[i].setLink(0);
					appendFile(command);
					while (true) {
						if (blocks[filestart].getLink() == 0) {
							blocks[filestart].setLink(temp);
							break;
						} else
							filestart = blocks[filestart].link - 1;
					}
					break;
				}
			}
		}

	}
	
	public void deleteBlocks(String filename,int start,int finish)
	{	
		for (int i = 0; i < blocks.length; i++) 
		{
			if (blocks[i].getPoint()[0] == '@') 
			{
				String a = "";
				for (int j = 0; j < filename.length(); j++) 
				{
					if (blocks[i].getPoint()[j + 1] != '.') 
					{
						a += blocks[i].getPoint()[j + 1];
					}
				}

				if (a.equals(filename)) 
				{
					int tempblock = 0;
					int templink = 0;
					int tempi=0;
					for (int j = 0; j < start; j++) 
					{
						if(j==start-1){
							tempblock=i;
					}
						tempi = i;
						i = blocks[i].link - 1;
					}
					for (int j = 0; j < finish-start+1; j++)
					{
						if(j==finish-start){templink=blocks[i].getLink();
					}
						tempi = i;
						i = blocks[i].link - 1;
						blocks[tempi]=new Block();
					}
					blocks[tempblock].setLink(templink);
				}
			}
		}
	}
	
	public void clearVDisk()
	{
		for(int i = 0;i<blocks.length;i++)
		{
			blocks[i] = new Block();
		}
	}
	
	public void Restore(String filename,String diskstart,String link)
	{
		int ds = Integer.parseInt(diskstart);
		int lk = Integer.parseInt(link);
			blocks[ds].setPoint(filename.toCharArray());
			blocks[ds].setLink(lk);
	}
}