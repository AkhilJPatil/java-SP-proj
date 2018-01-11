package geom;
public class Label extends Shape
{	
	private Point position;
	private String text;
	
	public Point getPosition()
	{
		return position;
	}
	public String getText()
	{
		return text;
	}
	
	public void setPosition(Point pt)
	{
		position = new Point();
		position.setX(pt.getX()+5);
		position.setY(pt.getY()-5);
	}
	public void setText(String varText)
	{
		text = varText;
	}
}