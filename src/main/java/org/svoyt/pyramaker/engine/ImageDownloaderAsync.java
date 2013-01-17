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
 * Image downloader async wrap
 * 
 */
public class ImageDownloaderAsync extends Thread {

	private final ImageDownloader imageDownloader;
	
	/**
	 * Constructor
	 * @param imageDownloader
	 */
	public ImageDownloaderAsync(ImageDownloader imageDownloader){
		this.imageDownloader = imageDownloader;
	}
	
	/**
	 * Run main process
	 */
	public void run(){
		imageDownloader.start();
	}
	
	/**
	 * Pause main process
	 */
	public void pauseWork(){
		imageDownloader.pause();
	}
	
	/**
	 * Stop main process
	 */
	public void stopWork(){
		imageDownloader.stop();
	}
	
	/**
	 * Check if work is done
	 * @return true if done
	 */
	public boolean isDone(){
		return imageDownloader.isDone();
	}
	
	/**
	 * Return log data
	 * @return log text
	 */
	public String getLog(){
		return imageDownloader.getLog();
	}
	
	/**
	 * Return current process state
	 * @return downloaded count
	 */
	public int getProcess(){
		return imageDownloader.getProcess();
	}
	
}
