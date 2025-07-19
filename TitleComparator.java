package studiplayer.audio;

import java.util.Comparator;

public class TitleComparator implements Comparator<AudioFile> {

	public TitleComparator() {
		
	}

	@Override
	public int compare(AudioFile o1, AudioFile o2) {
		if(o1.getTitle() == null || o2.getTitle() == null) 
		{
			//If Title is unable to be read, then we can deduce from FileName
			return (o1.toString().compareTo(o2.toString()));
		}
		return (o1.toString().compareTo(o2.toString()));
		//return (o1.getTitle()).compareTo(o2.getTitle());
	}

}
