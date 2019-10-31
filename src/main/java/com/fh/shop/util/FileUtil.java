package com.fh.shop.util;


import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.UUID;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class FileUtil {
	/**
	 * 导出pdf文件
	 * 创建人：王少鹏 1664046428@qq.com
	 * 创建时间：2019年7月24日 下午8:04:27
	 * 修改人：王少鹏 1664046428@qq.com
	 * 修改时间：2019年7月24日 下午8:04:27
	 * 修改备注：
	 *
	 * @param
	 * @param response
	 *            </pre>
	 */
	public static void pdfDownload(HttpServletResponse response, ByteArrayOutputStream byffer) {
		
		// inline在浏览器中直接显示，不提示用户下载
		// attachment弹出对话框，提示用户进行下载保存本地
		// 默认为inline方式
		response.setHeader("Content-Disposition", "attachment; filename=" + UUID.randomUUID().toString() + ".pdf");
		// 不同类型的文件对应不同的MIME类型
		response.setContentType("application/octet-stream;charset=UTF-8");
		ServletOutputStream out;
		try {
			//获取输出流
			out = response.getOutputStream();
			//调用方法下载
			byffer.writeTo(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 此方法作用:设置标题
	 * 创建人：王少鹏 1664046428@qq.com
	 * 创建时间：2019年7月24日 下午2:29:57
	 * 修改人：王少鹏 1664046428@qq.com
	 * 修改时间：2019年7月24日 下午2:29:57
	 * 修改备注：

	 */
	public static PdfPCell createHeadline(String value, Font font) {
		// 创建一个单元格
		PdfPCell cell = new PdfPCell();
		// new Paragraph()是段落的处理，可以设置段落的对齐方式，缩进和间距。
		cell.setPhrase(new Paragraph(value, font));
		//设置单元格的水平对齐方式
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		// 设置单元格的垂直对齐方式
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setMinimumHeight(30);//设置表格行高   
		cell.setBorderWidth(0f);//去除表格的边框
		cell.setColspan(19);//跨列
		return cell;
	}

	/**
	 * 此方法作用:创建单元格 设置内容
	 * 创建人：王少鹏 1664046428@qq.com 
	 * 创建时间：2019年7月24日 下午7:30:22 
	 * 修改人：王少鹏 1664046428@qq.com 
	 * 修改时间：2019年7月24日 下午7:30:22
	 * 修改备注：
	 * 
	 * @param value
	 * @param font
	 * @param align
	 * @return
	 * </pre>
	 */
	public static PdfPCell createCell(String value, Font font, int align) {
		// 创建一个单元格
		PdfPCell cell = new PdfPCell();
		// 设置单元格的垂直对齐方式
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		// 设置单元格的水平对齐方式
		cell.setHorizontalAlignment(align);
		cell.setPhrase(new Phrase(value, font));
		cell.setBorderWidth(0f);//去除表格的边框
		
		return cell;
	}

	static int maxWidth = 520;// 总宽度

	/**
	 * 创建一个书写器
	 * 创建人：王少鹏 1664046428@qq.com 
	 * 创建时间：2019年7月24日 下午6:34:13 
	 * 修改人：王少鹏 1664046428@qq.com 
	 * 修改时间：2019年7月24日 下午6:34:13 
	 * 修改备注：
	 * @param colNumber
	 * @return
	 *         </pre>
	 */
	public static PdfPTable createTable(int colNumber) {
		// 创建表格
		PdfPTable table = new PdfPTable(colNumber);
		// 设置表格的总宽度
		table.setTotalWidth(maxWidth);
		//锁定宽度
		table.setLockedWidth(true);
		// 设置表格的垂直对齐方式
		table.setHorizontalAlignment(Element.ALIGN_CENTER);
		// 设置边框
		table.getDefaultCell().setBorder(1);
		return table;
	}

	/**
	 * 
	* @Title: downloadFile 
	* @Description: 
	* @param @param request
	* @param @param response
	* @param @param downloadFile
	* @param @param fileName    入参
	* @return void    返回类型
	* @author Zhangtw
	* @e-mail 1058202069@qq.com
	* @throws
	* @date 2018年10月26日 下午4:13:41 
	* @version V1.0
	 */
	public static void downloadFile(HttpServletRequest request, HttpServletResponse response, String downloadFile, String fileName) {
		
		BufferedInputStream bis = null;
		InputStream is = null;
		OutputStream os = null;
		BufferedOutputStream bos = null;
		try {
			File file=new File(downloadFile); 
	        is = new FileInputStream(file);  //文件流的声明
	        os = response.getOutputStream(); //重点突出(特别注意),通过response获取的输出流，作为服务端向客户端浏览器输出内容的通道
	        // 为了提高效率使用缓冲区流
	        bis = new BufferedInputStream(is);
	        bos = new BufferedOutputStream(os);
	        // 处理下载文件名的乱码问题(根据浏览器的不同进行处理)
	        if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
	        	fileName = new String(fileName.getBytes("GB2312"),"ISO-8859-1");
	        } else {
	        	// 对文件名进行编码处理中文问题
	  	        fileName = java.net.URLEncoder.encode(fileName, "UTF-8");// 处理中文文件名的问题
	  	        fileName = new String(fileName.getBytes("UTF-8"), "GBK");// 处理中文文件名的问题
	        }
	        response.reset(); // 重点突出
	        response.setCharacterEncoding("UTF-8"); // 重点突出
	        response.setContentType("application/x-msdownload");// 不同类型的文件对应不同的MIME类型 // 重点突出
	        response.setHeader("Content-Disposition", "attachment;filename="+ fileName);// 重点突出
	        int bytesRead = 0;
	        byte[] buffer = new byte[4096];
	        while ((bytesRead = bis.read(buffer)) != -1){ //重点
	            bos.write(buffer, 0, bytesRead);// 将文件发送到客户端
	            bos.flush();
	        }
	        
		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		} finally {
			// 特别重要
	        // 1. 进行关闭是为了释放资源
	        // 2. 进行关闭会自动执行flush方法清空缓冲区内容
			try {
				if (null != bis) {
					bis.close();
					bis = null;
				}
				if (null != bos) {
					bos.close();
					bos = null;
				}
				if (null != is) {
					is.close();
					is = null;
				}
				if (null != os) {
					os.close();
					os = null;
				}
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
	}

	public static String calculateFileSize(long size) {
		DecimalFormat df = new DecimalFormat("0.00");
		if (size < 1024) {
			return size+"B";
		} else if (size < 1024 * 1024) {
			return df.format((double)size / 1024)+"KB";
		} else if (size < 1024 * 1024 * 1024) {
			return df.format((double)size / (1024 * 1024))+"MB";
		} else {
			return df.format((double)size / (1024 * 1024 * 1024))+"GB";
		}
	}

	public static String buildPdfHtml(Map data, String pdfTemplateFile, String templatePath){
		// 将其转换为html
		StringWriter sw = null;
		try {
			Configuration configuration = new Configuration();
			// 解决freemarker的乱码问题
			configuration.setDefaultEncoding("utf-8");
			configuration.setClassForTemplateLoading(FileUtil.class, templatePath);
			Template template = configuration.getTemplate(pdfTemplateFile);
			sw = new StringWriter();
			template.process(data, sw);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (TemplateException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return sw.toString();
	}



	public static File buildWord(Map data, String templateFile, String templatePath, String savePath){
		FileOutputStream out = null;
		OutputStreamWriter osw = null;
		File file = null;
		try {
			Configuration configuration = new Configuration();
			configuration.setClassForTemplateLoading(FileUtil.class, templatePath);
			configuration.setDefaultEncoding("utf-8");
			Template template = configuration.getTemplate(templateFile);
			file = new File(savePath+UUID.randomUUID().toString()+".docx");
			out = new FileOutputStream(file);
			osw = new OutputStreamWriter(out, "utf-8");
			template.process(data, osw);
			osw.flush();
		} catch (TemplateException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (null != osw) {
				try {
					osw.close();
					osw = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != out) {
				try {
					out.close();
					out = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return file;
	}




	public static String copyFile(File file, String fileName, String folderPath) {
		// 上传物理文件到服务器硬盘
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		String uploadFileName = null;
		try {
			// 构建读文件的流即输入流
			fis = new FileInputStream(file);
			// 构建输入缓冲区，提高读取文件的性能
			bis = new BufferedInputStream(fis);
			// 自动建立文件夹
			File folder = new File(folderPath);
			if (!folder.exists()) {
				// 创建文件夹
				folder.mkdirs();
			}
			// 为了保证上传文件的唯一性，可以通过uuid来解决
			// 为了避免中文乱码问题则新生成的文件名为uuid+原来文件名的后缀
			uploadFileName = UUID.randomUUID().toString()+getSuffix(fileName);
			// 构建写文件的流即输出流
			fos = new FileOutputStream(new File(folderPath+"/"+uploadFileName));
			// 构建输出缓冲区，提高写文件的性能
			bos = new BufferedOutputStream(fos);
			// 通过输入流读取数据并将数据通过输出流写到硬盘文件中
			byte[] buffer = new byte[4096];// 构建4k的缓冲区
			int s = 0;
			while ((s=bis.read(buffer)) != -1) {
				bos.write(buffer, 0, s);
				bos.flush();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
					bos = null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (fos != null) {
				try {
					fos.close();
					fos = null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (bis != null) {
				try {
					bis.close();
					bis = null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (fis != null) {
				try {
					fis.close();
					fis = null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return uploadFileName;
	}

	public static String copyFile(InputStream is, String fileName, String folderPath) {
		// 上传物理文件到服务器硬盘
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		String uploadFileName = null;
		try {
			// 构建输入缓冲区，提高读取文件的性能
			bis = new BufferedInputStream(is);
			// 自动建立文件夹
			File folder = new File(folderPath);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			// 为了保证上传文件的唯一性，可以通过uuid来解决
			// 为了避免中文乱码问题则新生成的文件名为uuid+原来文件名的后缀
			uploadFileName = UUID.randomUUID().toString()+getSuffix(fileName);
			// 构建写文件的流即输出流
			fos = new FileOutputStream(new File(folderPath+"/"+uploadFileName));
			// 构建输出缓冲区，提高写文件的性能
			bos = new BufferedOutputStream(fos);
			// 通过输入流读取数据并将数据通过输出流写到硬盘文件中
			byte[] buffer = new byte[4096];// 构建4k的缓冲区
			int s = 0;
			while ((s=bis.read(buffer)) != -1) {
				bos.write(buffer, 0, s);
				bos.flush();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
					bos = null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (fos != null) {
				try {
					fos.close();
					fos = null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (bis != null) {
				try {
					bis.close();
					bis = null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (is != null) {
				try {
					is.close();
					is = null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return uploadFileName;
	}

	public static  void deleteFile(File f) {
		if (f.exists()) {
			f.delete();
		}
	}

	public static void deleteFile(String path) {
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		}
	}

	/*public static void downloadFile(HttpServletRequest request, HttpServletResponse response, String downloadFile, String fileName) {

		BufferedInputStream bis = null;
		InputStream is = null;
		OutputStream os = null;
		BufferedOutputStream bos = null;
		try {
			File file=new File(downloadFile);
			is = new FileInputStream(file);  //文件流的声明
			os = response.getOutputStream(); //重点突出(特别注意),通过response获取的输出流，作为服务端向客户端浏览器输出内容的通道
			// 为了提高效率使用缓冲区流
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(os);
			// 处理下载文件名的乱码问题(根据浏览器的不同进行处理)
			if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
				fileName = new String(fileName.getBytes("GB2312"),"ISO-8859-1");
			} else {
				// 对文件名进行编码处理中文问题
				fileName = java.net.URLEncoder.encode(fileName, "UTF-8");// 处理中文文件名的问题
				fileName = new String(fileName.getBytes("UTF-8"), "GBK");// 处理中文文件名的问题
			}
			response.reset(); // 重点突出
			response.setCharacterEncoding("UTF-8"); // 重点突出
			response.setContentType("application/x-msdownload");// 不同类型的文件对应不同的MIME类型 // 重点突出
			response.setHeader("Content-Disposition", "attachment;filename="+ fileName);// 重点突出
			int bytesRead = 0;
			byte[] buffer = new byte[4096];
			while ((bytesRead = bis.read(buffer)) != -1){ //重点
				bos.write(buffer, 0, bytesRead);// 将文件发送到客户端
				bos.flush();
			}

		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		} finally {
			// 特别重要
			// 1. 进行关闭是为了释放资源
			// 2. 进行关闭会自动执行flush方法清空缓冲区内容
			try {
				if (null != bis) {
					bis.close();
					bis = null;
				}
				if (null != bos) {
					bos.close();
					bos = null;
				}
				if (null != is) {
					is.close();
					is = null;
				}
				if (null != os) {
					os.close();
					os = null;
				}
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
	}*/

	public static void downloadFile(HttpServletRequest request, HttpServletResponse response, File file) {

		BufferedInputStream bis = null;
		InputStream is = null;
		OutputStream os = null;
		BufferedOutputStream bos = null;
		try {
			String fileName = file.getName();
			is = new FileInputStream(file);  //文件流的声明
			os = response.getOutputStream(); //重点突出(特别注意),通过response获取的输出流，作为服务端向客户端浏览器输出内容的通道
			// 为了提高效率使用缓冲区流
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(os);
			// 处理下载文件名的乱码问题(根据浏览器的不同进行处理)
			if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
				fileName = new String(fileName.getBytes("GB2312"),"ISO-8859-1");
			} else {
				// 对文件名进行编码处理中文问题
				fileName = java.net.URLEncoder.encode(fileName, "UTF-8");// 处理中文文件名的问题
				fileName = new String(fileName.getBytes("UTF-8"), "GBK");// 处理中文文件名的问题
			}
			response.reset(); // 重点突出
			response.setCharacterEncoding("UTF-8"); // 重点突出
			response.setContentType("application/x-msdownload");// 不同类型的文件对应不同的MIME类型 // 重点突出
			response.setHeader("Content-Disposition", "attachment;filename="+ fileName);// 重点突出
			int bytesRead = 0;
			byte[] buffer = new byte[4096];
			while ((bytesRead = bis.read(buffer)) != -1){ //重点
				bos.write(buffer, 0, bytesRead);// 将文件发送到客户端
				bos.flush();
			}

		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		} finally {
			// 特别重要
			// 1. 进行关闭是为了释放资源
			// 2. 进行关闭会自动执行flush方法清空缓冲区内容
			try {
				if (null != bis) {
					bis.close();
					bis = null;
				}
				if (null != bos) {
					bos.close();
					bos = null;
				}
				if (null != is) {
					is.close();
					is = null;
				}
				if (null != os) {
					os.close();
					os = null;
				}
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
	}

	public static void xmlDownloadFile(HttpServletResponse response, Document document) {
		OutputStream os = null;
		XMLWriter xmlWriter = null;
		try {
			os = response.getOutputStream(); //重点突出(特别注意),通过response获取的输出流，作为服务端向客户端浏览器输出内容的通道
			// 处理下载文件名的乱码问题(根据浏览器的不同进行处理)
			response.reset(); // 重点突出
			response.setCharacterEncoding("UTF-8"); // 重点突出
			response.setContentType("application/x-msdownload");// 不同类型的文件对应不同的MIME类型 // 重点突出
			response.setHeader("Content-Disposition", "attachment;filename="+ UUID.randomUUID().toString()+".xml");// 重点突出
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("utf-8");
			xmlWriter = new XMLWriter(os, format);
			xmlWriter.write(document);
			xmlWriter.flush();
		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		} finally {
			// 特别重要
			// 1. 进行关闭是为了释放资源
			// 2. 进行关闭会自动执行flush方法清空缓冲区内容
			try {
				if (null != xmlWriter) {
					xmlWriter.close();
					xmlWriter = null;
				}
				if (null != os) {
					os.close();
					os = null;
				}
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
	}
	public static void pdfDownloadFile(HttpServletResponse response, String htmlContent) {
		OutputStream os = null;
		com.itextpdf.text.Document document = null;
		PdfWriter writer = null;
		try {
			os = response.getOutputStream(); //重点突出(特别注意),通过response获取的输出流，作为服务端向客户端浏览器输出内容的通道
			// 处理下载文件名的乱码问题(根据浏览器的不同进行处理)
			response.reset(); // 重点突出
			response.setCharacterEncoding("UTF-8"); // 重点突出
			response.setContentType("application/x-msdownload");// 不同类型的文件对应不同的MIME类型 // 重点突出
			response.setHeader("Content-Disposition", "attachment;filename="+ UUID.randomUUID().toString()+".pdf");// 重点突出
			// step 1
			document = new com.itextpdf.text.Document();
			// step 2
			writer = PdfWriter.getInstance(document, os);
			// step 3
			document.open();
			// step 4
			XMLWorkerFontProvider fontImp = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
			fontImp.register("simhei.ttf");
			XMLWorkerHelper.getInstance().parseXHtml(writer, document,
					new ByteArrayInputStream(htmlContent.getBytes("utf-8")), null, Charset.forName("UTF-8"), fontImp);
		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		} finally {
			// 特别重要
			// 1. 进行关闭是为了释放资源
			// 2. 进行关闭会自动执行flush方法清空缓冲区内容
			if (null != document) {
				document.close();
				document = null;
			}
			if (null != writer) {
				writer.close();
				writer = null;
			}
			if (null != os) {
				try {
					os.close();
					os = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void wordDownloadFile(HttpServletResponse response, String xmlContent) {
		OutputStream os = null;

		try {
			os = response.getOutputStream(); //重点突出(特别注意),通过response获取的输出流，作为服务端向客户端浏览器输出内容的通道
			// 处理下载文件名的乱码问题(根据浏览器的不同进行处理)
			response.reset(); // 重点突出
			response.setCharacterEncoding("UTF-8"); // 重点突出
			response.setContentType("application/x-msdownload");// 不同类型的文件对应不同的MIME类型 // 重点突出
			response.setHeader("Content-Disposition", "attachment;filename="+ UUID.randomUUID().toString()+".docx");// 重点突出

			os.write(xmlContent.getBytes());
			os.flush();

		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		} finally {
			// 特别重要
			// 1. 进行关闭是为了释放资源
			// 2. 进行关闭会自动执行flush方法清空缓冲区内容

			if (null != os) {
				try {
					os.close();
					os = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void xlsDownloadFile(HttpServletResponse response, Workbook wb) {
		OutputStream os = null;
		try {
			os = response.getOutputStream(); //重点突出(特别注意),通过response获取的输出流，作为服务端向客户端浏览器输出内容的通道
			// 处理下载文件名的乱码问题(根据浏览器的不同进行处理)
			response.reset(); // 重点突出
			response.setCharacterEncoding("UTF-8"); // 重点突出
			response.setContentType("application/x-msdownload");// 不同类型的文件对应不同的MIME类型 // 重点突出
			response.setHeader("Content-Disposition", "attachment;filename="+ UUID.randomUUID().toString()+".xlsx");// 重点突出
			wb.write(os);
		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		} finally {
			// 特别重要
			// 1. 进行关闭是为了释放资源
			// 2. 进行关闭会自动执行flush方法清空缓冲区内容
			try {
				if (null != os) {
					os.close();
					os = null;
				}
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
	}

	private static String getSuffix(String fileName) {
		int index = fileName.lastIndexOf(".");
		String suffix = fileName.substring(index);
		return suffix;
	}

	public static void excelDownload(XSSFWorkbook workBook,HttpServletResponse response) {
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			//让浏览器识别是什么类型的文件
			response.reset(); // 重点突出
			response.setCharacterEncoding("UTF-8"); // 重点突出
			response.setContentType("application/x-msdownload");// 不同类型的文件对应不同的MIME类型 // 重点突出
			// inline在浏览器中直接显示，不提示用户下载
			// attachment弹出对话框，提示用户进行下载保存本地
			// 默认为inline方式
			response.setHeader("Content-Disposition", "attachment;filename="+UUID.randomUUID().toString()+".xlsx");
			workBook.write(out);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if( null != out){
				try {
					out.close();
					out = null;
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}

	}
	public static void xmlDownload(Document document, HttpServletResponse response) {
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("utf-8");
		XMLWriter write = null;
		try {
			write = new XMLWriter(response.getOutputStream(),format);
			//设置是否转义，默认值是true，代表转义
			write.setEscapeText(false);
			//让浏览器识别是什么类型的文件
			response.reset(); // 重点突出
			response.setCharacterEncoding("UTF-8"); // 重点突出
			response.setContentType("application/x-msdownload");// 不同类型的文件对应不同的MIME类型 // 重点突出
			// inline在浏览器中直接显示，不提示用户下载
			// attachment弹出对话框，提示用户进行下载保存本地
			// 默认为inline方式
			response.setHeader("Content-Disposition", "attachment;filename="+ UUID.randomUUID()+".xml");
			write.write(document);
			write.flush();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if( null != write){
			try {
				write.close();
				write = null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}


}
