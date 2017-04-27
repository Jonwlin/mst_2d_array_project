import java.awt.Point;

public class point
{       
    private int x;
    private int y;
    private String distanceVal;
    
    public point(int x, int y)
    {
    	this.x= x;
    	this.y= y;
    	distanceVal = x + "" + y;// Tentative distance for compare to. Consider changing this to a hash
    }

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public String toString() {
	        return x + " " + y;
	}	
    

	@Override
	public boolean equals(Object object) { 
		
		//arraylist is not properly computing equals for compareTo or something, repeat elements found in array
		
		if(object==null || object.getClass() != getClass())
		{
			return false;
		}
		
		point other = (point) object;
		return this.distanceVal.equals(other.getDistanceVal());
	}
	
	@Override
	public int hashCode()
	{
		return Integer.parseInt(distanceVal);
	}
	
	public String getDistanceVal() {
		return distanceVal;
	}

	public void setDistanceVal(String distanceVal) {
		this.distanceVal = distanceVal;
	}
    
}