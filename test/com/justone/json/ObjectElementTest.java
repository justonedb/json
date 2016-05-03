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
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Duncan Pauly
 * @version 1.0
 */
public class ObjectElementTest {
  
  public ObjectElementTest() {
  }
  
  
  /**
   * Test of getParentElement method, of class ObjectElement.
   */
  @Test
  public void testGetParentElement() {
    
    System.out.println("getParentElement");
    
    ObjectElement instance = new ObjectElement();
    ScalarElement element=new ScalarElement("a");
    instance.putElement("a",element);
    assertEquals(instance, element.getParentElement());
    
  }//testGetParentElement()
 
  /**
   * Test of getKey method, of class ObjectElement.
   */
  @Test
  public void testGetKey() {
    
    System.out.println("getKey");
    
    ObjectElement instance = new ObjectElement();
    ScalarElement element=new ScalarElement();
    instance.putElement("a",element);
    assertEquals("a",element.getKey());

    element=new ScalarElement();
    instance.putElement("b",element);
    assertEquals("b",element.getKey());

  }//testGetKey()
  
  /**
   * Test of getIndex method, of class ObjectElement.
   */
  @Test
  public void testGetIndex() {
    
    System.out.println("getIndex");
    
    ObjectElement instance = new ObjectElement();
    ScalarElement element=new ScalarElement();
    instance.putElement("a",element);
    assertEquals(-1,element.getIndex());

  }//testGetIndex()
    
  /**
   * Test of isScalar method, of class ObjectElement.
   */
  @Test
  public void testIsScalar() {
    
    System.out.println("isScalar");

    ObjectElement instance = new ObjectElement();
    assertEquals(false, instance.isScalar());
    
  }//testIsScalar()
    
  /**
   * Test of isObject method, of class ObjectElement.
   */
  @Test
  public void testIsObject() {
    
    System.out.println("isObject");
    
    ObjectElement instance = new ObjectElement();
    assertEquals(true, instance.isObject());
    
  }//testIsObject()

  /**
   * Test of isArray method, of class ObjectElement.
   */
  @Test
  public void testIsArray() {
    
    System.out.println("isArray");
    
    ObjectElement instance = new ObjectElement();
    assertEquals(false, instance.isArray());
    
   }//testIsArray()

  /**
   * Test of toString method, of class ObjectElement.
   */
  @Test
  public void testToString() {
    
    System.out.println("toString");
    
    ObjectElement instance = new ObjectElement();
    assertEquals("{}", instance.toString());
    instance.putElement("a",new ScalarElement());
    assertEquals("{\"a\":null}", instance.toString());
    instance.putElement("b",new ScalarElement(false));
    assertEquals("{\"a\":null,\"b\":false}", instance.toString());
    instance.putElement("c",new ScalarElement(1));
    assertEquals("{\"a\":null,\"b\":false,\"c\":1}", instance.toString());
    instance.putElement("d",new ScalarElement(1.0));
    assertEquals("{\"a\":null,\"b\":false,\"c\":1,\"d\":1.0}", instance.toString());
    instance.putElement("e",new ScalarElement("string"));
    assertEquals("{\"a\":null,\"b\":false,\"c\":1,\"d\":1.0,\"e\":\"string\"}", instance.toString());
  
  }//testToString()

  /**
   * Test of size method, of class ObjectElement.
   */
  @Test
  public void testSize() {
    
    System.out.println("size");

    ObjectElement instance = new ObjectElement();
    assertEquals(0, instance.size());
    instance.putElement("a",new ScalarElement());
    assertEquals(1, instance.size());
    instance.putElement("b",new ScalarElement(false));
    assertEquals(2, instance.size());
    instance.putElement("c",new ScalarElement(1));
    assertEquals(3, instance.size());
    instance.putElement("d",new ScalarElement(1.0));
    assertEquals(4, instance.size());
    instance.putElement("e",new ScalarElement("string"));
    assertEquals(5, instance.size());

  }//testSize()

  /**
   * Test of hasKey method, of class ObjectElement.
   */
  @Test
  public void testHasKey() {
    
    System.out.println("hasKey");

    ObjectElement instance = new ObjectElement();
    assertEquals(false, instance.hasKey("a"));

    instance.putElement("a", new ScalarElement());
    assertEquals(true, instance.hasKey("a"));
    
  }//testHasKey()

  /**
   * Test of hasIndex method, of class ObjectElement.
   */
  @Test
  public void testHasIndex() {
    
    System.out.println("hasIndex");
    
    ObjectElement instance = new ObjectElement();
    assertEquals(false, instance.hasIndex(0));

    instance.putElement("a", new ScalarElement());
    assertEquals(false, instance.hasIndex(0));

  }//testHasIndex()

  /**
   * Test of getElement method, of class ObjectElement.
   */
  @Test
  public void testGetChildElement_String() {
    
    System.out.println("getChildElement");
    
    ObjectElement instance = new ObjectElement();
    assertEquals(null, instance.getChildElement("a"));
    
    Element nullElement=new ScalarElement();
    instance.putElement("a", nullElement);
    assertEquals(nullElement, instance.getChildElement("a"));
    
    Element booleanElement=new ScalarElement(false);
    instance.putElement("b", booleanElement);
    assertEquals(booleanElement,instance.getChildElement("b"));

  }//testGetElement_String()

  /**
   * Test of getElement method, of class ObjectElement.
   */
  @Test
  public void testGetChildElement_Integer() {
    
    System.out.println("getChildElement");
    
    ObjectElement instance = new ObjectElement();
    assertEquals(null, instance.getChildElement(0));

    instance.putElement("a", new ScalarElement());
    assertEquals(null, instance.getChildElement(0));
  
  }//testGetElement_Integer()

/**
   * Test of getChildElements method, of class ObjectElement.
   */
  @Test
  public void testGetChildElements() {
    
    System.out.println("getChildElements");
    
    ObjectElement instance = new ObjectElement();
    Iterator<Element> iterator = instance.getChildElements();
    assertEquals(false, iterator.hasNext());
    
    ScalarElement elementA=new ScalarElement();
    ScalarElement elementB=new ScalarElement();
    ScalarElement elementC=new ScalarElement();
    instance.putElement("a",elementA);    
    instance.putElement("b",elementB);    
    instance.putElement("c",elementC);    
     
    iterator = instance.getChildElements();
    assertEquals(elementA, iterator.next());
    assertEquals(elementB, iterator.next());
    assertEquals(elementC, iterator.next());
   
  }//testGetChildElements()  

  /**
   * Test of getDescendentElements method, of class ObjectElement.
   */
  @Test
  public void testGetDescendentElements() {
    
    System.out.println("getDescendentElements");
    
    ObjectElement objectInstance = new ObjectElement();
    Element element1=new ScalarElement();
    objectInstance.putElement("a", element1);
    LinkedList<Element>list = objectInstance.getDescendentElements("a",new LinkedList<Element>());
    
    assertTrue(list.contains(element1));
    assertEquals(1,list.size());
    
    ObjectElement parentInstance = new ObjectElement();
    parentInstance.putElement("b", objectInstance);
    
    list = parentInstance.getDescendentElements("a",new LinkedList<Element>());
    
    assertTrue(list.contains(element1));
    assertEquals(1,list.size());
    
    Element element2=new ScalarElement();
    parentInstance.putElement("a", element2);
    
    list = parentInstance.getDescendentElements("a",new LinkedList<Element>());
    
    assertTrue(list.contains(element1));
    assertTrue(list.contains(element2));
    assertEquals(2,list.size());

    ObjectElement rootInstance = new ObjectElement();
    rootInstance.putElement("c", parentInstance);
    
    list = rootInstance.getDescendentElements("a",new LinkedList<Element>());
    
    assertTrue(list.contains(element1));
    assertTrue(list.contains(element2));
    assertEquals(2,list.size());
    
  }//testGetDescendentElements()

  /**
   * Test of getScalarElements method, of class ObjectElement.
   */
  @Test
  public void testGetScalarElements() {
    
    System.out.println("getScalarElements");
    
    ScalarElement element = new ScalarElement();
    ObjectElement instance = new ObjectElement();
    instance.putElement("a",element);
    
    LinkedList<Element> list = instance.getScalarElements(new LinkedList<Element>());
    
    assertTrue(list.contains(element));
    assertEquals(1,list.size());
    
  }//testGetScalarElements()  
  
  /**
   * Test of getKeyIterator method, of class ObjectElement.
   */
  @Test
  public void testGetKeyIterator() {
    
    System.out.println("getKeyIterator");
    
    ObjectElement instance = new ObjectElement();
    Iterator<String> iterator = instance.getKeyIterator();
    assertEquals(false, iterator.hasNext());
    
    instance.putElement("a",new ScalarElement());    
    instance.putElement("b",new ScalarElement());    
    instance.putElement("c",new ScalarElement());    
     
    iterator = instance.getKeyIterator();
    assertEquals("a", iterator.next());
    assertEquals("b", iterator.next());
    assertEquals("c", iterator.next());
   
  }//testGetKeyIterator()
  
}//ObjectElementTest{}
