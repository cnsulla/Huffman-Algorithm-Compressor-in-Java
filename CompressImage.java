import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;
// import java.util.*;

public class CompressImage
{
    private HuffmanNode[] bStringArr;
    private int[] pixels;
    private int ptrIndex = 0, cursor = 0, length = 0, xSize, ySize;
    private BufferedImage img = null;
    private HuffmanNode write = new HuffmanNode(0);
    private String DESTINATION;
    

    public CompressImage(HuffmanNode node, int size, String IMGFILEPATH, String DEST)
    {
        this.DESTINATION = DEST;
        bStringArr = new HuffmanNode[size];
        getStringArr(node, 0);

        readImage(IMGFILEPATH, node.freq);
        this.write.setBitString(1);
        writeFile();
    }

    private void readImage(String FILEPATH, int size)
    {

        try {
            File f = new File(FILEPATH + ".png");
            this.img = ImageIO.read(f);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }

        this.pixels = new int[size];
        for (int y = 0; y < img.getHeight(); y++)
        {
            for (int x = 0; x < img.getWidth(); x++)
            {
                this.pixels[cursor] = img.getRGB(x,y);
                cursor++;
            }
        }

        this.xSize = img.getWidth();
        this.ySize = img.getHeight();
    }

    public void getStringArr(HuffmanNode node, int bit)
    {
        if (node.left == null && node.right == null)
        {
            bit = bit<<1 | 1;
            node.setBitString(bit);
            addToArr( node );
            return;
        }
        
        getStringArr( node.left , (bit<<1) );
        getStringArr( node.right , (bit<<1) | 1);
    }

    private void addToArr(HuffmanNode input)
    {
        bStringArr[this.ptrIndex] = input;
        ptrIndex++;
    }

    private HuffmanNode getBits(int pVal)
    {
        for (int i = 0; i < bStringArr.length; i++)
        {
            if (pVal == bStringArr[i].pVal)
            {
                return bStringArr[i];
            }
        }
        return null;
    }

    private boolean writeInt(HuffmanNode add)
    {
        while ((add.getBitString() & 0xff) != 0)
        {
            if (this.length == 8)
            {
                this.write.setBitString(this.write.getBitString() & (0xff >> 8-length));
                return true;
            }

            this.length += 1;

            // System.out.print(Integer.toBinaryString(add.getBitString()) + " -> ");
            this.write.setBitString(this.write.getBitString() << 1 | ((add.getBitString()) & 1));
            // System.out.print(Integer.toBinaryString(this.write.getBitString()) + " new: ");

            add.setBitString(add.getBitString() >> 1);
            // System.out.println(Integer.toBinaryString(add.getBitString()) + " " + this.length);
        }
        return false;
    }

    private void writeFile()
    {
        try(FileOutputStream out = new FileOutputStream(DESTINATION, true)){
            
            out.write( (byte) xSize >> 24 & 0xff);
            out.write( (byte) xSize >> 16 & 0xff);
            out.write( (byte) xSize >> 8 & 0xff);
            out.write( (byte) xSize & 0xff);
            out.write( (byte) ySize >> 24 & 0xff);
            out.write( (byte) ySize >> 16 & 0xff);
            out.write( (byte) ySize >> 8 & 0xff);
            out.write( (byte) ySize & 0xff);

            for (int i = 0 ; i < pixels.length; i++)
            {
                HuffmanNode in = getBits(pixels[i]);
                // System.out.println("currently writing to int: " + in.getBitString());   
                if (i == pixels.length-1 || writeInt(in))
                {
                    // System.out.println("writing pixel: " + in.pVal + " with string: " + Integer.toBinaryString(this.write.bitString & 0xff) + " " + i);
                    out.write( (byte) (this.write.getBitString() & 0xff) );
                    this.length = 0;
                    this.write.setBitString(1);
                }
                // System.out.println( fileContent.get(i) + " " + i);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}