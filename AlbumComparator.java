package studiplayer.audio;

import java.util.Comparator;

public class AlbumComparator implements Comparator<AudioFile> {

	public AlbumComparator() {

	}

	@Override
	public int compare(AudioFile o1, AudioFile o2) {
		//If Author is unable to be read, then we must give priority to ones with no Album.
		if(o1.getAlbum() == null && o2.getAlbum() == null) 
		{
			return 0;
		}
		else if(o1.getAlbum() == null) 
		{
			return -1;
		}else if(o2.getAlbum() == null) 
		{
			return 1;
		}
		return (o1.getAlbum()).compareTo(o2.getAlbum());
	}

}
