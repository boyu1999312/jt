package com.jt.controller;

import java.util.List;

import com.jt.service.ItemCatService;
import com.jt.vo.EasyUITree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/item/cat/")
public class ItemCatController {
	
	@Autowired
	private ItemCatService itemCatService;
	// 显示 子叶类目
	@RequestMapping("queryItemName")
	public String queryItemName(Long itemCatId) {
		return itemCatService.findItemCatNameByCatId(itemCatId);
	}
	// 显示树状分类
	@RequestMapping("list")
	public List<EasyUITree> list(@RequestParam(value="id",defaultValue="0")Long parentId) {
		/*itemCatService.findItemCatByParentId(parentId);*/
		return itemCatService.findItemCatByCache(parentId);
	}
}
