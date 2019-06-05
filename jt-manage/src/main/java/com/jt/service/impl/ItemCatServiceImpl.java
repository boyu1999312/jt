package com.jt.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.ItemCatMapper;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.EasyUITree;
import com.jt.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jt.pojo.ItemCat;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;


@Service
public class ItemCatServiceImpl implements ItemCatService {
	@Autowired
	private ItemCatMapper itemCatMapper;
	@Autowired
	private Jedis jedis;

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


	@Override
	public List<EasyUITree> findItemCatByCache(Long parentId){
		String key = "ITEM_CAT_"+parentId;
		String result = jedis.get(key);
		List<EasyUITree> treeList = new ArrayList<>();
		if(StringUtils.isEmpty(result)) {
			//如果为null 查询数据库
			List<EasyUITree> list = findItemCatByParentId(parentId);
			treeList = list;
			String json = ObjectMapperUtil.toJson(treeList);
			jedis.setex(key, 7*24*3600, json);
			System.out.println("业务查询数据库");
		}else{
			//标识缓存中有数据
			treeList = ObjectMapperUtil.toObject(result, treeList.getClass());
			System.out.println("业务查询Redis缓存");
		}

		return	treeList;
	}


	
}
