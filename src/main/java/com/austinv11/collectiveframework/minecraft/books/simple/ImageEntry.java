package com.austinv11.collectiveframework.minecraft.books.simple;

import com.austinv11.collectiveframework.minecraft.books.api.Entry;
import com.austinv11.collectiveframework.minecraft.utils.client.GuiUtils;
import com.austinv11.collectiveframework.utils.math.Ratio;
import com.austinv11.collectiveframework.utils.math.TwoDimensionalVector;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * This is an easy-to-use entry implementation for images
 */
public class ImageEntry extends Entry {
	
	/**
	 * Set this to true to scale the image to fit the width and height provided
	 */
	public boolean scaleToSize = true;
	/**
	 * Set this to true to retain the aspect ratio for the image when scaling to size
	 */
	public boolean retainRatio = true;
	private ResourceLocation image;
	private Ratio ratio;
	
	public ImageEntry(TwoDimensionalVector coords, int width, int height, ResourceLocation image) throws IOException {
		this(coords, width, height);
		this.image = image;
		BufferedImage backgroundImage = ImageIO.read(GuiUtils.getResourceAsStream(image));
		int backgroundWidth = backgroundImage.getWidth();
		int backgroundHeight = backgroundImage.getHeight();
		ratio = new Ratio(backgroundWidth, backgroundHeight);
	}
	
	private ImageEntry(TwoDimensionalVector coords, int width, int height) {
		super(coords, width, height);
	}
	
	@Override
	public void onRender(int dt) {
		GL11.glPushMatrix();
		double scaleX = 1, scaleY = 1;
		if (scaleToSize) {
			double[] ratio = this.ratio.getOriginalRatio();
			int width = (int) ratio[0];
			int height = (int) ratio[1];
			if (retainRatio) {
				scaleX = (double) super.width / (double) width;
				scaleY = (double) super.height / (double) height;
				double valueToScale = scaleX < scaleY ? scaleX : scaleY;
				GL11.glScaled(valueToScale, valueToScale, 1);
			} else {
				scaleX = (double) super.width / (double) width;
				scaleY = (double) super.height / (double) height;
				GL11.glScaled(scaleX, scaleY, 1);
			}
		}
		Minecraft.getMinecraft().getTextureManager().bindTexture(image);
		drawTexturedModalRect(getCoords().getRoundedX(), getCoords().getRoundedY(), 0, 0, (int) (super.width*(1/scaleX)), (int) (super.height*(1/scaleY)));
		GL11.glPopMatrix();
	}
}
