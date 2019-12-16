import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;

public class DecompressImage
{
    public DecompressImage(String TREEFILEPATH, String IMGFILEPATH)
    {
        File image = new File(IMGFILEPATH);
        readImgFile(image);
    }

    private void readImgFile(File IMGFILEPATH)
    {
        
    }
}