import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JProgressBar;
import javax.swing.filechooser.FileSystemView;
import javax.swing.plaf.ProgressBarUI;
import javax.xml.crypto.Data;
/*
 * Notes, changing extractInfoFromImage to in real time get characters out. Point of this
 * Is so szeto can stop generating prim when it hits an exit character for extraction
 * 
 */
public class szetograph {

	
	protected Picture picture;
	protected String base2Val;
	protected ArrayList<String> holdingNextBits;
	protected String binary;
	private LinkedList<String> allBits;
	protected Random random;
	protected int seed;
	protected LinkedList<String> hiddenInfo;
	protected File selectedFile;
	protected JProgressBar progressBar;
	protected primMaze prim;
	String words;
	
	/* stub kinda */
	
	public static void main(String[] args)
	{
		JProgressBar progressBar = new JProgressBar();
		szetograph szeto = new szetograph("7980", "D:\\workplace\\szetography\\gunterhidden.png","hmmIwonderWhatsWRong", progressBar);
		System.out.println(szeto.decrypt());
	//	szeto.encrypt();
		//szeto.getPrim().printPrim();
	}

	
	
	/*psuedo constructor used from GUI  */
	
	public szetograph(String seed, String location, String words,JProgressBar progressBar)
	{
		this.seed = Integer.parseInt(seed);
		picture = new Picture(location);
		random = new Random(this.seed);
		setAllBits(new LinkedList<String>());
		hiddenInfo = new LinkedList<String>();
		this.words = words;
		this.progressBar = progressBar;
		prim = new primMaze(picture.width(),picture.height(), this.seed);

	}
	

	public szetograph(String seed, String location, String words,JProgressBar progressBar,primMaze otherPrim)
	{
		this.seed = Integer.parseInt(seed);
		picture = new Picture(location);
		random = new Random(this.seed);
		setAllBits(new LinkedList<String>());
		hiddenInfo = new LinkedList<String>();
		this.words = words;
		this.progressBar = progressBar;
		prim = new primMaze(this.seed, otherPrim);
	}
	
	public void encrypt()
	{
		setWords(words);
		writeInformationToNewImage(prim);
		picture.show();
	}
	
	public primMaze getPrim()
	{
	return prim;
	}

	
	public String decrypt()
	{
		String returnedString;
		return extractInformation(prim);
		
		//returnedString = this.getDecypheredString();
		//if(returnedString!=null)
		//{
			//return returnedString;
		//}
		/* Hidden partion */
		/* This needs to be returned to. Until I have the write functionality of a hidden graph I cant do the read */
				
	//	return null;
	}
	

	
	public boolean setWords(String words) //returns true if successful false if it wont fit in image.
	{
		if((words.length()*8 + 8) > (picture.height()*picture.width() * 2))
		{
			popup.start("Words is too big for picture");
			return false;
		}
		assert(allBits.isEmpty());
		allBits = generateBinaryBits(words);
		return true;
	}
	

	protected void writeInformationToNewImage(primMaze prim)
	{
		progressBar.setMaximum(getAllBits().size());
		point startingPoint = prim.pointPrimMazeStart();
		point currentPoint;
		/* starting point */
		picture.set(startingPoint.getX(), startingPoint.getY(), changeColor(picture.get(startingPoint.getX(), startingPoint.getY()),getAllBits().remove(0),getAllBits().remove(0),getAllBits().remove(0)));
		for(int a = 0; a < getAllBits().size(); a++)
		{
			System.out.println("Current: " + a + " max: " + getAllBits().size());
			progressBar.setValue(a);
			progressBar.setMaximum(getAllBits().size());
			currentPoint = prim.getPointStep();
			picture.set(currentPoint.getX(), currentPoint.getY(), changeColor(picture.get(currentPoint.getX(), currentPoint.getY()), getAllBits().remove(0), getAllBits().remove(0), getAllBits().remove(0)));
		}
		
	}
	
	protected void writeInformationToSpecificBit(point editedPoint, String val1, String val2, String val3) //if you want to write info
	{
		Color changedColor = picture.get(editedPoint.getX(), editedPoint.getY());
		changedColor = changeColor(changedColor, val1, val2, val3);
		picture.set(editedPoint.getX(),editedPoint.getY(), changedColor);
	}
	
	/* extract information seems not to be working correctly */
	protected String extractInformation(primMaze prim)
	{
		int progressBarVal = 0;
		String returnedInfo = "";
		point startingPoint = prim.pointPrimMazeStart();
		point currentPoint;
		char currentChar;
		/* starting point */
		addColorToHiddenInfo(picture.get(startingPoint.getX(), startingPoint.getY()));
		progressBar.setMinimum(0);
		progressBar.setMaximum((picture.height()*picture.width())/3);
		System.out.println(prim.isPrimFinished());
		while(!prim.isPrimFinished())
		{
			progressBar.setValue(progressBarVal);
			currentPoint = prim.getPointStep();
			addColorToHiddenInfo(picture.get(currentPoint.getX(), currentPoint.getY()));
			if(hiddenInfo.size()%4==0)
			{
				 currentChar = getCharacterFromHiddenList();
				 if((int)currentChar==27) //if char and next char are exit chars
				 {
					 currentChar = getCharacterFromHiddenList();
					 if((int)currentChar==27)
					 {
						 break;
					 }
				 }
				// System.out.println(currentChar);
				 returnedInfo += currentChar +"";
			}
		}
		
		return returnedInfo; //stub
		//System.out.println(hiddenInfo);
	}

	
	protected void extractInformationFromLinkedList(LinkedList<point> llPoint)
	{
		assert(hiddenInfo.isEmpty());
		while(!llPoint.isEmpty())
		{
			addColorToHiddenInfo(picture.get(llPoint.get(0).getX(), llPoint.get(0).getY()));
			llPoint.remove(0);
		}
		
		//System.out.println(hiddenInfo);
	}
	
	protected LinkedList<String> generateBinaryBits(String message)
	{
		LinkedList<String> returnedList = new LinkedList<String>();
		ArrayList<String> splitMessage = new ArrayList<String>(Arrays.asList(message.split("")));
		for(int a = 0; a < splitMessage.size(); a++)
		{
			updateHoldingNextBits(splitMessage.get(a).charAt(0));
			returnedList.addAll(holdingNextBits);
		}
		returnedList.add("00"); // Exit Character
		returnedList.add("01");
		returnedList.add("10");
		returnedList.add("11");
		returnedList.add("00"); // Due to randomness it's possible for 27 to generate from random blocks so i put two you know
		returnedList.add("01"); // just in case
		returnedList.add("10");
		returnedList.add("11");
		
		//*3 because we have 3 different colors. This needs to change. Cant use plausible deniability with this 
		for(int a = returnedList.size(); a < (picture.height()* picture.width())*3; a++) //after we fill the allBits array with exit val + our data just fill the shit in with whateveer
		{
			returnedList.add(getRandomBinary());
		}
		
		return returnedList;
		
	}
	
	protected String getRandomBinary() {// gets binary stuff
		return (random.nextInt(2)+""+(random.nextInt(2)));
	}
	
	protected void updateHoldingNextBits(char character) {
	    binary = "0"+Integer.toBinaryString(character);
	    assert(holdingNextBits.isEmpty());
	    holdingNextBits = new ArrayList<String>(Arrays.asList(binary.split("(?<=\\G..)"))); //splits string into bits of twos
	}
	
	
	protected Color changeColor(Color changedColor, String val1, String val2, String val3)
	{
		return new Color(changeLSB(changedColor.getRed(), val1),changeLSB(changedColor.getGreen(),val2),changeLSB(changedColor.getBlue(), val3));
	}
	

    protected int changeLSB(int val, String changedBits)
    {		
        base2Val = Integer.toString(val,2);
        if(Integer.toString(val,2).length()<8)
        {
            String zeros = "";
            for(int a = 0; a < 8-Integer.toString(val,2).length(); a++)
            {
                zeros += "0";
            }
            base2Val = zeros + base2Val;
        }
        base2Val = base2Val.substring(0,8-changedBits.length()) + changedBits;
        return Integer.parseInt(base2Val, 2);
    }
    
    
    protected String getLSB(int val, int sigDigits)
    {		
        String base2Val = Integer.toString(val,2);
        if(Integer.toString(val,2).length()<8)
        {
            String zeros = "";
            for(int a = 0; a < 8-Integer.toString(val,2).length(); a++)
            {
                zeros += "0";
            }
            base2Val = zeros + base2Val;
        }
        base2Val = base2Val.substring(8-sigDigits,8);
        return base2Val;
    }
    
    /* debug
     * 
     */
    protected String getColorLSB(Color color)
    {		
    	assert(color!=null);
    	return getLSB(color.getRed(), 2) + getLSB(color.getGreen(), 2) + getLSB(color.getBlue(), 2);
    }
    
    protected void addColorToHiddenInfo(Color color)
    {		
    	assert(color!=null);
    	hiddenInfo.add(getLSB(color.getRed(), 2));
    	hiddenInfo.add(getLSB(color.getGreen(), 2));
    	hiddenInfo.add(getLSB(color.getBlue(), 2));
    }
    
    /*Runtime is 2N 
     * */ 
    public String getDecypheredString()
    {
    	String returnedString = "";
    	char returnedChar = ' '; //empty char
    
    	
    	while(!hiddenInfo.isEmpty() && hiddenInfo.size()>16) //if not empty and current value is not exit value
    	{
    		returnedString+=returnedChar;
    		returnedChar = getCharacterFromHiddenList();
    		if((int)returnedChar==27)
    		{
    			returnedChar = getCharacterFromHiddenList();
    			if((int)returnedChar==27)
    			{
    				break; //if we hit double exit characters we out 
    			}
    		}
    	}
    	
    	if(!((int)returnedChar==27))
		{
			return null; // if last character wasnt a returnChar then something wasnt working as intended, or plausible
		}
    	
    	return returnedString;
    }
	
    public String getHiddenListInString()
    {
    	String returnedString = "";
    
    	
    	while(hiddenInfo.size()>16)
    	{
    		returnedString += getCharacterFromHiddenList();
    	}
    	
    	return returnedString;
    }
    
    /* plz dont edit me*/
    public char getCharacterFromHiddenList()
    {
    	assert(!hiddenInfo.isEmpty());
    	assert(hiddenInfo.size()%4==0);
    	String word = hiddenInfo.remove(0) + hiddenInfo.remove(0) + hiddenInfo.remove(0) + hiddenInfo.remove(0);
    	return (char)Integer.parseInt(word, 2);
    }

	public LinkedList<String> getAllBits() {
		return allBits;
	}

	public void setAllBits(LinkedList<String> allBits) {
		this.allBits = allBits;
	}
	
	
	public Picture getPicture()
	{
		return picture;
	}
	
	public LinkedList<String> getHiddenInfo()
	{
		return hiddenInfo;
	}
    
	public void selectFile()
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		//int result = fileChooser.showOpenDialog(this);
	//	if (result == JFileChooser.APPROVE_OPTION) {
		    File selectedFile = fileChooser.getSelectedFile();
		    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
		//}
	}
	
	public void setFile(String location)
	{
		selectedFile = new File(location);
	}
	
	public void setSeed(String seed)
	{
	this.seed = Integer.parseInt(seed);	
	}
	
	public void showPicture()
	{
		picture.show();
	}
}
