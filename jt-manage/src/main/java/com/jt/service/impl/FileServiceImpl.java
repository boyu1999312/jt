package com.jt.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import com.jt.service.FileService;
import com.jt.vo.ImageVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FileServiceImpl implements FileService {

	private String localDirPath = "D:/1-jt/image/";	 //本地图片文件夹路径
	private String urlPath = "http://image.jt.com/"; //定义虚拟url
	
	
	
	/**
	 * 1.获取图片的名称
	 * 2.校验是否为图片类型
	 * 3.检验是否为恶意程序 使用ImageIO.read的参数为InputStream的方法 以及getWidth() getHeight()
	 * 			判断是否为一个图片类型的文件
	 * 4.分文件保存 按时间存储 yyyy-MM-dd HH:mm:ss
	 * 5.防止文件重名  UUID  32位 16进制数
	 * 
	 * 正则表达式
	 * 	1.^   开始
	 * 	2.$   结束
	 * 	3.'.' 表示任意单个字符 "*"任意个 "+"表示至少一个
	 * 	4.\   转义
	 * 	5.()  分组
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	
	@Override
	public ImageVO updateFile(MultipartFile uploadFile){
		//获取文件名并全转小写
		String fileName = uploadFile.getOriginalFilename().toLowerCase();
		ImageVO imageVO = new ImageVO();
		if(!fileName.matches("^.+\\.(jpg|png|gif)$")) {
			//不是 jpg png gif 后缀的文件 直接返回错误VO
			return imageVO.setError(1);
		}
		try {
			BufferedImage bufferedImage = ImageIO.read(uploadFile.getInputStream());
			int width = bufferedImage.getWidth();
			int height = bufferedImage.getHeight();
			
			if(width == 0 || height == 0)
				//没有图片属性固有的宽度和高度，直接返回错误VO
				return imageVO.setError(1);
			
			//4.时间转化为字符串
			String dataDir = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
			//UUID
			String uuid = UUID.randomUUID().toString().replace("-", "");
			//文件后缀
			String fileType = fileName.substring(fileName.lastIndexOf("."));
			//5.准备文件夹
			File file = new File(localDirPath + dataDir);
			if(!file.exists())
				file.mkdirs();

			//6.拼接url路径
			String realurlPath = urlPath + dataDir + "/" + uuid + fileType;
			
			//将文件信息回传给页面
			uploadFile.transferTo(new File(file.getAbsolutePath()+"/"+uuid+fileType));
			imageVO.setError(0)
			.setHeight(height)
			.setWidth(width)
			.setUrl(realurlPath);
		} catch (IOException e) {
			e.printStackTrace();
			//不是图片直接返回错误VO
			return imageVO.setError(1);
		}
		
		return imageVO;
	}
	
}
