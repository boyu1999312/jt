package com.jt.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("tb_item_cat")
public class ItemCat extends BasePojo implements Serializable{
	@TableId(type = IdType.AUTO)
	private Long id;
	private Long parentId; 	//父ID=0时，代表一级类目
	private String name;		//名称
	private Integer status = 1; //默认值为1，可选值：1正常，2删除
	private Integer sortOrder;	//排序号
	private Boolean isParent;	//是否为父级
}
