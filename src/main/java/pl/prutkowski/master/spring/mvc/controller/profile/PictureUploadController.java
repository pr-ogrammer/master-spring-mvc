package pl.prutkowski.master.spring.mvc.controller.profile;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;
import pl.prutkowski.master.spring.mvc.config.PictureUploadProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.util.Locale;

/**
 * Created by programmer on 10/17/16.
 */
@Controller
public class PictureUploadController {

    private final Resource picturesDir;
    private final Resource anonymousPicture;
    private final MessageSource messageSource;
    private UserProfileSession userProfileSession;
    private static final String UPLOAD_FORM_VIEW = "profile/profilePage";
    private static final String REDIRECT_TO_PROFILE = "redirect:/profile";

    @Autowired
    public PictureUploadController(PictureUploadProperties uploadProperties, MessageSource messageSource, UserProfileSession userProfileSession) {
        picturesDir = uploadProperties.getUploadPath();
        anonymousPicture = uploadProperties.getAnonymousPicture();
        this.messageSource = messageSource;
        this.userProfileSession = userProfileSession;
    }

    @RequestMapping(value = "/profile", params = {"upload"}, method = RequestMethod.POST)
    public String onUpload(MultipartFile file, RedirectAttributes redirectAttrs) throws IOException {
        if (file.isEmpty() || !isImage(file)) {
            redirectAttrs.addFlashAttribute("error", "Incorrect file type. Please upload an image.");
            return REDIRECT_TO_PROFILE;
        }
        Resource picturePath = copyFileToPictures(file);
        userProfileSession.setPicturePath(picturePath);
        return REDIRECT_TO_PROFILE;
    }

    @RequestMapping(value = "/uploadedPicture")
    public void getUploadedPicture(HttpServletResponse response) throws IOException {
        Resource picturePath = getPicturePathFromUserSession();
        response.setHeader("Content-Type", URLConnection.guessContentTypeFromName(picturePath.getFilename()));
        IOUtils.copy(picturePath.getInputStream(), response.getOutputStream());
    }

    @RequestMapping("uploadError")
    public ModelAndView onUploadError(Locale locale) {
        ModelAndView modelAndView = new ModelAndView(UPLOAD_FORM_VIEW);
        modelAndView.addObject("error", messageSource.getMessage("upload.file.too.big", null, locale));
        modelAndView.addObject("profileForm", userProfileSession.toForm());
        return modelAndView;
    }

    @ExceptionHandler(IOException.class)
    public ModelAndView handleIOException(Locale locale) {
        ModelAndView modelAndView = new ModelAndView(UPLOAD_FORM_VIEW);
        modelAndView.addObject("error", messageSource.getMessage("upload.io.exception", null, locale));
        modelAndView.addObject("profileForm", userProfileSession.toForm());
        return modelAndView;
    }

    private Resource copyFileToPictures(MultipartFile file) throws IOException {
        String fileExtension = getFileExtension(file.getOriginalFilename());
        File tempFile = File.createTempFile("pic", fileExtension, picturesDir.getFile());
        try (InputStream in = file.getInputStream(); OutputStream out = new FileOutputStream(tempFile)) {
            IOUtils.copy(in, out);
        }
        return new FileSystemResource(tempFile);
    }

    private Resource getPicturePathFromUserSession() {
        return userProfileSession.getPicturePath() != null ? userProfileSession.getPicturePath() : anonymousPicture;
    }

    private boolean isImage(MultipartFile file) {
        return file.getContentType().startsWith("image");
    }

    private static String getFileExtension(String name) {
        return name.substring(name.lastIndexOf("."));
    }
}
