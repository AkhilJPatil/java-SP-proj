package geom;
public class Point extends Shape
{
	private double x, y;
	public void setPosition(double x1,double y1)
	{
		x=x1;
		y=y1;
	}
	public String toString()
	{
		return "Point("+x+","+y+")";
	}
	public double distance(double x1,double y1)
	{
		return Math.sqrt(((x-x1)*(x-x1)) +((y-y1)*(y-y1)));
	}
	public double getX()
	{	
		return x;
	}
	public double getY()
	{	
		return y;
	}
	public void setX(double valx)
	{	
		x = valx;
	}
	public void setY(double valy)
	{	
		y = valy;
	}
	
}