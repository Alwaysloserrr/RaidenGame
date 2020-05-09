package RaidenObjects.Aircrafts.ShootingAircrafts;

import RaidenObjects.Weapons.BigBullet;
import Utils.RaidenObjectController;
import Utils.RaidenObjectOwner;

import static World.World.*;

public final class BigShootingAircraft extends BaseShootingAircraft{
    private static float maxSpeedX = 1.0f, targetY = 50f;

    public BigShootingAircraft(float x, float y) {
        super("BigShootingAircraft", x, y, 100, 65, 0.5f,
                RaidenObjectOwner.BOSS, RaidenObjectController.AI,
                500, 13, 300,
                150, 50, 5f);
    }

    public void shootWeapon() {
        int gameStepSinceReady = gameStep.intValue() - gameStepWhenReady - getInitWeaponCoolDown();
        if (gameStepSinceReady % getWeaponCoolDown() < 18 && gameStepSinceReady % 6 == 0) {
                new BigBullet(getX() - 20, getMaxY() - 5,
                        getX() + rand.nextFloat() * 100, getY() + windowHeight/2f + rand.nextFloat() * 100);
                new BigBullet(getX() + 20, getMaxY() - 5,
                        getX() + rand.nextFloat() * 100, getY() + windowHeight/2f + rand.nextFloat() * 100);
        }
    }

    @Override
    public void initSpeed() {
        super.initSpeed();
        if (!hasReachedTarget) {
            if (getY() > targetY) {
                hasReachedTarget = true;
                gameStepWhenReady = gameStep.intValue();
            }
            else {
                setSpeedY(getInitMaxSpeed());
                return;
            }
        }
        setSpeedY(getMaxSpeed());

        // x-direction: move around initial x position
        if ((gameStep.intValue() - gameStepWhenReady) % 100 < 50 && !isOutOfWorld(getX() - maxSpeedX, getY()))
            setSpeedX(-maxSpeedX);
        else if (!isOutOfWorld(getX() + maxSpeedX, getY()))
            setSpeedX(maxSpeedX);
    }
}
