package main.status;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import main.gfx.TextRenderer;

public class Messages {

	private MessageTypes type = MessageTypes.NONE;

	private Color customBGColor = Color.BLACK;
	private Color customColor = Color.WHITE;
	private String customText;
	private BufferedImage customImage;

	public void setCustomImage(BufferedImage image) {
		this.customImage = image;
	}
	
	public void setCustomBGColor(Color customBGColor) {
		this.customBGColor = customBGColor;
	}

	public void setCustomColor(Color customColor) {
		this.customColor = customColor;
	}

	public void setCustomText(String customText) {
		this.customText = customText;
	}

	public void setMessageType(MessageTypes type) {
		this.type = type;
	}

	public void render(Graphics g) {
		
		g.setFont(new Font("Franklin Gothic Demi", Font.BOLD, 20 / Game.PIXEL_SIZE));
		
		switch (type) {
		case CUSTOM_CENTERED:
			TextRenderer.writeTextBGCentered(g, this.customText, this.customColor, this.customBGColor);
			break;
		case CUSTOM_IMAGED_CENTERED:
			TextRenderer.writeTextBGImageCentered(g, this.customText, this.customColor, this.customBGColor, this.customImage);
			break;
		case LOADING:
			TextRenderer.writeTextBGCentered(g, "Loading...", Color.WHITE, Color.BLACK);
			break;
		case START:
			TextRenderer.writeTextBGCentered(g, "Press Enter to Start", Color.WHITE, Color.BLACK);
			break;
		case NONE:
			break;
		default:
			TextRenderer.writeTextBGCentered(g, "{No Status}", Color.RED, Color.WHITE);
		}
	}
}
