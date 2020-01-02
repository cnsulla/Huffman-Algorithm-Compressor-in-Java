import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;

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
        getStringArr(node, 1);

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

    private boolean writeByte(int add)
    {

        System.out.println("Accepted: " + Integer.toBinaryString(add));
        int i = 32;
        while (i > 0)
        {
            i--;

            if (((add>>(i-1)) & 1) == 1)
            {
                i--;
                System.out.println("i = " + i);
                break;
            }
        }

        while ((add) != 0 || i > 0)
        {
            if (this.length == 8)
            {
                return true;
            }

            this.length += 1;
            System.out.print("(" + Integer.toBinaryString((add >> (i-1)) & 1) +") -> ");
            this.write.setBitString((this.write.getBitString() & ((add >> (i-1)) & 1)));
            System.out.println(Integer.toBinaryString(this.write.getBitString())  + " " + length);
            i--;
        }
        return false;
    }

    private void writeFile()
    {
        try(FileOutputStream out = new FileOutputStream(DESTINATION, true)){

            out.write( (byte) (xSize >> 24 & 0xff));
            out.write( (byte) (xSize >> 16 & 0xff));
            out.write( (byte) (xSize >> 8 & 0xff));
            out.write( (byte) (xSize & 0xff));
            
            out.write( (byte) (ySize >> 24 & 0xff));
            out.write( (byte) (ySize >> 16 & 0xff));
            out.write( (byte) (ySize >> 8 & 0xff));
            out.write( (byte) (ySize & 0xff));
            
            for (int i = 0 ; i < pixels.length; i++)
            {
                HuffmanNode in = getBits(pixels[i]);
                if (i == pixels.length-1 || writeByte(in.getBitString()))
                {
                    System.out.println("writing string: " + Integer.toBinaryString(this.write.bitString & 0xff) + " " + i);
                    out.write( (byte) (this.write.getBitString() & 0xff) );
                    this.length = 0;
                    this.write.setBitString(1);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}