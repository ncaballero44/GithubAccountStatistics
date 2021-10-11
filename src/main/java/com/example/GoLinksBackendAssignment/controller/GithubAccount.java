package com.example.GoLinksBackendAssignment.controller;

import java.io.IOException;
//import java.util.HashMap;
import java.util.Map;

import org.kohsuke.github.*;

import net.minidev.json.JSONObject;

public class GithubAccount 
{
	private String username="";
	private GitHub gh=null;
	private GHUser ghUser=null;
	private Map<String, GHRepository> allRepositories=null;
									

	
	public boolean userExists=false;
						 
//	public Map<String, Long> allLanguagesUsed=new HashMap<String, Long>();			//All languages used by this account and how many bytes each language uses
	
	//TODO List of languages and their counts. Should probably use map 
	
	public GithubAccount(String username)
	{
		this.username=username;
		
		try 
		{
			this.gh = GitHub.connectAnonymously();									//Connect to GitHub
			this.ghUser=this.gh.getUser(this.username);								//Get the GitHub account based off of a username
			this.allRepositories=this.ghUser.getRepositories();							//Get all of the account's public repos
			this.userExists=true;
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		//If the account was found, then userExists is set to true.
		//Otherwise, the method will error out and userExists will remain false
																 
	}
	//O(n*m) where n=all of the account's repos and m=all languages used in the current repo
	public JSONObject generateAllRepoStats()
	{
		JSONObject stats = new JSONObject();
		
		int totalNumberOfRepos=this.allRepositories.size();		//Total number of public repos including forked repos
		int totalNumberOfStargazers=0; 							//Total number of stargazers for ALL repositories
		int totalForkCount=0; 									//Total fork count for ALL repositories
		String averageSizeOfRepository="";						//Average size of a repository.
		int totalSumOfRepoSizes=0;								//All of the sizes of all the account's repos added up
//		Map<String, Long> currentListOfLanguages=null;
		for(Map.Entry<String, GHRepository> currentRepo : this.allRepositories.entrySet())
		{
			totalNumberOfStargazers += currentRepo.getValue().getStargazersCount();
			totalForkCount += currentRepo.getValue().getForksCount();
			totalSumOfRepoSizes += currentRepo.getValue().getSize();
			
//			try 
//			{
//				currentListOfLanguages=currentRepo.getValue().listLanguages(); //<--Slows down code immensely
//			} catch (IOException e) 
//			{
//				e.printStackTrace();
//			}
			
//			updateLanguageList(currentListOfLanguages);
		}
		
		averageSizeOfRepository=getAverageSizeOfRepos(totalSumOfRepoSizes, totalNumberOfRepos);
		
		stats.put("totalNumberOfRepos", totalNumberOfRepos);
		stats.put("totalNumberOfStargazers", totalNumberOfStargazers);
		stats.put("totalForkCount", totalForkCount);
		stats.put("averageSizeOfRepos", averageSizeOfRepository);
		
		return stats;
	}
	
	public JSONObject generateUnforkedRepoStats()
	{
		JSONObject stats = new JSONObject();
		
		int totalNumberOfRepos=0;								//Total number of public repos WITHOUT forked repos
		int totalNumberOfStargazers=0; 							//Total number of stargazers for UNFORKED repositories
		String averageSizeOfRepository="";						//Average size of a repository.
		int totalSumOfRepoSizes=0;								//All of the sizes of all the account's repos added up
		
		for(Map.Entry<String, GHRepository> currentRepo : this.allRepositories.entrySet())
		{
			if(currentRepo.getValue().getForksCount()==0)
			{
				totalNumberOfRepos += 1;
				totalNumberOfStargazers += currentRepo.getValue().getStargazersCount();
				totalSumOfRepoSizes += currentRepo.getValue().getSize();
			}
		}
		
		averageSizeOfRepository=getAverageSizeOfRepos(totalSumOfRepoSizes, totalNumberOfRepos);
		
		stats.put("totalNumberOfRepos", totalNumberOfRepos);
		stats.put("totalNumberOfStargazers", totalNumberOfStargazers);
		stats.put("averageSizeOfRepos", averageSizeOfRepository);
		
		return stats;
	}
	
	private String getAverageSizeOfRepos(int totalSumOfRepoSizes, int numberOfRepos)
	{
		double averageSizeOfRepos=totalSumOfRepoSizes/numberOfRepos;
		
		int sizeOfKB=1024; 					//1024 bytes in a KB
		int sizeOfMB=sizeOfKB*sizeOfKB;		//1024 KB in a MB
		int sizeOfGB=sizeOfMB*sizeOfKB;		//1024 MB in a GB
		
		if(averageSizeOfRepos/sizeOfGB>0)
		{
			return String.valueOf(averageSizeOfRepos/sizeOfGB)+" GB";
		}
		if(averageSizeOfRepos/sizeOfMB>0)
		{
			return String.valueOf(averageSizeOfRepos/sizeOfMB)+" MB";
		}
		if(averageSizeOfRepos/sizeOfKB>0)
		{
			return String.valueOf(averageSizeOfRepos/sizeOfKB)+" KB";
		}
		
		return String.valueOf(averageSizeOfRepos)+" bytes";
	}
	
//	private void updateLanguageList(Map<String, Long> currentListOfLanguages) <-- currently slows down whole project to the point of slowing down future runs. Needs optimization
//	{
//		for(Map.Entry<String, Long> currentLanguage : currentListOfLanguages.entrySet())
//		{
//			if(this.allLanguagesUsed.containsKey(currentLanguage.getKey()))
//			{
//				long updatedLanguageSize=currentLanguage.getValue()+this.allLanguagesUsed.get(currentLanguage.getKey());
//				this.allLanguagesUsed.put(currentLanguage.getKey(), updatedLanguageSize);
//			}
//			else
//			{
//				this.allLanguagesUsed.put(currentLanguage.getKey(), currentLanguage.getValue());
//			}
//		}
//	}
}
