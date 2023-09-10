#####################################################################################################
#      Instructions for compiling and starting your source code from the console (non-IDE).         #
#   Instruction is written for the state where the console is opened in the project's root folder.  #
#####################################################################################################


1. Create a target directory
ex00/ImagesToChar$ mkdir target

2. Compile sourcecode to target directory with .class extensions
ex00/ImagesToChar$ javac src/java/edu/school21/printer/*/*.java -d ./target

3. Run program with three arguments, where
  - the first argument is a character representing WHITE color,
  - the second argument is a character representing BLACK color,
  - the third argument is a path to the file with .bmp extension.
ex00/ImagesToChar$ java -classpath ./target edu/school21/printer/app.Program . 0 ../../../materials/it.bmp

4. Clean
ex00/ImagesToChar$ rm -rf target