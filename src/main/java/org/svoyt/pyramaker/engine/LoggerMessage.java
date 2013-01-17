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

import java.util.Date;

/**
 * Message for logger
 */
public class LoggerMessage {
	
	private final Date dateTime;
	private final String message;
	
	/**
	 * Constructor
	 * @param dateTime date and time of message
	 * @param message text for message
	 */
    public LoggerMessage(Date dateTime, String message){
    	this.dateTime = dateTime;
    	this.message = message;
    }
    
    /**
     * Date and time of message
     * @return date and time of message
     */
    public Date getDateTime(){
    	return dateTime;
    }
    
    /**
     * Text for message
     * @return text for message
     */
    public String getMessage(){
    	return message;
    }
}
