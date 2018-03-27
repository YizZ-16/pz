package pz.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pz.config.ResponseCodeEnum;
import pz.service.ResponseService;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;

@RestController
@CrossOrigin
@RequestMapping(value = "/file")
@Slf4j
public class UploadController {

    @Autowired
    private ResponseService responseService;


    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public HashMap<String, Object> uploadFile (
            HttpServletRequest request,
            @RequestParam MultipartFile file
    ) {
        if (file.isEmpty()) {
            return responseService.fail(
                    ResponseCodeEnum.UNKNOWN_ERROR,"file upload fail, file not empty!");
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        //System.out.println("上传的文件名为：" + fileName);
        // 获取文件的后缀名
        //String suffixName = fileName.substring(fileName.lastIndexOf("."));
        String relativePath = request.getSession().getServletContext().getRealPath("/");
        log.info(relativePath);
        // 文件上传后的路径
        String filePath = "G:\\test\\";
        // 解决中文问题，liunx下中文路径，图片显示问题
        // fileName = UUID.randomUUID() + suffixName;
        File dest = new File(filePath + fileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            return responseService.success("upload success");
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseService.fail(
                ResponseCodeEnum.UNKNOWN_ERROR,"file upload fail");
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void downloadFile(
            @RequestParam(value = "FILE_NAME") String fileName,
            HttpServletResponse res
    ) {
        log.info(fileName);
        if (fileName.isEmpty()) {
            return;
        }
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            File file = new File("G:\\test\\"
                    + fileName);
            if (file.exists()) {
                res.setHeader("content-type", "application/octet-stream");
                res.setContentType("application/octet-stream");
                res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
                byte[] buff = new byte[1024];
                os = res.getOutputStream();
                bis = new BufferedInputStream(new FileInputStream(file));
                int i = bis.read(buff);
                while (i != -1) {
                    os.write(buff, 0, buff.length);
                    os.flush();
                    i = bis.read(buff);
                }
            }else{
                res.setCharacterEncoding("UTF-8");
                res.setHeader("Content-type","text/html;charset=UTF-8");
                PrintWriter writer = res.getWriter();
                writer.write("平横图文件不存在");
               // writer.flush();
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("success");

    }
}
