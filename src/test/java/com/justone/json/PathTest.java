/*
 * The MIT License
 *
 * Copyright 2016 JustOne Database Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.justone.json;

import junit.framework.TestCase;

/**
 *
 * @author Duncan
 */
public class PathTest extends TestCase {
  
  public PathTest(String testName) {
    super(testName);
  }
  
  @Override
  protected void setUp() throws Exception {
    super.setUp();
  }
  
  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Test of toString method, of class Path.
   */
  public void testToString() {
    System.out.println("toString");
    
    Path instance = new Path("/@a");
    assertEquals("/@a", instance.toString());
    
    instance = new Path("\\@a");
    assertEquals("\\@a", instance.toString());

    instance = new Path("/@a/@b");
    assertEquals("/@a/@b", instance.toString());
  
    instance = new Path("\\@a\\@b");
    assertEquals("\\@a\\@b", instance.toString());
  
    instance = new Path("/#1");
    assertEquals("/#1", instance.toString());

    instance = new Path("/#1/#2");
    assertEquals("/#1/#2", instance.toString());

    instance = new Path("/?.");
    assertEquals("/?.", instance.toString());

    instance = new Path("/?./?.*");
    assertEquals("/?./?.*", instance.toString());

    instance = new Path("/");
    assertEquals("/", instance.toString());
        
  }//testToString()
  
  /**
   * Test of getTag method, of class Path.
   */
  public void testGetTag() {
    
    System.out.println("getTag");

    ObjectElement objectInstance = new ObjectElement();
    ScalarElement elementa=new ScalarElement();
    objectInstance.putElement("a",elementa);
    assertEquals("@a", Path.getTag(elementa));

    ScalarElement elementb=new ScalarElement();
    objectInstance.putElement("b",elementb);
    assertEquals("@b", Path.getTag(elementb));
        
    ArrayElement arrayInstance = new ArrayElement();
    ScalarElement element0=new ScalarElement();
    arrayInstance.addElement(element0);
    assertEquals("#0", Path.getTag(element0));
    
    ScalarElement element1=new ScalarElement();
    arrayInstance.addElement(element1);
    assertEquals("#1", Path.getTag(element1));

  }//testGetTag()  
  
  /**
   * Test of getPath method, of class Path.
   */
  public void testGetPath() {
    
    System.out.println("getPath");

    ObjectElement objectInstance = new ObjectElement();
    ScalarElement elementa=new ScalarElement();
    objectInstance.putElement("a",elementa);
    assertEquals("/@a", Path.getPath(elementa,'/'));
    assertEquals("\\@a", Path.getPath(elementa,'\\'));
        
    ArrayElement arrayInstance = new ArrayElement();
    ScalarElement element0=new ScalarElement();
    arrayInstance.addElement(element0);
    assertEquals("/#0", Path.getPath(element0,'/'));
    
    arrayInstance.addElement(objectInstance);
    assertEquals("/#1/@a", Path.getPath(elementa,'/'));

  }//testGetLabel()   
   
}//PathTest{}
