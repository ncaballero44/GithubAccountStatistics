package com.example.GoLinksBackendAssignment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.minidev.json.JSONObject;

@Controller
public class GithubAccountController 
{
	@GetMapping("getForm")
	public String getForm()
	{
		return "usernameFrom";
	}
	
	@PostMapping("/saveDetails")
	public String saveDetails(@RequestParam("username") String username, @RequestParam("hiddenField") String hiddenField, ModelMap modelMap)
	{
		GithubAccount githubAccount=new GithubAccount(username);
		
		if(githubAccount.userExists==false)
		{
			return "userNotFound";
		}
		
		JSONObject stats = new JSONObject();
		
		if(hiddenField.equals("checked"))
		{
			stats=githubAccount.generateAllRepoStats();
		}
		else if(hiddenField.equals("unchecked"))
		{
			stats=githubAccount.generateUnforkedRepoStats();
		}
		else
		{
			stats.put("Error", "Please try again");
		}

		modelMap.put("stats", stats.toJSONString());
		
		return "viewDetails";
	}
}
