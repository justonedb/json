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
 * Abstract JSON element
 * @author Duncan Pauly
 * @version 1.0
 */
public abstract class Element {
  
  /**
   * Indicates element is a null
   */
  public final static int NULL=1;
  /**
   * Indicates element is a boolean 
   */
  public final static int BOOLEAN=2;
  /**
   * Indicates element is a number 
   */
  public final static int NUMBER=3;
  /**
   * Indicates element is a string 
   */
  public final static int STRING=4;
  /**
   * Indicates element is an object 
   */
  public final static int OBJECT=5;
  /**
   * Indicates element is an array 
   */
  public final static int ARRAY=6;
  
  /**
   * Type of element
   */
  protected final int fType;
  /**
   * Parent of element
   */
  protected Element iParent;
  /**
   * Object key of the element
   */
  protected String iKey;
  /**
   * Array index of the element
   */
  protected int iIndex;
  
  /**
   * Constructor for element
   * @param aType 
   */
  protected Element(int aType) {
    
    fType=aType;//set element type
    
  }//Element()
  
 /**
   * Returns the element type
   * @return type of the element
   */
  public int type() {
    
    assert fType>0;
    
    return fType;//return element type
    
  }//type()

 /**
   * Returns the parent element
   * @return parent of the element
   */
  public Element getParentElement() {
    
    return iParent;//return parent element
    
  }//getParentElement()

 /**
   * Returns the key of this element
   * @return key of the element; or null if none 
   */
  public String getKey() {
    
    return iKey;//return key of this element
    
  }//getKey()

 /**
   * Returns the index of this element
   * @return index of this element; or -1 if none
   */
  public int getIndex() {
    
    return iIndex;//return index of this element
    
  }//getIndex()

  /**
   * Returns a JSON string representation of the element
   * @return element in string representation
   */
  @Override
  public abstract String toString();
  
  /**
   * Returns the number of child elements in the element
   * @return number of child elements in the element
   */
  public abstract int size();
 
  /**
   * Indicates if the element is a scalar element
   * @return true if the element is an object
   */
  public abstract boolean isScalar();

  /**
   * Indicates if the element is an object element
   * @return true if the element is an object
   */
  public abstract boolean isObject();
  
  /**
   * Indicates if the element is an array element
   * @return true if the element is an array
   */
  public abstract boolean isArray();
  
  /**
   * Indicates if there is a child element with a given key 
   * @param aKey key of the child element
   * @return true if there is a child element with the given key 
   */
  public abstract boolean hasKey(String aKey);
  
  /**
   * Indicates if a there is a child element with a given array index
   * @param aIndex index of the child element
   * @return true if there is a child element at the given index
   */
  public abstract boolean hasIndex(Integer aIndex);
  
  /**
   * Indicates if there is a child element with a given key pattern
   * @param aPattern key pattern of the child element
   * @return true if there is a child element with the given key pattern
   */
  public abstract boolean hasPattern(Pattern aPattern);

  /**
   * Returns the immediate child element with a given key
   * @param aKey key of the child element
   * @return child element with the key or null if none 
   */
  public abstract Element getChildElement(String aKey);
  
  /**
   * Returns the immediate child element with the given array index
   * @param aIndex index of the child element
   * @return child element at the given index or null if none
   */
  public abstract Element getChildElement(Integer aIndex);
  
  /**
   * Returns the first immediate child element with the given key pattern
   * @param aPattern pattern of the child element
   * @return first child element with the given pattern or null if none
   */
  public abstract Element getChildElement(Pattern aPattern);

  /**
   * Returns an iterator of immediate child elements
   * @return iterator for child elements
   */
  public abstract Iterator<Element> getChildElements();
  
  /**
   * Returns a list of all descendent elements with a given object key below this element 
   * @param aKey key of the descendent elements to be searched for
   * @param aList list to place descendent elements into
   * @return list containing descendent elements
   */
  public abstract LinkedList<Element> getDescendentElements(String aKey, LinkedList<Element> aList); 
    
  /**
   * Returns a list of all descendent scalar elements below this element 
   * @param aList list to place descendent scalar elements into
   * @return list containing descendent scalar elements
   */
  public abstract LinkedList<Element> getScalarElements(LinkedList<Element> aList);
  
}//Element{}
