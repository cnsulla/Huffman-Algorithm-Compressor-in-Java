import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.nio.ByteBuffer;

public class DecompressImage
{
    private int x, y;
    private BufferedImage image;

    public DecompressImage(HuffmanNode tree, String TREEFILEPATH, String IMGFILEPATH)
    {
        File image = new File(IMGFILEPATH);
        readImgDimensions(image);
        
    }

    // private void drawImage()
    // {
    //     this.image = new BufferedImage(this.x, this.y, BMP);

    // }

    // private void getPixel()
    // {
    //     try(FileInputStream in = new FileInputStream(imageFile)){

    //         in.read(bArray);
    //         in.close();

    //     } catch (FileNotFoundException e) {
    //         System.out.println(e);
    //     } catch (IOException e) {
    //         System.out.println(e);
    //     }
    // }

    // private int traverseTree(HuffmanNode node)
    // {
    //     if ()
    //     {

    //     }
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

        this.x = dimensions[0];
        this.y = dimensions[1];
    }
}