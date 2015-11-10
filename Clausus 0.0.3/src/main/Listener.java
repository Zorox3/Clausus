package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import main.gfx.gui.Action;
import main.gfx.gui.menu.StaticMenues;
import main.inventory.Inventory;
import main.level.Level;

public class Listener implements KeyListener, MouseListener,
		MouseMotionListener, MouseWheelListener {

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (!Game.showConsole) {
			switch (key) {
			case KeyEvent.VK_D:
				Game.isMoving = true;
				Game.dir = Game.player.movingSpeed;
				break;
			case KeyEvent.VK_A:
				Game.isMoving = true;
				Game.dir = -Game.player.movingSpeed;
				break;
			case KeyEvent.VK_SPACE:
			case KeyEvent.VK_W:
				Game.isJumping = true;
				break;
			case KeyEvent.VK_I:
				Inventory.isOpen = Inventory.isOpen ? false : true;
				break;
			case KeyEvent.VK_F10:
			case KeyEvent.VK_0:

				break;
			case KeyEvent.VK_F12:
			case KeyEvent.VK_8:

				break;
			case KeyEvent.VK_F4:
				Game.debugRendering++;
				if (Game.debugRendering > 2)
					Game.debugRendering = 0;
				break;

			case KeyEvent.VK_F5:
				Game.shadowDebug = Game.shadowDebug ? false : true;
				break;
			case KeyEvent.VK_UP:
				Game.gui.selected--;
				break;
			case KeyEvent.VK_DOWN:
				Game.gui.selected++;
				break;
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (!Game.showConsole) {
			switch (key) {
			case KeyEvent.VK_PLUS:
				Action.manageActions(Action.addTime);
				break;
			case KeyEvent.VK_MINUS:
				Action.manageActions(Action.removeTime);
				break;
			case KeyEvent.VK_F12:
				Action.manageActions(Action.switchTime);
				break;
			case KeyEvent.VK_V:
				Action.manageActions(Action.toggelVsync);
				break;

			case KeyEvent.VK_P:
				Level.CHUNKS_RENDERED++;
				if (Level.CHUNKS_RENDERED > Game.level.chunk.size()) {
					Game.level.generateChunk();
				}
				break;
			case KeyEvent.VK_O:
				if (Level.CHUNKS_RENDERED > Level.MINIMUM_CHUNKS_RENDERED) {
					Level.CHUNKS_RENDERED--;
				}
				break;
			case KeyEvent.VK_F3:
				Game.gameinfo = Game.gameinfo ? false : true;
				break;
			case KeyEvent.VK_ENTER:
				if (Game.gui.isActive())
					Game.gui.performAction();
				break;
			case KeyEvent.VK_D:
			case KeyEvent.VK_RIGHT:
				if (Game.dir == Game.player.movingSpeed) {
					Game.isMoving = false;
				}
				break;

			case KeyEvent.VK_A:
			case KeyEvent.VK_LEFT:
				if (Game.dir == -Game.player.movingSpeed) {
					Game.isMoving = false;
				}
				break;
			case KeyEvent.VK_SPACE:
			case KeyEvent.VK_W:
				Game.isJumping = false;
				break;
			case KeyEvent.VK_ESCAPE:
				if (Game.gui.isActive()) {
					Game.gui.setActive(false);
				} else {
					Game.switchGui(StaticMenues.pauseMenu());
				}
				break;

			}
		}else{
			if (key == KeyEvent.VK_ENTER) {
				Game.console.addCommand();
			}else if(key == KeyEvent.VK_BACK_SPACE){
				Game.console.removeLast();
			}
			else{
				Game.console.add2Command(e.getKeyChar());
			}
		}
		if (key == KeyEvent.VK_F9) {
			Action.manageActions(Action.showConsole);
		}


	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (e.getWheelRotation() > 0) {
			if (Game.gui.isActive()) {
				Game.gui.selected++;
			} else {
				if (Inventory.selected < Inventory.INV_CELL_LENGHT - 1) {
					Inventory.selected++;
				} else {
					Inventory.selected = 0;
				}
			}
		}
		if (e.getWheelRotation() < 0) {
			if (Game.gui.isActive()) {
				Game.gui.selected--;
			} else {
				if (Inventory.selected > 0) {
					Inventory.selected--;
				} else {
					Inventory.selected = Inventory.INV_CELL_LENGHT - 1;
				}
			}
		}

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Game.mouse.setLocation(e.getX() / Game.PIXEL_SIZE + Game.sX, e.getY()
				/ Game.PIXEL_SIZE + Game.sY);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Game.mouse.setLocation(e.getX() / Game.PIXEL_SIZE + Game.sX, e.getY()
				/ Game.PIXEL_SIZE + Game.sY);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (!Game.showConsole)
			if (Game.gui.isActive()) {
				Game.gui.performAction();
			}
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			Game.isLeftMousePressed = true;
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			Game.isRightMousePressed = true;
		}
		Inventory.click(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			Game.isLeftMousePressed = false;
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			Game.isRightMousePressed = false;
		}
	}

}
