package capstone.market.initData;

import capstone.market.domain.*;
import capstone.market.repository.*;
import capstone.market.service.ChatService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@Slf4j
public class DataLoader {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PurchasedRepository purchasedRepository;
//    @Autowired
//    private ChatRoomRepository chatRoomRepository;
//    @Autowired
//    private ChatMessageRepository chatMessageRepository;
    @Autowired
    private PostDataJpaRepository postDataJpaRepository;
    @Autowired
    ChatService chatService;

    @PostConstruct
    public void init() {
        // 초기화 작업 수행
        Member memberA = new Member("memberA");
        memberA.setUsername("memberA");
        memberRepository.save(memberA);

        Member memberB = new Member("memberB");
        memberB.setUsername("memberB");
        memberRepository.save(memberB);

        Member memberC = new Member("memberC");
        memberC.setUsername("memberC");
        memberRepository.save(memberC);

        // MemberA 가 새 게시글 등록
        Post postByMemberA = new Post();
        postByMemberA.setWho_posted(memberA);
        postDataJpaRepository.save(postByMemberA);

        // MemberB 가 새 게시글 등록
        Post postByMemberB = new Post();
        postByMemberB.setWho_posted(memberB);
        postDataJpaRepository.save(postByMemberB);

        // MemberB 가 MemberA의 게시글에서 문의하기 버튼을 누름
        ChatRoom chatRoom = chatService.startChatRoomService(postByMemberA, memberB);
        ChatRoom chatRoom1 = chatService.startChatRoomService(postByMemberA, memberC);

        // MemberB 가 채팅방에서 hello memberA 입력 후 전송 버튼을 누름
        ChatMessage chatMessage = chatService.startChatMessageService(chatRoom, memberB, "hello memberA");
        ChatMessage chatMessage1 = chatService.startChatMessageService(chatRoom1, memberC, "hello memberA");

        // memberA 가 채팅방에서 hello memberB 답장
        chatService.startChatMessageService(chatRoom, memberA, "hello memberB");

        // 해당 포스트에 등록된 채팅방 리스트를 불러온다
        List<ChatRoom> rooms = chatService.getChatRoomLists(postByMemberA.getPostId());
        System.out.println("rooms.size() = " + rooms.size());
        for (ChatRoom room : rooms) {
            System.out.println("room.getMember().getUsername() = " + room.getMember().getUsername());
        }

        // memberA 가 등록한 게시글에 대한 대화목록을 불러온다
        List<ChatMessage> chatLists = chatService.getChatLists(5L);

        for (ChatMessage message : chatLists) {
            System.out.println("message.getMember().getUsername() = " + message.getMember().getUsername());
            System.out.println("message.getText() = " + message.getMessage());
        }

        ChaRoomResponse chatResponse = new ChaRoomResponse(chatRoom.getPost(), chatMessage);

        System.out.println(chatResponse);

//        ChatRoom chatRoom = new ChatRoom();
//        chatRoom.setPost(post);
//        chatRoomRepository.save(chatRoom);
//
//        ChatMessage chatMessage = new ChatMessage();
//        chatMessage.setMember(memberB);
//        chatMessage.setText("hello memberA!");
//        chatMessage.setChatRoom(chatRoom);
//        chatMessageRepository.save(chatMessage);
    }

    @Data
    static class ChatRoomResponse {
        private String sender;
        private String receiver;

        public ChatRoomResponse(String memberA, String memberB) {
            this.sender = memberA;
            this.receiver = memberB;
        }
    }

    @Data
    static class ChaRoomResponse {
        private String usernameA;
        private String usernameB;
        private String message;

        public ChaRoomResponse(Post post, ChatMessage chatMessage) {
            this.usernameA = post.getWho_posted().getUsername();
            this.usernameB = chatMessage.getMember().getUsername();
            this.message = chatMessage.getMessage();
        }
    }
}