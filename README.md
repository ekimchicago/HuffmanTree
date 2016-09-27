# HuffmanTree
HuffmanTree is a java class that allows for file compression and decompression. It can take a file and encode its contents in a .zip fashion and take the same file and decode it to return its contents to its original state. 
</br>
For a school project, I had to find a way to create an ADT Huffman Tree Class type that takes in a file, compresses and encrypts it, then is able to unzip and extract the same file. (A Huffman Tree is a methodology for compressing/encoding information that weighs “counts” the number of characters and assigns them a hierarchy in code where more frequently used characters get fewer bits for faster processing).
</br>
Files that are my code:</br>
HuffmanTree.java</br>
</br>
Helper files provided to me and are not my code:</br>
Encode.java</br>
Decode.java</br>
MakeCode.java</br>
BitInputStream.java</br>
BitOutputStream.java</br>
</br>
Steps:</br>
1. Take an ordinary .txt file and run MakeCode.java over it and provide the console with a new <b>code</b> file name.</br>
2. Then, run Encode.java to encode the file. You will provide the console original file name, the code file name from step 1, and create a new <b>output</b> file name.</br>
3. Now that you have an <b>output</b> file, this is your encoded file, compressed. You can run Decode.java to return the encoded <b>output</b> file to its original state.</br>
</br>
See more on my website <a href="https://terranjendro.wordpress.com/">here</a>.
