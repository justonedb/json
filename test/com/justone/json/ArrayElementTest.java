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

import java.util.LinkedList;
import java.util.Iterator;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 * @author Duncan Pauly
 * @version 1.0
 */
public class ArrayElementTest {
  
  public ArrayElementTest() {
  }

  /**
   * Test of getParentElement method, of class ArrayElement.
   */
  @Test
  public void testGetParentElement() {
    
    System.out.println("getParentElement");
    
    ArrayElement instance = new ArrayElement();
    ScalarElement element=new ScalarElement("a");
    instance.addElement(element);
    assertEquals(instance, element.getParentElement());

  }//testGetParentElement()
 
  /**
   * Test of getKey method, of class ArrayElement.
   */
  @Test
  public void testGetKey() {
    
    System.out.println("getKey");
    
    ArrayElement instance = new ArrayElement();
    ScalarElement element=new ScalarElement();
    instance.addElement(element);
    assertNull(element.getKey());

  }//testGetKey()
  
  /**
   * Test of getIndex method, of class ArrayElement.
   */
  @Test
  public void testGetIndex() {
    
    System.out.println("getIndex");
    
    ArrayElement instance = new ArrayElement();
    ScalarElement element=new ScalarElement();
    instance.addElement(element);
    assertEquals(0,element.getIndex());

    element=new ScalarElement();
    instance.addElement(element);
    assertEquals(1,element.getIndex());

  }//testGetIndex()
  
  
  /**
   * Test of isScalar method, of class ArrayElement.
   */
  @Test
  public void testIsScalar() {
    
    System.out.println("isScalar");

    ArrayElement instance = new ArrayElement();
    assertEquals(false, instance.isScalar());
    
  }//testIsScalar()
    
  /**
   * Test of isObject method, of class ArrayElement.
   */
  @Test
  public void testIsObject() {
    
    System.out.println("isObject");
    
    ArrayElement instance = new ArrayElement();
    assertEquals(false, instance.isObject());

    LinkedList<Element> list=new LinkedList<>();
    list.add(new ScalarElement());
    instance = new ArrayElement(list);
    assertEquals(false, instance.isObject());
    
  }//testIsObject()

  /**
   * Test of isArray method, of class ArrayElement.
   */
  @Test
  public void testIsArray() {
    
    System.out.println("isArray");
    
    ArrayElement instance = new ArrayElement();
    assertEquals(true, instance.isArray());

    LinkedList<Element> list=new LinkedList<>();
    list.add(new ScalarElement());
    instance = new ArrayElement(list);
    assertEquals(true, instance.isArray());
        
  }//testIsArray()

  /**
   * Test of toString method, of class ArrayElement.
   */
  @Test
  public void testToString() {
    
    System.out.println("toString");
    
    ArrayElement instance = new ArrayElement();
    assertEquals("[]", instance.toString());
    
    LinkedList<Element> list=new LinkedList<>();
    list.add(new ScalarElement());
    instance = new ArrayElement(list);
    assertEquals("[null]", instance.toString());

    list=new LinkedList<>();
    list.add(new ScalarElement(false));
    instance = new ArrayElement(list);
    assertEquals("[false]", instance.toString());

    list=new LinkedList<>();
    list.add(new ScalarElement(1));
    instance = new ArrayElement(list);
    assertEquals("[1]", instance.toString());

    list=new LinkedList<>();
    list.add(new ScalarElement(1.0));
    instance = new ArrayElement(list);
    assertEquals("[1.0]", instance.toString());

    list=new LinkedList<>();
    list.add(new ScalarElement("string"));
    instance = new ArrayElement(list);
    assertEquals("[\"string\"]", instance.toString());
    
    list=new LinkedList<>();
    list.add(new ScalarElement());
    list.add(new ScalarElement(false));
    list.add(new ScalarElement(1));
    list.add(new ScalarElement(1.0));
    list.add(new ScalarElement("string"));
    instance = new ArrayElement(list);
    assertEquals("[null,false,1,1.0,\"string\"]", instance.toString());

  }//testToString()

  /**
   * Test of size method, of class ArrayElement.
   */
  @Test
  public void testSize() {
    
    System.out.println("size");
    
    ArrayElement instance = new ArrayElement();
    assertEquals(0, instance.size());

    instance = new ArrayElement();
    instance.addElement(new ScalarElement());
    assertEquals(1, instance.size());

    instance.addElement(new ScalarElement(false));
    assertEquals(2, instance.size());

    instance.addElement(new ScalarElement(1));
    assertEquals(3, instance.size());
    
    instance.addElement(new ScalarElement(1.0));
    assertEquals(4, instance.size());
    
    instance.addElement(new ScalarElement("string"));
    assertEquals(5, instance.size());

  }//testSize()

  /**
   * Test of hasKey method, of class ArrayElement.
   */
  @Test
  public void testHasKey() {
    
    System.out.println("hasKey");
    
    ArrayElement instance = new ArrayElement();
    assertEquals(false, instance.hasKey("a"));

    instance.addElement(new ScalarElement());
    assertEquals(false, instance.hasKey("a"));
  
    instance.addElement(new ScalarElement(false));
    assertEquals(false, instance.hasKey("a"));
  
    instance.addElement(new ScalarElement(1));
    assertEquals(false, instance.hasKey("a"));
    
    instance.addElement(new ScalarElement(1.0));
    assertEquals(false, instance.hasKey("a"));
    
    instance.addElement(new ScalarElement("string"));
    assertEquals(false, instance.hasKey("a"));

  }//testHasKey()

  /**
   * Test of hasIndex method, of class ArrayElement.
   */
  @Test
  public void testHasIndex() {
    
    System.out.println("hasIndex");
    
    ArrayElement instance = new ArrayElement();
    assertEquals(false, instance.hasIndex(0));
    
    instance.addElement(new ScalarElement());
    assertEquals(true, instance.hasIndex(0));

    assertEquals(false, instance.hasIndex(1));

    instance.addElement(new ScalarElement());
    assertEquals(true, instance.hasIndex(1));
  
  }//testHasIndex()

  /**
   * Test of getElement method, of class ArrayElement.
   */
  @Test
  public void testGetChildElement_String() {
    
    System.out.println("getChildElement");
    
    ArrayElement instance = new ArrayElement();
    assertEquals(null, instance.getChildElement("a"));

    instance.addElement(new ScalarElement());
    assertEquals(null, instance.getChildElement("a"));
    
  }//testGetElement_String()

  /**
   * Test of getElement method, of class ArrayElement.
   */
  @Test
  public void testGetChildElement_Integer() {
    
    System.out.println("getChildElement");
    
    ArrayElement instance = new ArrayElement();
    assertEquals(null, instance.getChildElement(0));

    Element nullElement=new ScalarElement();
    instance.addElement(nullElement);
    Element booleanElement=new ScalarElement(false);
    instance.addElement(booleanElement);
    Element integerElement=new ScalarElement(1);
    instance.addElement(integerElement);
    Element floatElement=new ScalarElement(1.0);
    instance.addElement(floatElement);
    Element stringElement=new ScalarElement("string");
    instance.addElement(stringElement);

    assertEquals(nullElement, instance.getChildElement(0));
    assertEquals(booleanElement,instance.getChildElement(1));
    assertEquals(integerElement,instance.getChildElement(2));
    assertEquals(floatElement,instance.getChildElement(3));
    assertEquals(stringElement,instance.getChildElement(4));
    assertEquals(null,instance.getChildElement(5));
            
  }//testGetElement_Integer()

  /**
   * Test of getChildElements method, of class ArrayElement.
   */
  @Test
  public void testGetChildElements() {
    
    System.out.println("getChildElements");
    
    ArrayElement instance = new ArrayElement();
    Iterator<Element> iterator = instance.getChildElements();
    
    assertEquals(false, iterator.hasNext());
    
    Element nullElement=new ScalarElement();
    instance.addElement(nullElement);
    Element booleanElement=new ScalarElement(false);
    instance.addElement(booleanElement);
    Element integerElement=new ScalarElement(1);
    instance.addElement(integerElement);
    Element floatElement=new ScalarElement(1.0);
    instance.addElement(floatElement);
    Element stringElement=new ScalarElement("string");
    instance.addElement(stringElement);

    iterator = instance.getChildElements();
    assertEquals(nullElement, iterator.next());
    assertEquals(booleanElement, iterator.next());
    assertEquals(integerElement, iterator.next());
    assertEquals(floatElement, iterator.next());
    assertEquals(stringElement, iterator.next());
    
  }//testGetChildElements()
  
  /**
   * Test of getDescendentElements method, of class ArrayElement.
   */
  @Test
  public void testGetDescendentElements() {
    
    System.out.println("getDescendentElements");
    
    ArrayElement arrayInstance = new ArrayElement();
    arrayInstance.addElement(new ScalarElement());
    LinkedList<Element> list = arrayInstance.getDescendentElements("a",new LinkedList<Element>());
    
    assertTrue(list.isEmpty());
    
    ObjectElement objectInstance = new ObjectElement();
    Element element1=new ScalarElement();
    objectInstance.putElement("a", element1);
    arrayInstance.addElement(objectInstance);
    list = arrayInstance.getDescendentElements("a",new LinkedList<Element>());
    
    assertTrue(list.contains(element1));
    assertEquals(1,list.size());

  }//testGetDescendentElements()

  /**
   * Test of getScalarElements method, of class ArrayElement.
   */
  @Test
  public void testGetScalarElements() {
    
    System.out.println("getScalarElements");
    
    ScalarElement element = new ScalarElement();
    ArrayElement instance = new ArrayElement();
    instance.addElement(element);
    
    LinkedList<Element> list = instance.getScalarElements(new LinkedList<Element>());
    
    assertTrue(list.contains(element));
    assertEquals(1,list.size());
    
  }//testGetDescendentElements()
  
}//ArrayElementTest{}
