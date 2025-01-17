package components.weapon.explosive;

import components.DamageType;
import components.mod.offense.ArtemisMod;
import components.mod.offense.CerberusMod;
import components.mod.offense.HermesMod;
import components.mod.offense.NyxMod;
import components.mod.utility.AthenaMod;
import components.weapon.WeaponTargetUnit;
import components.weapon.WeaponType;
import engine.Utility;
import engine.states.Game;
import objects.entity.missile.MissileDark;
import objects.entity.missile.MissileEntity;
import objects.entity.missile.MissileInferno;
import objects.entity.unit.Unit;
import ui.sound.Sounds;

public abstract class ExplosiveWeapon extends WeaponTargetUnit
{
    public static final WeaponType WEAPON_TYPE = WeaponType.EXPLOSIVE;
    public static final DamageType DAMAGE_TYPE = DamageType.TRUE;

    protected float soundPitchMin;
    protected float soundPitchMax;

    public ExplosiveWeapon()
    {
        super();
        weaponType = WEAPON_TYPE;
        damageType = DAMAGE_TYPE;
        name = "Missile";
    }

    public void applyMod()
    {
        if (getOwner().hasMod(ArtemisMod.class))
        {
            name = ArtemisMod.NAME;
            maxRange += ArtemisMod.MISSLE_RANGE_BONUS;
        }
        if (getOwner().hasMod(HermesMod.class))
        {
            name = HermesMod.NAME;
            useTime -= HermesMod.REDUCED_USE_TIME;
            cooldown += HermesMod.INCREASED_COOLDOWN;
        }
        if (getOwner().hasMod(CerberusMod.class))
        {
            name = CerberusMod.NAME;
        }
        if (getOwner().hasMod(NyxMod.class))
        {
            name = NyxMod.NAME;
        }
        if(getOwner().hasMod(AthenaMod.class))
        {
            name = "Tactical Missile";
            radius += Math.round(radius * AthenaMod.AREA_INCREASE);
        }
    }

    protected void playAudio()
    {
        Sounds.laser.play(getOwner().getPosition(), Utility.random(soundPitchMin, soundPitchMax));
    }

    protected void animation(Unit a, boolean isHit)
    {

    }

    protected void activation(Unit target, boolean isHit)
    {

        if (getOwner().hasMod(CerberusMod.class))
        {
            Game.addMissile(new MissileInferno(getOwner(), target, isHit,
                    getMaxRange(), getDamage(), getDamageType(), getRadius(),
                    CerberusMod.DURATION, CerberusMod.DAMAGE_TOTAL));
        } else if (getOwner().hasMod(NyxMod.class))
        {
            Game.addMissile(new MissileDark(getOwner(), target, isHit,
                    getMaxRange(), getDamage(), getDamageType(), getRadius(),
                    NyxMod.DURATION));
        } else
        {
            Game.addMissile(new MissileEntity(getOwner(), target, isHit, getMaxRange(), getDamage(), getDamageType(), getRadius()));
        }
    }


}
