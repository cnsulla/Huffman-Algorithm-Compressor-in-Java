import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;


public class GUI extends JFrame implements ActionListener{
    private JButton openImageButton;
    private JButton showImageButton;
    private JButton compressImageButton;
    private JButton trainNewButton;
    private JButton trainExistingButton;
    private JButton resetButton;
    private JPanel buttons1;
    private JPanel buttons2;
    private JPanel labels;
    private JPanel information;
    private JPanel originalPanel;
    private JPanel compressedPanel;
    private JLabel originalImage;
    private JLabel compressedImage;
    private JLabel originalLabel;
    private JLabel compressedLabel;
    private JScrollPane originalScroll;
    private JScrollPane compressedScroll;
    private JTextField informationTextField;
    private Font font = new Font("Product Sans Regular", Font.BOLD, 16);
    private File file;
    private File directory;
    private ImageIcon originalBG;
    private ImageIcon compressedBG;
    private HeapToHuff heap;
    private HuffmanNode huffTreeOrig;
    private CompressImage comp;
    private DecompressImage decomp;
    private String name;

    public static void main(String[] args) 
    {
        new GUI();
    }

    public String getFileSize(File f)
    {
        double b = f.length();
        double kb = (b / 1024);
        double mb = (kb / 1024);

        if(mb >= 1)
            return String.format("%.02f", mb - 0.01) + " MB";
        else return String.format("%.02f", kb - 0.01) + " KB";
    }

    //If file selection mode is FILES ONLY
    public File getFile(int button)
    {
    	JFileChooser files = new JFileChooser();
    	files.setFileSelectionMode(JFileChooser.FILES_ONLY);

    	switch(button)
    	{
    		case 1: //PNG File
    			files.setDialogTitle("Open Image (PNG File only)");
	            files.setFileFilter(new FileNameExtensionFilter(".PNG files", "png", "PNG"));
    	        break;
	    	case 2: //HUFF File
	            files.setDialogTitle("Choose Huffman File");
    	        files.setFileFilter(new FileNameExtensionFilter(".HUFF files", "huff", "HUFF"));
    	    	break;
    	    case 3: //KECS File
    	    	files.setDialogTitle("Open Image (XL File only");
    	    	files.setFileFilter(new FileNameExtensionFilter(".XL files", "xl", "XL"));
    	}

    	int choice = files.showOpenDialog(null);

    	if(choice == JFileChooser.CANCEL_OPTION)
    		return null;
    	else return files.getSelectedFile();	    
    }

    //IF file selection mode is DIRECTORIES ONLY
    public File getDirectory()
    {
    	JFileChooser files = new JFileChooser();
        files.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        files.setDialogTitle("Choose Directory");
        int choice = files.showOpenDialog(null);

        if(choice == JFileChooser.CANCEL_OPTION)
        	return null;
        else return files.getSelectedFile();
    }

    public GUI() 
    {
        super("Huffman Image Compressor");
        setSize(804,585);
        setLayout(null);
        setLocation(300,50);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setBackground(Color.BLACK);

        //PANELS
        //1st - Buttons at the top
        buttons1 = new JPanel();
        buttons1.setBounds(0, 0, 790,50);
        buttons1.setLayout(new GridLayout(1,3));

        //2nd - Buttons at the bottom
        buttons2 = new JPanel();
        buttons2.setBounds(0, 500, 790,50);
        buttons2.setLayout(new GridLayout(1,3));

        //3rd - Images Display
        labels = new JPanel();
        labels.setBounds(0,50,790,400);
        labels.setLayout(new GridLayout(1,2));

        information = new JPanel();
        information.setBounds(0,450,790,50);
        information.setBackground(Color.BLACK);
        information.setLayout(null);

        //4th - Original Image Display
        originalPanel = new JPanel();
        originalPanel.setBackground(Color.BLACK);
        originalPanel.setOpaque(true);

        //5th - Compressed Image Display
        compressedPanel = new JPanel();
        compressedPanel.setBackground(Color.BLACK);
        compressedPanel.setOpaque(true);

        //BUTTONS
        openImageButton = new JButton("Open PNG Image");
        openImageButton.setFont(font);
        openImageButton.setBackground(Color.BLACK);
        openImageButton.setForeground(Color.WHITE);
        openImageButton.setFocusPainted(false);
        openImageButton.addActionListener(this); 

        showImageButton = new JButton("Show Compressed Image");
        showImageButton.setFont(font);
        showImageButton.setBackground(Color.BLACK);
        showImageButton.setForeground(Color.WHITE);
        showImageButton.setFocusPainted(false);
        showImageButton.setEnabled(false);
        showImageButton.addActionListener(this);

        compressImageButton = new JButton("Compress Image");
        compressImageButton.setFont(font);
        compressImageButton.setBackground(Color.BLACK);
        compressImageButton.setForeground(Color.WHITE);
        compressImageButton.setFocusPainted(false);        
        compressImageButton.setEnabled(false);
        compressImageButton.addActionListener(this);

        trainNewButton = new JButton("Train New Huffman Tree");
        trainNewButton.setFont(font);
        trainNewButton.setBackground(Color.BLACK);
        trainNewButton.setForeground(Color.WHITE);
        trainNewButton.setFocusPainted(false);        
        trainNewButton.setEnabled(false);
        trainNewButton.addActionListener(this);

        trainExistingButton = new JButton("Train Existing Huffman Tree");
        trainExistingButton.setFont(font);
        trainExistingButton.setBackground(Color.BLACK);
        trainExistingButton.setForeground(Color.WHITE);
        trainExistingButton.setFocusPainted(false);        
        trainExistingButton.setEnabled(false);
        trainExistingButton.addActionListener(this);        

        resetButton = new JButton("Reset");
        resetButton.setFont(font);
        resetButton.setBackground(Color.BLACK);
        resetButton.setForeground(Color.WHITE);
        resetButton.setFocusPainted(false);                
        resetButton.setEnabled(false);
        resetButton.addActionListener(this);

        //LABELS
        //Background Images
        originalBG = new ImageIcon("OriginalBG.png");
        compressedBG = new ImageIcon("CompressedBG.png");

        originalLabel = new JLabel();
        originalLabel.setIcon(originalBG);
        originalLabel.setBounds(0,0,393,400);

        compressedLabel = new JLabel();
        compressedLabel.setIcon(compressedBG);
        //compressedLabel.setBounds(0,0,393,400);

        //Image Displays
        originalImage = new JLabel();
        compressedImage = new JLabel();

        //Scroll Panes
        originalScroll = new JScrollPane();
        originalScroll.setBorder(null);

        compressedScroll = new JScrollPane();
        compressedScroll.setBorder(null);

        //TEXT FIELD
        informationTextField = new JTextField("Huffman Image Compressor\t\t           Sulla - Eclipse (2019)");
        informationTextField.setBounds(10,10,765,30);
        informationTextField.setEditable(false);
        informationTextField.setOpaque(false);
        informationTextField.setBorder(null);
        informationTextField.setForeground(Color.WHITE);
        informationTextField.setFont(font);

        originalPanel.add(originalLabel);
        originalPanel.add(originalImage);
        compressedPanel.add(compressedLabel);
        compressedPanel.add(compressedImage);
        labels.add(originalPanel);
        labels.add(compressedPanel);
        information.add(informationTextField);

        buttons1.add(openImageButton);
        buttons1.add(compressImageButton);
        buttons1.add(showImageButton);
        buttons2.add(trainNewButton);
        buttons2.add(trainExistingButton);
        buttons2.add(resetButton);
        add(labels);
        add(buttons1);
        add(buttons2);
        add(information);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == openImageButton)
        {
            file = getFile(1);
            
           	if(file != null)
            {
           		informationTextField.setText("File Size: " + getFileSize(file));

                //ORIGINAL LABEL
               	originalLabel.setVisible(false);
               	originalPanel.setBackground(null);

               	//JLABEL for Original Image
               	originalImage.setIcon(new ImageIcon(file.getAbsolutePath()));
               	originalImage.setOpaque(false);
                	
               	originalPanel.add(originalScroll);
               	originalScroll.setPreferredSize(new Dimension(392,395));
               	originalScroll.setOpaque(false);
               	originalScroll.setViewportView(originalImage);
                
               	//Enabling new buttons
               	resetButton.setEnabled(true);
               	trainNewButton.setEnabled(true);
               	trainExistingButton.setEnabled(true);
           	}
        }

        if(e.getSource() == trainNewButton)
        {
            directory = getDirectory();
            
            if(directory != null)
            {
                compressImageButton.setEnabled(true);

                name = file.getName();
                System.out.println(name);
                int pos = name.lastIndexOf(".");
              	name = name.substring(0,pos);
                System.out.println(name);

                heap = new HeapToHuff(name);
                huffTreeOrig = heap.getHuffmanTree();
                SerializeTree en = new SerializeTree(huffTreeOrig, directory.getParentFile().getName(), "treefile.fl");
            }
        }

        /* if(e.getSource() == trainExistingButton)
        {
            file = getFile(2);

            if(file != null)
            {
                compressImageButton.setEnabled(true);    
                directory = getDirectory();
            }
        }*/

        if(e.getSource() == compressImageButton)
        {
            showImageButton.setEnabled(true);
            comp = new CompressImage(huffTreeOrig, huffTreeOrig.freq, name, "hello.xl");
        }

        if(e.getSource() == showImageButton)
        {
            
        	file = getFile(3);
        	decomp = new DecompressImage(huffTreeOrig, "mama", "hello.xl");
        	compressedLabel.setVisible(false);
            compressedPanel.setBackground(null);
        	compressedImage.setIcon(new ImageIcon(decomp.drawImage("hello.xl", huffTreeOrig)));
            informationTextField.setText(informationTextField.getText() + "\t\t             File Size: " + getFileSize(file));
            /*file = getFile(2);

            if(file != null)
            {
            	file = getFile(3);
            	if(file != null)
            	{
            		informationTextField.setText(informationTextField.getText() + "\t\t             File Size: " + getFileSize(file));
            		

            		compressedLabel.setVisible(false);
            		compressedPanel.setBackground(null);

		            compressedImage.setIcon(new ImageIcon(file.getAbsolutePath()));
        		    compressedPanel.add(compressedScroll);
            
		            compressedScroll.setPreferredSize(new Dimension(395,395));
        		    compressedScroll.setViewportView(compressedImage);

            	}
            }*/
        }

        if(e.getSource() == resetButton)
        {
            file = null;
            directory = null;
            informationTextField.setText("Huffman Image Compressor\t\t           Sulla - Eclipse (2019)");

            originalLabel.setVisible(true);
            compressedLabel.setVisible(true);
            originalPanel.setBackground(Color.BLACK);
            compressedPanel.setBackground(Color.BLACK);

            trainNewButton.setEnabled(false);
            trainExistingButton.setEnabled(false);
            compressImageButton.setEnabled(false);
            showImageButton.setEnabled(false);
            resetButton.setEnabled(false);
        }
    }
}