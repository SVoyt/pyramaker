/*
 * Copyright 2012 Sergey Voyteshonok info@svoyt.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.svoyt.pyramaker.engine;

/**
 * Rectangle
 */
public class Rectangle {
	
	/**
	 * Left rectangle border (x coordinate)
	 */
    public double left;
    
	/**
	 * Top rectangle border (y coordinate)
	 */
    public double top;
    
	/**
	 * Right rectangle border (x coordinate)
	 */
    public double right;
    
	/**
	 * Bottom rectangle border (y coordinate)
	 */
    public double bottom;
    
    /**
     * Default constructor
     */
    public Rectangle(){
    	
    }
    
    /**
     * Constructor
     */
    public Rectangle(double left, double top, double right, double bottom){
    	this.left = left;
    	this.top = top;
    	this.right = right;
    	this.bottom = bottom;
    }
    
    /**
     * Rectangle width (right-left)
     * 
     * @return width
     */
    public double getWidth(){
    	return right - left;
    }
    
    /**
     * Rectangle height (bottom-top)
     * 
     * @return height
     */
    public double getHeight(){
    	return bottom - top;
    }
}
