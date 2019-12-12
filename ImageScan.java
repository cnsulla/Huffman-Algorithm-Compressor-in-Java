import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.*;

public class ImageScan 
{
    private BufferedImage img = null;
    private File f = null;
    private int[] pixels;
    private int cursor=0;
    private HuffmanNode[] returnArr;
    private HuffmanNode[] temp;

    public HuffmanNode[] ImageScan(String FILEPATH)
    {
        getImage(FILEPATH);
        getAFreqList();
        return returnArr;
    }

	public void getImage(String FILEPATH)
	{
        try {
            f = new File(FILEPATH + ".png");
            img = ImageIO.read(f);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
        
        pixels = new int[(img.getHeight())*(img.getWidth())];
        getPixel();
    }

    public void getPixel()
    {
        for (int y = 0; y < img.getHeight(); y++)
        {
            for (int x = 0; x < img.getWidth(); x++)
            {
                pixels[cursor] = img.getRGB(x,y);
                cursor++;
            }
        }
        // System.out.println("Total of "+pixels.length+" pixels read.");
        Arrays.sort(pixels);

        // for (int i = 0; i<pixels.length; i++)
        // {
        //     System.out.println(pixels[i]);
        // }
    }

    public void getAFreqList()
    {
        temp = new HuffmanNode[pixels.length];

        int j = 0;
        int freq = 0;
        int i = 0;

        for (i = 0; i < pixels.length; i++)
        {
            freq++;
            if (i == pixels.length-1 || pixels[i] != pixels[i+1])
                {
                    // System.out.println(pixels[i] + " : "+freq);
                    HuffmanNode in = new HuffmanNode(pixels[i], freq);
                    temp[j] = in;
                    // System.out.println("pixel " + temp[j].pVal + " has freq: "+temp[j].freq);
                    j++;
                    freq = 0;
                }
        }
        // showList(temp);

        this.returnArr = new HuffmanNode[j];
        for (int x = 0; x < j; x++)
        {
            returnArr[x] = temp[x];
        }

        Arrays.sort(returnArr, new SortbyFreq());
        // System.out.println("Sorted. . . . . array of length: " + returnArr.length);
        // showList(returnArr);
    }

    public void showList(HuffmanNode[] arr)
    {
        int i = 0;
        System.out.println("Read number of unique pixels: "+arr.length);
        while (i < arr.length && arr[i] != null)
        {
            // System.out.println(arr[i].getFreq());
            System.out.println("At index "+i+", pixel "+arr[i].pVal+" has freq "+arr[i].freq);
            i++;
        }
        System.out.println("Final number of unique pixels: "+i);
    }

    public class SortbyFreq implements Comparator<HuffmanNode>
    {
        public int compare(HuffmanNode a, HuffmanNode b) 
        { 
            return a.freq - b.freq; 
        } 
    }
}

   

