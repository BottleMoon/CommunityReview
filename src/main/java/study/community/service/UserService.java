package study.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import study.community.domain.User;
import study.community.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean createUser(User user){
        if(existsById(user.getId())){
            return false;
        }
        user.setCreateDate(LocalDateTime.now());
        userRepository.save(user);
        return true;
    }
    public boolean login(User user)throws Exception{
        boolean same = false;
        for (User i :userRepository.findAll()){
            if (i.getId().equals(user.getId()) && i.getPassword().equals(user.getPassword())) {
                same = true;
                break;
            }
        }
        return same;
    }

    public Optional<User> getUser(Long id){
        return userRepository.findById(id);
    }


    public List<User> list() {
        return userRepository.findAll();
    }

    public User findById(String id) {
        for(User i : userRepository.findAll()){
            if(id.equals(i.getId())){
                return i;
            }
        }
        return null;
    }
    public boolean existsById(String id){
        boolean result = false;
        for(User i : userRepository.findAll()){
            if(id.equals(i.getId())){
                result = true;
            }
        }
        return result;
    }
}
