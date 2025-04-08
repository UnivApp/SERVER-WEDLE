package yerong.wedle.chat.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yerong.wedle.chat.domain.chatting.Message;
import yerong.wedle.chat.dto.chatting.ChatRequest;
import yerong.wedle.chat.dto.chatting.ChatResponse.ChatMessageListResponse;
import yerong.wedle.chat.dto.chatting.ChatResponse.ChatRoomsResponse;
import yerong.wedle.chat.dto.chatting.ChatResponse.CreateNewChatRoomResponse;
import yerong.wedle.chat.service.ChatService;

@Slf4j
@RestController
@RequiredArgsConstructor
//@RequestMapping("/api")
@Tag(name = "Chat", description = "채팅 API Document")
public class ChatApiController {

    private final ChatService chatService;
    private final SimpMessagingTemplate template;

    @PostMapping("/api/chatroom")
    public ResponseEntity<?> createChatRoom(@RequestBody @Valid final ChatRequest.CreateNewChatRoomRequest requestDto,
                                            BindingResult bindingResult) {

        CreateNewChatRoomResponse respDto =
                chatService.createOrGetChatRoom(requestDto);
        if (respDto.isNew()) {
            return ResponseEntity.status(HttpStatus.CREATED).body("채팅방이 생성되었습니다");
        } else {
            return ResponseEntity.ok("기존 채팅방을 응답합니다");
        }
    }

    @GetMapping("/api/auth/chatroom")
    public ResponseEntity<?> findChatRoomList() {
        ChatRoomsResponse result = chatService.getChatRooms();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/api/chat/messages/{chatRoomId}")
    public ResponseEntity<?> findChatMessages(
            @PathVariable("chatRoomId") Long chatRoomId,
            @RequestParam(required = false) String cursor,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        ChatMessageListResponse result
                = chatService.getChatMessages(chatRoomId, cursor, pageable);

        return ResponseEntity.ok(result);
    }

    /**
     * 클라이언트가 SEND 할 수 있는 경로 "/pub/chat/message"
     */
    @MessageMapping("/chat/message")
    public void sendMessage(Message message) {
        chatService.sendMessage(message);
    }
}