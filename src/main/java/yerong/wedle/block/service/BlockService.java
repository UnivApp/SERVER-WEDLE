package yerong.wedle.block.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import yerong.wedle.block.domain.Block;
import yerong.wedle.block.dto.BlockRequest;
import yerong.wedle.block.dto.BlockedMemberInfoResponse;
import yerong.wedle.block.dto.BlockedMemberResponse;
import yerong.wedle.block.exception.AlreadyBlockedMemberException;
import yerong.wedle.block.exception.BlockNotFoundException;
import yerong.wedle.block.repository.BlockRepository;
import yerong.wedle.member.domain.Member;
import yerong.wedle.member.exception.MemberNotFoundException;
import yerong.wedle.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class BlockService {
    private final BlockRepository blockRepository;
    private final MemberRepository memberRepository;

    public void blockMember(BlockRequest blockRequest) {
        String socialId = getCurrentUserId();
        Member blocker = memberRepository.findBySocialId(socialId).orElseThrow(MemberNotFoundException::new);
        Member blockedMember = memberRepository.findById(blockRequest.getBlockedMemberId())
                .orElseThrow(MemberNotFoundException::new);

        if (blockRepository.existsByBlockerIdAndBlockedMemberId(blocker.getMemberId(), blockedMember.getMemberId())) {
            throw new AlreadyBlockedMemberException();
        }

        Block block = Block.builder()
                .blockerId(blocker.getMemberId())
                .blockedMemberId(blockedMember.getMemberId())
                .build();
        blockRepository.save(block);
    }

    public void unblockMember(BlockRequest blockRequest) {
        String socialId = getCurrentUserId();
        Member blocker = memberRepository.findBySocialId(socialId).orElseThrow(MemberNotFoundException::new);
        Member blockedMember = memberRepository.findById(blockRequest.getBlockedMemberId())
                .orElseThrow(MemberNotFoundException::new);
        Block block = blockRepository.findByBlockerIdAndBlockedMemberId(blocker.getMemberId(),
                blockedMember.getMemberId()).orElseThrow(BlockNotFoundException::new);
        blockRepository.delete(block);
    }

    public boolean isBlocked(Long blockerId, Long blockedMemberId) {
        return blockRepository.existsByBlockerIdAndBlockedMemberId(blockerId, blockedMemberId);
    }

    public BlockedMemberResponse getBlockedMembers() {
        String socialId = getCurrentUserId();
        Member blocker = memberRepository.findBySocialId(socialId).orElseThrow(MemberNotFoundException::new);
        List<Block> blocks = blockRepository.findByBlockerId(blocker.getMemberId());
        return new BlockedMemberResponse(blocker.getMemberId(),
                blocks.stream()
                        .map(this::convertToMemberBlockResponse)
                        .collect(Collectors.toList()));
    }

    public BlockedMemberInfoResponse convertToMemberBlockResponse(Block block) {
        Long blockedMemberId = block.getBlockedMemberId();
        Member blockedMember = memberRepository.findById(blockedMemberId).orElseThrow(MemberNotFoundException::new);

        return new BlockedMemberInfoResponse(blockedMember.getMemberId(),
                blockedMember.getNickname(), blockedMember.getProfileImageUrl());
    }

    private String getCurrentUserId() {
        String socialId = SecurityContextHolder.getContext().getAuthentication().getName();

        return socialId;
    }
}
