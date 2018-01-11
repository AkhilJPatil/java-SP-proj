package geom;
public class Circle extends Shape
{
	Point topLeftPoint;//earlier used it as center of the circle
	double diameter;
	
	public double area()
	{	
		return 3.142*(diameter/2)*(diameter/2);
	}
	public double perimeter()
	{	
		return 2*3.142*(diameter/2);
	}
	public boolean contains(Point pt)
	{
		Point centpt = new Point();
		centpt = this.centroid();
		if((diameter/2) > (centpt.distance(pt.getX(),pt.getY())))
			return true;
		return false;
	}
	public Point centroid()
	{
		Point cent = new Point();
		cent.setX(topLeftPoint.getX()+(diameter/2));
		cent.setY(topLeftPoint.getY()+(diameter/2));
		return cent;
	}
	public void setTopLeftPoint(Point pt)
	{
		topLeftPoint = new Point();
		topLeftPoint.setX(pt.getX());
		topLeftPoint.setY(pt.getY());
	}
	public void setDiameter(double di)
	{
		diameter = di;
	}
	public Point getTopLeftPoint()
	{
		return topLeftPoint;
	}
	public double getDiameter()
	{
		return diameter;
	}

}