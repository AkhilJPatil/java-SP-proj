package geom;
public class Rectangle extends Shape
{
	Point topLeftPoint;
	double width, height;
	
	public double area()
	{	
		return width*height;
	}
	public double perimeter()
	{	
		return 2*(width+height);
	}
	public boolean contains(Point pt)
	{
		if(pt.getX()>topLeftPoint.getX() && pt.getX()<(topLeftPoint.getX()+width) && pt.getY()>topLeftPoint.getY() && pt.getY()<(topLeftPoint.getY()+height))
			return true;
		return false;
	}
	public Point centroid()
	{
		Point centpt = new Point();
		centpt.setX(topLeftPoint.getX()+(width/2));
		centpt.setY(topLeftPoint.getY()+(height/2));
		return centpt;
	}
	public void setTopLPt(Point pt)
	{	
		topLeftPoint = new Point();
		topLeftPoint.setX(pt.getX());
		topLeftPoint.setY(pt.getY());
	}
	public void setWidth(double wd)
	{
		width = wd;
	}
	public void setHeight(double ht)
	{
		height = ht;
	}
	public Point getTopLeftPoint()
	{
		return topLeftPoint;
	}
	public double getWidth()
	{
		return width;
	}
	public double getHeight()
	{
		return height;
	}

}