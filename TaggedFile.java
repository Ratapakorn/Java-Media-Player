package studiplayer.audio;
import java.io.File;
import java.util.Map;

import studiplayer.basic.TagReader;

public class TaggedFile extends SampledFile {
	private String album;
	//private String title;
	
	public TaggedFile() throws NotPlayableException
	{
		throw new NotPlayableException("AudioFiles", "No Parameter is inputted");
	}
	
	public TaggedFile(String path) throws NotPlayableException {
		super(path);
		readAndStoreTags();
		super.title = titleAnalyzer();
		super.author = authorAnalyzer();
		super.duration = readDuration();
		album = albumAnalyzer();
		
	}
	
	@Override
	public String getTitle() 
	{
		return super.title; //titleAnalyzer();
	}
	
	@Override
	public String getAuthor() 
	{
		return super.author;		
	}
	
	public long getDuration() 
	{
		return super.duration;
	}
	
	public String getAlbum() 
	{	
		return album;
	}
	
	public String readTitle() 
	{
		String tTitle = null;
		Map<String, Object> tagMap = TagReader.readTags(getPathname());
		for (String tag : tagMap.keySet()) {
			Object value = tagMap.get(tag);
			if(tag.equals("title")) 
			{
				//System.out.println(value);
				tTitle = value.toString();
			}
		}
		
		return tTitle;
	}
	
	public String readAuthor() 
	{
		String tAuthor = null;
		Map<String, Object> tagMap = TagReader.readTags(getPathname());
		for (String tag : tagMap.keySet()) {
			Object value = tagMap.get(tag);
			if(tag.equals("author")) 
			{
				tAuthor = value.toString();
			}
		}
		
		return tAuthor;
	}
	
	public int readDuration() 
	{
		int duration = 0;
		Map<String, Object> tagMap = TagReader.readTags(getPathname());
		for (String tag : tagMap.keySet()) {
			Object value = tagMap.get(tag);
			if(tag.equals("duration")) 
			{
				duration = Integer.parseInt(value.toString());
			}
		}
		return duration;
	}
	
	public String readParsedTitle() 
	{
		String title = getFilename();
		String np = "";
		String titleName = title;
		int dashLoc = titleName.lastIndexOf(" - ");
		String remWhiteSpace = titleName.replace(" ", "");
		if(dashLoc == -1) 
		{
			int a = 0;
			int b = titleName.length() - 1;
			
			while(titleName.substring(a, a+1).equals(" ") && a < titleName.length()) 
			{
				a += 1;
			}
			
			while(titleName.substring(b, b+1).equals(" ") && b < titleName.length()) 
			{
				b -= 1;
			}
			
			np = titleName.substring(a, b + 1);
			
			b = np.length() - 1;
			
			while(!(titleName.substring(b, b+1).equals(".") && b < titleName.length()))
			{
				b -= 1;
			}
			
			title = np.substring(0, b);
			
			return title;
		}else if(remWhiteSpace.equals("-")) 
		{
			title = "";
			return "";
		}
		else 
		{
			titleName = titleName.substring(dashLoc + 3);
		}
		
		int i = 0;
		int j = titleName.length() - 1;
		
		while(titleName.substring(i, i+1).equals(" ") && i < titleName.length()) 
		{
			i += 1;
		}
		
		while(titleName.substring(j, j+1).equals(" ") && i < titleName.length()) 
		{
			j -= 1;
		}
		
		np = titleName.substring(i, j + 1);
		
		j = titleName.length() - 1;
		
		while(!(titleName.substring(j, j+1).equals(".") && i < titleName.length()))
		{
			j -= 1;
		}
		
		np = titleName.substring(0, j);
		
		i = 0;
		j = np.length() - 1;
		
		while(np.substring(i, i+1).equals(" ") && i < np.length()) 
		{
			i += 1;
		}
		
		while(np.substring(j, j+1).equals(" ") && i < np.length()) 
		{
			j -= 1;
		}
		
		title = np.substring(i, j + 1);
		
		return title;
	}
	
	public String readParsedAuthor() 
	{
		String np = "";
		String authorName = getFilename();
		int dashLoc = authorName.lastIndexOf(" - ");
		String remWhiteSpace = authorName.replace(" ", "");
		if(dashLoc == -1) 
		{
			//tAuthor = "";
			return "";
		}else if(remWhiteSpace.equals("-")) 
		{
			//author = "";
			return "";
		}
		else 
		{
			authorName = authorName.substring(0, dashLoc);
		}
		
		//System.out.println(dashLoc);
		
		int i = 0;
		int j = authorName.length() - 1;
		
		while(authorName.substring(i, i+1).equals(" ") && i < authorName.length()) 
		{
			i += 1;
		}
		
		while(authorName.substring(j, j+1).equals(" ") && i < authorName.length()) 
		{
			j -= 1;
		}
		
		np = authorName.substring(i, j + 1);
		
		//author = np;
		return np;
		
	}
	
	public String spaceRemover(String t) 
	{
		String remW = t.replace(" ", "");
		if(remW.equals(t)) 
		{
			return t;
		}
		
		int y = t.length() - 1;
		for(int i = y; i > 0; i--) 
		{
			if(t.substring(y, y+1).equals(" ")) 
			{
				y -= 1;
			}
		}
		return t.substring(0, y + 1);
	
	}
	
	public String titleAnalyzer() 
	{
		String tTitle = readTitle();
		
		if(tTitle == null) 
		{
			return readParsedTitle();
		}
		
		return spaceRemover(tTitle);
	}
	
	public String authorAnalyzer() 
	{
		String tAuthor = readAuthor();
		
		if(tAuthor == null) 
		{
			return readParsedAuthor();
		}
		
		return spaceRemover(tAuthor);
	}
	
	public String albumAnalyzer() 
	{
		if(album == null) 
		{
			return "";
		}
		
		return spaceRemover(album);
	}
	
	public void readAndStoreTags() throws NotPlayableException
	{
		String tTitle = "";
		String tAuthor = "";
		int duration = 0;
		try 
		{
			Map<String, Object> tagMap = TagReader.readTags(getPathname());
			for (String tag : tagMap.keySet()) {
				Object value = tagMap.get(tag);
				if(tag.equals("title")) 
				{
					//System.out.println(value);
					tTitle = value.toString();
				}
				if(tag.equals("author")) 
				{
					//System.out.println(value);
					tAuthor = value.toString();
				}
				if(tag.equals("album")) 
				{
					//System.out.println(value);
					album = value.toString();
				}
				if(tag.equals("duration")) 
				{
					//System.out.println(value);
					duration = Integer.parseInt(value.toString());
				}
//				System.out.printf("key: %-25s value: %-30s (type: %s)\n",
//						tag, value, value.getClass().getSimpleName());
			}
		}catch(RuntimeException e) 
		{
			throw new NotPlayableException("File contains an Invalid Tag", e); //When File does not contain a tag/ or invalid tag
		}
		
		
		
	}
	
	@Override
	public String toString() 
	{
		String formatter = "";
		if(getAuthor() != "") 
		{
			formatter = formatter + getAuthor() + " - ";
		}
		if(getTitle() != "") 
		{
			formatter = formatter + getTitle() + " - ";
		}
		if(getAlbum() != "") 
		{
			formatter = formatter + getAlbum() + " - ";
		}
		return formatter + timeFormatter((long) getDuration());
	}
	
	

}
