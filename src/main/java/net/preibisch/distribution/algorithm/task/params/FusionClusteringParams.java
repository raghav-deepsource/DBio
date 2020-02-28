package net.preibisch.distribution.algorithm.task.params;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import mpicbg.models.AffineModel1D;
import mpicbg.spim.data.SpimDataException;
import mpicbg.spim.data.sequence.ViewDescription;
import mpicbg.spim.data.sequence.ViewId;
import net.imglib2.Interval;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.realtransform.AffineTransform3D;
import net.imglib2.type.numeric.real.FloatType;
import net.preibisch.distribution.algorithm.errorhandler.logmanager.MyLogger;
import net.preibisch.legacy.io.IOFunctions;
import net.preibisch.mvrecon.fiji.spimdata.SpimData2;
import net.preibisch.mvrecon.fiji.spimdata.boundingbox.BoundingBox;
import net.preibisch.mvrecon.process.fusion.FusionTools;

public class FusionClusteringParams extends ParamJsonHelpers implements ParamsJsonSerialzer<FusionClusteringParams> {
	private double downsampling;
	private Map<ViewId, AffineTransform3D> registrations;
	private Set<ViewDescription> views;
	private boolean useBlending;
	private boolean useContentBased;
	private int interpolation;
	private BoundingBox boundingBox;
	private Map<ViewId, AffineModel1D> intensityAdjustment;

	public FusionClusteringParams() {}
	
	public FusionClusteringParams( Interval boundingBox, double downsampling,
			Map<ViewId, AffineTransform3D> registrations, Set<ViewDescription> views, boolean useBlending,
			boolean useContentBased, int interpolation, Map<ViewId, AffineModel1D> intensityAdjustment) {
		super();
		this.downsampling = downsampling;
		this.registrations = registrations;
		this.views = views;
		this.useBlending = useBlending;
		this.useContentBased = useContentBased;
		this.interpolation = interpolation;
		this.boundingBox = new BoundingBox(boundingBox);
		this.intensityAdjustment = intensityAdjustment;
	}

	public Map<ViewId, AffineTransform3D> getRegistrations() {
		return registrations;
	}

	public Set<ViewDescription> getViews() {
		return views;
	}

	public Interval getBoundingBox() {
		return boundingBox;
	}

	public Map<ViewId, AffineModel1D> getIntensityAdjustment() {
		return intensityAdjustment;
	}

	public boolean useBlending() {
		return useBlending;
	}

	public boolean useContentBased() {
		return useContentBased;
	}

	public int getInterpolation() {
		return interpolation;
	}

	public double getDownsampling() {
		return downsampling;
	}
 
	public FusionClusteringParams fromJson(File file) throws JsonSyntaxException, JsonIOException, FileNotFoundException  {
		return FusionClusteringParams.getGson().fromJson(new FileReader(file), FusionClusteringParams.class);
	}


	@Override
	public void toJson(File file) {
		try (PrintWriter out = new PrintWriter(file)) {
			String json = getGson().toJson(this);
			out.print(json);
			out.flush();
			out.close();
			IOFunctions.println("File saved: " + file.getAbsolutePath() + " | Size: " + file.length());
		} catch (IOException e) {
			MyLogger.log().error(e);
		}
	}

	public RandomAccessibleInterval<FloatType> fuse(String input,BoundingBox bb) throws SpimDataException {	
		SpimData2 spimdata = getSpimData(input);
		return FusionTools.fuseVirtual(
			spimdata.getSequenceDescription().getImgLoader(),
			getRegistrations(),
			spimdata.getSequenceDescription().getViewDescriptions(),
			getViews(),
			useBlending(),
			useContentBased(),
			getInterpolation(),
//			getBoundingBox(),
			bb,
			getDownsampling(),
			getIntensityAdjustment() ).getA();}

}