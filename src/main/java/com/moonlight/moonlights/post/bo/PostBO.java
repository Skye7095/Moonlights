package com.moonlight.moonlights.post.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.moonlight.moonlights.common.FileManagerService;
import com.moonlight.moonlights.post.comment.bo.CommentBO;
import com.moonlight.moonlights.post.comment.model.CommentDetail;
import com.moonlight.moonlights.post.dao.PostDAO;
import com.moonlight.moonlights.post.like.bo.LikeBO;
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
	
	@Autowired
	private LikeBO likeBO;
	
	@Autowired
	private CommentBO commentBO;
	
	public int addPost(int userId, String content, MultipartFile file) {
		
		String imagePath = FileManagerService.saveFile(userId, file);
		return postDAO.insertPost(userId, content, imagePath);
	}
	
	
	
	public List<PostDetail> getPostDetailList(int userId) {
		
		List<Post> postList = postDAO.selectPostList();
		
		// 생성된 postDetail 객체를 리스트로 구성한다.
		List<PostDetail> postDetailList = new ArrayList<>();
		for(Post post:postList) {
			// postDetail 객체를 생성하고, post 객체의 정보를 저장한다.
			PostDetail postDetail = new PostDetail();
			
			// userName 값을 저장한다.
			User user = userBO.getUserById(post.getUserId());
						
			// like의 갯수를 저장한다.
			int likeNumber = likeBO.selectLikeNumber(post.getId());
			
			// 특정 userId가 특정 postId에 like를 했는지 안 했는지 확인한다
			boolean isLike = likeBO.isLike(userId, post.getId());
			
			// comment중 userName/content 저장한다.	
			List<CommentDetail> commentList = commentBO.getCommentList(post.getId());

			postDetail.setId(post.getId());
			postDetail.setUserId(post.getUserId());
			postDetail.setContent(post.getContent());
			postDetail.setImagePath(post.getImagePath());
			postDetail.setLikeNumber(likeNumber);
			postDetail.setLike(isLike);
			postDetail.setUserName(user.getName());			
			postDetail.setCommentList(commentList);
			
			postDetailList.add(postDetail);
		}
		
		return postDetailList;
	}
	
	public int deletePost(int postId, int userId) {
		
		Post post = postDAO.selectPost(postId);
		
		// 대상 post 를 삭제
		int count = postDAO.deletePost(postId, userId);
		if(count == 1) {
			
			FileManagerService.removeFile(post.getImagePath());
			
			// post와 관련된 댓글 삭제
			commentBO.deleteCommentByPostId(postId);
			
			// 좋아요 삭제
			likeBO.deleteLikeByPostId(postId);
		}
		
		return count;
	}
}
