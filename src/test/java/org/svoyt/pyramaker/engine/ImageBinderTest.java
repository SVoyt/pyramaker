/*
 * Copyright 2012 Sergey Voyteshonok
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

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

import org.junit.Test;
import org.opengis.referencing.FactoryException;

public class ImageBinderTest {

	/*
	 * Well-known text for EPSG:3857
	 */
	public final static String WKT_EPSG_3857 = "PROJCS[\"WGS 84 / Pseudo-Mercator\",  GEOGCS[\"WGS 84\",    DATUM[\"World Geodetic System 1984\",      SPHEROID[\"WGS 84\", 6378137.0, 298.257223563, AUTHORITY[\"EPSG\", \"7030\"]],      AUTHORITY[\"EPSG\", \"6326\"]],    PRIMEM[\"Greenwich\", 0.0, AUTHORITY[\"EPSG\", \"8901\"]],    UNIT[\"degree\", 0.017453292519943295],    AXIS[\"Geodetic longitude\", EAST],    AXIS[\"Geodetic latitude\", NORTH],    AUTHORITY[\"EPSG\", \"4326\"]],  PROJECTION[\"Popular Visualisation Pseudo Mercator\", AUTHORITY[\"EPSG\", \"1024\"]],  PARAMETER[\"semi_minor\", 6378137.0],  PARAMETER[\"latitude_of_origin\", 0.0],  PARAMETER[\"central_meridian\", 0.0],  PARAMETER[\"scale_factor\", 1.0],  PARAMETER[\"false_easting\", 0.0],  PARAMETER[\"false_northing\", 0.0],  UNIT[\"m\", 1.0],  AXIS[\"Easting\", EAST],  AXIS[\"Northing\", NORTH],AUTHORITY[\"EPSG\", \"3857\"]]";
	
	public final static double LEFT = 4422150;
	public final static double RIGHT = 4467707;
	public final static double TOP = 8252428;
	public final static double BOTTOM = 8197699;
	
	@Test
	public void testBind() throws IOException,FactoryException {
		BufferedImage image = null;
		String filename = null;
		File bindedImage = null; 
		//read image
		final JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
			image = ImageIO.read(file);
        }
		
	    //choosing filename to save
	    returnVal = fc.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
        	bindedImage = fc.getSelectedFile();
            filename = bindedImage.getAbsolutePath();
        }
		ImageBinder.bind(image,filename,LEFT,TOP,RIGHT,BOTTOM,WKT_EPSG_3857,true,100);
		
		assertTrue("Binded image does not exists",bindedImage.exists());
	}

}
