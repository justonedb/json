/*

MIT License
 
Copyright (c) 2016 JustOne Database Inc

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

*/

package com.justone.json;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Pattern;

/**
 * A JSON scalar element (null, boolean, number or string)
 * @author Duncan Pauly
 * @version 1.0
 */
public class ScalarElement extends Element {

  private final String fScalar;//scalar value

  /**
   * Constructs a generic scalar element from a string
   * @param aType scalar type
   * @param aValue string representation of the value
   */
  protected ScalarElement(int aType, String aValue) {
    
    super(aType);//base constructor
    
    assert aValue!=null;
    assert (aType>=NULL)&&(aType<=STRING);
    
    fScalar=aValue;
    
  }//ScalarElement()

  /**
   * Constructs a string scalar value
   * @param aValue string value
   */
  public ScalarElement(String aValue) {
    
    super(STRING);//base constructor
    
    assert aValue!=null;
    fScalar=aValue;
    
  }//ScalarElement()

  /**
   * Constructs a boolean scalar value
   * @param aValue boolean value
   */
  public ScalarElement(boolean aValue) {
    
    super(BOOLEAN);//base constructor
    
    fScalar=String.valueOf(aValue);
    
  }//ScalarElement()

  /**
   * Constructs an integer scalar value
   * @param aValue integer value
   */
  public ScalarElement(long aValue) {
    
    super(NUMBER);//base constructor
    
    fScalar=String.valueOf(aValue);
    
  }//ScalarElement()

  /**
   * Constructs a floating number scalar value
   * @param aValue floating number value
   */
  public ScalarElement(double aValue) {
    
    super(NUMBER);//base constructor
    
    fScalar=String.valueOf(aValue);
    
  }//ScalarElement()

  /**
   * Constructs a null scalar value
   */
  public ScalarElement() {
    
    super(NULL);//base constructor
    
    fScalar="null";
    
  }//ScalarElement()

  /**
   * Always returns true
   * @return true
   */
  @Override
  public boolean isScalar() {
    
    assert fScalar!=null;
    
    return true;//is a scalar
    
  }//isScalar{}

  /**
   * Always returns false
   * @return false
   */
  @Override
  public boolean isObject() {
    
    assert fScalar!=null;
    
    return false;//not an object
    
  }//isObject{}

  /**
   * Always returns false
   * @return false
   */
  @Override
  public boolean isArray() {
    
    assert fScalar!=null;
    
    return false;//not an array
    
  }//isArray{}
 
  /**
   * Return JSON string representation of scalar value
   * @return string representation of scalar value
   */
  @Override
  public String toString() {
    
    assert fScalar!=null;
    
    if (fType==STRING) {//if string value
      return '"'+fScalar+'"';//enclose string value in quotes
    } else {//else non string value
      return fScalar;
    }//if text value
    
  }//toString()

  /**
   * Always returns 0
   * @return 0
   */
  @Override
  public int size() {
    
    assert fScalar!=null;
    
    return 0;//no child elements
    
  }//size()
      
  /**
   * Always returns false
   * @param aKey key to verify
   * @return always returns false
   */
  @Override
  public boolean hasKey(String aKey) {
    
    assert aKey!=null;//assert key is defined
    
    assert fScalar!=null;
    
    return false;//not an object
    
  }//hasKey()

  /**
   * Always returns false
   * @param aIndex index to verify
   * @return always returns false
   */
  @Override
  public boolean hasIndex(Integer aIndex) {
    
    assert aIndex>=0;
    assert fScalar!=null;
    
    return false;//not an array
    
  }//hasIndex()

  /**
   * Always returns false
   * @param aPattern key pattern to verify
   * @return always returns false
   */
  @Override
  public boolean hasPattern(Pattern aPattern) {
    
    assert aPattern!=null;//assert pattern is defined
    
    assert fScalar!=null;
    
    return false;//not an object
    
  }//hasPattern()

  /**
   * Always returns null
   * @param aKey key to retrieve by
   * @return always returns null
   */
  @Override
  public Element getChildElement(String aKey) {
    
    assert aKey!=null;
    assert fScalar!=null;
    
    return null;//not an object
    
  }//getChildElement()
  
  /**
   * Always returns null
   * @param aIndex index to retrieve by
   * @return always returns null
   */
  @Override
  public Element getChildElement(Integer aIndex) {
    
    assert aIndex>=0;
    assert fScalar!=null;
    
    return null;//not an array
    
  }//getChildElement()
  
  /**
   * Always returns null
   * @param aPattern key pattern to retrieve by
   * @return always returns null
   */
  @Override
  public Element getChildElement(Pattern aPattern) {
    
    assert aPattern!=null;
    assert fScalar!=null;
    
    return null;//not an array
    
  }//getChildElement()

  /**
   * Always returns null
   * @return always returns null
   */
  @Override
  public Iterator<Element> getChildElements() {
    
    return null;
    
  }//getChildElements()
    
  /**
   * Returns no descendent elements 
   * @param aKey key of the element to be searched for
   * @param aList list to place descendent elements into
   * @return list containing no new elements
   */
  @Override
  public LinkedList<Element> getDescendentElements(String aKey, LinkedList<Element> aList) {
    
    assert aKey!=null;
    assert aList!=null;
    
    return aList;//move along, nothing to see here
    
  }//getDescendentElements()    
  
  /**
   * Returns this element 
   * @param aList list to place descendent elements into
   * @return list with this element added to it
   */
  @Override
  public LinkedList<Element> getScalarElements(LinkedList<Element> aList) {
    
    assert aList!=null;
    
    aList.add(this);//add this element to the list
    return aList;//we are done
    
  }//getScalarElements()    

}//ScalarElement{} 
  