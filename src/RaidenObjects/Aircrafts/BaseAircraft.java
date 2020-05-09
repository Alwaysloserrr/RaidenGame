package RaidenObjects.Aircrafts;

import RaidenObjects.BaseRaidenObject;
import Utils.RaidenObjectController;
import Utils.RaidenObjectOwner;

import java.io.File;
import java.nio.file.Paths;

import static World.World.gameStep;

/**
 * Subclass of BaseRaidenObject, base class of all air crafts in the game,
 * including shooting air crafts and self-destruct air crafts.
 * Class attributes include:
 *     - file2image - An object-image mapping for loadImage function
 * Object attributes include:
 *     - coordinates of object center
 *     - states (alive? on screen?)
 *     - born time (protected)
 *     - object name
 *     - object owner and controller
 *     - object size
 *     - max speed and speed (in pixels per step)
 *     - max and current hp
 *     - max and current steps after death - for death effects
 *     - crash damage - for air craft crashes
 * Object methods include:
 *     - Getters (public) and setters (protected) for some of the attributes
 *     - step (public abstract) - Take a step and modify relative attributes
 *     - getImageFile (public) - Get path to current image of the object
 *     - loadImage (protected static) - Load an image to memory. Used in {@code paint} function.
 *     - paint - Paint the object on screen
 *     - hasHit - Judge if two objects has hit each other
 *     - isOutOfWorld - Judge if the object is out of the Raiden world
 *     - getRandomPlayer (protected) - Get a random player. Used in enemy air crafts / weapons.
 *     - getClosestPlayer (protected) - Get the closest player. Used in enemy air crafts / weapons.
 *     - move (protected) - Move at current speed. Used in {@code step} function.
 *     - receiveDamage - Receive damage and change state if dead
 */
public abstract class BaseAircraft extends BaseRaidenObject {
    protected int hp, stepsAfterDeath = 0;
    protected int maxHp, maxStepsAfterDeath, crashDamage;

    protected BaseAircraft(String name, float x, float y, int sizeX, int sizeY, float maxSpeed,
                           RaidenObjectOwner owner, RaidenObjectController controller,
                           int maxHp, int maxStepsAfterDeath, int crashDamage) {
        super(name, x, y, sizeX, sizeY, maxSpeed, owner, controller);
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.maxStepsAfterDeath = maxStepsAfterDeath;
        this.crashDamage = crashDamage;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getCrashDamage() {
        return crashDamage;
    }

    protected void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getMaxStepsAfterDeath() {
        return maxStepsAfterDeath;
    }

    public int getStepsAfterDeath() {
        return stepsAfterDeath;
    }

    public void receiveDamage(int damage) {
        hp -= damage;
        if (getOwner().isPlayer())
            System.out.println(hp);
        if (hp <= 0)
            markAsDead();
    }

    public void interactIfContacted(BaseAircraft aircraft) {
        if (this.isAlive() && aircraft.isAlive() && this.hasHit(aircraft) &&
                this.getOwner().isEnemyTo(aircraft.getOwner())) {
            this.receiveDamage(aircraft.getCrashDamage());
            aircraft.receiveDamage(this.getCrashDamage());
        }
    }

    protected void incrStepsAfterDeath() {
        ++stepsAfterDeath;
    }

    public File getImageFile() {
        int stepsAfterDeath = getStepsAfterDeath();
        if (stepsAfterDeath <= getMaxStepsAfterDeath()) {
            String filename = getName() + stepsAfterDeath;
            if (!isAlive() && gameStep.intValue() % 2 == 0) {
                incrStepsAfterDeath();
            }
            return Paths.get("data", "images", filename + ".png").toFile();
        }
        else {
            markAsOffScreen();
            return null;
        }
    }
}
