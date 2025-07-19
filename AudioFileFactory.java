package studiplayer.audio;

import java.io.File;

public class AudioFileFactory {

	public AudioFileFactory() {
		
	}
	
	public static AudioFile createAudioFile(String path) throws NotPlayableException
	{
		if (path.length() >= 4) {
			String fileExtension = path.substring(path.length() - 3).toLowerCase();
			if(fileExtension.equals("wav")) 
			{
				return new WavFile(path);
			}else if(fileExtension.equals("ogg") || fileExtension.equals("mp3")) 
			{
				if(new File(path).exists()) 
				{
					return new TaggedFile(path);
				}else 
				{
					throw new NotPlayableException(path, "Unknown suffix for AudioFile \"" + path + "\"");
				}
				
			}else 
			{
				return null;
			}
		}
		else 
		{
			//return null;
			throw new NotPlayableException(path, "Unknown suffix for AudioFile \"" + path + "\"");
		}
	}

}
