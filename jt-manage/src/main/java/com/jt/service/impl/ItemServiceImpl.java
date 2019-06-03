package com.jt.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.ItemDescMapper;
import com.jt.mapper.ItemMapper;
import com.jt.vo.EasyUIData;
import com.jt.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper itemDescMapper;
	
	@Override
	public EasyUIData findItemByPage(Integer page, Integer rows) {
		System.out.println("abc");
		Integer totle = itemMapper.selectCount(null);
		Integer pageStart = (page-1)*rows;
		List<Item> itemList = itemMapper.findItemByPage(pageStart, rows);
		return new EasyUIData(totle, itemList);
	}

	@Transactional //添加事务的控制
	@Override
	public void saveItem(Item item, ItemDesc itemDesc) {
		item.setStatus(1).setCreated(new Date()).setUpdated(item.getCreated());
		itemMapper.insert(item);
		itemDescMapper.insert((ItemDesc) itemDesc
				.setItemId(item.getId())
				.setCreated(item.getCreated())
				.setUpdated(item.getCreated()));
	}

	@Transactional
	@Override
	public void updateItem(Item item, ItemDesc itemDesc) {
		item.setUpdated(new Date());
		itemMapper.updateById(item);
		;
		itemDescMapper.updateById(((ItemDesc) itemDesc
				.setUpdated(item.getUpdated()))
				.setItemId(item.getId()));
	}

	
	@Transactional
	@Override
	public void updateStatus(Long[] ids, Integer status) {
		itemMapper.update((Item) new Item().setStatus(status).setUpdated(new Date()), new QueryWrapper<Item>().in("id", ids));
		
	}

	@Transactional
	@Override
	public void deleteItem(Long[] ids) {

		
		List<Long> asList = Arrays.asList(ids);
		itemMapper.deleteBatchIds(asList);
		itemDescMapper.deleteBatchIds(asList);
	}

	@Override
	public ItemDesc findItemDescById(Long itemId) {
		
		return itemDescMapper.selectById(itemId);
	}
	
	
	
	
	
	
	
	
	
	
}
