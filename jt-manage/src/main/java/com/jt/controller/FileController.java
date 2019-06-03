package com.jt.controller;

import java.io.File;
import java.io.IOException;

import com.jt.service.FileService;
import com.jt.vo.ImageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;




@Controller
public class FileController {

	@Autowired
	private FileService fileService;
	
	/** 
	 * 1.当用户上传完成时，重定向到上传的页面
	 * 2.指定文件上传路径
	 * 3.实现文件上传 
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping("/file")
	public String file(MultipartFile fileImage) throws IllegalStateException, IOException {
		 //1.获取input的name
		String name = fileImage.getName();
		//2.获取文件名
		String fileName = fileImage.getOriginalFilename();
		
		System.out.println("name: "+ name + " | fileName: " + fileName);
		//3.定义文件夹路径
		File file = new File("D:/1-jt/iamge");
		if(!file.exists())
			file.mkdirs();
		//4.实现文件上传
		
		fileImage.transferTo(new File("D:/1-jt/iamge/"+fileName));
		return "redirect:/file.jsp";
	}
	
	
	//实现文件的上传
	@RequestMapping("/pic/upload")
	@ResponseBody
	public ImageVO upLoadFile(MultipartFile uploadFile) {
		
		return fileService.updateFile(uploadFile);
	}
}
