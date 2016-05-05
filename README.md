# json

## Description

Fast parser for streaming JSON messages.

## Overview

This package is for use in parsing streamed JSON messages which contain some expected
structure. A single parser instance is able to parse multiple messages and the navigation paths 
to reach elements of interest need only be defined once prior to parsing any messages.

Methods are also provided for the run-time exploration of the message structure after it has been parsed. 

## Elements

A JSON message is parsed into a hierarchy of elements where each element is 
a scalar, array or object element. Use the parse() method to parse one or more messages.

Use getRootElement() to return the root element and use getElement() to return an element found 
at a specified path from the parsed message. The hierarchy can also be navigated using element methods to 
navigate to child elements.

## Paths

A path represents a hierarchy of tags for navigating a JSON message. Paths
are constructed from strings of the form /tag/tag/ where the leading character
is a tag separator and hierarchies are navigated from left to right. 

A tag may be either an object key or an array index. An object key is the at character (@)
followed by the key name without quotation marks; while an array index is the hash character (#) 
followed by an integer, starting at zero for the first element.

For a JSON message of {"a":1,"b":{"c":2}}, the strings "/@a" and "/@b/@c" represent the paths
to the elements 1 and 2 respectively. For a JSON message of {"a":[1,2,3]} the strings
"/@a/#0" and "/@a/#2" represent the paths for elements 1 and 3 respectively. 


## Usage Example

    /*
    construct parser
    */
    Parser parser = new Parser();
    /* 
    construct paths for expected elements 
    */
    Path identityPath=new Path("/@identity");
    Path latitudePath=new Path("/@location/@latitude");
    Path longitudePath=new Path("/@location/@longitude");
    /*
    parse a JSON message
    */
    parser.parse("{\"identity\":12345,\"location\":{\"latitude\":51.5047650,\"longitude\":-2.4841220}}");
    /*
    get elements via paths
    */
    Element identityElement=parser.getElement(identityPath);
    Element latitudeElement=parser.getElement(latitudePath);
    Element longitudeElement=parser.getElement(longitudePath);
    /*
    output elements as strings
    */
    System.out.println("id="+identityElement.toString()+" loc="+latitudeElement.toString()+","+longitudeElement.toString());
    
## Dependencies

None

## Support

Email support@justonedb.com for support.

## Author

Duncan Pauly

