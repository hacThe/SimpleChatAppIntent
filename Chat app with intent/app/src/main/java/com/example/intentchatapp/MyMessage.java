package com.example.intentchatapp;

import java.util.ArrayList;
import java.util.List;

public class MyMessage {
    ArrayList<String> sender = new ArrayList<>();
    List<Boolean> isSender = new ArrayList<Boolean>();
    List<Boolean> isPicture = new ArrayList<Boolean>();
    public  void AddSender(String list1)
    {
        sender.add(list1);
        isSender.add(true);
        isPicture.add(false);
    }
    public  void AddReceiver(String list1)
    {
        sender.add(list1);
        isSender.add(false);
        isPicture.add(false);
    }
    public void AddSenderPicture(String imageInfo)
    {

        sender.add(imageInfo);
        isSender.add(true);
        isPicture.add(true);
    }

    public void AddReciverPicture(String imageInfo)
    {

        sender.add(imageInfo);
        isSender.add(false);
        isPicture.add(true);
    }

}
