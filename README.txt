TUCS Programming Competition Idea
Given a formatted image the program should output a bounding box for each "Creeper" found in the image.

Image format:
Images are stored as plain text hex encoded RGB triples with a line for each row of pixels,
and a space between each pixel. A blank line indicates the end of the file.

For example a 3x2 red image is:
FF0000 FF0000 FF0000
FF0000 FF0000 FF0000


Output format:
The output is the min and max points of the bounding boxes around each creeper. Formatted as the integers: "x1 x2 y1 y2". One bounding box on each line.
For example, a bounding box from (1, 3) to (5, 8):
1 3 5 8

Scoring:
Scoring is based on the proximity of each bounding box point to the expected point.

The Examples:
Step 1: Compile
javac *.java

Step 2: Create a .creep formatted file from an image to give to the program
java CreepEncodeImage creeper.jpeg

Step 3: Pass the .creep formatted file to the program, it will output the bounding box
java TUCSDevCompExample2012 --output creeper.jpeg.creep.png < creeper.jpeg.creep 

Example output:
120 340 240 480
