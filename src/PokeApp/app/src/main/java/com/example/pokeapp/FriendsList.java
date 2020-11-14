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

    public void addAll(ArrayList<Friend> friends){
        friendsArray.addAll(friends);
        for (Friend f: friends) {
            friendsMap.put(f.getUUID(), f);
        }
    }

    public void clearList(){
        friendsArray.clear();
        friendsMap.clear();
    }

    public Friend getFriendFromUUID(String UUID){
        return friendsMap.get(UUID);
    }

    public void addFriend(Friend friend){
        friendsArray.add(friend);
        friendsMap.put(friend.getUUID(), friend);
    }

    public Friend getFriendAt(int i){
        try {
            return friendsArray.get(i);
        }catch (IndexOutOfBoundsException e){
            return null;
        }
    }
}
