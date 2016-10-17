package pl.prutkowski.master.spring.mvc.controller.profile;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.prutkowski.master.spring.mvc.config.PictureUploadProperties;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;

/**
 * Created by programmer on 10/17/16.
 */
@Controller
public class PictureUploadController {

    private final Resource picturesDir;
    private final Resource anonymousPicture;
    private static final String UPLOAD_FORM_VIEW = "profile/uploadPage";

    @Autowired
    public PictureUploadController(PictureUploadProperties uploadProperties) {
        picturesDir = uploadProperties.getUploadPath();
        anonymousPicture = uploadProperties.getAnonymousPicture();
    }

    @RequestMapping("upload")
    public String uploadPage() {
        return UPLOAD_FORM_VIEW;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String onUpload(MultipartFile file, RedirectAttributes redirectAttrs) throws IOException {
        if (file.isEmpty() || !isImage(file)) {
            redirectAttrs.addFlashAttribute("error", "Incorrect file type. Please upload an image.");
            return "redirect:/upload";
        }
        copyFileToPictures(file);
        return UPLOAD_FORM_VIEW;
    }

    @RequestMapping(value = "/uploadedPicture")
    public void getUploadedPicture(HttpServletResponse response) throws IOException {
        response.setHeader("Content-Type", URLConnection.guessContentTypeFromName(anonymousPicture.getFilename()));
        IOUtils.copy(anonymousPicture.getInputStream(), response.getOutputStream());
    }

    private Resource copyFileToPictures(MultipartFile file) throws IOException {
        String fileExtension = getFileExtension(file.getOriginalFilename());
        File tempFile = File.createTempFile("pic", fileExtension, picturesDir.getFile());
        try (InputStream in = file.getInputStream(); OutputStream out = new FileOutputStream(tempFile)) {
            IOUtils.copy(in, out);
        }
        return new FileSystemResource(tempFile);
    }

    private boolean isImage(MultipartFile file) {
        return file.getContentType().startsWith("image");
    }

    private static String getFileExtension(String name) {
        return name.substring(name.lastIndexOf("."));
    }
}
