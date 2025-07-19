package studiplayer.audio;
import java.io.File;

import studiplayer.basic.WavParamReader;

public class WavFile extends SampledFile {
	//private long duration;
	public WavFile() throws NotPlayableException
	{
		throw new NotPlayableException("AudioFiles", "No Parameter is inputted");
	}

	public WavFile(String path) throws NotPlayableException
	{
		super(path);
		readAndSetDurationFromFile();
	}
	
	public long getDuration() 
	{
		return durationReader();
	}
	
	public void readAndSetDurationFromFile() throws NotPlayableException
	{
		try 
		{
			WavParamReader.readParams(getPathname());
			float fps = WavParamReader.getFrameRate();
			long numF = WavParamReader.getNumberOfFrames();
			
			long Duration = computeDuration(numF, fps);
			//duration = Duration;
		}catch(RuntimeException e) 
		{
			throw new NotPlayableException("File's Parameter could not be read", e);
		}
		
	}
	
	@Override
	public String toString() 
	{
		//return Duration;
		return getAuthor() + " - " + getTitle() + " - " + timeFormatter(durationReader());
	}
	
	public String formatDuration() 
	{
		//return Duration;
		return timeFormatter(durationReader());
	}
	
	public static long computeDuration(long numberOfFrames, float frameRate) 
	{
		return (long) ((1000000) * ((float) numberOfFrames / (frameRate)));
	}
	
	public long durationReader() 
	{
		WavParamReader.readParams(getPathname());
		float fps = WavParamReader.getFrameRate();
		long numF = WavParamReader.getNumberOfFrames();
		
		long Duration = computeDuration(numF, fps);
		return Duration;
	}
	
	

}
