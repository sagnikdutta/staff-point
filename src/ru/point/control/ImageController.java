package ru.point.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.ModelMap;
import ru.point.dao.SmartDao;
import ru.point.model.User;
import ru.point.view.Message;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.channels.Channels;
import java.net.URL;


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

    @RequestMapping(value = "/user/image/{userId}", method = RequestMethod.GET)
    public void getUserFace(@PathVariable long userId, HttpServletResponse response) throws IOException {
        User u = dao.get(User.class, userId);
        if (u.getProfile().getFacePath() != null) {
            if (new File(u.getProfile().getFacePath()).exists()) {

                ReadableByteChannel inputChannel = Channels.newChannel(new FileInputStream(u.getProfile().getFacePath()));
                WritableByteChannel outputChannel = Channels.newChannel(response.getOutputStream());

                fastChannelCopy(inputChannel, outputChannel);
                
                return;
            }
        }

        response.sendRedirect("/i/user-128.png");
    }

    @RequestMapping(value = "/user/image/{userId}", method = RequestMethod.POST)
    public ModelAndView saveUserFace(@CookieValue(required = false) Cookie session,
                                     @PathVariable long userId, @RequestParam("image") MultipartFile f, ModelMap model) throws IOException {

        User u = dao.get(User.class, userId);

        if (f == null || f.getSize() == 0) {
            peopleController.editUser(session, userId, new Message("Странный какой-то файл", false), model);
        }


        File userLocalStorage = new File(LOCAL_IMG + u.getLogin());
        if (!userLocalStorage.exists()) {
            userLocalStorage.mkdir();
        }

        File face = new File(userLocalStorage, "face.jpg");
        u.getProfile().setFacePath(face.getAbsolutePath());

        InputStream in = f.getInputStream();
        FileOutputStream out = new FileOutputStream(face);

        ReadableByteChannel inputChannel = Channels.newChannel(in);
        WritableByteChannel outputChannel = Channels.newChannel(out);

        fastChannelCopy(inputChannel, outputChannel);

        inputChannel.close();
        outputChannel.close();

        return peopleController.editUser(session, userId, new Message("Лицо принято"), model);
    }
}
