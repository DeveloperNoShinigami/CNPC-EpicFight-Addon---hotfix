/*
 * Decompiled with CFR 0.152.
 */
package noppes.npcs.api.entity.data;

public interface INPCAi {
    public int getAnimation();

    public void setAnimation(int var1);

    public int getCurrentAnimation();

    public void setReturnsHome(boolean var1);

    public boolean getReturnsHome();

    public int getRetaliateType();

    public void setRetaliateType(int var1);

    public int getMovingType();

    public void setMovingType(int var1);

    public int getNavigationType();

    public void setNavigationType(int var1);

    public int getStandingType();

    public void setStandingType(int var1);

    public boolean getAttackInvisible();

    public void setAttackInvisible(boolean var1);

    public int getWanderingRange();

    public void setWanderingRange(int var1);

    public boolean getInteractWithNPCs();

    public void setInteractWithNPCs(boolean var1);

    public boolean getStopOnInteract();

    public void setStopOnInteract(boolean var1);

    public int getWalkingSpeed();

    public void setWalkingSpeed(int var1);

    public int getMovingPathType();

    public boolean getMovingPathPauses();

    public void setMovingPathType(int var1, boolean var2);

    public int getDoorInteract();

    public void setDoorInteract(int var1);

    public boolean getCanSwim();

    public void setCanSwim(boolean var1);

    public int getSheltersFrom();

    public void setSheltersFrom(int var1);

    public boolean getAttackLOS();

    public void setAttackLOS(boolean var1);

    public boolean getAvoidsWater();

    public void setAvoidsWater(boolean var1);

    public boolean getLeapAtTarget();

    public void setLeapAtTarget(boolean var1);

    public void setMountControl(boolean var1);
}

