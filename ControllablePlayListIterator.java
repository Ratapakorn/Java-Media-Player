package studiplayer.audio;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ControllablePlayListIterator implements Iterator<AudioFile>{
	private List<AudioFile> pl;
	private int index;
	private String currSearch;
	private SortCriterion sorting;

	public ControllablePlayListIterator(List<AudioFile> list){
		index = 0;
		pl = new ArrayList<AudioFile>(list);
		if(currSearch != null && sorting != null) 
		{
			pl = sortAndSearch(list, currSearch, sorting);
		}
	}
	
	public ControllablePlayListIterator(List<AudioFile> list, String sr, SortCriterion sc) 
	{
		pl = sortAndSearch(list, sr, sc);	
		index = 0;
	}
	
	public ArrayList<AudioFile> sortAndSearch(List<AudioFile> list, String sr, SortCriterion sc)
	{
		currSearch = sr;
		sorting = sc;
		//Exclude Files that are not included in the search.
		List<AudioFile> includedFile = new ArrayList<AudioFile>();
		
		if(sc == null) 
		{
			sc = SortCriterion.DEFAULT;
		}
		
		if(!(list == null))
		{
			if(!(list.isEmpty())) {
				if(sc.equals(SortCriterion.ALBUM)) 
				{
					if(sr == null) 
					{
						includedFile = new ArrayList<AudioFile>(list);
					}else 
					{
						for(int i = 0; i < list.size(); i++) 
						{
							if(list.get(i).getAlbum() != null) 
							{
								if(list.get(i).getAlbum().contains(sr)) 
								{
									includedFile.add(list.get(i));
								}
							}
							

						}
					}
					
					includedFile.sort(new AlbumComparator());
				}else if(sc.equals(SortCriterion.AUTHOR)) 
				{
					if(sr == null) 
					{
						includedFile = new ArrayList<AudioFile>(list);
					}else 
					{
						for(int i = 0; i < list.size(); i++) 
						{
							if(list.get(i).getAuthor().contains(sr)) 
							{
								includedFile.add(list.get(i));
							}
						}
					}
					
					if(includedFile.isEmpty()) 
					{
						for(int i = 0; i < list.size(); i++) 
						{
							if(list.get(i).toString().contains(sr)) 
							{
								includedFile.add(list.get(i));
							}
						}
					}
					
					includedFile.sort(new AuthorComparator());
				}else if(sc.equals(SortCriterion.TITLE)) 
				{
					if(sr == null) 
					{
						includedFile = new ArrayList<AudioFile>(list);
					}else 
					{
						for(int i = 0; i < list.size(); i++) 
						{
							if(list.get(i).toString().contains(sr)) 
							{
								includedFile.add(list.get(i));
							}
						}
					}
					
					includedFile.sort(new TitleComparator());
				}else if(sc.equals(SortCriterion.DURATION)) 
				{
					if(sr != null && sr != "") 
					{
						for(int i = 0; i < list.size(); i++) 
						{
							
							if(list.get(i).toString().contains(sr)) 
							{
								includedFile.add(list.get(i));
							}
						}
					}else 
					{
						includedFile = new ArrayList<AudioFile>(list);
					}

					includedFile.sort(new DurationComparator());
					
				}else //DEFAULT
				{
					if(sr != null && sr != "") 
					{
						for(int i = 0; i < list.size(); i++) 
						{
							
							if(list.get(i).toString().contains(sr)) 
							{
								includedFile.add(list.get(i));
							}
						}
					}else 
					{
						includedFile = new ArrayList<AudioFile>(list);
					}
					
				}
			}
		}
		
		pl = includedFile;
		return (ArrayList<AudioFile>) includedFile;
	}
	
	public boolean checkHasNext() 
	{
		//Logic for Playlist Iterable Class
		return (index < (pl.size() - 1));
	}
	
	@Override
	public boolean hasNext() {
		//Logic for iterator() Class
		return (index < (pl.size()));
		
	}
	
	public AudioFile computeNextSong() 
	{
		if(!(pl.isEmpty())) 
		{
			if(checkHasNext()) 
			{
				index++;
			}else 
			{
				index = 0;
			}
			AudioFile current = pl.get(index);
			return current;
		}
		return null;
	}
	
	public AudioFile fetchNextSong() 
	{
		//For Main Playlist
		return computeNextSong();
	}

	@Override
	public AudioFile next() {
		//For Iterator()
		if(!(pl.isEmpty())) 
		{
			AudioFile current = pl.get(index);
			if(hasNext()) 
			{
				index++;
			}
			return current;
		}
		return null;
		
		
	}
	
	public AudioFile doJumpToAudioFile(AudioFile file) 
	{
		//For Playlist implementation
		int fileIndex = pl.indexOf(file);
		if(fileIndex == -1) //file cannot be found
		{
			return null;
		}else 
		{
			index = fileIndex;
			return file;
		}
	}
	
	public AudioFile jumpToAudioFile(AudioFile file) 
	{
		//For Iterator()
		int fileIndex = pl.indexOf(file);
		if(fileIndex == -1) //file cannot be found
		{
			return null;
		}else 
		{
			if(index < (pl.size() - 1)) 
			{
				index = fileIndex + 1;
			}else 
			{
				index = fileIndex + 1;
			}
			
			return file;
		}
		
		
	}
	
	//Additional Methods for Playlist
	
	public AudioFile current() 
	{
		if(!(pl.isEmpty())) 
		{
			return pl.get(index);
		}
		return null;
		
	}
	
	public void playListRestart(String search) 
	{
		index = 0;
		pl = sortAndSearch(pl, search, sorting);
	}
	
//	public void restartPlaybackSequence(String search) 
//	{
//		index = 0;
//		
//	}
	
	public void restartPlaybackSequence(SortCriterion sorter) 
	{
		index = 0;
		pl = sortAndSearch(pl, currSearch, sorter);
	}
	
	public void reloadPlaybackSequence(LinkedList<AudioFile> updatedPlaylist) 
	{
		index = 0;
		pl = sortAndSearch(updatedPlaylist, currSearch, sorting);
	}
	
	public void addPlaybackSequence(AudioFile addedFile) 
	{
		index = 0;
		pl.add(addedFile);
		pl = sortAndSearch(pl, currSearch, sorting);
	}
	
	public void removePlaybackSequence(AudioFile addedFile) 
	{
		index = 0;
		pl.remove(addedFile);
		pl = sortAndSearch(pl, currSearch, sorting);
	}
	

}
