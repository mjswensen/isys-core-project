/////////////////////////////////////////////////////////////////
///   This file is an example of an Object Relational Mapping in
///   the ISys Core at Brigham Young University.  Students
///   may use the code as part of the 413 course in their
///   milestones following this one, but no permission is given
///   to use this code is any other way.  Since we will likely
///   use this code again in a future year, please DO NOT post
///   the code to a web site, share it with others, or pass
///   it on in any way.


package edu.byu.isys413.data;

/**
 * An employee.  Example BO that has inheritance.
 *
 * @author Conan C. Albrecht <conan@warp.byu.edu>
 * @version 1.1
 */
public class Employee extends Person {

    @BusinessObjectField
    private String username = null;
    @BusinessObjectField
    private String password = null;
    @BusinessObjectField
    private java.util.Date birthdate = null;
    @BusinessObjectField
    private double salary = 0.0;
    @BusinessObjectField
    private float favoriteNumber = 0.0f;
    @BusinessObjectField
    private int IQ = 0;
    @BusinessObjectField
    private long distance = 0;

    /** Creates a new instance of BusinessObject */
    public Employee(String id) {
        super(id);
    }//constructor

    /**
     * Returns the username.
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     *  Sets the username.
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
        setDirty();
    }

    /**
     * Returns the password.
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
        setDirty();
    }

  /**
   * @return the birthdate
   */
  public java.util.Date getBirthdate() {
    return birthdate;
  }

  /**
   * @param birthdate the birthdate to set
   */
  public void setBirthdate(java.util.Date birthdate) {
    this.birthdate = birthdate;
    setDirty();
}

  /**
   * @return the salary
   */
  public double getSalary() {
    return salary;
  }

  /**
   * @param salary the salary to set
   */
  public void setSalary(double salary) {
    this.salary = salary;
    setDirty();
  }

  /**
   * @return the favoriteNumber
   */
  public float getFavoriteNumber() {
    return favoriteNumber;
  }

  /**
   * @param favoriteNumber the favoriteNumber to set
   */
  public void setFavoriteNumber(float favoriteNumber) {
    this.favoriteNumber = favoriteNumber;
    setDirty();
  }

  /**
   * @return the IQ
   */
  public int getIQ() {
    return IQ;
  }

  /**
   * @param IQ the IQ to set
   */
  public void setIQ(int IQ) {
    this.IQ = IQ;
    setDirty();
  }

  /**
   * @return the distance
   */
  public long getDistance() {
    return distance;
  }

  /**
   * @param distance the distance to set
   */
  public void setDistance(long distance) {
    this.distance = distance;
    setDirty();
  }

  
}//class
