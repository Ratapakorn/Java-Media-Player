package studiplayer.audio;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Iterator;

public class PlayList implements Iterable<AudioFile>{
	private LinkedList<AudioFile> playlist;
	//private int current;
	private String search;
	private SortCriterion sortCriterion;
	private ControllablePlayListIterator cpli;

	public PlayList(){
		playlist = new LinkedList<AudioFile>();
		//current = 0;
		search = null;
		sortCriterion = SortCriterion.DEFAULT;
		cpli = new ControllablePlayListIterator(playlist, search, sortCriterion);
	}
	
	public PlayList(String path)//throws NotPlayableException
	{
		search = null;
		sortCriterion = SortCriterion.DEFAULT;
		
		cpli = new ControllablePlayListIterator(playlist, search, sortCriterion);
		
		if(!(new File(path).exists())) 
		{
			throw new EmptyStackException(); //I was unable to use other types of Exception for some reason.
		}
		playlist = loadFromM3U(path);

	}
	
	@Override
	public Iterator<AudioFile> iterator() {
		return new ControllablePlayListIterator(playlist, search, sortCriterion);
	}
	
	public void setSearch(String value) 
	{
		//Restart Playback Sequence
		cpli.playListRestart(value);
		
		search = value;
	}
	
	public String getSearch() 
	{
		return search;
	}
	
	public SortCriterion getSortCriterion() {
		return sortCriterion;
	}

	public void setSortCriterion(SortCriterion sortCriterion) {
		//Restart Playback Sequence
		cpli.restartPlaybackSequence(sortCriterion);
		this.sortCriterion = sortCriterion;
	}
	
	public LinkedList<AudioFile> getList()
	{
		return playlist;
	}
	
	public void add(AudioFile file) 
	{
		//Restart Playback Sequence
		cpli.addPlaybackSequence(file);
		
		playlist.add(file);
	}
	
	public void remove(AudioFile file) 
	{
		//Restart Playback Sequence
		cpli.removePlaybackSequence(file);
		
		playlist.remove(file);
	}
	
	public int size() 
	{
		return playlist.size();
	}
	
	public AudioFile currentAudioFile() 
	{
		return cpli.current();
	}
	
	public void nextSong() 
	{
		cpli.fetchNextSong();
	}
	
	public void jumpToAudioFile(AudioFile audioFile) 
	{
		cpli.doJumpToAudioFile(audioFile);
	}
	
	public void saveAsM3U(String pathname) 
	{
		LinkedList<String> paths = new LinkedList<String>();
		for (int i = 0; i < playlist.size(); i++) 
		{
			paths.add(playlist.get(i).getPathname());
		}
		
		FileWriter writer = null;
		String sep = System.getProperty("line.separator");
		
		try {
			// create the file if it does not exist, otherwise reset the file
			// and open it for writing
			writer = new FileWriter(pathname);
			for (String line : paths) {
				// write the current line + newline char
				writer.write(line + sep);
			}
		} catch (IOException e) {
			throw new RuntimeException("Unable to write file " + pathname + "!");
		} finally {
			try {
				//Debugging Purposes: System.out.println("File " + pathname + " written!");
				// close the file writing back all buffers
				writer.close();
			} catch (Exception e) {
				// ignore exception; probably because file could not be opened
			}
		}
	}
	
	public LinkedList<AudioFile> loadFromM3U(String path) { 
		
		LinkedList<AudioFile> newplaylist = new LinkedList<AudioFile>();
		Scanner scanner = null;
		
		try {
			// open the file for reading
			scanner = new Scanner(new File(path));
			
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if(!(line.isEmpty()) || line.length() != 0) 
				{
					if(!(line.substring(0,1).equals("#")) && !(line.isBlank()) && !(line.equals(" "))) 
					{
						if(new File(line).exists()) 
						{
							newplaylist.add(AudioFileFactory.createAudioFile(line));
						}
						
					}
				}
				
			}
		} catch (IOException e) {
			//throw new RuntimeErrorException(e);
		} catch (NotPlayableException e) {
			
			e.printStackTrace();
		} finally {
			try {
				//Debugging Purposes: System.out.println("File " + path + " read!");
				scanner.close();	
			} catch (Exception e) {
				// ignore; probably because file could not be opened
			}
		}
		
		playlist = newplaylist;
		
		//Restart Playback Sequence
		cpli.reloadPlaybackSequence(playlist);
		
		return newplaylist;
	}
	
	@Override
	public String toString()
	{
		return playlist.toString();
	}
	
//	Removed for subtask G)
	
//	public void setCurrent(int pos) Removed Current Attributes
//	{
//		current = pos;
//	}
	
//	public int getCurrent() Removed Current Attributes
//	{
//		return current;
//	}

	
	

}
