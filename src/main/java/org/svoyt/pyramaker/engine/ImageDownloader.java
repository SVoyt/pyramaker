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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.opengis.referencing.FactoryException;

/**
 * Object that downloads images
 * 
 */
public class ImageDownloader {
	
    private final ImageDownloaderParams params;
    private final Logger logger;
    private boolean needToStop = false;
    private boolean needToPause = false;
    private boolean isDone = false;
    private int downloadedCount;
    
    /**
     * Object constructor
     * 
     * @param params set of parameters for downloader
     * @throws NullPointerException if params is null
     */
    public ImageDownloader(ImageDownloaderParams params){
    	if (params ==null){
    		throw new NullPointerException("params is null");
    	}
        this.params = params;
        this.logger = new Logger();
    }
    
    /**
     * Stops download process
     */
    public void stop(){
    	needToStop = true;
    }
    
    /**
     * Pause download process
     */
    public void pause(){
    	needToPause = !needToPause;
    }
    
    /**
     * Check on done
     * @return true if process is done
     */
    public boolean isDone(){
    	return isDone;
    }
    
    /**
     * Increase process counter
     */
    private void increaseCounter(){
    	downloadedCount++;
    }
    
    /**
     * Return  count of downloaded images 
     * 
     * @return count of downloaded images
     */
    public int getProcess(){
    	return downloadedCount;
    }
    
    /**
     * Log data
     * @return log text
     */
    public String getLog(){
    	return logger.getLog();
    }
    
    
    /**
     * Function downloads one image by url and stretch it to tilesize
     * 
     * @param imageUrl url of image
     * @param tileSize size of image (tileSize = height = width),
     *  needs because some services return tile with greater than other tiles size 
     *  (e.g. all tiles has tile size equal to 256, but some tiles returns with 512)
     *  and function will stretchs images to tile size by default
     *  
     *  @return downloaded image
     *  
     *  @throws NullPointerException if imageUrl is null
     *  @throws IllegalArgumentException if tileSize is less than 0
     */
    public BufferedImage downloadImage(String imageUrl, int tileSize){
    	if (tileSize<=0){
    		throw new IllegalArgumentException("tileSize must be greater than 0");
    	}
    	if (imageUrl == null){
    		throw new NullPointerException("imageUrl is null");
    	}
    	BufferedImage image  = null;
    	BufferedImage downloadedImage = null;
    	try{
    	    URL url = new URL(imageUrl);
    	    downloadedImage = ImageIO.read(url);
    	}
    	catch (MalformedURLException e){
    		logger.write("Url is malformed - %s",imageUrl);
    	}
    	catch (IOException e){
    		logger.write("Cant read image by url - %s",imageUrl);
    	}
    	finally{
    		image = new BufferedImage(tileSize, tileSize, BufferedImage.TYPE_INT_RGB);
    		Graphics gr = image.getGraphics();
    		gr.setColor(Color.WHITE);
    		gr.fillRect(0, 0, tileSize, tileSize);
    		if (downloadedImage!=null){
    			gr.drawImage(downloadedImage, 0, 0, tileSize, tileSize, null);
    		}
    		gr.dispose();
    	}
    	return image;
    }
    
    /**
     * Run main downloading process
     */
    public void start(){
    	isDone = false;
        for (int layerNumber = params.getStartLevel(); layerNumber >= params.getEndLevel(); layerNumber--){
        	
        	//creating path for layer
        	int pathNum = params.getStartLevel()-layerNumber;
        	final String pathName = String.format("%s/%s",params.getPath(),pathNum); 
            final File f = new File(pathName);
            f.mkdir();

            //counting start\end tiles
            int z = layerNumber;
            double size = Math.pow(2, z) * params.getTileSize();
            
            double minX = ((params.getRegionLayerBounds().left - params.getMaxLayerBounds().left) / params.getMaxLayerBounds().getWidth()) * size;
            double minY = ((params.getRegionLayerBounds().top - params.getMaxLayerBounds().top) / params.getMaxLayerBounds().getHeight()) * size;
            double maxX = ((params.getRegionLayerBounds().right - params.getMaxLayerBounds().left) / params.getMaxLayerBounds().getWidth()) * size;
            double maxY = ((params.getRegionLayerBounds().bottom - params.getMaxLayerBounds().top) / params.getMaxLayerBounds().getHeight()) * size;

            int startXtile = (int)Math.floor(minX / params.getTileSize());
            int startYtile = (int)Math.floor(minY / params.getTileSize());
            int endXtile = (int)Math.floor(maxX / params.getTileSize());
            int endYtile = (int)Math.floor(maxY / params.getTileSize());

            for (int i = startXtile; i <= endXtile; i++){
                for (int j = startYtile; j <= endYtile; j++){
                	if (needToStop){
                		logger.write("Stopped by user");
                		return;
                	}
                	while(needToPause){
                		try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
                    	if (needToStop){
                    		logger.write("Paused and stopped by user");
                    		return;
                    	}
                	}
                	
                    double boundMinX = params.getRegionLayerBounds().left + ((i * params.getTileSize()) / size) * params.getRegionLayerBounds().getWidth();
                    double boundMinY = params.getRegionLayerBounds().top + ((j * params.getTileSize()) / size) * params.getRegionLayerBounds().getHeight();
                    double boundMaxX = params.getRegionLayerBounds().left + (((i + 1) * params.getTileSize()) / size) * params.getRegionLayerBounds().getWidth();
                    double boundMaxY = params.getRegionLayerBounds().top + (((j + 1) * params.getTileSize()) / size) * params.getRegionLayerBounds().getHeight();
                    
                    // I like C# formatting =)
                    final String url = params.getUrlPattern().replace("{0}", String.valueOf(i)).replace("{1}",String.valueOf(j)).replace("{2}",String.valueOf(z));// String.format(params.getUrlPattern(), i, j, z);
                    final String tileFilename = String.format("%s/%s/cache_%s_%s.tif", params.getPath(), pathNum, i - startXtile + 1, j - startYtile + 1);
                    final BufferedImage img = downloadImage(url,params.getTileSize());
                    try{
                        ImageBinder.bind(
                        		img, 
                        		tileFilename, 
                        		boundMinX, boundMinY, boundMaxX, boundMaxY, 
                        		params.getWkt(), 
                        		params.isJpegCompressionUsed(), 
                        		params.getJpegQuality());
                    }
                    catch(FactoryException e){
                    	logger.write("wkt parser error - %s", e.getMessage());
                    }
                    catch(IOException e){
                    	logger.write("Can't create tile with name - %s", tileFilename);
                    }
                    increaseCounter();
                }
            }
        }
        isDone = true;
        logger.write("Work finished.");
    }
}
