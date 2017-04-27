import java.awt.Color;
import java.awt.Point;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class primMaze {
	
	private int primArray[][];
	private ArrayList<point> bagOfPoints;
	private HashSet<point> bagOfPointsHash;
	private int seed;
	private int xSize;
	private int ySize;
	private point nextPoint;
	private int count;
	private int replaceNumber;
	private HashSet<point> headerHash;
	private static BigInteger countingInts= new BigInteger("1");
	
	Random random;
	
	public static void main(String[] args)
	{
		int count = 0;
		primMaze prim = new primMaze(5, 5, 3291050);
		prim.pointPrimMazeStart(); 
		while(!prim.isPrimFinished())
		{
			prim.getPointStep();
			System.out.println(" ");
		}
		System.out.println(prim.returnBigInt());

	}
	

	
	public primMaze(int xSize, int ySize, int seed)
	{
		this.xSize = xSize;
		this.ySize = ySize;
		this.count = 0;
		primArray = new int[xSize][ySize];
		bagOfPoints = new ArrayList<point>();
		bagOfPointsHash = new HashSet<point>();
		headerHash = new HashSet<point>();
		this.seed = seed;
		random = new Random(seed);
		replaceNumber = 1; //default, should only be changed/ use dif constructor when debugging 
	}

	
	@SuppressWarnings("unchecked")
	public primMaze(int seed,primMaze otherPrim) //lets you instantiate a new prim 
	{																	// and copies over the visitedPoints. new seed and bagOfPoints
		this.xSize = otherPrim.getxSize();
		this.ySize = otherPrim.getySize();
		primArray = new int[xSize][ySize];
		bagOfPoints = new ArrayList<point>();
		bagOfPointsHash = new HashSet<point>();
		headerHash = new HashSet<point>();
		this.seed = seed;
		this.replaceNumber = 1;
		count = otherPrim.getCount();
		random = new Random(seed);
		
		
		primArray = new int[otherPrim.getPrimArray().length][];
		for(int i = 0; i < otherPrim.getPrimArray().length; i++)
		    primArray[i] = otherPrim.getPrimArray()[i].clone();
		
	}
	
	@SuppressWarnings("unchecked")
	public primMaze(int seed,primMaze otherPrim,int replaceNumber) //lets you instantiate a new prim 
	{																	// and copies over the visitedPoints. new seed and bagOfPoints
		this.xSize = otherPrim.getxSize();
		this.ySize = otherPrim.getySize();
		primArray = new int[xSize][ySize];
		bagOfPoints = new ArrayList<point>();
		bagOfPointsHash = new HashSet<point>();
		headerHash = new HashSet<point>();
		this.seed = seed;
		this.count = otherPrim.getCount();
		this.replaceNumber = replaceNumber;
		random = new Random(seed);
		
		
		primArray = new int[otherPrim.getPrimArray().length][];
		for(int i = 0; i < otherPrim.getPrimArray().length; i++)
		    primArray[i] = otherPrim.getPrimArray()[i].clone();
		
	}
	
	public primMaze(int xSize, int ySize, int seed, int replaceNumber)
	{
		this.xSize = xSize;
		this.ySize = ySize;
		primArray = new int[xSize][ySize];
		bagOfPoints = new ArrayList<point>();
		bagOfPointsHash = new HashSet<point>();
		this.seed = seed;
		random = new Random(seed);
		this.replaceNumber = 1; //default, should only be changed/ use dif constructor when debugging 
	}

	public point getPointStep()
	{
		if(bagOfPoints.size()==0)
		{
			System.out.println(bagOfPoints.size());
			System.out.println("theres no more points left");
			return null;
		}
		
		nextPoint = bagOfPoints.remove(randInt(0, bagOfPoints.size()-1));
	 
		addElementToPrimArray(nextPoint.getX(),nextPoint.getY(),replaceNumber);
		updateBag(nextPoint.getX(), nextPoint.getY());
		return nextPoint;
	}
	
	public point pointPrimMazeStart() //places the first point on primm maze
	{
		point start = getFragmentedPoint();
		addElementToPrimArray(start.getX(),start.getY(),replaceNumber);
		updateBag(start.getX(), start.getY());
		return (new point(start.getX(), start.getY()));
	}
	
	/* this is a slow method. Maybe implement stuff later if it slows program down but its only a 2d array so */
	private point getFragmentedPoint()
	{
		int xStart = randInt(0,xSize-1);
		int yStart = randInt(0,ySize-1);
		while(!(primArray[xStart][yStart]==0)) //checks if starting point is a "visited" point. I could find a faster way to do this but it
		{									// it only needs to really do it once
			xStart = randInt(0,xSize-1); //Uhhmmm embarrassing needed to be - 1 because reasons
			yStart = randInt(0,ySize-1);
			System.out.println("searching");
		}
		return new point(xStart,yStart);
	}
	
	public LinkedList<point> getHeaderPoint(int size)
	{
		LinkedList<point> returnedList = new LinkedList<point>();
		outerloop:
		for(int x = 0; x < xSize; x++)
		{
			for(int y = 0; y < ySize; y++)
			{
				returnedList.add(new point(x,y));
				headerHash.add(new point(x, y));
				addElementToPrimArray(x,y,8);
				if(returnedList.size()==size) //only add until it its the size
				{
					break outerloop;
				}
			}
		}
		return returnedList;
	}
	
	public HashSet<point> getBagOfPointsHash()
	{
		return bagOfPointsHash;
	}
	
    public void printPrim()
    {
        for(int x = 0; x <  primArray.length; x++)
        {
            for(int y = 0; y < primArray[x].length; y++)            
            {
                System.out.print(primArray[x][y]);
            }
            System.out.println("");
        }
    }
	
	private void updateBag(int x, int y)
	{

		try
		{
			if(primArray[x][y+1]==0)
			{
				if(!headerHash.contains(new point(x,y+1)) && bagOfPointsHash.add(new point(x, y+1))) //if element not in set.. 
				{
				bagOfPoints.add(new point(x, y+1));
				}
				//primArray[x][y+1]=8;
			}
		}
		catch(IndexOutOfBoundsException e)
		{
			
		}
		
		try
		{
			if(primArray[x][y-1]==0)
			{
				if(!headerHash.contains(new point(x,y-1)) && bagOfPointsHash.add(new point(x, y-1))) //if element not in set.. or header
				{
				bagOfPoints.add(new point(x, y-1));
				}
			}
		}
		catch(IndexOutOfBoundsException e)
		{
			
		}
		
		try
		{
			if(primArray[x+1][y]==0)
			{
				if(!headerHash.contains(new point(x+1,y)) && bagOfPointsHash.add(new point(x+1, y))) //if element not in set.. 
				{
				bagOfPoints.add(new point(x+1, y));
				}
			}
		}
		catch(IndexOutOfBoundsException e)
		{
			
		}
		
		try
		{
			if(primArray[x-1][y]==0)
			{
				if(!headerHash.contains(new point(x-1,y)) && bagOfPointsHash.add(new point(x-1, y))) //if element not in set.. 
				{
				bagOfPoints.add(new point(x-1, y));
				}
			}
		}
		catch(IndexOutOfBoundsException e)
		{
			
		}
		
	//	printPrim();
		//printBagOfPoints();
	}
	
	private int randInt(int min, int max) { //non inclusive 
		return random.nextInt(max - min + 1) + min;
	}
	
	public int[][] getPrimArray() {
		return primArray;
	}


	public void setPrimArray(int[][] primArray) {
		this.primArray = primArray;
	}


	public ArrayList<point> getBagOfPoints() {
		return bagOfPoints;
	}


	public void setBagOfPoints(ArrayList<point> bagOfPoints) {
		this.bagOfPoints = bagOfPoints;
	}


	public int getSeed() {
		return seed;
	}


	public void setSeed(int seed) {
		this.seed = seed;
	}


	public int getxSize() {
		return xSize;
	}


	public void setxSize(int xSize) {
		this.xSize = xSize;
	}


	public int getySize() {
		return ySize;
	}


	public void setySize(int ySize) {
		this.ySize = ySize;
	}


	public point getNextPoint() {
		return nextPoint;
	}


	public void setNextPoint(point nextPoint) {
		this.nextPoint = nextPoint;
	}


	public Random getRandom() {
		return random;
	}


	public void setRandom(Random random) {
		this.random = random;
	}


	public void setBagOfPointsHash(HashSet<point> bagOfPointsHash) {
		this.bagOfPointsHash = bagOfPointsHash;
	}


	public void setCount(int count) {
		this.count = count;
	}


	public void printBagOfPoints()
	{
		System.out.println("arraylist" + bagOfPoints);
		System.out.println("hash" + bagOfPointsHash);
	}
	
	
	public boolean isPrimFinished()
	{
		if(bagOfPoints.size()==0) //searches for stragglers 
		{
			outer:
			for(int x = 0; x < xSize; x++)
			{
				for(int y =0; y < ySize; y++)
				{
					if(primArray[x][y]==0)
					{
						System.out.println("found straggler at" + x +" " + y);
						bagOfPoints.add(new point(x,y));
						bagOfPointsHash.add(new point(x,y));
						break outer;
					}
				}
			}
		}
		return bagOfPoints.size()==0;
	}
	
	public int getCount()
	{
		return count;
	}
	
	private void addElementToPrimArray(int x, int y, int value) //security measures to ensure a point shouldnt be added where it shouldnt
	{

		BigInteger factorial = factorial(bagOfPoints.size());
		printPrim();
		System.out.println(factorial);
		System.out.println(bagOfPoints);
		countingInts = countingInts.multiply(new BigInteger((factorial+"")));
		if(primArray[x][y]==0)
		{
			count++;
			primArray[x][y]=value;
			bagOfPoints.remove(new point(x,y));
			return;
		}
		else
		{
			System.out.println("Error");
			System.out.println(x + " " + y);
			System.out.println("At" + primArray[x][y]);
			assert false;
		}
	}
	
	public BigInteger returnBigInt()
	{
		return countingInts;
	}
	
    public static BigInteger factorial(long n) {
        BigInteger fact = new BigInteger("1"); // this  will be the result
        for (int i = 1; i <= n; i++) {
    		fact = fact.multiply(new BigInteger((i+"")));
        }
        return fact;
    }
	

}
