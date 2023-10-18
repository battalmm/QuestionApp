package supports;

import com.korkmazyusufcan.questionapp.dto.request.PostCommentRequest;
import com.korkmazyusufcan.questionapp.dto.request.UpdateCommentRequest;
import com.korkmazyusufcan.questionapp.dto.response.CommentResponse;
import com.korkmazyusufcan.questionapp.entity.Comment;
import com.korkmazyusufcan.questionapp.entity.Post;
import com.korkmazyusufcan.questionapp.entity.user.User;

import java.util.List;

public class CommentTestSupport {

    public static Long DEFAULT_COMMENT_ID = 1L;
    public static Long DEFAULT_USER_ID = 5L;
    public static Long DEFAULT_POST_ID = 10L;

    public Comment generateComment(){
        return  Comment.builder()
                .id(DEFAULT_COMMENT_ID)
                .post(generatePost())
                .user(generateUser())
                .text("comment-text")
                .build();
    }

    public Comment updateComment(Comment comment,UpdateCommentRequest updateCommentRequest){
        comment.setText(updateCommentRequest.getText());
        return comment;
    }

    public PostCommentRequest generatePostCommentRequest(){
        return  PostCommentRequest.builder()
                .postId(DEFAULT_POST_ID)
                .userId(DEFAULT_USER_ID)
                .text("post-comment-request-text")
                .build();
    }

    public CommentResponse generateCommentResponse(Comment comment){
        return CommentResponse.builder()
                .id(comment.getId())
                .text(comment.getText())
                .userName(comment.getUser().getName())
                .build();
    }

    public List<Comment> generateCommentList(){
        return List.of(generateComment());
    }

    public List<CommentResponse> generateCommentResponseList(Comment comment){
        return  List.of(generateCommentResponse(comment));
    }

    public User generateUser(){
        return  User.builder()
                .id(DEFAULT_USER_ID)
                .name("username")
                .surname("surname")
                .email("user-mail")
                .build();
    }

    public Post generatePost(){
        return  Post.builder()
                .id(DEFAULT_POST_ID)
                .text("post-text")
                .build();
    }
}
