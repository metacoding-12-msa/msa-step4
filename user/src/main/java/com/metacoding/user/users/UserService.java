package com.metacoding.user.users;

import com.metacoding.user.core.handler.ex.Exception404;
import com.metacoding.user.core.util.JwtUtil;
import static com.metacoding.user.core.util.JwtUtil.TOKEN_PREFIX;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public String login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new Exception404("유저네임을 찾을 수 없습니다."));
        String token = jwtUtil.create(user.getId(), user.getUsername());
        return TOKEN_PREFIX + token;
    }

    public UserResponse findById(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception404("회원 정보를 찾을 수 없습니다."));
        return UserResponse.from(user);
    }
}