package com.vldby.demochat.service;

import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class TimeSource {

    public Date currentTimestamp() {
        return new Date();
    }

    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public ZonedDateTime now() {
        return ZonedDateTime.now();
    }
}
