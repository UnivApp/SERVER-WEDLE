package yerong.wedle.oauth.princiapl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import yerong.wedle.member.domain.Member;
import yerong.wedle.member.repository.MemberRepository;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String socialId) throws UsernameNotFoundException {
        Member member = memberRepository.findBySocialId(socialId).orElseThrow();
        if(member == null){
            return null;
        }
        else{
            return new PrincipalDetails(member);
        }
    }
}
