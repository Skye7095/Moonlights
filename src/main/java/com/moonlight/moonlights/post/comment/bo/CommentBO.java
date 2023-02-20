package com.moonlight.moonlights.post.comment.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moonlight.moonlights.post.comment.dao.CommentDAO;
import com.moonlight.moonlights.post.comment.model.Comment;
import com.moonlight.moonlights.post.comment.model.CommentDetail;
import com.moonlight.moonlights.user.bo.UserBO;
import com.moonlight.moonlights.user.model.User;

@Service
public class CommentBO {
	
	@Autowired
	private CommentDAO commentDAO;
	
	@Autowired
	private UserBO userBO;	
	
	public int addComment(int userId, int postId, String content) {
		return commentDAO.insertComment(userId, postId, content);
	}
	
	public List<CommentDetail> getCommentDetail(){
		List<Comment> commentList = commentDAO.selectCommentList();
		
		List<CommentDetail> commentDetailList = new ArrayList<>();
		for(Comment comment:commentList) {
			CommentDetail commentDetail = new CommentDetail();
			
			commentDetail.setPostId(comment.getPostId());
			commentDetail.setContent(comment.getContent());
			
			
			User user = userBO.getUserById(comment.getUserId());
			commentDetail.setUserName(user.getName());
			
			commentDetailList.add(commentDetail);
		}
		
		return commentDetailList;
	}

}
