package org.quantumclient.qubit.mangers;

import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;
import java.util.List;

public class FriendManger {

    private final List<String> friends = new ArrayList<>();

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
}
