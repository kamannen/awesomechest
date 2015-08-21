package kamannen.awesomechest.handler;

import kamannen.awesomechest.lib.References;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;

public class EntityHelper {
    public static NBTTagCompound getCustomEntityData(final Entity entity) {
        if (entity != null && entity.getEntityData().hasKey(References.MOD_ID) && entity.getEntityData().getTag(References.MOD_ID) instanceof NBTTagCompound) {
            return entity.getEntityData().getCompoundTag(References.MOD_ID);
        }

        return new NBTTagCompound();
    }

    public static void saveCustomEntityData(final Entity entity, final NBTTagCompound nbtTagCompound) {
        if (entity != null) {
            entity.getEntityData().setTag(References.MOD_ID, nbtTagCompound);
        }
    }
}
