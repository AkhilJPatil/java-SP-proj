package geom;
import java.awt.Color;

public class Shape
{
	private double strokeWidth;
	private Color strokeColor;
	private Color fillColor;
	private float transparency;
		
	//Getters
	public double getStrokeWidth()
	{
		return strokeWidth;
	}
	public Color getStrokeColor()
	{
		return strokeColor;
	}
	public Color getFillColor()
	{
		return fillColor;
	}	
	public float getTransparency()
	{
		return transparency;
	}	
	
	//Setters
	public void setStrokeWidth(double w)
	{
		strokeWidth = w;
	}
	public void setStrokeColor(Color sclr)
	{
		strokeColor = sclr;
	}
	public void setFillColor(Color fclr)
	{
		fillColor = fclr;
	}
	public void setTransparency(float trn)
	{
		transparency = trn;
	}
}