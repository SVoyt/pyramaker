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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.GridCoverageFactory;
import org.geotools.coverage.grid.io.AbstractGridFormat;
import org.geotools.coverage.grid.io.imageio.GeoToolsWriteParams;
import org.geotools.gce.geotiff.GeoTiffFormat;
import org.geotools.gce.geotiff.GeoTiffWriteParams;
import org.geotools.gce.geotiff.GeoTiffWriter;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.referencing.CRS;
import org.opengis.parameter.GeneralParameterValue;
import org.opengis.parameter.ParameterValueGroup;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;


/**
 * Class that binds buffered image to coordinates 
 */
public class ImageBinder {
    
	/**
	 * Binding function
	 * 
	 * @param image buffered image that needs to be binded
	 * @param filename absolute path of result tiff file with binded image
	 * @param left left border of bound rectangle
	 * @param top top border of bound rectangle
	 * @param right right border of bound rectangle
	 * @param bottom bottom border of bound rectangle 
	 * @param crsWKT well-known text that describes coordinate reference system
	 *         
	 */
	public final static void bind(BufferedImage image,String filename, double left, double top, double right, double bottom, String crsWKT, boolean usingJpegCompression, float jpegQuality) throws IOException, FactoryException{
		final File f = new File(filename);
		final ImageOutputStream imageOutStream = ImageIO.createImageOutputStream(f);
		final GeoTiffWriter writer = new GeoTiffWriter(imageOutStream);
		
		final GeoTiffFormat format = new GeoTiffFormat();
		final GeoTiffWriteParams wp = new GeoTiffWriteParams();
		wp.setCompressionMode(GeoTiffWriteParams.MODE_EXPLICIT);
		if (usingJpegCompression){
			wp.setCompressionType("JPEG");
			wp.setCompressionQuality(jpegQuality/100F);
		}
		wp.setTilingMode(GeoToolsWriteParams.MODE_EXPLICIT);
		wp.setTiling(256, 256);
		 
		final ParameterValueGroup writerParams = format.getWriteParameters();
		writerParams.parameter(AbstractGridFormat.GEOTOOLS_WRITE_PARAMS.getName().toString())
		            .setValue(wp);
		
		CoordinateReferenceSystem crs = CRS.parseWKT(crsWKT);
		ReferencedEnvelope envelope = new ReferencedEnvelope(left, right, top, bottom, crs);
		GridCoverageFactory factory = new GridCoverageFactory();
		GridCoverage2D gc = factory.create("IMG", image, envelope);
		writer.write(gc, (GeneralParameterValue[]) writerParams.values().toArray(new GeneralParameterValue[1]));
		imageOutStream.flush();
		imageOutStream.close();
    }
}
