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
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;

/**
 * A JSON object element
 * @author Duncan Pauly
 * @version 1.0
 */
public class ObjectElement extends Element {

  /**
   * Map of child elements
   */
  private final TreeMap<String,Element> fObject;//map of values
  
  /**
   * Constructs an empty object
  */
  public ObjectElement() {
    
    super(OBJECT);//base constructor
    
    fObject=new TreeMap<>();//create empty child element map
    
  }//ObjectElement()
  
  /**
   * Constructs an object from a tree map of parsed values
   * @param aObject tree map of parsed values
   */
  public ObjectElement(TreeMap<String,Element> aObject) {
    
    super(OBJECT);//base constructor
    
    assert aObject!=null;
    fObject=aObject;//set object
     
    Iterator entries = fObject.entrySet().iterator();
    while (entries.hasNext()) {//for each element in the map
      Entry entry = (Entry) entries.next();//get next map entry
      String key = (String)entry.getKey();//get entry key
      Element element = (Element)entry.getValue();//get entry value
      element.iKey=key;//set element key
      element.iParent=this;//assign parent
      element.iIndex=-1;//no element index
    }//for each element in the map
       
  }//ObjectElement()
  
  /**
   * Puts an element into the object
   * @param aKey key for the element
   * @param aElement element to be put
   */
  public void putElement(String aKey, Element aElement) {
    
    assert aKey!=null;
    assert aElement!=null;
    assert fObject!=null;
    
    fObject.put(aKey,aElement);//add element to map
    aElement.iParent=this;//assign parent
    aElement.iKey=aKey;//set element key
    aElement.iIndex=-1;//no element index
        
  }//putElement{}

  /**
   * Always returns false
   * @return false
   */
  @Override
  public boolean isScalar() {
    
    assert fObject!=null;
    
    return false;//not a scalar
    
  }//isScalar{}

  /**
   * Always returns true
   * @return true
   */
  @Override
  public boolean isObject() {
    
    assert fObject!=null;
    
    return true;//is an object
    
  }//isObject{}

  /**
   * Always returns false
   * @return false
   */
  @Override
  public boolean isArray() {
    
    assert fObject!=null;
    
    return false;//not an array
    
  }//isArray{}
 
  /**
   * Returns JSON string representation of object value
   * @return string representation of object value
   */
  @Override
  public String toString() {
    
    assert fObject!=null;
    
    if (fObject.size()==0) return "{}";

    StringBuilder buffer=new StringBuilder();//buffer for string construction

    Iterator<Map.Entry<String, Element>> iterator=fObject.entrySet().iterator();//create map entry iterator
    while (iterator.hasNext()) {//for each map entry
      Map.Entry<String, Element> entry=iterator.next();//get next entry
      String key=entry.getKey();//get element key
      Element element=entry.getValue();//get child element

      /* append string of the form: ,"key":<element> */
      buffer.append(',');
      buffer.append('"');
      buffer.append(key.toString());
      buffer.append('"');
      buffer.append(':');
      buffer.append(element.toString());
    }//for each map entry
    
    return "{"+buffer.substring(1)+"}";//wrap in brackets and remove leading comma
    
  }//toString()
    
  /**
   * Returns the number of elements in the object
   * @return number of elements in the object
   */
  @Override
  public int size() {
    
    assert fObject!=null;  
    
    return fObject.size();//return size of the map
    
  }//size()
  
  /**
   * Indicates if object contains the key
   * @param aKey key to verify
   * @return true if key is known in this object
   */
  @Override
  public boolean hasKey(String aKey) {
    
    assert aKey!=null;
    assert fObject!=null;
    
    return fObject.containsKey(aKey);//map contains key?
    
  }//hasKey()

  /**
   * Always returns false
   * @param aIndex index to verify
   * @return always returns false
   */
  @Override
  public boolean hasIndex(Integer aIndex) {
    
    assert fObject!=null;
    
    return false;//not an array
    
  }//hasIndex()
  
  /**
   * Indicates if object contains the key
   * @param aPattern key pattern to verify
   * @return true if key is known in this object
   */
  @Override
  public boolean hasPattern(Pattern aPattern) {
    
    assert aPattern!=null;
    assert fObject!=null;
    
    Iterator iterator=getKeyIterator();//get iterator for all child keys
    while (iterator.hasNext()) {//for all child keys
      String key=(String)iterator.next();//get next child key
      if (aPattern.matcher(key).matches()) {//if child key matches the pattern
        return true;//we have a match
      }//if child key matches the pattern
    }//for all child keys
    
    return false;//no match found
    
  }//hasPattern()

  /**
   * Returns the parsed element associated with the key
   * @param aKey key to retrieve by
   * @return 
   */
  @Override
  public Element getChildElement(String aKey) {
    
    assert aKey!=null;    
    assert fObject!=null;
    
    return fObject.get(aKey);//get element from map
    
  }//getChildElement()
  
  /**
   * Always returns null
   * @param aIndex index to retrieve by
   * @return always returns null
   */
  @Override
  public Element getChildElement(Integer aIndex) {
    
    assert aIndex>=0;    
    assert fObject!=null;
    
    return null;//not an array
    
  }//getChildElement()
  
  /**
   * Returns the parsed element associated with the key pattern
   * @param aPattern key pattern to retrieve by
   * @return 
   */
  @Override
  public Element getChildElement(Pattern aPattern) {
    
    assert aPattern!=null;    
    assert fObject!=null;
    
    Iterator iterator=getChildElements();//get iterator for all child elements
    while (iterator.hasNext()) {//for all child elements
      Element element=(Element)iterator.next();//get next child element
            
      if ((element.iKey!=null)&&(aPattern.matcher(element.iKey).matches())) {//if child key matches the pattern
        return element;//we have found a matching child
      }//if child key matches the pattern
    }//for all child elements
    
    return null;//no match found
    
  }//getChildElement()
  
  /**
   * Returns an iterator for the child elements
   * @return iterator for the child elements
   */
  @Override
  public Iterator<Element> getChildElements() {
    
    assert fObject!=null;
    
    return fObject.values().iterator();//return map value iterator
    
  }//getChildElements()

  /**
   * Returns a list of all descendent elements with a given key below this element 
   * @param aKey key of the element to be searched for
   * @param aList list to place descendent elements into
   * @return list containing descendent elements
   */
  @Override
  public LinkedList<Element> getDescendentElements(String aKey, LinkedList<Element> aList) {
    
    assert aKey!=null;
    assert aList!=null;
    
    Iterator<Element> iterator=getChildElements();//get child element iterator
    while (iterator.hasNext()) {//while iterator not empty
      Element element=iterator.next();//get next child element
      if (element.iKey.equals(aKey)) {//if child has the key being searched for
        aList.add(element);//add child to the list
      } else {//else child does not have the key being searched for
        element.getDescendentElements(aKey, aList);//get descendents from child
      }//if child has the key being searched for
    }//while iterator not empty      

    return aList;
    
  }//getDescendentElements    

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
      iterator.next().getScalarElements(aList);//get scalar elements from child
    }//while child iterator not empty      
    
    return aList;//we are done
    
  }//getScalarElements      
  
  /**
   * Returns an iterator for the object element keys
   * @return iterator for the object element keys
   */
  public Iterator<String> getKeyIterator() {
    
    assert fObject!=null;
    
    return fObject.keySet().iterator();//return key iterator from map
    
  }//getKeyIterator()
  
}//ObjectElement
