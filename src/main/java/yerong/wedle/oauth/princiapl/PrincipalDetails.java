package yerong.wedle.oauth.princiapl;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import yerong.wedle.member.domain.Member;
import yerong.wedle.member.exception.MemberNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


@Getter
@Setter
public class PrincipalDetails implements UserDetails {
    private Member member;
    Map<String, Object> attributes;
    public PrincipalDetails() {
    }
    public PrincipalDetails(Member member){
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collector = new ArrayList<>();
        collector.add(() -> member.getRole().getKey());
        return collector;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {

        if (this.member != null) {
            return this.member.getSocialId();
        } else {
            throw new MemberNotFoundException();
        }
    }

    //계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //패스워드의 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        return true;
    }

}
