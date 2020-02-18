# Virtual-Machine
Implementation of a simplified virtual machine partially. 

  In the virtual machine there are three parts: CPU, Disk and RAM. CPU processes the command set. The structures to process commands are in RAM. The function of the virtual Disk (vdisk) is to provide permanency. Mainly, vdisk is simulated in the project.

# RAM
•	In the RAM, file structures include name, size and diskstart. The diskstart indicates the start of the file on the vdisk. \
•	Commands and files to be worked on must be kept on RAM for quick access.\
•	All files must be synchronized in RAM and vdisk.\
•	The memory structures of the files in the RAM must be sorted by diskstart address and the file name. 


# VDISK

•	The vdisk consists of 30 blocks (1-30).\
•	Each block contains 11 byte/characters. \
•	The first 10 byte/characters contains data.\
•	The 11th byte indicates the next block.\
•	1-30  : Next block of the file\
•	: End of file\
•	Vdisk data character set: 0-9, a-z, A-Z, space \
•	Special characters:   . and @ \
•	.    :   no character/data\
•	@  :   start of the file\
•	File name should begin with @ character and the first block is used for file name.


# CPU
CPU is the part which processes the following command set.
•	load file.txt // load and run an executable batch file from harddisk\
•	print filename // print the content of the file on the screen\
•	create filename // create a file\
•	append filename “Hello World” // append data to the end of the file (as a block/blocks)\
•	insert filename 3 “Hello World” // insert data into the file from the 3rd block (as a block/blocks)\
•	delete filename // delete file\
•	delete filename 5 7 // delete blocks from 5 to 7\
•	printdisk // print all vdisk on the screen\
•	defrag // defragment vdisk\
•	store // save vdisk to harddisk as a file (vdisk.txt)\
•	restore // restore vdisk from harddisk, clear RAM structures, and create appropriate new memory structures in the RAM

# PROJECT TEAM
This project was done by 3 people.(The names of the two of them are Vuslat YOLCU, Ezgi Berfin ŞAHİN)
