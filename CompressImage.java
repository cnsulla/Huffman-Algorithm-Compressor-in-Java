import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.*;

public class CompressImage
{
    private HuffmanNode[] bStringArr;
    private int[] pixels;
    private int ptrIndex = 0, cursor = 0;
    private BufferedImage img = null;

    public CompressImage(HuffmanNode node, int size, String IMGFILEPATH)
    {
        bStringArr = new HuffmanNode[size];
        getStringArr(node, 0);
        int i = 0;
        
        while (bStringArr[i] != null)
        {
            System.out.println(bStringArr[i].pVal + " : " + Integer.toBinaryString( bStringArr[i].getBitString()) );
            i++;
        }

        readImage(IMGFILEPATH, node.freq);
    }

    private void readImage(String FILEPATH, int size)
    {
        
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

    public void addToArr(HuffmanNode input)
    {
        bStringArr[this.ptrIndex] = input;
        ptrIndex++;
    }

    public void writeFile(HuffmanNode[] fileContent)
    {
        try(FileOutputStream out = new FileOutputStream("imgcompressed.img", true)){
            for (int i = 0 ; i < fileContent.length; i++)
            {
                out.write( (byte) (fileContent[i].getBitString() >> 24 ) & 0xff);
                out.write( (byte) (fileContent[i].getBitString() >> 16 ) & 0xff);
                out.write( (byte) (fileContent[i].getBitString() >> 8 ) & 0xff);
                out.write( (byte) (fileContent[i].getBitString() & 0xff) );

                // System.out.println( fileContent.get(i) + " " + i);
                out.close();
            }

        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}