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

import java.util.ArrayList;
import java.util.Date;

/**
 * Simple log object
 */
public class Logger {
	
	ArrayList<LoggerMessage> messages;
	
	/**
	 * Constructor
	 */
	public Logger(){
	    messages = new ArrayList<LoggerMessage>();
	}
	
	/**
	 * Write log message to log
	 * @param msg message text
	 */
    public void write(String msg){
    	messages.add(new LoggerMessage(new Date(),msg));
    }
    
	/**
	 * Write log message to log
	 * @param msg pattern for message 
	 * @param args parameters for pattern
	 */
    public void write(String msg,Object... args){
    	messages.add(new LoggerMessage(new Date(),String.format(msg, args)));
    }
    
    /**
     * Return log data
     * @return log data as string
     */
    public String getLog(){
    	StringBuilder sb = new StringBuilder();
    	for (int i=0;i<messages.size();i++){
    		LoggerMessage msg = messages.get(i);
    		sb.append(String.format("%1$td.%1$tm.%1$tY %1$tH:%1$tM:%1$tS - %2$s\n", msg.getDateTime(), msg.getMessage()));
    	}
    	return sb.toString();
    }
}
