package com.moonlight.moonlights.post.like.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moonlight.moonlights.post.like.dao.LikeDAO;

@Service
public class LikeBO {
	
	@Autowired
	private LikeDAO likeDAO;
	
	public int addLike(int userId, int postId) {
		return likeDAO.insertLike(userId, postId);
	};
	
	// postId를 전달 받고, 좋아용 갯수를 리턴하는 메소드
	public int selectLikeNumber(int postId) {
		return likeDAO.selectLikeNumber(postId);
	}
	
	// postId와 userId를 전달 받고 좋아용 여부 리턴하는 메소드
	public boolean isLike(int userId, int postId) {
		if(likeDAO.selectLikeCountByUserId(userId, postId) == 0) {
			return false;
		}else {
			return true;
		}
	}
	
	// postId와 userId를 전달받고 해당 좋아요 행을 삭제한다.
	public int unlike(int postId, int userId) {
		return likeDAO.deleteLike(postId, userId);
	}
	
	// postId 기반을 좋아요를 삭제
	public int deleteLikeByPostId(int postId) {
		return likeDAO.deleteLikeByPostId(postId);
	}
}
