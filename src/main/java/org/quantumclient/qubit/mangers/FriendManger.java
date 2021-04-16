package org.quantumclient.qubit.mangers;

import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FriendManger {

    private final List<UUID> uuids = new ArrayList<>();

    public void addFriend(UUID uuid) {
        if (!uuids.contains(uuid))
            uuids.add(uuid);
    }

    public void addFriend(PlayerEntity playerEntity) {
        addFriend(playerEntity.getUuid());
    }

    public boolean isFriend(UUID uuid) {
        return uuids.contains(uuid);
    }

    public boolean isFriend(PlayerEntity playerEntity) {
        return uuids.contains(playerEntity.getUuid());
    }

    public void deleteFriend(UUID uuid) {
        if (uuids.contains(uuid)) {
            uuids.remove(uuid);
        }
    }

    public void deleteFriend(PlayerEntity playerEntity) {
        deleteFriend(playerEntity.getUuid());
    }

    public List<UUID> getFriendList() {
        return uuids;
    }
}
