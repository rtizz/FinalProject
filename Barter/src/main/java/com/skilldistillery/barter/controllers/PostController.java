package com.skilldistillery.barter.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.barter.entities.Comment;
import com.skilldistillery.barter.entities.Post;
import com.skilldistillery.barter.services.PostService;

@RestController
@RequestMapping("api")
@CrossOrigin({"*", "http://localhost/"})
public class PostController {
	
	@Autowired
	private PostService postService;
	
	
	@GetMapping("posts")
	public List<Post> index(HttpServletRequest req, HttpServletResponse res) {
		return postService.indexAll();
	} 
	
	@GetMapping("posts/user/{uId}")
	public Set<Post> indexByUser( HttpServletRequest req, HttpServletResponse res, @PathVariable int uId) {
		return postService.postsByUser(uId);
	} 
	
	@GetMapping("posts/{pId}")
	public Post indexByPostId( Principal principal, HttpServletRequest req, HttpServletResponse res, @PathVariable int pId) {
		return postService.postById(pId, principal.getName());
	} 
	
	@GetMapping("posts/{keyword}")
	public List<Post> indexByPostId( HttpServletRequest req, HttpServletResponse res, @PathVariable String keyword) {
		return postService.postKeywordSearch(keyword, keyword);
	} 
	
	@GetMapping("posts/comments/{pId}")
	public List<Comment> indexByPostId(Principal principal, HttpServletRequest req, HttpServletResponse res, @PathVariable String keyword, @PathVariable int pId) {
		return postService.postComments(principal.getName(), pId );
	} 
	
	@PostMapping("posts")
	public Post createPost(Principal principal, HttpServletRequest req, HttpServletResponse res, @RequestBody Post post) {
		Post createPost = null;
		try {
			createPost = postService.createPost(principal.getName(), post);
			if (createPost != null) {
				res.setStatus(201);
			} else {
				res.setStatus(400);
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(400);
		}
		return createPost;
	} 
	
	@PutMapping("posts/{pId}")
	public Post updatePost(Principal principal, HttpServletRequest req, HttpServletResponse res, @PathVariable int pId, @RequestBody Post post) {
		try {
			post = postService.updatePost(principal.getName(), pId, post);
			if (post != null) {
				res.setStatus(201);
			} else {
				res.setStatus(400);
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(400);
		}
		return post;
	} 
	
    @DeleteMapping("posts/{pId}")
	public void destroyPost(Principal principal, HttpServletRequest req, HttpServletResponse res, @PathVariable int pId) {
    	 try {
    		 if (postService.destroyPost(principal.getName(), pId)) {
    			 res.setStatus(204);
    			 
    		 } else { 
    			 res.setStatus(404);
    		 }
    		} catch (Exception e) {
    			e.printStackTrace();
    			res.setStatus(400);
    		}
	}
	
	

}