package com.hello.screen;

import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;
import org.pmw.tinylog.writers.ConsoleWriter;
import org.pmw.tinylog.writers.FileWriter;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerConfiguration {

    public LoggerConfiguration() {

        Configurator.currentConfig()
                .formatPattern("[{date}] - {level} ({class_name}) {method}() at line:{line}  ***  {message}  ***")
                .removeAllWriters()
                .addWriter(new ConsoleWriter())
                .addWriter(new FileWriter("logs.txt"))
                .level(Level.TRACE)
                .activate();

    }
}
