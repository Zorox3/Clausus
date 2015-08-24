package main.extentions;

public class DoubleRectangle {

	public double x, y, width, height;
	
	public DoubleRectangle(){
		setBounds(0, 0, 0, 0);
	}
	
	public DoubleRectangle(int x, int y, int width, int height){
		setBounds(x, y, width, height);

	}
	
	public void setBounds(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
}
