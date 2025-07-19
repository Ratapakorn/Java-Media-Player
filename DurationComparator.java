package studiplayer.audio;

import java.util.Comparator;

public class DurationComparator implements Comparator<AudioFile> {
	private AudioFile t1;

	public DurationComparator() {
		
	}

	@Override
	public int compare(AudioFile o1, AudioFile o2) {
		//If Duration is unable to be read, then we also give it priority
		if(o1.getDuration() == -1) 
		{
			return -1;
		}else if(o2.getDuration() == -1) 
		{
			return 1;
		}
		return (int) (o1.getDuration() - o2.getDuration());
	}

}
