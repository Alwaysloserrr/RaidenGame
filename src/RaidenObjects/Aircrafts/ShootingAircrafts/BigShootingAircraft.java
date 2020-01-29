package RaidenObjects.Aircrafts.ShootingAircrafts;

import RaidenObjects.Weapons.BigBullet;
import Utils.RaidenObjectController;
import Utils.RaidenObjectOwner;

import static World.World.gameStep;
import static World.World.rand;

public class BigShootingAircraft extends BaseShootingAircraft{
    static float maxSpeedX = 1.0f;

    public BigShootingAircraft(float x, float y) {
        super("BigShootingAircraft", x, y, 100, 65, 0.3f,
                RaidenObjectOwner.BOSS, RaidenObjectController.AI,
                500, 13, 300, 150, 150);
    }

    public void shootWeapon() {
        PlayerAircraft closestPlayer = getClosestPlayer();
        int gameStepSinceBirth = gameStep.intValue() - gameStepAtBirth;
        if (gameStepSinceBirth % getWeaponCoolDown() < 15 && gameStepSinceBirth % 5 == 0) {
                new BigBullet(getX() - 20, getMaxY() - 5,
                        closestPlayer.getX() + rand.nextFloat() * 50, closestPlayer.getY() + rand.nextFloat() * 100);
                new BigBullet(getX() + 20, getMaxY() - 5,
                        closestPlayer.getX() + rand.nextFloat() * 50, closestPlayer.getY() + rand.nextFloat() * 100);
        }
    }

    @Override
    public void initSpeed() {
        super.initSpeed();
        setSpeedY(getMaxSpeed());
        if ((gameStep.intValue() - gameStepAtBirth) % 100 < 50 && !isOutOfWorld(getX() - maxSpeedX, getY()))
            setSpeedX(-maxSpeedX);
        else if (!isOutOfWorld(getX() + maxSpeedX, getY()))
            setSpeedX(maxSpeedX);
    }
}
