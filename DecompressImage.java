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

    public DecompressImage(HuffmanNode tree, String TREEFILEPATH, String IMGFILEPATH)
    {
        image = new File(IMGFILEPATH);
        readImgDimensions(image);
        drawImage(IMGFILEPATH, tree);
    }

    private BufferedImage drawImage(String imageFile, HuffmanNode node)
    {
        this.bfrdImage = new BufferedImage(this.x, this.y, BufferedImage.TYPE_INT_RGB);
        byte[] bArray = new byte[this.x*this.y];

        try(FileInputStream in = new FileInputStream(imageFile)){

            in.read(bArray, 8, -1);
            in.close();

        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
        int i = 0;
        for (int b = 0; b < this.y; b++)
        {
            for (int v = 0; v < this.x; v++)
            {
                while (i < bArray.length)
                {
                    int pixel = traverseTree(node, bArray[i]);
                    Color pix = new Color(pixel);
                    bfrdImage.setRGB(x, y, pix.getRGB());
                }
            }
        }

        return bfrdImage;
    }

    private int traverseTree(HuffmanNode node, byte bit)
    {
        if (node.right == null && node.left == null)
        {
            return node.pVal;
        }

        else if ((bit & 1) == 0)
        {
            traverseTree(node.left, (byte) (bit >> 1));
        }

        else if ((bit & 1) == 1)
        {
            traverseTree(node.right, (byte) (bit >> 1));
        }

        return 0;
    }

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

        this.x = dimensions[0];
        this.y = dimensions[1];
    }
}