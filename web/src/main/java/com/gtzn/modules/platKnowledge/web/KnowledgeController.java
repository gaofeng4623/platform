package com.gtzn.modules.platKnowledge.web;

import com.gtzn.common.config.Global;
import com.gtzn.common.persistence.Ajax;
import com.gtzn.common.persistence.Pager;
import com.gtzn.common.utils.IdGen;
import com.gtzn.common.utils.StringUtils;
import com.gtzn.common.web.BaseController;
import com.gtzn.modules.digital.entity.Attachment;
import com.gtzn.modules.digital.entity.File;
import com.gtzn.modules.digital.entity.Folder;
import com.gtzn.modules.digital.service.AttachmentService;
import com.gtzn.modules.digital.service.FileService;
import com.gtzn.modules.digital.service.FolderService;
import com.gtzn.web.util.WebUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.*;

/**
 * describe TODO
 *
 * @author kgm
 * @version 1.0
 *          create_date 2017/5/8 10:09
 **/
@Controller
@RequestMapping("/knowledge")
public class KnowledgeController extends BaseController {

	@Autowired
	private FolderService folderService;
	@Autowired
	private FileService fileService;
	@Autowired
	private AttachmentService attachmentService;

	@RequiresPermissions("knowledge:knowledge:view")
	@RequestMapping("/index")
	public String index() {
		return "modules/platKnowledge/index";
	}

	/**
	 * 通过文件夹id获取子文件夹数据
	 *
	 * @param folder 文件夹id
	 * @return 子文件夹数据
	 */
	@RequiresPermissions("knowledge:knowledge:view")
	@RequestMapping("/treeData")
	@ResponseBody
	public List<Folder> treeData(Folder folder) {
		return folderService.findChildList(folder);
	}

	/**
	 * 新增or修改一个文件夹节点
	 *
	 * @param folder 要增加or修改的文件夹
	 * @return 增加后or修改后的文件夹
	 */
	@RequiresPermissions("knowledge:knowledge:view")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Folder save(Folder folder) {
		folder.setCreateBy(WebUtil.getUser());
		folder.setCreateDate(new Date());
		return folderService.saveOrUpdateFolder(folder);
	}

	/**
	 * 删除一个文件夹节点
	 *
	 * @param folder 要删除的文件夹
	 * @return 增加后or修改后的文件夹
	 */
	@RequiresPermissions("knowledge:knowledge:view")
	@RequestMapping(value = "/deleteFolder", method = RequestMethod.POST)
	@ResponseBody
	public Ajax deleteFolder(Folder folder) {
		Ajax ajax = new Ajax(true);
		try {
			folderService.deleteFolder(folder);
			ajax.setMsg("删除成功！");
		} catch (Exception e) {
			ajax.setSuccess(false);
			ajax.setMsg("删除失败！");
			e.printStackTrace();
		}
		return ajax;
	}

	@RequiresPermissions("knowledge:knowledge:view")
	@RequestMapping(value = "/updateFolder", method = RequestMethod.POST)
	@ResponseBody
	public Ajax updateFolder(Folder folder) {
		Ajax ajax = new Ajax(true);
		try {
			folderService.saveOrUpdateFolder(folder);
			ajax.setMsg("修改成功！");
		} catch (Exception e) {
			ajax.setSuccess(false);
			ajax.setMsg("修改失败！");
			e.printStackTrace();
		}
		return ajax;
	}

	@RequiresPermissions("knowledge:knowledge:view")
	@RequestMapping("/list")
	public String list(Folder folder, Model model) {
		model.addAttribute("folderId", folder.getId());
		return "modules/platKnowledge/knowledgeList";
	}

	@RequiresPermissions("knowledge:knowledge:view")
	@RequestMapping("/form")
	public String form(File file, Model model) {
		File file1 = fileService.findFile(file);
		if (file1 == null) {
			file1 = file;
		}
		model.addAttribute("file", file1);
		return "modules/platKnowledge/knowledgeForm";
	}

	@RequiresPermissions("knowledge:knowledge:view")
	@RequestMapping("/downAndPreView")
	public String downAndPreView(File file, Model model) {
		model.addAttribute("file", file);
		return "modules/platKnowledge/downAndPreView";
	}

	/**
	 * 通过文件夹id获取该文件夹（即档案类型）下的所有文件、文章
	 * 分页显示
	 *
	 * @param file 查询条件，file内部包含所属的文件夹信息
	 * @return 文件、文章列表
	 */
	@RequiresPermissions("knowledge:knowledge:view")
	@RequestMapping("/load")
	@ResponseBody
	public Pager<File> load(Pager<File> pager, File file) {
		return fileService.findList(pager, file);
	}

	/**
	 * 获取该文件所拥有的所有附件
	 *
	 * @param file 查询条件
	 * @return 文件的附件
	 */
	@RequiresPermissions("knowledge:knowledge:view")
	@RequestMapping("/loadAttachment")
	@ResponseBody
	public List<Attachment> loadAttachment(File file) {
		file = fileService.findFile(file);
		return fileService.getAttachmentByGroupId(file.getAttGroupId());
	}

	/**
	 * 加载上传下载页面
	 *
	 * @param folderId 文件所属的文件夹id（档案类型）
	 */
	@RequiresPermissions("knowledge:knowledge:view")
	@RequestMapping("/upLoadJsp")
	public String upLoadJsp(String folderId, Model model) {
		model.addAttribute("folderId", folderId);
		return "modules/platKnowledge/upLoadFile";
	}

	/**
	 * 处理文件上传，将文件信息保存到数据库，并将文件保存到磁盘
	 *
	 * @param file     要保存的文件
	 * @param folderId 文件所属的文件夹（档案类型）
	 * @return 保存后的文件信息
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/upload/{folderId}", method = RequestMethod.POST)
	@ResponseBody
	public synchronized File upload(@RequestParam(value = "file", required = false) MultipartFile file,
									@PathVariable("folderId") String folderId) throws IOException {
		File myFile = new File();
		if (!file.isEmpty()) {
			//保存file信息
			File tempFile = new File();
			Folder folder = new Folder();
			folder.setId(folderId);
			tempFile.setFolder(folder);
			String fileName = file.getOriginalFilename();
			tempFile.setFileName(fileName);
			tempFile.setTitle(fileName);
			tempFile.setUser(WebUtil.getUser());
			tempFile.setCreateDate(new Date());
			tempFile.setAttGroupId(IdGen.uuid());
			myFile = fileService.save(tempFile);

			//保存attachment信息
			Attachment attachment = new Attachment();
			attachment.setGroupId(tempFile.getAttGroupId());
			attachment.setFileName(tempFile.getFileName());
			String saveName = System.currentTimeMillis() + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
			attachment.setFilePath(saveName);
			attachment.setFileSize(new BigDecimal((double) file.getSize() / (1024 * 1024)).setScale(2, RoundingMode
					.UP) + "MB");
			attachment.setUser(WebUtil.getUser());
			attachment.setCreateDate(tempFile.getCreateDate());
			attachmentService.save(attachment);

			List<Attachment> list = new ArrayList<>();
			list.add(attachment);
			myFile.setFiles(list);

			InputStream in = null;
			OutputStream out = null;
			try {
				java.io.File dir = new java.io.File(Global.getConfig("knowledgeUpLoadPath"));
				if (!dir.exists()) {
					dir.mkdirs();
				}
				java.io.File serverFile = new java.io.File(dir.getAbsolutePath() + "/" + saveName);
				in = file.getInputStream();
				out = new FileOutputStream(serverFile);
				byte[] b = new byte[1024];
				int len;
				while ((len = in.read(b)) > 0) {
					out.write(b, 0, len);
				}
				Attachment fileMeta = new Attachment();
				fileMeta.setFileName(file.getOriginalFilename());
				fileMeta.setFilePath("uploadFiles/" + file.getOriginalFilename());
				List<Attachment> attachments = new ArrayList<>();
				attachments.add(fileMeta);
				myFile.setFiles(attachments);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return myFile;
	}

	/**
	 * 根据文件id删除文件及附件
	 *
	 * @param file 要删除的文件信息
	 */
	@RequiresPermissions("knowledge:knowledge:view")
	@RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
	@ResponseBody
	public Ajax deleteFile(@RequestBody List<File> file) {
		Ajax ajax = new Ajax(true);
		try {
			fileService.delete(file);
			ajax.setMsg("删除成功！");
		} catch (Exception e) {
			ajax.setMsg("删除失败！");
			ajax.setSuccess(false);
			e.printStackTrace();
		}
		return ajax;
	}

	/**
	 * 根据条件删除附件
	 *
	 * @param attachment 要删除的附件id
	 */
	@RequiresPermissions("knowledge:knowledge:view")
	@RequestMapping(value = "/deleteAttachment", method = RequestMethod.POST)
	@ResponseBody
	public Ajax deleteAttachment(@RequestBody Attachment attachment) {
		Ajax ajax = new Ajax(true);
		try {
			attachmentService.delete(attachment);
			ajax.setMsg("删除成功！");
		} catch (Exception e) {
			ajax.setMsg("删除失败！");
			ajax.setSuccess(false);
			e.printStackTrace();
		}
		return ajax;
	}

	/**
	 * 下载文件
	 *
	 * @param file 要下载的文件信息
	 */
	@RequiresPermissions("knowledge:knowledge:view")
	@RequestMapping(value = "/downFile", method = RequestMethod.GET)
	public void downFile(File file, Boolean preview, HttpServletResponse response) {
		String url = file.getUrl();
		String fileName = file.getFileName();
		try {
			java.io.File file1 = new java.io.File("D://uploadFiles//" + url);
			if (!preview) {
				response.setContentType("multipart/form-data");
				response.setHeader("Content-disposition", "attachment; filename=\"" + new String(fileName.getBytes
						("utf-8"), "ISO8859-1") + "\"");
				response.setHeader("Content-Length", String.valueOf(file1.length()));
			} else {
				URL u = new URL("file:///" + file1.getAbsolutePath());
				String contentType = u.openConnection().getContentType();
				response.setContentType(contentType);
				response.setHeader("Content-Disposition", "inline;filename=" + file1.getName());
			}
			FileCopyUtils.copy(new FileInputStream("D://uploadFiles//" + url), response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 新增知识或修改知识
	 *
	 * @param request 要保存的文本、附件
	 */
	@RequiresPermissions("knowledge:knowledge:view")
	@RequestMapping(value = "/saveKnowledge", method = RequestMethod.POST)
	public String saveKnowledge(MultipartHttpServletRequest request, Model model) {
		String id = request.getParameter("id");
		;
		String folderId = request.getParameter("folderId");
		String title = request.getParameter("title");
		String keyWords = request.getParameter("keyWords");
		String content = request.getParameter("content");
		File file = new File();
		file.setId(id);
		if (StringUtils.isNotBlank(id)) {
			file = fileService.findFile(file);
		} else {
			Folder folder = new Folder();
			folder.setId(folderId);
			file.setFolder(folder);
			file.setAttGroupId(IdGen.uuid());
		}
		file.setTitle(title);
		file.setKeyWords(keyWords);
		file.setContent(content);
		file.setUser(WebUtil.getUser());
		file.setCreateDate(new Date());
		fileService.save(file);

		MultiValueMap<String, MultipartFile> map = request.getMultiFileMap();
		List<MultipartFile> list = map.get("files");
		if (!list.isEmpty()) {
			for (MultipartFile multipartFile : list) {
				//保存attachment信息
				Attachment attachment = new Attachment();
				attachment.setGroupId(file.getAttGroupId());
				String fileName = multipartFile.getOriginalFilename();
				if (StringUtils.isBlank(fileName)) {
					break;
				}
				attachment.setFileName(fileName);
				String saveName = System.currentTimeMillis() + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
				attachment.setFilePath(saveName);
				attachment.setFileSize(new BigDecimal((double) multipartFile.getSize() / (1024 * 1024)).setScale(2,
						RoundingMode
						.UP) + "MB");
				attachment.setUser(WebUtil.getUser());
				attachment.setCreateDate(file.getCreateDate());
				attachmentService.save(attachment);

				InputStream in = null;
				OutputStream out = null;
				try {
					java.io.File dir = new java.io.File(Global.getConfig("knowledgeUpLoadPath"));
					if (!dir.exists()) {
						dir.mkdirs();
					}
					java.io.File serverFile = new java.io.File(dir.getAbsolutePath() + "/" + saveName);
					in = multipartFile.getInputStream();
					out = new FileOutputStream(serverFile);
					byte[] b = new byte[1024];
					int len;
					while ((len = in.read(b)) > 0) {
						out.write(b, 0, len);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (out != null) {
						try {
							out.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (in != null) {
						try {
							in.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		model.addAttribute("folderId", file.getFolder().getId());
		return "modules/platKnowledge/knowledgeList";
	}

	@RequiresPermissions("knowledge:knowledge:view")
	@RequestMapping(value = "/findFiles/{id}", method = RequestMethod.GET)
	@ResponseBody
	public File findFiles(File file) {
		return fileService.findFile(file);
	}

}
