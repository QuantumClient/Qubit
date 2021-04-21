package org.quantumclient.qubit.mangers;

import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;
import java.util.List;

public class FriendManger implements MangerManger.Manger {

    private List<String> friends;

    public void addFriend(String name) {
        if (!friends.contains(name))
            friends.add(name);
    }

    public void addFriend(PlayerEntity playerEntity) {
        addFriend(playerEntity.getGameProfile().getName());
    }

    public boolean isFriend(String name) {
        return friends.contains(name);
    }

    public boolean isFriend(PlayerEntity playerEntity) {
        return isFriend(playerEntity.getGameProfile().getName());
    }

    public void deleteFriend(String name) {
        if (friends.contains(name)) {
            friends.remove(name);
        }
    }

    public void deleteFriend(PlayerEntity playerEntity) {
        deleteFriend(playerEntity.getGameProfile().getName());
    }

    public List<String> getFriends() {
        return friends;
    }

    @Override
    public void init() {
        friends = new ArrayList<>();
    }

    @Override
    public String getName() {
        return "Friends";
    }

}