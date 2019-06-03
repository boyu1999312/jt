package com.jt.vo;

import java.io.Serializable;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import com.jt.pojo.Item;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class EasyUIData implements Serializable{

	private Integer total;		//记录总数
	private List<Item> rows;	//数据集合
}
