package com.covid19.match.events;

import com.covid19.match.dtos.UserDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserCreatedEvent extends ApplicationEvent {
    private UserDto userDto;
    private String originalPassword;

    public UserCreatedEvent(Object source, UserDto userDto, String originalPassword) {
        super(source);
        this.userDto = userDto;
        this.originalPassword = originalPassword;

    }
}
