package com.austinv11.collectiveframework.minecraft.books.core;

import com.austinv11.collectiveframework.minecraft.CollectiveFramework;
import com.austinv11.collectiveframework.minecraft.books.api.Book;
import com.austinv11.collectiveframework.minecraft.books.api.Entry;
import com.austinv11.collectiveframework.minecraft.books.api.InteractiveEntry;
import com.austinv11.collectiveframework.minecraft.books.api.Page;
import com.austinv11.collectiveframework.minecraft.utils.client.GuiUtils;
import com.austinv11.collectiveframework.utils.TimeProfiler;
import com.austinv11.collectiveframework.utils.math.TwoDimensionalVector;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

@SideOnly(Side.CLIENT)
public class GuiBook extends GuiScreen {
	
	private ItemBook bookItem;
	private ItemStack stack;
	private Book book;
	private TimeProfiler profiler;
	private EntityPlayer player;
	
	public GuiBook(EntityPlayer player) {
		FMLCommonHandler.instance().bus().register(this);
		MinecraftForge.EVENT_BUS.register(this);
		ItemStack heldItem = player.getHeldItem(EnumHand.MAIN_HAND);
		if (heldItem.isEmpty() || !(heldItem.getItem() instanceof ItemBook)) {
			GuiUtils.closeCurrentGui();
			return;
		}
		stack = heldItem;
		bookItem = (ItemBook) heldItem.getItem();
		try {
			book = bookItem.getBook().getConstructor(ItemBook.class, ItemStack.class).newInstance(bookItem, stack);
		} catch (Exception e) {
			CollectiveFramework.LOGGER.error("Exception opening book gui for "+bookItem.getClass().getName());
			e.printStackTrace();
			GuiUtils.closeCurrentGui();
			return;
		}
		book.handoffGui(this);
		this.player = player;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		if (profiler == null)
			profiler = new TimeProfiler();
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		//TODO: Maybe auto scale the gui?
		drawBackground();
		drawCurrentPage();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
//		CollectiveFramework.LOGGER.info(profiler.getTime());
		profiler = new TimeProfiler();
	}
	
	@Override
	public void initGui() {
		book.onOpen(player);
	}
	
	@Override
	public void updateScreen() {
		super.updateScreen();
	}
	
	private void drawEntries(Page page) {
		for (Entry entry : page.getEntries()) {
			GL11.glPushMatrix();
			if (!entry.useAbsoluteCoords && book.getBackground() != null) {
				try {
					ScaledResolution resolution = GuiUtils.getCurrentResolution();
					BufferedImage backgroundImage = ImageIO.read(GuiUtils.getResourceAsStream(book.getBackground()));
					int backgroundWidth = backgroundImage.getWidth();
					int backgroundHeight = backgroundImage.getHeight();
					int backX = (resolution.getScaledWidth() - backgroundWidth) / 2;
					int backY = (resolution.getScaledHeight() - backgroundHeight) / 2;
					GL11.glTranslated(backX, backY, 0);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			GL11.glRotatef(entry.getPitch(), 1, 0, 0);
			GL11.glRotatef(entry.getYaw(), 0, 1, 0);
			GL11.glRotatef(entry.getRotation(), 0, 0, 1);
			if (entry.drawDebugLines)
				drawRect(entry.getCoords().getRoundedX(), entry.getCoords().getRoundedY(), 
						entry.getCoords().getRoundedX()+entry.width, entry.getCoords().getRoundedY()+entry.height, 
						Color.LIGHT_GRAY.getRGB());
			entry.onRender((int) profiler.getTime());
			if (entry.drawDebugLines) {
				drawHorizontalLine(entry.getCoords().getRoundedX(), entry.getCoords().getRoundedX()+entry.width, 
						entry.getCoords().getRoundedY(), Color.BLACK.getRGB());
				drawHorizontalLine(entry.getCoords().getRoundedX(), entry.getCoords().getRoundedX()+entry.width,
						entry.getCoords().getRoundedY()+entry.height, Color.BLACK.getRGB());
				drawVerticalLine(entry.getCoords().getRoundedX(), entry.getCoords().getRoundedY(),
						entry.getCoords().getRoundedY()+entry.height, Color.BLACK.getRGB());
				drawVerticalLine(entry.getCoords().getRoundedX()+entry.width, entry.getCoords().getRoundedY(), 
						entry.getCoords().getRoundedY()+entry.height, Color.BLACK.getRGB());
			}
			GL11.glPopMatrix();
		}
	}
	
	private void drawCurrentPage() {
		int currentPage = book.getCurrentPage();
		Page page = book.getPages()[currentPage];
		ResourceLocation background = page.getBackground();
		ScaledResolution resolution = GuiUtils.getCurrentResolution();
		if (background != null)
			try {
				BufferedImage backgroundImage = ImageIO.read(GuiUtils.getResourceAsStream(background));
				int backgroundWidth = backgroundImage.getWidth();
				int backgroundHeight = backgroundImage.getHeight();
				int backX = (resolution.getScaledWidth() - backgroundWidth) / 2;
				int backY = (resolution.getScaledHeight() - backgroundHeight) / 2;
				Minecraft.getMinecraft().getTextureManager().bindTexture(background);
				drawTexturedModalRect(backX, backY, 0, 0, resolution.getScaledWidth(), resolution.getScaledHeight());
			} catch (IOException e) {
				e.printStackTrace();
			}
		page.onRender((int) profiler.getTime());
		drawEntries(page);
	}
	
	private void drawBackground() {
		ResourceLocation background = book.getBackground();
		ScaledResolution resolution = GuiUtils.getCurrentResolution();
		if (background != null)
			try {
				BufferedImage backgroundImage = ImageIO.read(GuiUtils.getResourceAsStream(background));
				int backgroundWidth = backgroundImage.getWidth();
				int backgroundHeight = backgroundImage.getHeight();
				int backX = (resolution.getScaledWidth() - backgroundWidth) / 2;
				int backY = (resolution.getScaledHeight() - backgroundHeight) / 2;
				Minecraft.getMinecraft().getTextureManager().bindTexture(background);
				drawTexturedModalRect(backX, backY, 0, 0, backgroundWidth, backgroundHeight);
			} catch (IOException e) {
				e.printStackTrace();
			}
		book.onRender((int) profiler.getTime());
		if (book.drawDebugLines) {
			for (int x = 0; x < resolution.getScaledWidth(); x++) {
				if (x % 2 == 0 && x % 4 != 0) {
					drawVerticalLine(x, 0, resolution.getScaledHeight(), Color.LIGHT_GRAY.getRGB());
				} else if (x % 4 == 0) {
					drawHorizontalLine(x, 0, resolution.getScaledHeight(), Color.GRAY.getRGB());
				}
			}
			for (int y = 0; y < resolution.getScaledHeight(); y++) {
				if (y % 2 == 0 && y % 4 != 0) {
					drawHorizontalLine(0, resolution.getScaledWidth(), y, Color.LIGHT_GRAY.getRGB());
				} else if (y % 4 == 0) {
					drawHorizontalLine(0, resolution.getScaledWidth(), y, Color.GRAY.getRGB());
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onKeyboardInput(InputEvent.KeyInputEvent event) {
		char eventChar = Keyboard.getEventCharacter();
		boolean isDown = Keyboard.getEventKeyState();
		Page page = book.getPages()[book.getCurrentPage()];
		for (Entry entry : page.getEntries())
			if (entry instanceof InteractiveEntry) {
				((InteractiveEntry) entry).onKeyboardEvent(eventChar, isDown);
			}
	}
	
	@SubscribeEvent
	public void onMouseInput(MouseEvent event) {
		Page page = book.getPages()[book.getCurrentPage()];
		for (Entry entry : page.getEntries())
			if (entry instanceof InteractiveEntry) {
				int offsetX = 0, offsetY = 0;
				TwoDimensionalVector startCoords = entry.getCoords();
				if (!entry.useAbsoluteCoords)
					try {
						ScaledResolution resolution = GuiUtils.getCurrentResolution();
						BufferedImage backgroundImage = ImageIO.read(GuiUtils.getResourceAsStream(book.getBackground()));
						int backgroundWidth = backgroundImage.getWidth();
						int backgroundHeight = backgroundImage.getHeight();
						offsetX = (resolution.getScaledWidth() - backgroundWidth) / 2;
						offsetY = (resolution.getScaledHeight() - backgroundHeight) / 2;
						startCoords = startCoords.add(new TwoDimensionalVector(offsetX, offsetY));
					} catch (Exception e) {
						e.printStackTrace();
					}
				if (event.getX() >= startCoords.getRoundedX() && event.getX() <= startCoords.getRoundedX()+entry.width
						&& event.getY() >= startCoords.getRoundedY() && event.getY() <= entry.height)
					((InteractiveEntry) entry).onMouseEvent(event.getX()-offsetX, event.getY()-offsetY, event.getDx(), event.getDy(), event.getButton(), event.getDwheel(), event.isButtonstate());
			}
	}
	
	@Override
	public void onGuiClosed() {
		book.onClose();
		FMLCommonHandler.instance().bus().unregister(this);
		MinecraftForge.EVENT_BUS.unregister(this);
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return book.doesPauseGame();
	}
}
