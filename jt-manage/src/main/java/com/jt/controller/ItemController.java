package com.jt.controller;

import com.jt.service.ItemService;
import com.jt.vo.EasyUIData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.SysResult;

@RestController
@RequestMapping("/item/")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	//查询商品列表分页信息 分页查询
	@RequestMapping("query")
	public EasyUIData findItemByPage(Integer page, Integer rows) {
		return itemService.findItemByPage(page, rows);
	}
	
	@RequestMapping("save")
	public SysResult saveItem(Item item, ItemDesc itemDesc) {
		try {
			itemService.saveItem(item, itemDesc);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail("商品新增失败");
		}
	}
	
	@RequestMapping("update")
	public SysResult updateItem(Item item, ItemDesc itemDesc) {
		try {
			itemService.updateItem(item, itemDesc);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail("商品修改失败");
		}
	}
	
	@RequestMapping("delete")
	public SysResult deleteItem(Long[] ids) {
		try {
			itemService.deleteItem(ids);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail("商品删除失败");
		}
	}
	
	@RequestMapping("reshelf")
	public SysResult reshelfItem(Long[] ids) {
		try {
			Integer status = 1; //上架
			itemService.updateStatus(ids, status);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail("商品上架失败");
		}
	}
	
	@RequestMapping("instock")
	public SysResult instockItem(Long[] ids) {
		try {
			Integer status = 2; //下架
			itemService.updateStatus(ids, status);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail("商品下架失败");
		}
	}
	
	@RequestMapping("query/item/desc/{itemId}")
	public SysResult findItemDescById(@PathVariable Long itemId) {
		try {
			ItemDesc itemDesc = itemService.findItemDescById(itemId);
			return SysResult.msg(itemDesc);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail("商品详情查询失败");
		}
	}
	
	
}
