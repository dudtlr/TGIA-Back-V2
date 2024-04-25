package capstone.market.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
//@Builder
@Document(indexName = "post")
public class PostDocument {

    @Id
    @Field(type = FieldType.Keyword)
    private Long post_id;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Keyword)
    private Long member_id;

    @Field(type = FieldType.Keyword)
    private Long purchase_id;

    @Field(type = FieldType.Keyword)
    private String category;

    @Field(type = FieldType.Keyword)
    private String department;

    @Field(type = FieldType.Text)
    private String text;

    @Field(type = FieldType.Integer)
    private Integer price;

    @Field(type = FieldType.Integer)
    private Integer views;

    @Field(type = FieldType.Integer)
    private Integer likes;

    @Field(type = FieldType.Keyword)
    private String locationType;

    @Field(type = FieldType.Text)
    private String location_text;

    @Field(type = FieldType.Keyword)
    private List<String> images = new ArrayList<>();

    @Field(type = FieldType.Date, format = {DateFormat.date_hour_minute_second_millis, DateFormat.epoch_millis})
    private LocalDateTime createdDate;

    @Field(type = FieldType.Date, format = {DateFormat.date_hour_minute_second_millis, DateFormat.epoch_millis})
    private LocalDateTime modifiedDate;

    @Field(type = FieldType.Text)
    private String item_name;

    @Field(type = FieldType.Keyword)
    private String college;

    @Field(type = FieldType.Keyword)
    private String track;

    @Field(type = FieldType.Keyword)
    private String statusType;


     public PostDocument(Post post){
        this.post_id = post.getPostId();
        this.college = post.getCollege().toString();
        this.category = post.getCategoryType().toString();
        this.title = post.getPost_title();
        this.text = post.getPost_text();
        this.member_id = post.getWho_posted().getId();
        this.price = post.getPrice();
        this.department = post.getDepartmentType().toString();
        this.location_text = post.getLocation_text();
        this.locationType = post.getLocationType().toString();
        this.item_name = post.getItem_name();
        this.createdDate = post.getCreatedDate();
        this.modifiedDate = post.getModifiedDate();
        this.views = post.getViews();
        this.likes = post.getLikes();
        this.statusType = post.getStatus().toString();
    }
}