package texttoimage;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Main {

    public static void main(String[] args) {
 
      try {
		textToImage("দৃষ্টিভঙ্গি কেমন হতে পারে", 30);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

    }
    
    
    public  static void textToImage(String text,  float size) throws IOException {
        //Derives font to new specified size, can be removed if not necessary.
      
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        try{
            Graphics2D g2d = img.createGraphics();
         //   Font font = new Font("Arial", Font.PLAIN, 48);
            Font fnt=Font.createFont(Font.TRUETYPE_FONT, new File("/path/to/AponaLohit.ttf"));
            fnt = fnt.deriveFont(Font.PLAIN,size);
            System.out.println(fnt.getName());
            System.out.println(fnt.getNumGlyphs());
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
//            g2d.setBackground(Color.WHITE);
//            g2d.setColor(Color.BLUE);
            g2d.setColor(new Color(255, 255, 255));
            g2d.fillRect(0, 0, width, height);
            g2d.setColor(Color.BLACK);
            
            g2d.drawString(text, 0, fm.getAscent());
            g2d.dispose();
            
            try {
                ImageIO.write(img, "png", new File("2.png"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
	    }catch(Exception e){
	    e.printStackTrace();
	    }
    }
}
