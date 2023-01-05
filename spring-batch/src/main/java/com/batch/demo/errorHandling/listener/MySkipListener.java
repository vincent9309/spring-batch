package com.batch.demo.errorHandling.listener;

import org.springframework.batch.core.SkipListener;
import org.springframework.stereotype.Component;

@Component
public class MySkipListener implements SkipListener<String, String> {
    @Override
    public void onSkipInRead(Throwable t) {

    }

    @Override
    public void onSkipInWrite(String item, Throwable t) {

    }

    @Override
    public void onSkipInProcess(String item, Throwable t) {
        System.out.println(item + " got exception：" + t);
    }
}
