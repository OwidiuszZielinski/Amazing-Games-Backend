package com.example.amazinggamesbackend.core.baseclasses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
public class AppInfo {
    private String name;
    private String version;

    public AppInfo(String name, String version) {
        this.name = name;
        this.version = version;
    }
}
