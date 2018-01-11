package geom;
public class Polyline extends Shape
{
	private Point points[];
	private int numPoints;
	
	public void setPoints(Point []varPt)
	{
		points = varPt;
	}
	public void setNumPoints(int varNumPt)
	{
		numPoints = varNumPt;
	}
	
	public Point[] getPoints()
	{
		return points ;
	}
	public int getNumPoints()
	{
		return numPoints ;
	}
}