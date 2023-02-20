package com.moonlight.moonlights.post;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.moonlight.moonlights.post.bo.PostBO;
import com.moonlight.moonlights.post.model.Post;
import com.moonlight.moonlights.post.model.PostDetail;
import com.moonlight.moonlights.user.model.User;

@Controller
@RequestMapping("/post")
public class PostController {

	@Autowired
	private PostBO postBO;
	
	@GetMapping("/timeline/view")
	public String timeline(Model model) {
		
		List<PostDetail> postDetailList = postBO.getPostDetailList();
		model.addAttribute("postDetailList", postDetailList);
		
		return "post/timeline";
	}
	
}
