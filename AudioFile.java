package studiplayer.audio;
import java.io.File;

public abstract class AudioFile {
	private String pathName;
	private String fileName;
	private String author;
	private String title;
	
	public AudioFile() throws NotPlayableException
	{
		//throw new NotPlayableException("AudioFiles", "No Parameter is inputted");
	}
	
	public AudioFile(String path) throws NotPlayableException
	{
		pathName = "";
		fileName = "";
		author = "";
		title = "";
		parsePathname(path);
		String parsedName = pathName;
		File file = new File(parsedName);
		if(!(file.exists())) 
		{
			throw new NotPlayableException(pathName, "The path does not exists.");
		}
		parseFilename(fileName);
	}
	
	private boolean isWindowOS() {
		return System.getProperty("os.name").toLowerCase()
		 .indexOf("win") >= 0;
	}
	
	public void parsePathname(String path) 
	{
		if(path.equals("")) 
		{
			return;
		}
		parsePath(path);
		parseFile(path);
	}
	
	public void parsePath(String path)
	{	
		String np = "";
		String originalPath = path;
		
		if(isWindowOS()) 
		{
			//Handle drive letter
			if(path.substring(0,1).equals("/") && path.substring(2,3).equals("/") )
		    {
		      //Linux: must convert to windows 
		      String driveLetter = path.substring(1,2);
		      path = driveLetter + ":\\" + path.substring(3); // /d/ => d:/
		    }

		    np = path.replace('/', '\\'); //Window uses \ always
		    //System.out.println(np);

		    for(int i = 0; i <= np.length() - 2; i++)
		    {
		      if(np.substring(i, i+1).equals("\\") || np.substring(i, i+1).equals("$"))
		      {
		        if(np.substring(i + 1, i+2).equals("\\") || np.substring(i + 1, i+2).equals("$"))
		        {
		          np = np.substring(0, i+1) + "$" + np.substring(i + 2);
		        }
		        
		      }
		    }
		    np = np.replace("$", "");
		    //System.out.println(np);
			
			
		}else //Linux
		{
			//Handle drive letter
			if((path.substring(1,3).equals(":/")))
		    {
		      //Windows: must convert to Linux
		      String driveLetter = path.substring(0,1);
		      path = "/" + driveLetter + "/" + path.substring(3);
		    }
			
			np = path.replace('\\', '/');
		    //System.out.println(np);
			
			
			//Check Empty
		    for(int i = 0; i <= np.length() - 2; i++)
		    {
		      if(np.substring(i, i+1).equals("/") || np.substring(i, i+1).equals("$"))
		      {
		        if(np.substring(i + 1, i+2).equals("/") || np.substring(i + 1, i+2).equals("$"))
		        {
		          np = np.substring(0, i+1) + "$" + np.substring(i + 2);
		        }
		        
		      }
		    }
		    np = np.replace("$", "");
		    //System.out.println(np);
//			adjustedFile = path.substring(2); //Handle drive letter
//			adjustedPath = adjustedPath.replaceAll("///", "/");
//			//Normalize Path Separator for Linux
//			while (adjustedPath.indexOf("/") != -1) 
//			{
//				adjusted
//			}
		}
		
		if (!(originalPath.contains("\\")) && !(originalPath.contains("/"))) 
		{
			int i = 0;
			int j = originalPath.length() - 1;
			
			int nonspaceCount = 0;
			
			for (int y = 0; y < originalPath.length(); y++) 
			{
				if(!(np.substring(y, y+1).equals(" "))) 
			    {
			    	  nonspaceCount += 1;
			    }
			}
			
			if(nonspaceCount == 0) 
			{
				pathName = "";
				return;
			}
			
			while(originalPath.substring(i, i+1).equals(" ") && originalPath.length() >= 1) 
			{
				i += 1;
			}
			
			while(originalPath.substring(j, j+1).equals(" ") && originalPath.length() >= 1) 
			{
				j -= 1;
			}
			
			np = originalPath.substring(i, j + 1);
		}
		//System.out.println(originalPath);
		pathName = np;
	}
	
	public void parseFile(String path) 
	{
		String np = "";
		String originalPath = path;
		
		if (!(originalPath.contains("\\")) && !(originalPath.contains("/"))) 
		{
			np = originalPath;
		}
		
		String simplifiedName = pathName;
		
		if(isWindowOS()) 
		{
			//Window -> \
			while(simplifiedName.indexOf('\\') != -1) 
			{
				simplifiedName = simplifiedName.substring(simplifiedName.indexOf('\\') + 1);
			}
			np = simplifiedName;
		}
		else 
		{
			while(simplifiedName.indexOf('/') != -1) 
			{
				simplifiedName = simplifiedName.substring(simplifiedName.indexOf('/') + 1);
			}
			np = simplifiedName;
		}
		
		//System.out.println(originalPath);
		fileName = np;
	}
	
	////
	
	public void parseFilename(String filename) 
	{
		if(filename.equals("")) 
		{
			return;
		}
		
		if(filename.equals("-")) 
		{
			title = "-";
			return;
		}
		
		int nonspaceCount = 0;
		
		for (int y = 0; y < filename.length(); y++) 
		{
			if(!(filename.substring(y, y+1).equals(" "))) 
		    {
		    	  nonspaceCount += 1;
		    }
		}
		
		if(nonspaceCount == 0) 
		{
			return;
		}
		
		parseAuthor(filename);
		parseTitle(filename);
	}
	
	public void parseAuthor(String filename) 
	{
		String np = "";
		String authorName = filename;
		int dashLoc = authorName.lastIndexOf(" - ");
		String remWhiteSpace = authorName.replace(" ", "");
		if(dashLoc == -1) 
		{
			author = "";
			return;
		}else if(remWhiteSpace.equals("-")) 
		{
			author = "";
			return;
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
		
		author = np;
	}
	
	public void parseTitle(String filename) 
	{
		String np = "";
		String titleName = filename;
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
			
			return;
		}else if(remWhiteSpace.equals("-")) 
		{
			title = "";
			return;
		}
		else 
		{
			titleName = titleName.substring(dashLoc + 3);
		}
		
		//System.out.println(dashLoc);
		
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
		
		//title = np;
	}
	
	public String getPathname() 
	{
		return pathName;
	}
	
	public String getFilename() 
	{
		return fileName;
	}
	
	public String getAuthor() 
	{
		return author;
	}
	
	public String getTitle() 
	{
		return title;
	}
	
	@Override
	public String toString() 
	{
		return "";
	}
	
	public String getAlbum() 
	{
		return null;
	}
	
	public long getDuration() 
	{
		return -1;
	}
	
	public abstract void play() throws NotPlayableException;
	public abstract void togglePause();
	public abstract void stop();
	public abstract String formatDuration();
	public abstract String formatPosition();
}
