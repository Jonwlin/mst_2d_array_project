/**
 * Point class creates points on image to be used
 * 
 * @author Tyler Szeto
 * @version 1.0
 * @since 4-27-2017
 */

public class point {
  // tracks x and y value for point
  private int x;
  private int y;
  // String value for x added to y
  private String distanceVal;

  public point(int x, int y) {
    this.x = x;
    this.y = y;
    // Tentative distance for compare to. Consider changing this to a hash
    distanceVal = x + "" + y;
  }

  /**
   * returns x position of point
   * 
   * @return int x
   */
  public int getX() {
    return x;
  }

  /**
   * sets x position of point
   * 
   * @param int x
   */
  public void setX(int x) {
    this.x = x;
  }

  /**
   * gets y position of point
   * 
   * @return int y
   */
  public int getY() {
    return y;
  }

  /**
   * sets y position for point
   * 
   * @param int y
   */
  public void setY(int y) {
    this.y = y;
  }

  /**
   * toString prints out x and y position of point
   */
  public String toString() {
    return ("x position: " + x + " y position: " + y);
  }

  /**
   * equals method to check if two points are equal
   * 
   * @param object being compared to
   * @return boolean true if equal
   */
  @Override
  public boolean equals(Object object) {

    // ArrayList is not properly computing equals for compareTo or something,
    // repeat elements found in array

    if (object == null || object.getClass() != getClass()) {
      return false;
    }

    point other = (point) object;
    return this.distanceVal.equals(other.getDistanceVal());
  }

  /**
   * produces a hashCode for the point that is unique to this point
   * 
   * @return int value for this point
   */
  @Override
  public int hashCode() {
    return Integer.parseInt(distanceVal);
  }

  /**
   * getDistanceVal will return distance value
   * 
   * @return String for distance value
   */
  public String getDistanceVal() {
    return distanceVal;
  }

  /**
   * setDistanceVal will set the distance value
   * 
   * @param distanceVal the String value to set to
   */
  public void setDistanceVal(String distanceVal) {
    this.distanceVal = distanceVal;
  }

}