package com.creed.project.lcboapp.writer;

import org.springframework.batch.item.ItemWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestWriter implements ItemWriter {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestWriter.class);

    public TestWriter() { super(); }

    @Override
    public void write(List list) throws Exception {
        LOGGER.info(String.valueOf(list));
    }
}
