package capstone.market.bugigram;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

//    @PostMapping (value = "create/user")
//    public void createUser(@RequestBody UserDTO userDTO){
//       userService.createUser(userDTO);
//    }


    /**
     * 회원가입
     */
    @PostMapping(value = "create/user")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        try {
             UserDTO user = userService.createUser(userDTO);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create user");
        }
    }

    /**
     * 로그인
     */


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDTO userDTO) {
        try {
            UserDTO loggedInUser = userService.loginUser(userDTO.getEmail(), userDTO.getPassword());
            if (loggedInUser != null) {
                System.out.println("성공");
                return ResponseEntity.ok(loggedInUser);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to log in");
        }
    }


    /**
     * 모든 유저 조회해서 유저의 데이터들 프론트로 넘겨주기 // 맨위에 가로로 된 셀 리스트에 쓰일 데이터들
     */
    @GetMapping("/allusers")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        try {
            System.out.println("모든 유저 조회!!!성공!!!");
            List<UserDTO> allUsers = userService.getAllUsers();
            return ResponseEntity.ok(allUsers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    /**
     * 모든 게시글들을 조회해서 데이터를 프론트로 넘겨주기 + 최신순으로 넘겨준다
     */
    @GetMapping("/allPosts")
    public ResponseEntity<List<UserPostDTO>> getAllPosts() {
        try {
            System.out.println("모든 유저 조회!!!성공!!!");
            List<UserPostDTO> allUsers = userService.getAllPosts();
            return ResponseEntity.ok(allUsers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //기본키를 입력받아서 그사람의 게시물 다 보여주기
    /**
     *  그사람의 게시글(사진)을 보여주기
     */
    @GetMapping("/allMyPosts")
    public ResponseEntity<List<UserPostDTO>> getAllMyPosts(@RequestParam Integer userId) {
        try {
            System.out.println("모든 유저 게시글 !!!성공!!!");
            List<UserPostDTO> allUsers = userService.getAllMyPosts(userId);
            return ResponseEntity.ok(allUsers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    //게시글 생성
    @PostMapping("/create/post")
    public ResponseEntity<?> createPost(@RequestBody UserPostDTO userPostDTO) {

        try {
            System.out.println("게시글 생성 완료!!");
            userService.createPost(userPostDTO);
            return ResponseEntity.ok("Post created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create post");
        }
    }








}
