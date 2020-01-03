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

    public DecompressImage(HuffmanNode tree, String TREEFILEPATH, String IMGFILEPATH)
    {
        image = new File(IMGFILEPATH);
        readImgDimensions(image);
        drawImage(IMGFILEPATH, tree);
    }

    private BufferedImage drawImage(String imageFile, HuffmanNode node)
    {
        this.bfrdImage = new BufferedImage(this.x, this.y, BufferedImage.TYPE_INT_RGB);
        bArray = new byte[this.x*this.y];

        try(FileInputStream in = new FileInputStream(imageFile)){

            in.read(bArray, 8, -1);
            in.close();

        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }

        int i = 0;

        while (i < bArray.length)
        {
            traverseTree(node, bArray[i], 8);
            i++;
        }

        return bfrdImage;
    }

    private void traverseTree(HuffmanNode node, byte bit, int off)
    {
        if (node.right == null && node.left == null)
        {
            Color pix = new Color(node.pVal);
            bfrdImage.setRGB(x, y, pix.getRGB());
            return;
        }

        else if (((bit >> (off-1)) & 1) == 0)
        {
            traverseTree(node.left, (byte) (bit >> 1), off--);
        }

        else if (((bit >> (off-1)) & 1) == 1)
        {
            traverseTree(node.right, (byte) (bit >> 1), off--);
        }
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