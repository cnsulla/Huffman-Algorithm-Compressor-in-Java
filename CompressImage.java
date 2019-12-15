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
    private int ptrIndex = 0, cursor = 0, length = 0;
    private BufferedImage img = null;
    private HuffmanNode write = new HuffmanNode(0);
    

    public CompressImage(HuffmanNode node, int size, String IMGFILEPATH)
    {
        bStringArr = new HuffmanNode[size];
        getStringArr(node, 0);

        readImage(IMGFILEPATH, node.freq);
        this.write.setBitString(99);
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

    }

    public void getStringArr(HuffmanNode node, int bit)
    {
        if (node.left == null && node.right == null)
        {
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
            if (this.length == 32)
            {
                return true;
            }

            this.length += 8;
            
            this.write.setBitString(((add.getBitString() >> 8) & 0xff));
            System.out.print(Integer.toBinaryString(this.write.getBitString()) + " " + this.length + " ");

            add.setBitString(add.getBitString() >> 8);
            System.out.println(Integer.toBinaryString(add.getBitString()));
        }
        return false;
    }

    public void writeFile()
    {
        try(FileOutputStream out = new FileOutputStream("imgcompressed.XLV", true)){
            for (int i = 0 ; i < pixels.length; i++)
            {
                HuffmanNode in = getBits(pixels[i]);
                if (writeInt(in) == true)
                {
                    System.out.println("writing pixel: " + in.pVal + " with string: " + this.write.bitString + " " + i);
                    out.write( (byte) (this.write.getBitString()>>24 & 0xff) );
                    out.write( (byte) (this.write.getBitString()>>16 & 0xff) );
                    out.write( (byte) (this.write.getBitString()>>8 & 0xff) );
                    out.write( (byte) (this.write.getBitString() & 0xff) );
                    this.length = 0;
                    this.write.setBitString(0);
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