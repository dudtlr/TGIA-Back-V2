package capstone.market.bugigram;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
public class UserPostDTO {




    private Integer postId;
    private Integer userId;


    private String name; // 유저 이름

    private String imageFilename; // 올린 사람의 유저 이미지
    private String likeTextField;
    private String introTextField;

    private String postImageFilename;
    private Integer isreal;


    public UserPostDTO(UserPost userPost) {
        this.postId = userPost.getPostId();
        this.name = userPost.getUser().getName();
        this.imageFilename = userPost.getUser().getImageFilename();
        this.likeTextField = userPost.getLikeTextField();
        this.introTextField = userPost.getIntroTextField();
        this.postImageFilename = userPost.getPostImageFilename();
        this.isreal = userPost.getIsreal();

    }
}
