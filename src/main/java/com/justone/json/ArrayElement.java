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

import java.util.*;
import java.util.regex.Pattern;
/**
 * A JSON array element
 * @author Duncan Pauly
 * @version 1.0
 */
public class ArrayElement extends Element {

  /**
   * List of child elements
   */
  private final LinkedList<Element> fArray;
  
  /**
   * Constructs an empty array
   */
  public ArrayElement() {
    
    super(ARRAY);//base constructor
    fArray=new LinkedList<>();//create empty linked list
    
  }//ArrayElement{}

  /**
   * Constructs an array from a linked list of elements
   * @param aArray linked list of elements 
   */
  public ArrayElement(LinkedList<Element> aArray) {
    super(ARRAY);//base constructor
    assert aArray!=null;
    
    fArray=aArray;//set linked list

    Iterator iterator=fArray.iterator();
    int index=0;
    while (iterator.hasNext()) {//for each element
      ++index;
      Element element=(Element)iterator.next();
      element.iParent=this;//assign parent
      element.iIndex=index;//set element index
      element.iKey=null;//no element key
    }//for each element
    
  }//ArrayElement{}
  
  /**
   * Adds an element to the array
   * @param aElement element to be added to the array
   */
  public void addElement(Element aElement) {
    
    assert aElement!=null;
    assert fArray!=null;
    
    fArray.add(aElement);//add element
    aElement.iParent=this;//assign parent
    aElement.iIndex=fArray.size()-1;//set element index
    aElement.iKey=null;//no element key

  }//addElement{}

  /**
   * Always returns false
   * @return false
   */
  @Override
  public boolean isScalar() {
    
    assert fArray!=null;
    
    return false;//not a scalar
    
  }//isScalar{}

  /**
   * Always returns false
   * @return false
   */
  @Override
  public boolean isObject() {
    
    assert fArray!=null;
    
    return false;//not an object
    
  }//isObject{}

  /**
   * Always returns true
   * @return true
   */
  @Override
  public boolean isArray() {
    
    assert fArray!=null;
    
    return true;//is an array
    
  }//isArray{}

  /**
   * Returns a JSON string representation of the array element
   * @return string representation of the array element
   */
  @Override
  public String toString() {
    
    assert fArray!=null;
    
    if (fArray.size()==0) return "[]";//if empty array
    
    /* append each element in the linked list */
    StringBuilder buffer=new StringBuilder();//create string buffer
    Iterator iterator=fArray.iterator();//iterator for array elements
    while (iterator.hasNext()) {//for each element in the array
      buffer.append(',');//append element separator
      buffer.append(iterator.next());//append element
    }//for each element in the array
    
    return "["+buffer.substring(1)+"]";//wrap in brackets and remove leading comma
    
  }//toString()

  /**
   * Returns the number of elements in the array
   * @return number of elements in the array
   */
  @Override
  public int size() {
    
    assert fArray!=null;    
    
    return fArray.size();//number of elements
    
  }//size()
  
  /**
   * Always returns false
   * @param aKey key to verify
   * @return always returns false
   */
  @Override
  public boolean hasKey(String aKey) {
    
    assert aKey!=null;
    assert fArray!=null;
    
    return false;//array has no keys
    
  }//hasKey()
  
  /**
   * Indicates if the array index exists
   * @param aIndex index to verify
   * @return true if the element index exists
   */
  @Override
  public boolean hasIndex(Integer aIndex) {
    
    assert aIndex>=0;    
    assert fArray!=null;
    
    return (aIndex<fArray.size());//if index less than number of elements
    
  }//hasIndex()

  /**
   * Always returns false
   * @param aPattern key pattern to verify
   * @return always returns false
   */
  @Override
  public boolean hasPattern(Pattern aPattern) {
    
    assert aPattern!=null;
    assert fArray!=null;
    
    return false;//array has no keys
    
  }//hasPattern()

  /**
   * Always returns null
   * @param aKey key to retrieve by
   * @return always returns null
   */
  @Override
  public Element getChildElement(String aKey) {
    
    assert aKey!=null;
    assert fArray!=null;
    
    return null;//array has no keys
    
  }//getChildElement()

  /**
   * Returns element at array index
   * @param aIndex index to retrieve by
   * @return element at the index position or null if none
   */
  @Override
  public Element getChildElement(Integer aIndex) {
    
    assert aIndex>=0;
    assert fArray!=null;
    
    if (aIndex>=fArray.size()) return null;//if index beyond last element
    return fArray.get(aIndex);//return element
    
  }//getChildElement()

  /**
   * Always returns null
   * @param aPattern key pattern to retrieve by
   * @return always returns null
   */
  @Override
  public Element getChildElement(Pattern aPattern) {
    
    assert aPattern!=null;
    assert fArray!=null;
    
    return null;//array has no keys
    
  }//getChildElement()

  /**
   * Returns an iterator for elements in the array
   * @return iterator for the elements in the array
   */
  @Override
  public Iterator<Element> getChildElements() {
    
    assert fArray!=null;
    
    return fArray.iterator();//return linked list iterator
    
  }//getChildElements()
  
  /**
   * Returns all descendent elements in this array 
   * @param aKey key of the element to be searched for
   * @param aList list to place descendent elements into
   * @return list containing descendent elements
   */
  @Override
  public LinkedList<Element> getDescendentElements(String aKey, LinkedList<Element> aList) {
    
    assert aKey!=null;
    assert aList!=null;
    
    Iterator<Element> iterator=getChildElements();//get child element iterator
    while (iterator.hasNext()) {//while child iterator not empty
      iterator.next().getDescendentElements(aKey, aList);//get descendent element in child
    }//while child iterator not empty      
    
    return aList;//that's all folks
    
  }//getDescendentElements()    

  /**
   * Returns descendent scalar elements 
   * @param aList list to place descendent scalar elements into
   * @return list with descendent scalar element added to it
   */
  @Override
  public LinkedList<Element> getScalarElements(LinkedList<Element> aList) {
    
    assert aList!=null;
    
    Iterator<Element> iterator=getChildElements();//get child element iterator
    while (iterator.hasNext()) {//while child iterator not empty
      iterator.next().getScalarElements(aList);//add scalar elements from child
    }//while child iterator not empty      
    
    return aList;//we are done
    
  }//getScalarElements()    

}//ArrayElement{} 
  