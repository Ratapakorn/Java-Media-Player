package studiplayer.audio;
import java.util.Map;

import studiplayer.basic.BasicPlayer;
import studiplayer.basic.TagReader;

public abstract class SampledFile extends AudioFile{
	private static long sDuration;
	protected String title;
	protected long duration;
	protected String author;
	
	public SampledFile() throws NotPlayableException
	{
		throw new NotPlayableException("AudioFiles", "No Parameter is inputted");
	}
	
	public SampledFile(String path) throws NotPlayableException 
	{
		super(path);
	}

	@Override
	public void play() throws NotPlayableException{
		BasicPlayer.play(getPathname());
	}

	@Override
	public void togglePause() {
		BasicPlayer.togglePause();
	}

	@Override
	public void stop() {
		BasicPlayer.stop();
	}

	@Override
	public String formatDuration() {
		long duration = 0;
		Map<String, Object> tagMap = TagReader.readTags(getPathname());
		for (String tag : tagMap.keySet()) {
			Object value = tagMap.get(tag);
			if(tag.equals("duration")) 
			{
				duration = (long) value;
			}
//			System.out.printf("key: %-25s value: %-30s (type: %s)\n",
//					tag, value, value.getClass().getSimpleName());
		}
		return timeFormatter(duration);
	}

	@Override
	public String formatPosition() {
		return timeFormatter(BasicPlayer.getPosition());
	}
	
	public static String timeFormatter(long timeInMicroSeconds) 
	{
		if(timeInMicroSeconds < 0) 
		{
			throw new RuntimeException("Time cannot be less than zero");
		}else if(timeInMicroSeconds >= 6000000000L) 
		{
			throw new RuntimeException("Overloaded");
		}
		String formatted = "";
		long duration = timeInMicroSeconds / 1000000;
		long seconds = duration % 60;
		long minutes = (duration) / 60;
		if (minutes < 10) 
		{
			formatted += "0";
		}
		formatted += minutes + ":";
		if (seconds < 10) 
		{
			formatted += "0";
		}
		formatted += seconds;
		return formatted;
	}
	
	public long getDuration() 
	{
		return sDuration;
	}

}
