#####################################################################################################
#      Instructions for compiling and starting your source code from the console (non-IDE).         #
#   Instruction is written for the state where the console is opened in the project's root folder.  #
#####################################################################################################

1. Create a target directory
ex01/ImagesToChar$ mkdir target

2. Compile sourcecode to target directory with .class extensions
ex01/ImagesToChar$ javac src/java/edu/school21/printer/*/*.java -d target

3.  Copy the resources ro the target directory
ex01/ImagesToChar$ cp -R src/resources target/.

4. Create a .jar file
ex01/ImagesToChar$ jar -cfm ./target/images-to-chars-printer.jar src/manifest.txt -C ./target .

5. Run program with three arguments, where
  - the first argument is a character representing WHITE color,
  - the second argument is a character representing BLACK color,
  - the third argument is a path to the file with .bmp extension.
ex01/ImagesToChar$ java -jar ./target/images-to-chars-printer.jar . 0

6. Clean
ex01/ImagesToChar$ rm -rf target