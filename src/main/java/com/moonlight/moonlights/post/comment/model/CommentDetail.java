package com.moonlight.moonlights.post.comment.model;

public class CommentDetail {
	private String userName;
	private int postId;
	private String content;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
