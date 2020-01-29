package RaidenObjects.Weapons;

import Utils.RaidenObjectController;
import Utils.RaidenObjectOwner;

public class SmallBullet extends BaseWeapon {

    public SmallBullet(float x, float y) {
        super("SmallBullet", x, y, 5, 5, 4,
                RaidenObjectOwner.BOSS, RaidenObjectController.AI, 1);
    }

    public SmallBullet(float x, float y, float targetX, float targetY) {
        super("SmallBullet", x, y, 5, 5, 4,
                RaidenObjectOwner.BOSS, RaidenObjectController.AI, 1, targetX, targetY);
    }
}
