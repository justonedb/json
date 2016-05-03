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

import java.util.TreeMap;
import java.util.LinkedList;

/**
 * Parses JSON messages using a DOM parsing model.
 * <P>
 * This package is intended for use in parsing streamed JSON messages which contain some expected
 * structure. A single parser instance is able to parse multiple messages and the navigation path 
 * to reach an element of interest need only be defined once prior to parsing any messages. 
 * Methods are also provided for exploration of the message structure after it has been parsed. 
 * </P>
 * <P>
 * A JSON message is parsed into a hierarchy of elements where each element is 
 * a scalar, array or object element. Use the parse() method to parse one or more messages.
 * </P>
 * <P>
 * Use getRootElement() to return the root element and use getElement() to return an element found 
 * at a specified path from the parsed message. See Path for details. The hierarchy 
 * can then be navigated thereafter using element methods.
 * </P>
 * <P>
 * Use the contains(), containsAll() and containsAny() methods to test the existence of one or more
 * paths within a parsed message.
 * </P>
 * <br>
 * Code example:
 * <pre><code>
 * 
 * // construct parser 
 * Parser parser = new Parser();
 * 
 * // construct paths for expected elements
 * Path identityPath=new Path("/@identity");
 * Path latitudePath=new Path("/@location/@latitude");
 * Path longitudePath=new Path("/@location/@longitude");
 * 
 * // parse a JSON message
 * parser.parse("{\"identity\":12345,\"location\":{\"latitude\":51.5047650,\"longitude\":-2.4841220}}");
 * 
 * // get elements by their path
 * Element identityElement=parser.getElement(identityPath);
 * Element latitudeElement=parser.getElement(latitudePath);
 * Element longitudeElement=parser.getElement(longitudePath);
 * 
 * // output elements as strings
 * System.out.println("id="+identityElement.toString()+" loc="+latitudeElement.toString()+","+longitudeElement.toString());
 * 
 * </code></pre>
 *    
 * @author Duncan Pauly
 * @version 1.0
 */
public class Parser {

  /**
   * Represents no character at end of message
   */
  private static final char NONE=0;
  /**
   * Prefix character for object key label
   */
  public static final char KEY='@';
  /**
   * Prefix character for array index label
   */
  public static final char INDEX='#';
  /**
   * Buffer for string manipulation
   */
  private final StringBuilder fBuffer;
  
  /**
   * Message to be parsed
   */
  private String iMessage;
  
  /**
   * Character position in message being parsed
   */
  private int iIndex;
  /**
   * Root element of parsed message
   */
  private Element iRootElement;
  
  /**
   * Constructor for parser
   */
  public Parser() {
    
    fBuffer=new StringBuilder();//create string buffer
    
  }//Parser()
   
  /**
   * Parses a JSON message
   * @param aMessage message to be parsed
   */
  public void parse(String aMessage) {
    
    assert aMessage!=null;
    assert fBuffer!=null;
    
    iMessage=aMessage;//set parse string
    iIndex=0;//start parsing at first character
    iRootElement=parseRoot();//parse message
    
  }//parse()
  
  /**
   * Returns root element of parsed message 
   * @return root element of parsed message
   */
  public Element getRootElement() {
    
    assert iRootElement!=null;
    
    return iRootElement;//here you go
    
  }//getRootElement()
  
  /**
   * Returns a JSON string representation of a parsed message
   * @return string representation of the parsed message
   */
  @Override
  public String toString() {
    
    assert iRootElement!=null;
    
    return iRootElement.toString();
    
  }//toString()
  
  /**
   * Indicates if a parsed message contains a single path
   * @param aPath path to search for
   * @return true if the parsed message contains the path
   * @see Path
   */
  public boolean contains(Path aPath) {
    
    assert aPath!=null;
    assert iRootElement!=null;
    
    Element element=iRootElement;//start at root element
    for (int i=0;i<aPath.fDepth;++i) {//for each level
      if (aPath.fKeys[i]!=null) {//if object key
        element=element.getChildElement(aPath.fKeys[i]);//get element for object key
      } else {//else array index
        element=element.getChildElement(aPath.fIndexes[i]);//get element for array index
      }//if object key
      if (element==null) return false;//if element not found then return false        
    }//for each key
    
    return true;//path found
    
  }//contains

  /**
   * Indicates if a parsed message contains all given paths
   * @param aPaths paths to search for
   * @return true if the parsed message contains all of the given paths
   * @see Path
   */
  public boolean containsAll(Path[] aPaths) {
    
    assert aPaths!=null;
    
    for (int i=0;i<aPaths.length;++i) {//for each path
      if (!contains(aPaths[i])) return false;//not found, so quit
    }//for each path
    
    return true;//all paths found
    
  }//containsAll()

  /**
   * Indicates if a parsed message contains any of the given paths
   * @param aPaths paths to search for
   * @return true if the parsed message contains any of the given paths
   * @see Path
   */
  public boolean containsAny(Path[] aPaths) {
    
    assert aPaths!=null;
    
    for (int i=0;i<aPaths.length;++i) {//for each path
      if (contains(aPaths[i])) return true;//path found, so quit
    }//for each path
    
    return false;//no paths found
    
  }//containsAny()  
    
  /**
   * Returns an element associated with a path
   * @param aPath path of the element
   * @return string representation the element
   * @see Path
   */
  public Element getElement(Path aPath) {
    
    assert aPath!=null;
    assert iRootElement!=null;
    
    Element element=iRootElement;//start at root element
    for (int i=0;i<aPath.fDepth;++i) {//for each level
      if (aPath.fKeys[i]!=null) {//if object key
        element=element.getChildElement(aPath.fKeys[i]);//get element for object key
      } else {//else array index
        element=element.getChildElement(aPath.fIndexes[i]);//get element for array index
      }//if object key
      if (element==null) return null;//return null if path does not exist
    }//for each key

    return element;//our work here is done
  
  }//getElement()
  
  /**
   * Returns parsing context
   * @return parse context
   */
  private String context(){
    
    assert iMessage!=null;
    assert iIndex>=0;
    assert iIndex<=iMessage.length();

    return iMessage.substring(0, iIndex)+" ^ "+iMessage.substring(iIndex);//return parse context
    
  }//context()
 
  /**
   * Advances to the next non-whitespace character from the message being parsed
   * @return next non-whitespace character from the message being parsed
   */
  private char next(){
    
    assert iMessage!=null;
    assert iIndex>=0;
    assert iIndex<=iMessage.length();

    if (iIndex>=iMessage.length()) return NONE;//return no character if beyond end of the message
    return iMessage.charAt(iIndex++);//return current character and advance to next
    
  }//next()
  
  /**
   * Peeks at the next character from the message being parsed
   * @return next character from the message being parsed
   */
  private char peek(){
    
    assert iMessage!=null;
    assert iIndex>=0;
    assert iIndex<=iMessage.length();

    if (iIndex>=iMessage.length()) return NONE;//return no character if beyond end of the message
    return iMessage.charAt(iIndex);//return current character
    
  }//peek()
  
  /**
   * Rewinds to previous character in the message being parsed
   */
  private void back(){
    
    assert iMessage!=null;
    assert iIndex>=0;
    assert iIndex<=iMessage.length();

    if (iIndex==0) return;//if at start of message
    --iIndex;//move back one character
    
  }//back()
  
  /**
   * Skips over one or more characters
   * @param aSkip number of characters to be skipped
   */
  private void skip(int aSkip){
    
    assert aSkip>=0;
    assert iMessage!=null;
    assert iIndex>=0;
    assert iIndex<=iMessage.length();

    iIndex+=aSkip;//advance index by the skip distance
    if (iIndex>=iMessage.length()) iIndex=iMessage.length();//if beyond end then set index at end of message
    
  }//skip()
  
  /**
   * Parses a string from the message
   * @return string parsed
   */
  private String parseString () {
    
    assert iMessage!=null;
    assert iIndex>=0;
    assert iIndex<=iMessage.length();
    
    fBuffer.delete(0, fBuffer.length());//empty string buffer
    
    char chr=next();//consume opening quote
    assert chr=='"';//assert quote found
    chr=next();//get next character
    if (chr=='"') return "";//return empty string if closing quote
    while (chr!='"') {//until closing quote reached
      fBuffer.append(chr);//append character to the string
      chr=next();//get the next character
      if (chr==NONE) throw new RuntimeException("Invalid syntax : "+context());//Awwww....
      if (chr=='\\') {//if escape character
        fBuffer.append('\\');//append escape character
        fBuffer.append(next());//append next character
        chr=next();//get next character
      }//if escape character
    }//until closing quote reached 
    
    return fBuffer.toString();//return string in buffer
    
  }//parseString() 

  /**
   * Parses a number from the message
   * @return string representation of the number value
   */
  private String parseNumber () {
    
    assert iMessage!=null;
    assert iIndex>=0;
    assert iIndex<=iMessage.length();
    
    fBuffer.delete(0, fBuffer.length());//empty string buffer
        
    char chr=next();//get next character
    assert "-0123456789".indexOf(chr)>=0;//assert valid start character
    while ("0123456789.Ee+-".indexOf(chr)>=0) {//until non number character
      fBuffer.append(chr);//append to string buffer
      chr=next();//get next character
      if (chr==NONE) throw new RuntimeException("Invalid syntax : "+context());//gee, thanks...
    }//until non number character
    
    if ("]},".indexOf(chr)<0) throw new RuntimeException("Invalid syntax : "+context());//no way jose

    back(); //rewind to the terminator character
    
    return fBuffer.toString();//return string in buffer
    
  }//parseNumber() 
  
  /**
   * Parses a boolean token from the message
   * @return string representation of the boolean value
   */
  private String parseBoolean () {
    
    assert iMessage!=null;
    assert iIndex>=0;
    assert iIndex<=iMessage.length();
    
    char chr=next();//get next character
    assert "ft".indexOf(chr)>=0;//assert valid boolean start character
    switch (chr) {//switch on first character
      case 'f': skip(4);//skip to last character
                return "false";
      case 't': skip(3);//skip to last character
                return "true";
      default: assert false;//assert that we do not reach this statement
               return null;
    }//switch on first character
    
  }//parseBoolean() 
  
  /**
   * Parses a null token from the message
   * @return string representation of null
   */
  private String parseNull () {
    
    assert iMessage!=null;
    assert iIndex>=0;
    assert iIndex<=iMessage.length();

    char chr=next();//get next character
    assert chr=='n';//assert correct first character
    skip(3);//skip to end of null token
        
    return "null";//return string representation of null
    
  }//parseNull() 

  /**
   * Parses a message as the root element (object or array)
   * @return parsed element
   */
  private Element parseRoot() {
    
    assert iMessage!=null;
    assert iIndex>=0;
    assert iIndex<=iMessage.length();
    
    while (peek()<=' ') next();//skip whitespace
    switch (peek()) {//switch on next character
      case 'n': return new ScalarElement(parseNull());//parse null 
      case 'f': return new ScalarElement(parseBoolean());//parse false
      case 't': return new ScalarElement(parseBoolean());//parse true
      case '[': return new ArrayElement(parseArray());//parse array 
      case '{': return new ObjectElement(parseObject());//parse object
      default : throw new RuntimeException("Invalid syntax : "+context());//ruh roh
    }//switch on next character
    
  }//parseRoot()

  /**
   * Parses an array element
   * @return linked list representation of the array
   */
  private LinkedList<Element> parseArray() {
 
    LinkedList<Element> array=new LinkedList<>();//create linked list
    
    char chr=next();//consume first character
    assert chr=='[';//assert first character is an open square bracket
    while (chr!=']') {//until closing bracket
           
      switch (peek()) {//switch on next character
        case ' ':
        case '\t':
        case '\n':
        case '\r': chr=next(); //discard whitespace
                   break;
        case '"': array.add(new ScalarElement(Element.STRING,parseString()));//parse string 
                  break;
        case '-':
        case '0':
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9': array.add(new ScalarElement(Element.NUMBER,parseNumber()));//parse number
                  break;
        case 'f':
        case 't': array.add(new ScalarElement(Element.BOOLEAN,parseBoolean()));//parse boolean token
                  break;
        case 'n': array.add(new ScalarElement(Element.NULL,parseNull()));//parse null token
                  break;

        case '{': array.add(new ObjectElement(parseObject()));//parse object
                  break;
        case '[': array.add(new ArrayElement(parseArray()));//parse array
                  break;
        case ',': chr=next(); //consume the comma character
                  break;
        case ']': chr=next(); //consume the close bracket character
                  break;
        default : throw new RuntimeException("Invalid syntax : "+context());//holy syntax batman...

      }//switch on next character      
            
    }//until closing bracket 
    
    return array;//looking good Huston
    
  }//parseArray()
  
  /**
   * Parses an object
   * @return hashed map representation of the object
   */
  private TreeMap<String,Element> parseObject() {
 
    TreeMap<String,Element> object=new TreeMap<>();//create hashed map    
        
    char chr=next();//consume first character
    assert chr=='{';//assert first character is open curly bracket
    while (chr!='}') {//until closing bracket found
       
      switch (peek()) {//switch on next character
        case ' ':
        case '\t':
        case '\n':
        case '\r': chr=next(); //discard whitespace
                   break;
        case '"': String key=parseString();//parse key
                  while (peek()<=' ') next();//skip whitespace
                  chr=next();//consume the next character
                  if (chr!=':') throw new RuntimeException("Invalid syntax : "+context());//must be havin a giraffe?
                  while (peek()<=' ') next();//skip whitespace
                  switch (peek()) {//switch on the next character in key value pair
                    case '"': object.put(key, new ScalarElement(Element.STRING,parseString()));//parse string value
                              break;
                    case '-':
                    case '0':
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9': object.put(key, new ScalarElement(Element.NUMBER,parseNumber()));//parse number value
                              break;
                    case 'f':
                    case 't': object.put(key, new ScalarElement(Element.BOOLEAN,parseBoolean()));//parse boolean value
                              break;
                    case 'n': object.put(key, new ScalarElement(Element.NULL,parseNull()));//parse null value
                              break;
                    case '[': object.put(key, new ArrayElement(parseArray()));//parse array value 
                              break;
                    case '{': object.put(key, new ObjectElement(parseObject()));//parse object value
                              break;
                    default : throw new RuntimeException("Invalid syntax : "+context());//we have a problem houston
                  };//switch on the next character in key value pair
                  break;
        case ',': chr=next();//consume comma character
                  break;
        case '}': chr=next();//consume close bracket character
                  break;
        default : throw new RuntimeException("Invalid syntax : "+context());//gone pete tong
      }//switch on next character
                    
    }//until closing bracket found 
    
    return object;//happy days
    
  }//parseObject() 
  
}//Parse{}
