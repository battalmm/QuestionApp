package supports;

import com.korkmazyusufcan.questionapp.dto.PostDto;
import com.korkmazyusufcan.questionapp.dto.UserDto;
import com.korkmazyusufcan.questionapp.dto.request.PostCommentRequest;
import com.korkmazyusufcan.questionapp.dto.request.PostCreateRequest;
import com.korkmazyusufcan.questionapp.dto.request.UpdateCommentRequest;
import com.korkmazyusufcan.questionapp.dto.response.CommentResponse;
import com.korkmazyusufcan.questionapp.entity.Comment;
import com.korkmazyusufcan.questionapp.entity.Post;
import com.korkmazyusufcan.questionapp.entity.user.User;
import org.springframework.security.core.parameters.P;

import java.security.PublicKey;
import java.util.List;

public class TestSupport {

    public static Long DEFAULT_COMMENT_ID = 1L;
    public static Long DEFAULT_USER_ID = 5L;
    public static Long DEFAULT_POST_ID = 10L;
    public static String COMMENT_BASE_URL = "/api/v1/comments";

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

    public List<User> generateUserList(User user){
        return List.of(user);
    }

    public UserDto generateUserDto(User user){
        return UserDto.builder()
                .surname(user.getSurname())
                .creationDate(user.getCreationDate())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    public List<UserDto> generateUserDtoList(UserDto userDto){
        return List.of(userDto);
    }

    public Post generatePost(){
        return  Post.builder()
                .id(DEFAULT_POST_ID)
                .text("post-text")
                .build();
    }

    public List<Post> generatePostList(Post post){
        return List.of(post);
    }

    public PostDto generatePostDto(Post post){
        return PostDto.builder()
                .text(post.getText())
                .build();
    }

    public List<PostDto> generatePostDtoList(PostDto postDto){
        return List.of(postDto);
    }

    public PostCreateRequest generatePostCreateRequest(){
        return PostCreateRequest
                .builder()
                .text("post-create-request-text")
                .userId(DEFAULT_USER_ID)
                .build();
    }
}
