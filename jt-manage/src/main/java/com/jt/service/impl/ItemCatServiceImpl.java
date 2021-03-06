package com.jt.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.ItemCatMapper;
import com.jt.vo.EasyUITree;
import com.jt.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jt.pojo.ItemCat;


@Service
public class ItemCatServiceImpl implements ItemCatService {
	@Autowired
	private ItemCatMapper itemCatMapper;

	@Override
	public String findItemCatNameByCatId(Long itemCatId) {
		
		return itemCatMapper.selectById(itemCatId).getName();
	}


	@Override
	public List<EasyUITree> findItemCatByParentId(Long parentId) {
		List<ItemCat> ItemCatList = findItemCatList(parentId);
		List<EasyUITree> treeList = new ArrayList<>();
		//遍历集合数据  实现数据转换
		for (ItemCat list : ItemCatList) {
			treeList.add(new EasyUITree().setId(list.getId()).setText(list.getName()).setState(list.getIsParent()?"closed":"open"));
		}
		return treeList;
	}

	
	public List<ItemCat> findItemCatList(Long parentId){
		
		return	itemCatMapper.selectList(new QueryWrapper<ItemCat>().eq("parent_id", parentId));
	}




	
}
