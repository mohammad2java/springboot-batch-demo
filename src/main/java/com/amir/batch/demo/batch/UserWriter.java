package com.amir.batch.demo.batch;

import java.util.List;
import java.util.logging.Logger;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amir.batch.demo.model.User;
import com.amir.batch.demo.repository.UserRepository;

@Component
public class UserWriter implements ItemWriter<User> {

    private static final Logger LOGGER = Logger.getLogger(UserWriter.class.getName());

    @Autowired
    private UserRepository userRepository;

    @Override
    public void write(List<? extends User> users) throws Exception {
        LOGGER.info("Data saved for users:"+users+":"+Thread.currentThread().getName());
        users.stream().map(s -> {
        	s.setId(Double.valueOf(Math.random()*1000).longValue());
        	return s;}).forEach(userRepository::save);
    }
}
