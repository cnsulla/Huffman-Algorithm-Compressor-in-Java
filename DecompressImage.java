import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.nio.ByteBuffer;
import java.awt.Color;

public class DecompressImage
{
    private int x, y;
    private BufferedImage bfrdImage;
    private File image;
    private byte[] bArray;
    private int fx = 0, fy = 0;

    public DecompressImage(HuffmanNode tree, String TREEFILEPATH, String IMGFILEPATH)
    {
        image = new File(IMGFILEPATH);
        readImgDimensions(image);
        drawImage(IMGFILEPATH, tree);
    }

    private BufferedImage drawImage(String imageFile, HuffmanNode node)
    {
        this.bfrdImage = new BufferedImage(this.x, this.y, BufferedImage.TYPE_INT_RGB);
        bArray = new byte[x*y];

        try(FileInputStream in = new FileInputStream(imageFile)){

            in.read(bArray, 9, ((int) image.length()/8));
            in.close();

        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }

        int x = 0;
        HuffmanNode head = node;
        

        for (int i = 0; i < bArray.length; i++)
        {
            while (x > 0)
            {
                // x = traverseTree(node, bArray[i], x);
                if (node.right == null && node.left == null)
                {
                    System.out.println("leaf node at " + node.pVal + " with bit string: " + Integer.toBinaryString(node.bitString) + " printing at " +( fx+1 )+ ", " +( fy+1));
                    Color pix = new Color(node.pVal);
                    bfrdImage.setRGB(this.fx, this.fy, pix.getRGB());
                    imgDims();
                    
                    node = head;
                    break;
                }
                else if ((((bArray[i]) >> (x-1)) & 1 ) == 0)
                {
                    node = node.left;
                    x--;
                }
                else if ((((bArray[i]) >> (x-1)) & 1 ) == 1)
                {
                    node = node.right;
                    x--;
                }
            }
            System.out.println("STRING: " + Integer.toBinaryString(bArray[i]) + " at index " + i);
            x = 8;
        }

        return bfrdImage;
    }

    private void imgDims()
    {
        if (fx < x-1)
        {
            fx++;
        }
        else if (fy < y-1)
        {
            fx = 0;
            fy++;
        }
    }

    // private int traverseTree(HuffmanNode node, byte bit, int off)
    // {
    //     if (node.right == null && node.left == null)
    //     {
    //         System.out.println("leaf node at " + node.pVal + " with bit string: " + Integer.toBinaryString(node.bitString) + " printing at x, y");
    //         Color pix = new Color(node.pVal);
    //         bfrdImage.setRGB(x, y, pix.getRGB());
    //         return off;
    //     }

    //     else if (((bit >> (off-1)) & 1) == 0)
    //     {
    //         traverseTree(node.left, (byte) (bit >> 1), off--);
    //     }

    //     else if (((bit >> (off-1)) & 1) == 1)
    //     {
    //         traverseTree(node.right, (byte) (bit >> 1), off--);
    //     }

    //     return 0;
    // }

    private void readImgDimensions(File imageFile)
    {
        byte[] bArray = new byte[8];

        try(FileInputStream in = new FileInputStream(imageFile))
        {
            in.read(bArray, 0, 8);
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }

        int[] dimensions = new int[2];
        ByteBuffer bb = ByteBuffer.wrap(bArray);

        for (int i = 0; i < dimensions.length; i++)
        {
            dimensions[i] = bb.getInt();
            System.out.print(dimensions[i] + " ");
        }
        System.out.println(" ");
        this.x = dimensions[0];
        this.y = dimensions[1];
    }
}