public class MultiLinkedList 
{
	private FileNode head;

	public MultiLinkedList() 
	{
		this.head = null;
	}

	public void addFileByLetter(String filename, int diskstart) {
		FileNode newFileNode = new FileNode(filename, diskstart);
		if (head == null) {
			head = newFileNode;
			head.setDiskstart(diskstart);
		} else {

			FileNode temp = head;

			while (temp.getLetterDown() != null) {
				temp = temp.getLetterDown();
			}

			newFileNode.setDiskstart(diskstart);
			temp.setLetterDown(newFileNode);

		}
	}

	public void addFileByDiskstart(String filename, int diskstart) {
		FileNode newFileNode = new FileNode(filename, diskstart);
		if (head == null) {
			head = newFileNode;
		} else {
			FileNode temp = head;

			while (temp.getNumberDown() != null) {
				temp = temp.getNumberDown();
			}
			temp.setNumberDown(newFileNode);
		}
	}

	public void addAppend(String filename, String content) {
		if (head == null) {
			System.err.println("There is no file!");
		} else {
			AppendNode newAppendNode = new AppendNode(content);
			FileNode tempFile = head;

			while (tempFile != null && !tempFile.getFilename().toString().equals(filename)) {
				tempFile = tempFile.getLetterDown();
			}

			if (tempFile != null) {
				if (tempFile.getRight() == null) {
					tempFile.setRight(newAppendNode);
				} else {
					AppendNode tempAppend = tempFile.getRight();

					while (tempAppend.getNext() != null) {
						tempAppend = tempAppend.getNext();
					}

					tempAppend.setNext(newAppendNode);
				}
			} else {
				System.err.println("There is no file like " + filename);
			}
		}
	}

	public void displayByLetter() {
		if (head == null) {
			System.err.println("There is no file to display");
		} else {

			FileNode tempFile = head;
			while (tempFile != null) {
				System.out.print(tempFile.getFilename().toString() + " " + tempFile.getDiskstart() + "-->");
				AppendNode tempAppend = tempFile.getRight();

				while (tempAppend != null) {
					System.out.print(tempAppend.getContent().toString() + " , ");
					tempAppend = tempAppend.getNext();
				}

				System.out.println();
				tempFile = tempFile.getLetterDown();
			}
		}
	}

	public void displayByDiskstart() {
		if (head == null) {
			System.err.println("There is no file to display");
		} else {
			FileNode tempFile = head;

			while (tempFile != null) {
				System.out.print(tempFile.getFilename().toString() + "-->");
				AppendNode tempAppend = tempFile.getRight();

				while (tempAppend != null) {
					System.out.print(tempAppend.getContent().toString() + " , ");
					tempAppend = tempAppend.getNext();
				}

				System.out.println();
				tempFile = tempFile.getNumberDown();
			}
		}
	}

	public void deleteFile(String filename) {
		if (head == null) {
			System.err.println("There is no file!");
		} else {
			if (head.getFilename().toString().equals(filename)) {
				head = head.getLetterDown();
			} else {
				FileNode tempFile = head;
				FileNode prevFile = null;

				while (tempFile != null && !tempFile.getFilename().toString().equals(filename)) {
					prevFile = tempFile;
					tempFile = tempFile.getLetterDown();
				}

				if (tempFile != null) {
					prevFile.setLetterDown(tempFile.getLetterDown());
				} else {
					System.err.println("There is no file like " + filename);
				}
			}
		}
	}

	public void deleteAppend(Object filename, Object content) {
		if (head == null) {
			System.err.println("There is no file!");
		} else {
			FileNode tempFile = head;

			while (tempFile != null && !tempFile.getFilename().toString().equals(filename)) {
				tempFile = tempFile.getLetterDown();
			}

			if (tempFile != null) {
				if (tempFile.getRight() == null) {
					System.err.println("There is no append to delete!");
				} else {
					if (tempFile.getRight().getContent().toString().equals(content)) {
						tempFile.setRight(tempFile.getRight().getNext());
					} else {
						AppendNode tempAppend = tempFile.getRight();
						AppendNode prevAppend = null;

						while (tempAppend != null && !tempAppend.getContent().toString().equals(content)) {
							prevAppend = tempAppend;
							tempAppend = tempAppend.getNext();
						}

						if (tempAppend != null) {
							prevAppend.setNext(tempAppend.getNext());
						} else {
							System.err.println("There is no append like " + content);
						}
					}
				}
			} else {
				System.err.println("There is no append like " + filename);
			}
		}
	}

	public void insertAppend(String filename, String content, int place, VirtualMachine vm) {
		if (head == null) {
			System.err.println("There is no file!");
		} else {
			AppendNode newAppendNode = new AppendNode(content);
			FileNode tempFile = head;

			while (tempFile != null && !tempFile.getFilename().toString().equals(filename)) {
				tempFile = tempFile.getLetterDown();
			}

			if (tempFile != null) {
				if (tempFile.getRight() == null) {
					/* tempFile.setRight(newAppendNode); */
					System.err.println("There is no append to insert!");

				} else {

					AppendNode tempAppend = tempFile.getRight();
					AppendNode temp = null;

					for (int i = 0; i < place - 1; i++) {
						tempAppend = tempAppend.getNext();
					}
					temp = tempAppend.getNext();
					tempAppend.setNext(null);
					vm.appendFile(filename, content);

					if (temp != null) {
						while (temp.getNext() != null) {

							vm.appendFile(filename, temp.getContent());
							if (temp.getNext() != null)
								temp = temp.getNext();
						}
					}

					if (temp != null) {
						vm.appendFile(filename, temp.getContent());
					}
				}
			} else {
				System.err.println("There is no file like " + filename);
			}
		}
	}

	public void defrag(Vdisk vd) {

		vd.clearVDisk();
		if (head == null) {
			System.err.println("There is no file to display");
		} else {

			FileNode tempFile = head;
			while (tempFile != null) {
				vd.createFile(tempFile.getFilename());
				AppendNode tempAppend = tempFile.getRight();

				while (tempAppend != null) {
					String command = "append " + tempFile.getFilename() + " \"" + tempAppend.getContent() + "\"";
					vd.appendFile(command);
					tempAppend = tempAppend.getNext();
				}

				tempFile = tempFile.getLetterDown();
			}
		}
	}

	public void print(String filename) {
		if (head == null) {
			System.err.println("There is no file to display");
		} else {

			FileNode tempFile = head;
			while (tempFile != null) {
				if (filename.equals(tempFile.getFilename())) {
					AppendNode tempAppend = tempFile.getRight();

					while (tempAppend != null) {
						System.out.print(tempAppend.getContent().toString() + " , ");
						tempAppend = tempAppend.getNext();
					}
				}

				tempFile = tempFile.getLetterDown();
			}
		}
		System.out.println();
	}

	public void deleteContent(String filename, int start, int end) {

		if (head == null) {
			System.err.println("There is no file!");
		} else {
			AppendNode newAppendNode = new AppendNode("");
			FileNode tempFile = head;

			while (tempFile != null && !tempFile.getFilename().toString().equals(filename)) {
				tempFile = tempFile.getLetterDown();
			}

			if (tempFile != null) {
				if (tempFile.getRight() == null) {
					/* tempFile.setRight(newAppendNode); */
					System.err.println("There is no append to insert!");

				} else {

					AppendNode tempAppend = tempFile.getRight();
					AppendNode temp = null;

					for (int i = 0; i < start - 3; i++) {
						tempAppend = tempAppend.getNext();
					}
					temp = tempAppend.getNext();

					for (int i = 0; i < end - start + 1; i++) {
						temp = temp.getNext();
					}

					tempAppend.setNext(temp);
				}
			} else {
				System.err.println("There is no file like " + filename);
			}
		}

	}

}