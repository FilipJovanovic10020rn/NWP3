package rs.raf.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.raf.demo.model.User;
import rs.raf.demo.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private PasswordEncoder passwordEncoder;

    private UserRepository userRepository;
    private TaskScheduler taskScheduler;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, TaskScheduler taskScheduler) {
        this.passwordEncoder = passwordEncoder;

        this.userRepository = userRepository;
        this.taskScheduler = taskScheduler;
    }

    public User create(User user) {
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        return this.userRepository.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> myUser = this.userRepository.findUserByEmail(username);

        if(!myUser.isPresent()) throw new UsernameNotFoundException("User not found in the database");

        User user = myUser.get();

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getPermissions().forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getDescription())));
        return  new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    public List<User> findAll() {
        return this.userRepository.findAll();
    }


    public Object save(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
}
