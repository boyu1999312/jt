package com.jt.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ImageVO implements Serializable{
	private Integer error;	//错误码  0成功 1失败
	private String url;		//虚拟的图片url
	private Integer width;	//宽度
	private Integer height;	//高度
}
