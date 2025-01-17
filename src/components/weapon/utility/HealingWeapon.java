package components.weapon.utility;

import animations.AnimationManager;
import animations.beams.AnimBeamConstantTransparent;
import components.DamageType;
import components.mod.healing.ApolloMod;
import components.mod.healing.DionysusMod;
import components.mod.healing.PerseusMod;
import components.mod.healing.PythiaMod;
import components.weapon.WeaponTargetUnit;
import components.weapon.WeaponType;
import conditions.buffs.Fortified;
import conditions.buffs.Glory;
import conditions.buffs.Omen;
import conditions.buffs.Revelry;
import conditions.debuffs.Decay;
import conditions.instant.Repair;
import objects.entity.Entity;

public class HealingWeapon extends WeaponTargetUnit
{
    protected int beamWidth;
    protected int beamDuration;
    protected float healAmount;

    private boolean restoresShields = false;

    private float healingScalar = 1;

    public static final WeaponType WEAPON_TYPE = WeaponType.UTILITY;
    public static final DamageType DAMAGE_TYPE = DamageType.NONE;

    public HealingWeapon()
    {
        damageType = DAMAGE_TYPE;
        weaponType = WEAPON_TYPE;
        name = "Repair Beam";

    }

    public void applyMod()
    {
        if(getOwner().hasMod(PerseusMod.class))
        {
            name = PerseusMod.NAME;
            restoresShields = true;
        }

        if(getOwner().hasMod(ApolloMod.class))
        {
            name = ApolloMod.NAME;
        }

        if(getOwner().hasMod(DionysusMod.class))
        {
            name = DionysusMod.NAME;
        }

        if(getOwner().hasMod(PythiaMod.class))
        {
            name = PythiaMod.NAME;
        }
    }

    public int getMinRange() 						{	return 10;												}

    public boolean canUse(Entity target)
    {
        return super.canUse() && target != null && inRange(target);
    }

    protected void playAudio()
    {

    }

    protected void animation(objects.entity.unit.Unit a, boolean isHit)
    {
        AnimationManager.add(new AnimBeamConstantTransparent(getOwner(), a, beamWidth, beamDuration, 50, 100));
    }

    protected void activation(objects.entity.unit.Unit target, boolean isHit)
    {
        // ApolloMod breaks through decay
        if(getOwner().hasMod(ApolloMod.class))
        {
//            target.getConditions().removeDebuffs();
            target.addCondition(new Glory(ApolloMod.DURATION));
        }

        // Otherwise, we can't heal or apply positive conditions
        else if(target.hasCondition(Decay.class))
        {
            return;
        }

        target.addCondition(new Repair(healAmount * healingScalar, restoresShields));

        if(getOwner().hasMod(PerseusMod.class))
        {
            target.addCondition(new Fortified(PerseusMod.DURATION));
        }

        if(getOwner().hasMod(PythiaMod.class))
        {
            target.addCondition(new Omen(PythiaMod.DURATION));
        }

        if(getOwner().hasMod(DionysusMod.class))
        {
            target.addCondition(new Revelry(DionysusMod.DURATION));
        }


    }

}
