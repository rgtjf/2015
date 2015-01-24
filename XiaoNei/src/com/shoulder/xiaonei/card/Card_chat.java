package com.shoulder.xiaonei.card;

public class Card_chat {

	private String content;
	private int who;//���һ��Ǳ��˵ķ���
	private String name;//�����˵�����
	private String id;//�����˵�id
	private String photoUrl;
	private int chatType;
	private long timeStamp;
	private int status;//0��ʾ�����У�1��ʾ�ɹ���2��ʾʧ��
	
	public Card_chat(String content , int who , String name , String id ,String photoUrl ,int chatType, long timeStamp, int status){
		this.content = content.trim();
		this.who = who;
		this.name = name;
		this.id = id;
		this.photoUrl = photoUrl;
		this.chatType = chatType;
		this.timeStamp = timeStamp;
		this.status = status;
	}
	
	public String getContent(){
		return content;
	}
	
	public int getWho(){
		return who;
	}
	
	public String getName(){
		return name;
	}
	
	public String getId(){
		return id;
	}
	
	public String getPhotoUrl(){
		return photoUrl;
	}
	
	public int getChatType(){
		return chatType;
	}
	
	public long getTimeStamp(){
		return timeStamp;
	}
	public void setTimeStamp(long timeStamp){
		this.timeStamp = timeStamp;
	}
	
	public int getStatus(){
		return status;
	}
	public void setStatus(int status){
		this.status = status;
	}
}
