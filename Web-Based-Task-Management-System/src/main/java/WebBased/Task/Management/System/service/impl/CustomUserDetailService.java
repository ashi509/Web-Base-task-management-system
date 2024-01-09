package WebBased.Task.Management.System.service.impl;
import WebBased.Task.Management.System.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var users=userRepository.findByEmail(username)
                .orElseThrow(()->new UsernameNotFoundException("User does not exists"));
        return new CustomUserDetail(users);
    }
}
