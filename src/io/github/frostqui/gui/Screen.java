package io.github.frostqui.gui;

import java.util.Random;

import io.github.frostqui.world.Map;

public class Screen {

	private int w, h; // width and height of the screen
	public int[] pixels; // Pixels of the screen

	private Random random;

	public Camera camera;

	private final int ALPHA_COL = 0xffff00ff; // Color to make transparent

	public Screen(int w, int h) {
		this.w = w;
		this.h = h;
		this.pixels = new int[w * h];
		this.random = new Random();

		this.camera = new Camera(w, h);
	}

	/**
	 * Rendering the screen with pink pixels
	 * 
	 * @param xOff
	 * @param yOff
	 */

	public void render(int xOff, int yOff) {

		for (int j = 0; j < h; j++) {
			int yy = j + yOff;

			if (yy < 0 || yy >= h)
				break;

			for (int i = 0; i < w; i++) {
				int xx = i + xOff;

				if (xx < 0 || xx >= w)
					break;

				pixels[i + j * w] = random.nextInt(0xffffff);

			}

		}
	}

	public void fillRect(int xp, int yp, int width, int height, int color, boolean fixed) {
		
		if (fixed) {
			xp -= camera.x;
			yp -= camera.y;
		}

		for (int y = 0; y < height; y++) {
			int yo = yp + y;
			if (yo < 0 || yo >= this.h)
				continue;
			for (int x = 0; x < width; x++) {
				int xo = xp + x;
				if (xo < 0 || xo >= this.w)
					continue;
				pixels[xo + yo * this.w] = color;
			}
		}
	}

	/**
	 * Rendering a sprite int the game
	 * 
	 * @param x
	 *            coordinate
	 * @param y
	 *            coordinate
	 * @param sprite
	 *            to render
	 */

	public void renderSprite(int x, int y, Sprite sprite, boolean fixed) {

		for (int j = 0; j < sprite.size; j++) {
			int ya;
			if(fixed) {
				ya = j + y;
			}else {
				ya = j + y + camera.y;
			}
			
			for (int i = 0; i < sprite.size; i++) {
				int xa;
				
				if(fixed) {
					xa = i + x;
				}else {
					xa = i + x + camera.x;
				}
				
				if (xa < 0 || ya < 0 || xa >= w || ya >= h)
					break;
				if (sprite.pixels[i + j * sprite.size] != ALPHA_COL && sprite.pixels[i + j * sprite.size] != 0xff7f007f)
					pixels[xa + ya * w] = sprite.pixels[i + j * sprite.size];
			}
		}

	}

	/**
	 * Rendering a tile in the game
	 * 
	 * @param x
	 *            coordinate
	 * @param y
	 *            coordinate
	 * @param tile
	 *            to render
	 */

	public void renderTile(int x, int y, Tile tile) {

		for (int j = 0; j < tile.sprite.size; j++) {
			int ya = j + y - camera.y;
			for (int i = 0; i < tile.sprite.size; i++) {
				int xa = i + x - camera.x;

				if (xa < -tile.sprite.size || ya < 0 || xa >= w || ya >= h - 30)
					break;
				if (xa < 0)
					xa = 0;
				pixels[xa + ya * w] = tile.sprite.pixels[i + j * tile.sprite.size];
			}
		}

	}

	/**
	 * Rendering only one text character in the game
	 * 
	 * @param xp
	 *            x coordinate
	 * @param yp
	 *            y coordinate
	 * @param sprite
	 *            the character to render
	 * @param color
	 *            of the character
	 * @param fixed
	 *            ?
	 */

	public void renderTextCharacter(int xp, int yp, Sprite sprite, int color, boolean fixed) {
		if (fixed) {
			xp -= camera.y;
			yp -= camera.y;
		}
		for (int y = 0; y < sprite.getHeight(); y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.getWidth(); x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= w || ya < 0 || ya >= h)
					continue;
				int col = sprite.pixels[x + y * sprite.getWidth()];
				if (col != ALPHA_COL && col != 0xff7f007f)
					pixels[xa + ya * w] = color;
			}
		}
	}

	/**
	 * Clear the screen with black pixels
	 */

	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0x000000;
		}

	}

}
