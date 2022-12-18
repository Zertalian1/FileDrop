package ru.ccfit.filedrop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class FileSystemConfig {

    @Bean
    public Path rootPath( @Value("${server.file.root.path}") String rootPath){
        return Paths.get(rootPath);
    }
}
