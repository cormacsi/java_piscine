#####################################################################################################
#      Instructions for compiling and starting your source code from the console (non-IDE).         #
#   Instruction is written for the state where the console is opened in the project's root folder.  #
#####################################################################################################

#      The subject for working with .jar libraries downloaded from internet
1. Create a target directory
ex02/ImagesToChar$ mkdir target lib

2. Move the .jar libraries from resources
ex02/ImagesToChar$ mv ./src/resources/*.jar ./lib

3. Compile the code with libs
ex02/ImagesToChar$ javac -cp ".:lib/*" src/java/edu/school21/printer/*/*.java -d target

4. Unzip jar files to target directory
ex02/ImagesToChar$ cd target && jar -xf ../lib/jcommander-1.82.jar com && jar -xf ../lib/JColor-5.0.0.jar com && cd ..

5. Copy the resources ro the target directory
ex02/ImagesToChar$ cp -R src/resources target/

6. Create a .jar file
ex02/ImagesToChar$ jar -cfm ./target/images-to-chars-printer.jar src/manifest.txt -C ./target .

7. Run program with three arguments, where
  - the first argument is a string representing WHITE color "--white=",
  - the second argument is a string representing BLACK color "--black=".
ex02/ImagesToChar$ java -jar target/images-to-chars-printer.jar --white=RED --black=GREEN

8. Clean
ex02/ImagesToChar$ rm -rf target

#####################################################################################################
#                  Compilation and tests instruction for writing the code                           #
#####################################################################################################

1. Download the libraries from github
ex02/ImagesToChar$ git clone git@github.com:cbeust/jcommander.git
ex02/ImagesToChar$ cp -R jcommander/src/main/java/ src/java
ex02/ImagesToChar$ rm -rf jcommander

ex02/ImagesToChar$ git clone git@github.com:dialex/JColor.git
ex02/ImagesToChar$ cp -R JColor/src/main/java/ src/java
ex02/ImagesToChar$ rm -rf JColor

2. Copy the resources with picture
ex02/ImagesToChar$ cp -R src/resources/*.bmp target/.

3. Compilation of all the files
ex02/ImagesToChar$ javac src/java/*/*/*/*/*.java src/java/*/*/*/*.java -d target

4. Run it
ex02/ImagesToChar$ java -classpath ./target edu/school21/printer/app.Program --white=RED --black=GREEN