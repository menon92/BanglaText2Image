import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.print.Printable;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class BanglaTextToImage {
	public static String BN_DATA_MAP_PATH = "../corpus/bn-data-map.txt";
	public static String FONTS_DIR = "../fonts";
	public static String DATA_SAVED_DIR = "../data";
	
	public static int[] FONT_SIZES = {30, 40, 50};
	public static int[] BG_COLOR   = {255, 230, 205};
	
	public static int IMAGE_NAME_COUNTER = 1;
	public static int NUMBER_OF_CLASS = 11; // number of class you have
	public static int CHUNK_TO_TAKE = 5; // CHUNK_TO_TAKE must be <= chunks.lenght()
	
    public static void main(String[] args) {
    	createFolders();
    	bnMakeDateset();
    	System.out.println("All data created.");
    }
    
    public static void bnMakeDateset() {
    	ArrayList<String> chunks = getChunks();
    	System.out.println(chunks);
    	
    	for(int i = 0; i < CHUNK_TO_TAKE; i++) {
    		String chunk = chunks.get(i);
        	File folderFonts = new File(FONTS_DIR);
        	File[] listOfFonts = folderFonts.listFiles();
        	int classFolderName = i;
    
        	for (File fontFile : listOfFonts) {
        		if (fontFile.isFile()) {
        		  // System.out.println("File Font name:" + fontFile.getName());
        		  // System.out.println("font abs path: " + fontFile.getAbsolutePath());
        		  String fontAbsPath = fontFile.getAbsolutePath();
        		  for (int fs : FONT_SIZES) {
        			  for (int bgColor : BG_COLOR) {
        				  bnTextToImage(chunk, fontAbsPath, fs, bgColor, classFolderName);
        				  System.out.printf("Write[Ok]:%4d, fs-%2d, bg-color:%3d, folder:%4d, Font:%s\n", IMAGE_NAME_COUNTER, fs, bgColor, i, fontFile.getName());
        			  }  
        		  }
        		} else if (fontFile.isDirectory()) {
        			System.out.println("Directory " + fontFile.getName());
        		}
        	}
        	// re-init IMAGE_NAME_COUNTER
        	IMAGE_NAME_COUNTER = 1;
    	}
    }
    
    public static void createFolders() {
    	// create all folder for all class
    	for(int i = 0; i < NUMBER_OF_CLASS; i++) {
    		File classNameFile = new File(DATA_SAVED_DIR + "/" + i);
    		if(!classNameFile.exists()) {
    			if(classNameFile.mkdirs()) {
    				System.out.printf("folder %d created.\n", i);
    			}
    			else {
    				System.out.printf("folder %d creation failed.\n", i);
    			}
    		}
    	}
    }
    
    public static ArrayList<String> getChunks() {
    	ArrayList<String> chunks = new ArrayList<String>();
    	// read & load map file
    	try {
    		BufferedReader br = new BufferedReader(new FileReader(BN_DATA_MAP_PATH));
    		
    	    for(String line; (line = br.readLine()) != null; ) {
    	        // System.out.println("line is: " + line);
    	        String tempString[] = line.split(" ");
    	        String chunk = tempString[0].trim();
    	        // System.out.println("chunk is: " + chunk);
    	        chunks.add(chunk);
    	    }
    	    br.close();
    	    // System.out.println(chunks);
    	} catch (IOException ex) {
            ex.printStackTrace();
        }
		
    	return chunks;
	}
    
    public static void bnTextToImage(String text,  String fontPath, float size, int bgColor, int classFolderName) {
        //Derives font to new specified size, can be removed if not necessary.
      
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        try{
            Graphics2D g2d = img.createGraphics();
            // Font font = new Font("Arial", Font.PLAIN, 48);
            Font fnt=Font.createFont(Font.TRUETYPE_FONT, new File(fontPath));
            fnt = fnt.deriveFont(Font.PLAIN,size);
            // System.out.println(fnt.getName());
            // System.out.println(fnt.getNumGlyphs());
            g2d.setFont(fnt);
            FontMetrics fm = g2d.getFontMetrics();
            int width = fm.stringWidth(text);
            int height = fm.getHeight();
            g2d.dispose();

            img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            
            g2d = img.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
            g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
          
            g2d.setFont(fnt);
            fm = g2d.getFontMetrics();
            g2d.setColor(new Color(bgColor, bgColor, bgColor));
            g2d.fillRect(0, 0, width, height);
            g2d.setColor(Color.BLACK);
            
            g2d.drawString(text, 0, fm.getAscent());
            g2d.dispose();
            
            // write image to disk
            try {
            	// ImageIO.write(img, "png", new File("2.png"));
            	ImageIO.write(img, "png", new File(DATA_SAVED_DIR + "/" + classFolderName + "/" + IMAGE_NAME_COUNTER + ".png"));
            	IMAGE_NAME_COUNTER += 1;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
	    } catch(Exception e){
	    	e.printStackTrace();
	    }
    }
}
