package com.wiriamubin.service.chat;

import java.io.Serializable;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;


/** 
* @ClassName: ChatData 
* @Description: 聊天信息实体类
* @author zhou
* @date 2015-1-28 下午3:28:45 
*  将chatMessage和chatuserinfo合并为一个类
*  
*/
@Table(name = "chatdata", execAfterTableCreated = "CREATE UNIQUE INDEX index_name ON chatdata(userId)")
public class ChatData extends EntityBase implements Serializable{
	
	public ChatData( String userId, String name,
			String profileImage, String type, String body) {
		super();
		this.userId = userId;
		this.name = name;
		this.profileImage = profileImage;
		this.type = type;
		this.body = body;
	}
	/** 
	* @Fields userId : 用户id
	*/ 
	@Column(column = "userId") // 建议加上注解， 混淆后列名不受影响
	public String userId;
	
	@Override
	public String toString() {
		return "ChatData [userId=" + userId + ", name=" + name
				+ ", profileImage=" + profileImage + ", type=" + type
				+ ", body=" + body + "]";
	}
	/** 
	* @Fields name :  用户名
	*/ 
	@Column(column = "name") // 建议加上注解， 混淆后列名不受影响
	public String name;
	/** 
	* @Fields profileImage :用户头像地址 
	*/ 
	@Column(column = "profileImage") // 建议加上注解， 混淆后列名不受影响
	public String profileImage;
	
	
	/**
	 * 消息类型
	 */
	@Column(column = "type") 
	public String 			type;
	
	/**
	 * 消息正文		
	 */
	@Column(column = "body")
	public String 			body;

}
