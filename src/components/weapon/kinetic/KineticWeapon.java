package components.weapon.kinetic;

import animations.AnimationManager;
import animations.effects.AnimationInstantDeath;
import animations.projectile.AnimProjectileBulletCombat;
import components.DamageType;
import components.mod.offense.AchillesMod;
import components.mod.offense.AresMod;
import components.mod.offense.HadesMod;
import components.mod.offense.KratosMod;
import components.weapon.WeaponTargetUnitSequentialFire;
import components.weapon.WeaponType;
import conditions.debuffs.Decay;
import conditions.debuffs.Stop;
import conditions.instant.Damage;
import objects.entity.unit.Unit;
import ui.sound.Sounds;

public abstract class KineticWeapon extends WeaponTargetUnitSequentialFire
{
    public static final WeaponType WEAPON_TYPE = WeaponType.KINETIC;
    public static final DamageType DAMAGE_TYPE = DamageType.TRUE;


//    private static int finalShotCounter = 0;

    protected float soundPitch;
    protected float soundVolume;
    protected int animationSize;

    private int bonusShots = 0;
    private int bonusRange = 0;

    public KineticWeapon(int delayTotal)
    {
        super(delayTotal);
        weaponType = WEAPON_TYPE;
        damageType = DAMAGE_TYPE;
        name = "Autocannon";
    }

    public int getNumShots()
    {
        return numShots + bonusShots;
    }

    public int getMaxRange()
    {
        return super.getMaxRange() + bonusRange;
    }


    public void applyMod()
    {
        if(getOwner().hasMod(AresMod.class))
        {
            name = AresMod.NAME;
            blockPenetration += AresMod.BLOCK_PENETRATION;
            bonusRange += AresMod.RANGE_BONUS;
        }
        if(getOwner().hasMod(KratosMod.class))
        {
            name = KratosMod.NAME;
            bonusShots = KratosMod.SHOT_PENALTY;
            accuracy -= KratosMod.ACCURACY_PENALTY;
        }
        if(getOwner().hasMod(HadesMod.class))
        {
            name = HadesMod.NAME_KINETIC;
        }
        if(getOwner().hasMod(AchillesMod.class))
        {
            name = AchillesMod.NAME;
        }
    }

    protected void playAudio()
    {
        Sounds.mg.play(getOwner().getPosition(), 1.2f, .8f);
    }

    protected void animation(Unit target, boolean isHit)
    {
        AnimationManager.add(new AnimProjectileBulletCombat(getOwner(), target, animationSize, getActualTravelTime(target)));
    }

    protected void activation(Unit target, boolean isHit)
    {
        if(isHit)
        {
            // Damage
            if(getOwner().hasMod(KratosMod.class))
            {
                target.addCondition(new Damage(getDamage() * KratosMod.DAMAGE_SCALAR, getDamageType(), getActualTravelTime(target), getBlockPenetration()));
            }
            else
            {
                target.addCondition(new Damage(getDamage(), getDamageType(), getActualTravelTime(target), getBlockPenetration()));
            }

            // Kratos Mod Stop Effect
            if(getOwner().hasMod(KratosMod.class))
            {
                target.addCondition(new Stop(KratosMod.STOP_TIME, getActualTravelTime(target)));
            }

            // Hades Mod Decay Effect
            if(getOwner().hasMod(HadesMod.class))
            {
                target.addCondition(new Decay(HadesMod.DECAY_TIME, getActualTravelTime(target)));
             //   target.getConditions().removeBuffs();
            }

            // Achilles Mod Insta Kill
            if(getOwner().hasMod(AchillesMod.class))
            {
                if(target.getCurStructure() < AchillesMod.HEALTH_THRESHOLD)
                {
                    AnimationManager.add(new AnimationInstantDeath(target));
                    target.die();
//                    finalShotCounter++;
//                    System.out.println(finalShotCounter);
                }
            }

        }
    }

}
