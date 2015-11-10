package main.status;

import java.awt.Color;

import main.gfx.gui.Action;

public class Command {

	public static final Color ERROR = new Color(255, 0, 0);
	public static final Color NORMAL = new Color(255, 255, 255);

	private boolean noValue  = false;
	
	private String command;
	private String value = null;

	private Color textColor = NORMAL;

	private Action action;

	public Command(String command, String value, Action action) {
		this.command = command;
		this.value = value;
		this.action = action;
	}

	public Command(String originalCommand, Action action) {

		this.command = originalCommand;
		this.action = action;

	}

	public void performAction() {
		if ((value != null && value != "") || !noValue) {
			Action.manageActions(action, value);
			
			System.err.print("Command performed: ");
		}else{
			value = command;
			command = "Invalid Command: ";
			setTextColor(ERROR);
			
			System.err.print("Invalid Command: ");
		}
		
		System.err.println(getFullCommand());
	}

	public void setNoValue(boolean noValue) {
		this.noValue = noValue;
	}
	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCommand() {
		return command;
	}

	public String getValue() {
		return value;
	}

	public String getFullCommand() {
		return command + " " + value;
	}

	public Action getAction() {
		return action;
	}

	public Color getTextColor() {
		return textColor;
	}

	public void parse(String command) {
		String[] parts = command.split(" ");

		this.value = parts[1].trim();
	}

}
