package service;
import entities.user;
import java.io.File;
import java.util.List;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Optional;

public class userService {

    private ObjectMapper objectMapper = new ObjectMapper();
    private user user;
    private List<user> userList;

    private final String USER_FILE_PATH = "app/src/main/java/localDb/users.json";

    public userService(user user) throws IOException {
        this.user = user;
        loadUserListFromFile();
    }

    public userService() throws IOException {
        loadUserListFromFile();
    }


    private void loadUserListFromFile() throws IOException {
        userList = objectMapper.readValue(new File(USER_FILE_PATH), new TypeReference<List<user>>() {});
    }


    public Boolean loginUser(){
        Optional<user> foundUser = userList.stream().filter(user1 -> {
            return user1.getName().equals(user.getName());
        }).findFirst();
        return foundUser.isPresent();
    }


    public Boolean signUp(user user1){
        try{
            userList.add(user1);
            saveUserListToFile();
            return Boolean.TRUE;
        }catch (IOException ex){
            return Boolean.FALSE;
        }
    }

    private void saveUserListToFile() throws IOException {
        File usersFile = new File(USER_FILE_PATH);
        objectMapper.writeValue(usersFile, userList);
    }







}
