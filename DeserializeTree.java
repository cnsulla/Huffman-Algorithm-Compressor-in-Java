import java.io.*;
import java.util.*;
import java.nio.ByteBuffer;

public class DeserializeTree
{
    class Index { 
  
        int index = 0; 
    } 

    HuffmanNode returnNode;     
    Index index = new Index();

    public DeserializeTree(String FILEPATH)
    {
        File file = new File(FILEPATH);
        int[] outIArr = readFile(file);
        
        // for (int i = 0; i < outIArr.length; i++)
        // {
        //     System.out.println(outIArr[i]+ " " + i);
        // }

        this.returnNode = getTree(outIArr, outIArr.length);
    }

    public HuffmanNode returnTree()
    {
        return this.returnNode;
    }

    private int[] readFile(File fileIn)
    {
        byte[] bArray = new byte[(int) fileIn.length()];

        try(FileInputStream out = new FileInputStream(fileIn)){

            out.read(bArray);
            out.close();
        
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }

        int[] outArr = new int[bArray.length / 4];
        ByteBuffer bb = ByteBuffer.wrap(bArray);
        for (int i = 0; i < outArr.length; i++)
        {
            outArr[i] = bb.getInt();
        }
        
        return outArr;
    }

    private HuffmanNode makeTree(int[] intArr, Index preIndex, int key, int min, int max, int size)
    {

        if (preIndex.index >= size)
        {
            return null;
        }

        HuffmanNode root = null;

        if (key > min && key < max) {
            root = new HuffmanNode(key);
            // System.out.println("Node will have value "+key+"!");
            preIndex.index = preIndex.index + 1;

            if (preIndex.index < size)
            {
                // System.out.println("Yay we're setting the left and right trees now!");
                root.left = makeTree(intArr, preIndex, intArr[preIndex.index], min, key, size);
                root.right = makeTree(intArr, preIndex, intArr[preIndex.index], key, max, size);
            }
        }
        return root;
    }

    private HuffmanNode getTree(int[] iArr, int size)
    {
        int preIndex = 0;
        return makeTree(iArr, index, iArr[0], Integer.MIN_VALUE, 0, size);
    }

    public void printInorder(HuffmanNode node) { 
        if (node == null) { 
            return; 
        } 
        System.out.println(node.pVal + " "); 
        printInorder(node.left); 
        printInorder(node.right); 
    } 
}