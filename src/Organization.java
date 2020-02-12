import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

import enigma.console.Console;
import enigma.core.Enigma;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;

public class Organization {
	Console console = Enigma.getConsole("Virtual Machine", 100, 30, 25);
	Block[] blocks = new Block[30];
	public VirtualMachine vm = new VirtualMachine();
	public Vdisk vd = new Vdisk(blocks);
	private Scanner scn;

	public Organization() throws IOException {
		while (true) {
			scn = new Scanner(System.in);
			System.out.print("command> ");
			String command = scn.nextLine();
			takeCommand(command);
		}
	}

	private void takeCommand(String command) throws IOException {
		String[] com = command.split(" ");
		if ("load".equals(com[0])) {
			String text = com[1];
			fileOperation(text);
		}

		else if ("print".equals(com[0])) {
			vm.mll.print(com[1]);
		}

		else if ("create".equals(com[0])) {
			// vd.createFile(com[1]);
			vm.createFile(com[1], vd.emptyBlock(), vd);
		}

		else if ("append".equals(com[0])) {
			String append = "";
			char[] com1 = command.toCharArray();
			for (int i = 0; i < com1.length; i++) {
				if (com1[i] == '"') {
					for (int j = i + 1; j < com1.length - 1; j++) {
						append += com1[j];
					}
					break;
				}
			}
			String a = "\"";
			a += append;
			append += a;
			vd.appendFile(command);
			vm.appendFile(com[1], a);
		}

		else if ("insert".equals(com[0])) {
			vd.insert(command);
			String insert = "";
			char[] com1 = command.toCharArray();
			for (int i = 0; i < com1.length; i++) {
				if (com1[i] == '"') {
					for (int j = i + 1; j < com1.length - 1; j++) {
						insert += com1[j];
					}
					break;
				}
			}
			String a = "\"";
			a += insert;
			insert += a;
			int place = Integer.parseInt(com[2]);
			vm.insertAppend(com[1], a, place, vm);
		}

		else if ("delete".equals(com[0])) {
			if (com.length == 2) {
				vd.deleteFile(com[1]);
				vm.deleteFile(com[1]);
			} else {
				int start = Integer.parseInt(com[2]);
				int finish = Integer.parseInt(com[3]);
				vd.deleteBlocks(com[1], start, finish);
				vm.deleteContent(com[1], start, finish, vm);
			}

		}

		else if ("printdisk".equals(command)) 
		{
			vd.printDisk();		
		}

		else if ("defrag".equals(command)) {
			vm.mll.defrag(vd);
		}

		else if ("store".equals(command)) {
			store();
		}

		else if ("restore".equals(command)) {
			restore(vd, vm);
		}
		else if ("printram".equals(command))
		{
			vm.mll.displayByLetter();
		}

	}

	public void fileOperation(String text) {
		FileReader fileReader = null;
		BufferedReader buffReader = null;
		try {
			fileReader = new FileReader(new File("src/" + text));
			buffReader = new BufferedReader(fileReader);
			String line;

			while ((line = buffReader.readLine()) != null) {
				System.out.println(line);
				takeCommand(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fileReader != null)
					fileReader.close();
				if (buffReader != null)
					buffReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void store() throws IOException {
		String store = "";
		File file = new File("src/vdisk.txt");
		FileOutputStream fileoutputstream = new FileOutputStream(file);
		BufferedWriter bufferedwriter = new BufferedWriter(new OutputStreamWriter(fileoutputstream));

		for (int i = 0; i < blocks.length; i++) {

			if (blocks[i].getPoint()[0] == '@') {
				int filestart = i;
				while (blocks[filestart].getLink() != 0) {
					store += String.copyValueOf(blocks[filestart].getPoint());
					if (blocks[filestart].getPoint()[0] == '@') {
						if (filestart < 10) {
							store += "0";
						}
						store += filestart + "-";

						if (blocks[filestart].getLink() < 10) {
							store += "0";
						}
						store += blocks[filestart].getLink();
					}

					else {
						if (filestart < 10) {
							store += "0";
						}
						store += filestart + "-";

						if (blocks[filestart].getLink() < 10) {
							store += "0";
						}
						store += blocks[filestart].getLink();
					}
					filestart = blocks[filestart].link - 1;
				}

				if (blocks[filestart].getLink() == 0) {
					store += String.copyValueOf(blocks[filestart].getPoint());
					if (filestart < 10) {
						store += "0";
					}
					store += filestart+"-";
					if (blocks[filestart].getLink() < 10) {
						store += "0";
					}
					store += blocks[filestart].getLink();
					
				}
			}
		}

		bufferedwriter.write(store);
		bufferedwriter.newLine();
		bufferedwriter.close();
	}

	public void restore(Vdisk vd, VirtualMachine vm) 
	{
		FileReader fileReader = null;
		BufferedReader buffReader = null;
		try {
			fileReader = new FileReader(new File("src/vdisk.txt"));
			buffReader = new BufferedReader(fileReader);
			
			String line;
			while ((line = buffReader.readLine()) != null) 
			{
				vm.clearMll();
				vd.clearVDisk();
				String tempfile="";
				int counter = line.length() / 15;

				for (int i = 0; i < counter; i++) 
				{
					String a = null;
					if (i == 0)
						a = line.substring(i * 15, ((i + 1) * 15));
					else
						a = line.substring(i * 15, ((i + 1) * 15));
					
						int place = 0;
						String b = "";
						for (int j = 0; j < a.length(); j++)
						{
							if (a.charAt(j) != '.')
								b += a.charAt(j);
						}
						
						if (a.charAt(0) == '@') 
						{
							vm.createFile(b.substring(1, b.length() - 5), place, vd);
							tempfile=b.substring(1, b.length() - 5);
						}
						else{
							vm.appendFile(tempfile, b.substring(0, b.length() - 5));
						}
						vd.Restore(a.substring(0, 11), a.substring(10,12), a.substring(13));
					
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fileReader != null)
					fileReader.close();
				if (buffReader != null)
					buffReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}