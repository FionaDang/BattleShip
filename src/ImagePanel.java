/* Name: Fiona Dang
 * File: ImagePanel.java
 * Purpose: This ImagePanel class allows for the usage of a background image
 * Date: June 9, 2024
 **/

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class ImagePanel extends JComponent {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image image;
	private Image scaledimage;
    public ImagePanel(Image setImage) { //sets image to given image
        image = setImage;
        scaledimage = image;
        generateScaled();
    }
    
    public Dimension preferredSize() { //sets dimensions 
    	if (image == null) //if image does not exist, keep default dimension
    		return (new Dimension (1000, 600));
    	else //otherwise use image dimension
    		return (new Dimension (image.getWidth(getFocusCycleRootAncestor()), image.getHeight(getFocusCycleRootAncestor())));
    }
    
    /**
     * Scales the image
     */
    private void generateScaled() { 
    	if (image != null) //if image exists, scale it to fill
    		scaledimage = getScaledToFill(image, getSize());
    }
    
    /**
     * Scales the image to fill the required area
     * 
     * @param img
     * @param size
     * @return getScaled
     */
    private Image getScaledToFill(Image img, Dimension size) {
    	float scaleFactor = 2.5f;
    	return getScaled(img, scaleFactor);
    }
    
    /**
     *  Gets the scaled dimensions
     * 
     * @param img
     * @param factor
     * @return
     */
    private Image getScaled(Image img, double factor) {
        int targetWidth = (int) Math.round(img.getWidth(null) * factor); //gets width
        int targetHeight = (int) Math.round(img.getHeight(null) * factor); //gets height
        BufferedImage imgBuffer = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = imgBuffer.createGraphics();
        g.drawImage(img, 0, 0, targetWidth, targetHeight, null);
        g.dispose();
        return imgBuffer;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(scaledimage, 0, 0, this);
    }
}
