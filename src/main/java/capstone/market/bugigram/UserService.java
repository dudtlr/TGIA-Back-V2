package capstone.market.bugigram;

import capstone.market.profile_dto.PostDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserJpaRepository userJpaRepository;

    private final UserPostJpaRepository userPostJpaRepository;




    // 회원가입
    public UserDTO createUser(UserDTO userDTO){
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setNickname(userDTO.getNickname());
        user.setPassword(userDTO.getPassword());
        user.setImageFilename("jj11.png"); // 디폴트 값으로 선언
        userJpaRepository.save(user);

        return new UserDTO(user);
    }

    //로그인
    public UserDTO loginUser(String email, String password) {
        User user = userJpaRepository.findByEmailAndPassword(email, password);
        if (user != null) {
            // 일치하는 유저 정보를 UserDTO로 매핑하여 반환
            UserDTO userDTO = new UserDTO();
            userDTO.setEmail(user.getEmail());
            userDTO.setName(user.getName());
            userDTO.setNickname(user.getNickname());
            userDTO.setPassword(user.getPassword());
            userDTO.setUserId(user.getUserId());
            userDTO.setImageFilename(user.getImageFilename());
            return userDTO;
        } else {
            return null;
        }
    }

    // 모든 유저 리스트 조회하기
    public List<UserDTO> getAllUsers() {
        List<User> all = userJpaRepository.findAll();

        List<UserDTO> SearchUsers = all.stream()
                .map(u -> new UserDTO(u))
                .collect(Collectors.toList());

        return SearchUsers;
    }

    // 모든 게시물 조회하기
    public List<UserPostDTO> getAllPosts2() {
        List<UserPost> all = userPostJpaRepository.findAll();
        System.out.println(all);

        List<UserPostDTO> SearchPosts = all.stream()
                .map(u -> new UserPostDTO(u))
                .collect(Collectors.toList());
        System.out.println(SearchPosts);
        return SearchPosts;
    }

    public List<UserPostDTO> getAllPosts() {
        List<UserPost> all = userPostJpaRepository.findAll();

        List<UserPostDTO> searchPosts = all.stream()
                .sorted(Comparator.comparing(UserPost::getCreatedDate).reversed()) // createdAt 필드를 기준으로 최신순으로 정렬
                .map(UserPostDTO::new)
                .collect(Collectors.toList());

        return searchPosts;
    }



    public List<UserPostDTO> getAllMyPosts(Integer userId) {
        List<UserPost> allPosts = userPostJpaRepository.findAll();

        List<UserPostDTO> userPosts = allPosts.stream()
                .filter(post -> post.getUser().getUserId().equals(userId))
                .map(UserPostDTO::new)
                .collect(Collectors.toList());

        return userPosts;
    }

    public void createPost(UserPostDTO userPostDTO){

        User user = userJpaRepository.findById(userPostDTO.getUserId()).get(); // 게시글 작성자 찾기

        UserPost CreatePost = new UserPost();
        CreatePost.setUser(user);
        CreatePost.setPostImageFilename(userPostDTO.getPostImageFilename());
        CreatePost.setLikeTextField(userPostDTO.getLikeTextField());
        CreatePost.setIntroTextField(userPostDTO.getIntroTextField());
        CreatePost.setIsreal(1);

        userPostJpaRepository.save(CreatePost);





    }



}
