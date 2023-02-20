package com.moonlight.moonlights.post.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.moonlight.moonlights.common.FileManagerService;
import com.moonlight.moonlights.post.comment.bo.CommentBO;
import com.moonlight.moonlights.post.dao.PostDAO;
import com.moonlight.moonlights.post.model.Post;
import com.moonlight.moonlights.post.model.PostDetail;
import com.moonlight.moonlights.user.bo.UserBO;
import com.moonlight.moonlights.user.model.User;

@Service
public class PostBO {
	
	@Autowired
	private PostDAO postDAO;
	
	@Autowired
	private UserBO userBO;
	
	public int addPost(int userId, String content, MultipartFile file) {
		
		String imagePath = FileManagerService.saveFile(userId, file);
		return postDAO.insertPost(userId, content, imagePath);
	}
	
	
	
	public List<PostDetail> getPostDetailList() {
		
		List<Post> postList = postDAO.selectPostList();
		
		// 생성된 postDetail 객체를 리스트로 구성한다.
		List<PostDetail> postDetailList = new ArrayList<>();
		for(Post post:postList) {
			// postDetail 객체를 생성하고, post 객체의 정보를 저장한다.
			PostDetail postDetail = new PostDetail();
			postDetail.setId(post.getId());
			postDetail.setUserId(post.getUserId());
			postDetail.setContent(post.getContent());
			postDetail.setImagePath(post.getImagePath());
			
			// userName 값을 저장한다.
			User user = userBO.getUserById(post.getUserId());
			postDetail.setUserName(user.getName());			
			
			postDetailList.add(postDetail);
		}
		
		return postDetailList;
	}
	
	public int addLike(int userId, int postId) {
		return postDAO.insertLike(userId, postId);
	};
	
}
