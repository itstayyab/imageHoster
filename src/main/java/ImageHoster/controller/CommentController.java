package ImageHoster.controller;

import ImageHoster.model.Comment;
import ImageHoster.model.User;
import ImageHoster.service.CommentService;
import ImageHoster.service.ImageService;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommentController {

  @Autowired
  private ImageService imageService;

  @Autowired
  private CommentService commentService;

  // This method is used for posting comment on image
  @RequestMapping(value = "/image/{imageId}/{title}/comments", method = RequestMethod.POST)
  public String commentsOnImage(@PathVariable("imageId") Integer imageId, @PathVariable("title") String title, @RequestParam("comment") String comment, HttpSession session) throws IOException {

    User user = (User) session.getAttribute("loggeduser");
    Comment newComment = new Comment();
    newComment.setUser(user);
    newComment.setText(comment);
    newComment.setCreatedDate(new Date());
    newComment.setImage(imageService.getImage(imageId));

    commentService.updateImageComments(newComment);

    return "redirect:/images/" + imageId + "/" +
        URLEncoder.encode(title, StandardCharsets.UTF_8.toString());
  }
}
