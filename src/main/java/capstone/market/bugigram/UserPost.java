package capstone.market.bugigram;


import capstone.market.domain.BaseEntity;
import capstone.market.domain.Member;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class UserPost extends BaseEntity {

    @Id
    @GeneratedValue
    private Integer postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private String likeTextField;
    private String introTextField;

    private String postImageFilename;

    private Integer isreal = 0;


}
