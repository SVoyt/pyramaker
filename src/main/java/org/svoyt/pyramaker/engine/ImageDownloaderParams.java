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
 * Set of parameters for ImageDownloader
 */
public class ImageDownloaderParams {
	private final Rectangle maxLayerBounds;
    private final Rectangle regionLayerBounds;
    private final int tileSize;
    private final String urlPattern;
    private final int startLevel;
    private final int endLevel;
    private final String wkt;
    private final String path;
    private final boolean useJpegCompression;
    private final float jpegQuality;
    
    /**
     * Constructor
     * 
     * @param urlPattern tile url pattern 
     * @param maxLayerBounds
     * @param regionLayerBounds
     * @param tileSize tile size
     * @param startLevel
     * @param endLevel 
     * @param wkt CRS well-known text 
     * @param path output path
     * @param useJpegCompression true if need to you JPEG compression
     * @param jpegQuality 
     */
    public ImageDownloaderParams(
    		String urlPattern,
    		Rectangle maxLayerBounds, 
    		Rectangle regionLayerBounds, 
    		int tileSize, 
    		int startLevel, 
    		int endLevel,
    		String wkt,
    		String path,
    		boolean useJpegCompression,
    		float jpegQuality
    		){
    	this.maxLayerBounds = maxLayerBounds;
    	this.regionLayerBounds = regionLayerBounds;
    	this.urlPattern = urlPattern;
    	this.tileSize = tileSize;
    	this.startLevel = startLevel;
    	this.endLevel = endLevel;
    	this.wkt = wkt;
    	this.path = path;
    	this.useJpegCompression = useJpegCompression;
    	this.jpegQuality = jpegQuality;
    }

    /**
     * Tile size
     * @return tileSize
     */
    public int getTileSize() {
		return tileSize;
	}

    /**
     * Url pattern
     * @return urlPattern
     */
	public String getUrlPattern() {
		return urlPattern;
	}

	/**
	 * Start level (higher)
	 * @return startLevel
	 */
	public int getStartLevel() {
		return startLevel;
	}

	/**
	 * End level (lower)
	 * @return endLevel
	 */
	public int getEndLevel() {
		return endLevel;
	}

	/**
	 * CRS Well-known text
	 * @return wkt
	 */
	public String getWkt() {
		return wkt;
	}

	/**
	 * Output path
	 * @return path
	 */
	public String getPath() {
		return path;
	}

	/**
     * Get bounds of the layer, where tile count starts and ends
     * For example: left and top of rectangle defines where is first tile in tile grid
     * 
     * @return maximum bounds values
     */
	public Rectangle getMaxLayerBounds() {
		return maxLayerBounds;
	}

	/**
	 * Get region bounds 
	 * 
	 * @return bounds rectangle
	 */
	public Rectangle getRegionLayerBounds() {
		return regionLayerBounds;
	}
	
	/**
	 * Using Jpeg compression
	 * @return true if jpeg compression used
	 */
	public boolean isJpegCompressionUsed(){
		return useJpegCompression;
	}
	
	/**
	 * Compression quality if jpeg compression is used
	 *  100 - max quality
	 * @return jpeg compression quality in percents
	 */
	public float getJpegQuality(){
		return jpegQuality;
	}
	
	/**
	 * Calculate tile count for all layers
	 * @return tile count
	 */
    public int getTilesCount()
    {
        int count = 0;
        for (int layerNumber = getStartLevel(); layerNumber >= getEndLevel(); layerNumber--)
        {
            int z = layerNumber;
            double size = Math.pow(2, z) * getTileSize();
            double minX = ((regionLayerBounds.left - maxLayerBounds.left) / maxLayerBounds.getWidth()) * size;
            double minY = ((regionLayerBounds.top - maxLayerBounds.top) / maxLayerBounds.getHeight()) * size;
            double maxX = ((regionLayerBounds.right - maxLayerBounds.left) / maxLayerBounds.getWidth()) * size;
            double maxY = ((regionLayerBounds.bottom - maxLayerBounds.top) / maxLayerBounds.getHeight()) * size;

            int startXtile = (int)Math.floor(minX / tileSize);
            int startYtile = (int)Math.floor(minY / tileSize);
            int endXtile = (int)Math.floor(maxX / tileSize);
            int endYtile = (int)Math.floor(maxY / tileSize);
            count += (endXtile - startXtile +1) * (endYtile - startYtile+1);
        }
        return count;
    }

}
