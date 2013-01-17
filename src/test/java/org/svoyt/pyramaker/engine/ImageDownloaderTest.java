package org.svoyt.pyramaker.engine;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import org.junit.Before;
import org.junit.Test;

public class ImageDownloaderTest {

	public final static String WKT_EPSG_3857 = "PROJCS[\"WGS 84 / Pseudo-Mercator\",  GEOGCS[\"WGS 84\",    DATUM[\"World Geodetic System 1984\",      SPHEROID[\"WGS 84\", 6378137.0, 298.257223563, AUTHORITY[\"EPSG\", \"7030\"]],      AUTHORITY[\"EPSG\", \"6326\"]],    PRIMEM[\"Greenwich\", 0.0, AUTHORITY[\"EPSG\", \"8901\"]],    UNIT[\"degree\", 0.017453292519943295],    AXIS[\"Geodetic longitude\", EAST],    AXIS[\"Geodetic latitude\", NORTH],    AUTHORITY[\"EPSG\", \"4326\"]],  PROJECTION[\"Popular Visualisation Pseudo Mercator\", AUTHORITY[\"EPSG\", \"1024\"]],  PARAMETER[\"semi_minor\", 6378137.0],  PARAMETER[\"latitude_of_origin\", 0.0],  PARAMETER[\"central_meridian\", 0.0],  PARAMETER[\"scale_factor\", 1.0],  PARAMETER[\"false_easting\", 0.0],  PARAMETER[\"false_northing\", 0.0],  UNIT[\"m\", 1.0],  AXIS[\"Easting\", EAST],  AXIS[\"Northing\", NORTH],AUTHORITY[\"EPSG\", \"3857\"]]";
	
	ImageDownloader imageDownloader;
	
	@Before
	public void prepareImageDownloader(){
		//interactive test =)
		final JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fc.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
		    ImageDownloaderParams param = new ImageDownloaderParams(
		    		"http://c.tile.openstreetmap.org/{2}/{0}/{1}.png",
		    		new Rectangle(-19918247,19918247,19918247,-19918247),
		    		new Rectangle(-19918246,19918246,19918246,-19918246),
		    		256,
		    		2,
		    		0,
		    		WKT_EPSG_3857,
		    		file.getAbsolutePath(),
		    		true,
		    		100
		    		); 
		    imageDownloader = new ImageDownloader(param);
        }
	}
	
	
	@Test
	public void testDownloadImage() {
		BufferedImage img = imageDownloader.downloadImage("http://c.tile.openstreetmap.org/6/31/22.png", 256);
		assertTrue("downloaded image width is not right", img.getWidth() == 256);
	}
	
	@Test
	public void testStart() throws IOException{
		imageDownloader.start();
	}

}
