package ru.point.control;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.point.dao.SmartDao;
import ru.point.model.Session;
import ru.point.model.User;
import ru.point.model.board.Option;
import ru.point.model.board.Post;
import ru.point.model.board.Topic;
import ru.point.model.board.Vote;

import javax.servlet.http.Cookie;
import java.util.*;

/**
 * @author: Mikhail Sedov [12.03.2009]
 */
@Controller
public class BoardController {

    private static final String PREV_VOTE = "from Vote where user = :user and topic = :topic";
    private static final String GET_TOPIC = "from Post where topic = :topic order by time";
    private static final String VOTE_STAT = "select option.description, sum(*) from Vote where topic = :topic group by option";

    @Autowired
    private SmartDao dao;

    private void putCookie(Cookie sessionCookie, ModelMap model) {
        if (sessionCookie != null) {
            Session session = dao.get(Session.class, sessionCookie.getValue());
            if (session != null) {
                Hibernate.initialize(session.getUser());
                model.put("session", session);
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Read-only actions

    @RequestMapping("/board")
    public ModelAndView listTopics(@CookieValue(required = false) Cookie session,
                                   ModelMap model) {
        model.put("topics", dao.findAll(Topic.class));
        putCookie(session, model);
        return new ModelAndView("board/topics", model);
    }

    @RequestMapping("/board/topic/{topicId}")
    public ModelAndView getTopic(@PathVariable("topicId") long topicId, ModelMap model) {
        Map<String, String> args = new HashMap<String, String>();
        args.put("topic", String.valueOf(topicId));

        model.put("posts", dao.filter(Post.class, GET_TOPIC, args));
        return new ModelAndView("board/topic", model);
    }

    @RequestMapping("/board/topic/*/post/{postId}")
    public ModelAndView getPost(@PathVariable("postId") long postId, ModelMap model) {
        model.put("posts", dao.get(Post.class, postId));
        return new ModelAndView("board/topic", model);
    }

    @RequestMapping("/board/topic/{topicId}/vote")
    public ModelAndView getVoteResult(@PathVariable("topicId") long topicId, ModelMap model) {
        Map<String, String> args = new HashMap<String, String>();
        args.put("topic", String.valueOf(topicId));

        model.put("votes", dao.filter(Post.class, VOTE_STAT, args));
        return new ModelAndView("board/topic", model);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "/topic/{topicId}", method = POST)
    public void postReply(@CookieValue("session") String sessionId,
                          @PathVariable("topicId") long topicId,
                          @RequestParam("text") String text) {

        GregorianCalendar now = new GregorianCalendar();

        Session session = dao.get(Session.class, sessionId);
        Topic topic = dao.get(Topic.class, topicId);
        User user = session.getUser();

        Post post = new Post();
        post.setText(text);
        post.setUser(user);
        post.setTopic(topic);
        post.setTime(now);

        topic.setLastActivity(now);

        dao.save(post);
        dao.save(topic);
    }

    @RequestMapping(value = "/topic", method = POST)
    public String startNewTopic(@CookieValue("session") String sessionId,
                                @RequestParam("title") String title,
                                @RequestParam("text") String text) {

        GregorianCalendar now = new GregorianCalendar();

        Session session = dao.get(Session.class, sessionId);
        User user = session.getUser();

        Topic topic = saveTopic(now, title, null);

        Post post = new Post();
        post.setUser(user);
        post.setText(text);
        post.setTopic(topic);
        post.setTime(now);

        dao.save(post);

        return "redirect:/board/topic/" + topic.getId(); // TODO:
    }

    public String startNewVote(@CookieValue("session") String sessionId,
                               @RequestParam("title") String title,
                               @RequestParam("text") String text,
                               @RequestParam("options") List<Option> options) {

        GregorianCalendar now = new GregorianCalendar();

        Session session = dao.get(Session.class, sessionId);
        User user = session.getUser();

        Topic topic = saveTopic(now, title, options);

        if (text != null) {
            Post post = new Post();
            post.setUser(user);
            post.setText(text);
            post.setTopic(topic);
            post.setTime(now);

            dao.save(post);
        }

        return "redirect:/board/topic/" + topic.getId(); // TODO:
    }

    private Topic saveTopic(Calendar now, String title, List<Option> option) {
        Topic topic = new Topic();
        topic.setTitle(title);
        topic.setTopicStartTime(now);
        topic.setLastActivity(now);
        topic.setOptions(option);

        dao.save(topic);

        return topic;
    }

    @RequestMapping(value = "/topic/*/reply/{postId}", method = POST)
    public void replyTo(@CookieValue("session") String sessionId,
                        @PathVariable("postId") long replyToPostId,
                        @RequestParam("text") String text) {

        GregorianCalendar now = new GregorianCalendar();

        Session session = dao.get(Session.class, sessionId);
        Post replyTo = dao.get(Post.class, replyToPostId);
        Topic topic = replyTo.getTopic();
        User user = session.getUser();

        Post post = new Post();
        post.setText(text);
        post.setUser(user);
        post.setTopic(topic);
        post.setTime(now);

        topic.setLastActivity(now);

        dao.save(post);
        dao.save(topic);
    }

    @RequestMapping(value = "/topic/*/post/{postId}", method = POST)
    public void update(@CookieValue("session") String sessionId,
                       @PathVariable("postId") long postId,
                       @RequestParam("text") String text) {

        GregorianCalendar now = new GregorianCalendar();

        Session session = dao.get(Session.class, sessionId);
        Post post = dao.get(Post.class, postId);
        User user = session.getUser();
        Topic topic = post.getTopic();

        if (user.getId() == post.getUser().getId()) {
            post.setText(text);
            post.setUpdateTime(now);

            topic.setLastActivity(now);

            dao.save(post);
            dao.save(topic);
        }
    }

    @RequestMapping(value = "/topic/{topicId}/vote/{optionId}", method = POST)
    public void vote(@CookieValue("session") String sessionId,
                     @PathVariable("topicId") long topicId,
                     @PathVariable("optionId") long optionId) {

        GregorianCalendar now = new GregorianCalendar();

        Session session = dao.get(Session.class, sessionId);
        Topic topic = dao.get(Topic.class, topicId);
        Option option = dao.get(Option.class, optionId);
        User user = session.getUser();

        Map<String, String> map = new HashMap<String, String>();
        map.put("user", String.valueOf(user.getId()));
        map.put("topic", String.valueOf(topic.getId()));

        Vote vote = dao.findUniqueObject(Vote.class, PREV_VOTE, map);
        if (vote == null) {
            vote = new Vote();
            vote.setUser(user);
            vote.setTopic(topic);
            vote.setTime(now);
        } else {
            vote.setUpdateTime(now);
        }
        vote.setOption(option);

        dao.save(vote);
    }
}
