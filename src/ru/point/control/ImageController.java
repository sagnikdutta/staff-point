package ru.point.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.point.model.User;
import ru.point.utils.Images;
import ru.point.view.Message;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;


/**
 * @author: Mikhail Sedov [02.04.2009]
 */
@Controller
@Transactional
public class ImageController extends AbstractController {

    private static final String LOCAL_IMG = "d:/web/point/";

    static {
        File dir = new File(LOCAL_IMG);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    public static void fastChannelCopy(ReadableByteChannel src, WritableByteChannel dest) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 1024);
        while (src.read(buffer) != -1) {
            buffer.flip();
            dest.write(buffer);
            buffer.compact();
        }
        buffer.flip();
        while (buffer.hasRemaining()) {
            dest.write(buffer);
        }
    }

    @Autowired
    private PeopleController peopleController;

    @RequestMapping(value = "/user/image/{size}/{userId}", method = RequestMethod.GET)
    public void getUserFace(@PathVariable long userId, @PathVariable String size, HttpServletResponse response) throws IOException {
        User u = dao.get(User.class, userId);
        File userLocalStorage = new File(LOCAL_IMG + u.getLogin());
        if (!userLocalStorage.exists()) {
            redirectToDefault(u.isFemale(), size, response);
            return;
        }

        File face = new File(userLocalStorage, size);
        if (!face.exists()) {
            redirectToDefault(u.isFemale(), size, response);
            return;
        }

        ReadableByteChannel inputChannel = Channels.newChannel(new FileInputStream(face));
        WritableByteChannel outputChannel = Channels.newChannel(response.getOutputStream());

        fastChannelCopy(inputChannel, outputChannel);
    }

    private void redirectToDefault(boolean isFemale, String size, HttpServletResponse response) throws IOException {
        String url = "/i/cristal/user_male_128.png";
        if (isFemale && "s".equals(size)) {
            url = "/i/cristal/user_female_64.png";
        } else if (isFemale) {
            url = "/i/cristal/user_female_128.png";
        } else if ("s".equals(size)) {
            url = "/i/cristal/user_male_64.png";
        }

        response.sendRedirect(url);
    }

    @RequestMapping(value = "/user/image/{userId}", method = RequestMethod.POST)
    public ModelAndView saveUserFace(@CookieValue(required = false) Cookie session,
                                     @PathVariable long userId, @RequestParam("image") MultipartFile f, ModelMap model) throws IOException {

        User u = dao.get(User.class, userId);

        if (f == null || f.getSize() == 0) {
            return peopleController.editUser(session, userId, new Message("Странный какой-то файл", false), model);
        }

        File userLocalStorage = new File(LOCAL_IMG + u.getLogin());
        if (!userLocalStorage.exists()) {
            userLocalStorage.mkdir();
        }

        File face = new File(userLocalStorage, "o");
        u.getProfile().setFacePath(face.getAbsolutePath());

        InputStream in = f.getInputStream();
        FileOutputStream out = new FileOutputStream(face);

        ReadableByteChannel inputChannel = Channels.newChannel(in);
        WritableByteChannel outputChannel = Channels.newChannel(out);

        fastChannelCopy(inputChannel, outputChannel);

        inputChannel.close();
        outputChannel.close();

        // resize original
        try {
            BufferedImage original = ImageIO.read(face);
            // small
            if (original.getHeight() > original.getWidth()) {
                BufferedImage s = Images.resize(original, original.getWidth() * 90 / original.getHeight(), 90);
                ImageIO.write(s, "jpg", new File(userLocalStorage, "s"));
            } else {
                BufferedImage s = Images.resize(original, 90, original.getHeight() * 90 / original.getWidth());
                ImageIO.write(s, "jpg", new File(userLocalStorage, "s"));
            }
            // medium
            if (original.getWidth() > 240) {
                BufferedImage m = Images.resize(original, 240, original.getHeight() * 240 / original.getWidth());
                ImageIO.write(m, "jpg", new File(userLocalStorage, "m"));
            } else {
                ImageIO.write(original, "jpg", new File(userLocalStorage, "m"));
            }
        } catch (Exception e) {
            return peopleController.editUser(session, userId, new Message("Лицо мне ваше не мило, " + e.getMessage(), false), model);
        }

        return peopleController.editUser(session, userId, new Message("Лицо принято"), model);
    }


}
