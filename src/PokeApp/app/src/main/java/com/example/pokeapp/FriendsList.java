package com.example.pokeapp;

import java.util.ArrayList;
import java.util.HashMap;

public class FriendsList{

    private static ArrayList<Friend> friendsArray = new ArrayList<>();
    private static HashMap<String, Friend> friendsMap = new HashMap<>();

    public int getSize(){
        return friendsArray.size();
    }

    public boolean isEmpty(){
        return friendsArray.isEmpty();
    }

    public boolean contains(Friend f){
        return getFriendFromUUID(f.getUUID()) != null;
    }

    public Friend getFriendFromUUID(String UUID){
        return friendsMap.get(UUID);
    }

    public void addFriend(Friend friend){
        friendsArray.add(friend);
        friendsMap.put(friend.getUUID(), friend);
    }

    public void removeFriendByUUID(String UUID){
        for (Friend f: friendsArray) {
            if(f.getUUID().equals(UUID)){
                friendsArray.remove(f);
            }
        }
        friendsMap.remove(UUID);
    }

    public Friend getFriendAt(int i){
        try {
            return friendsArray.get(i);
        }catch (IndexOutOfBoundsException e){
            return null;
        }
    }
}
