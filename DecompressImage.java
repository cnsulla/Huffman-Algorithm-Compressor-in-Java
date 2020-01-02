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
    //     this.image = new BufferedImage(this.x, this.y);

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
    //     if (){}
    // }

    private void readImgDimensions(File imageFile)
    {
        byte[] bArray = new byte[8];

        try(FileInputStream in = new FileInputStream(imageFile)){

            in.read(bArray, 0, 8);

            // for (int i = 0; i < 8; i++)
            // {
            //     // bArray[i] = (byte) in.read();
            //     System.out.println(Integer.toBinaryString(bArray[i] & 0xff));
            // }
            
            in.close();

        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }

        for (int i = 0; i<bArray.length; i++)
        {
            if (i %4 == 0)
                System.out.println("---");
            System.out.println(Integer.toBinaryString(bArray[i] & 0xff));
        }

        int[] dimensions = new int[2];
        ByteBuffer bb = ByteBuffer.wrap(bArray);

        // byte[] arr = {0, 0, 2, 0, 
        //                 0, 0, 1, 0};
        // ByteBuffer ba = ByteBuffer.wrap(arr);
        
        // System.out.println(ba.getInt() + " " + ba.getInt());

        for (int i = 0; i < dimensions.length; i++)
        {
            dimensions[i] = bb.getInt();
            System.out.print(dimensions[i] + " ");
        }

        this.x = dimensions[0];
        this.y = dimensions[1];
    }
}