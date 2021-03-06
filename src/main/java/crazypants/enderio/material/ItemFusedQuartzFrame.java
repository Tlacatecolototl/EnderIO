package crazypants.enderio.material;

import java.util.Collections;
import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import crazypants.enderio.EnderIO;
import crazypants.enderio.EnderIOTab;
import crazypants.enderio.ModObject;
import crazypants.enderio.crafting.IEnderIoRecipe;
import crazypants.enderio.crafting.impl.EnderIoRecipe;
import crazypants.enderio.machine.MachineRecipeRegistry;
import crazypants.enderio.machine.painter.BasicPainterTemplate;
import crazypants.enderio.machine.painter.PainterUtil;
import crazypants.enderio.machine.painter.TileEntityPaintedBlock;

public class ItemFusedQuartzFrame extends Item {

  public static ItemFusedQuartzFrame create() {
    ItemFusedQuartzFrame result = new ItemFusedQuartzFrame();
    result.init();
    return result;
  }

  protected ItemFusedQuartzFrame() {
    setCreativeTab(EnderIOTab.tabEnderIO);
    setUnlocalizedName("enderio." + ModObject.itemFusedQuartzFrame.name());
    setMaxStackSize(64);
  }

  protected void init() {
    GameRegistry.registerItem(this, ModObject.itemFusedQuartzFrame.unlocalisedName);
    MachineRecipeRegistry.instance.registerRecipe(ModObject.blockPainter.unlocalisedName, new FramePainterRecipe(this));
  }

  @Override
  public void registerIcons(IIconRegister IIconRegister) {
  }

  @Override
  public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float par8,
      float par9, float par10) {

    if(world.getBlock(x, y, z) == EnderIO.blockFusedQuartz) {
      TileEntityPaintedBlock tecb = (TileEntityPaintedBlock) world.getTileEntity(x, y, z);
      if(tecb == null) {
        return false;
      }
      tecb.setSourceBlock(PainterUtil.getSourceBlock(itemStack));
      tecb.setSourceBlockMetadata(PainterUtil.getSourceBlockMetadata(itemStack));
      world.markBlockForUpdate(x, y, z);
      world.markBlockForUpdate(x, y, z);
      if(!world.isRemote) {
        if(!player.capabilities.isCreativeMode) {
          itemStack.stackSize--;
        }
      }
      return true;
    } else {
      return false;
    }
  }

  @Override
  @SideOnly(Side.CLIENT)
  public boolean isFull3D() {
    return true;
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void addInformation(ItemStack item, EntityPlayer par2EntityPlayer, List list, boolean par4) {
    super.addInformation(item, par2EntityPlayer, list, par4);
    list.add(PainterUtil.getTooltTipText(item));
  }

  public static final class FramePainterRecipe extends BasicPainterTemplate {

    private ItemFusedQuartzFrame i;

    public FramePainterRecipe(ItemFusedQuartzFrame itemFusedQuartzFrame) {
      i = itemFusedQuartzFrame;
    }

    @Override
    public boolean isValidTarget(ItemStack target) {
      return target != null && target.getItem() == i;
    }

    @Override
    public List<IEnderIoRecipe> getAllRecipes() {
      ItemStack is = new ItemStack(i, 1, 0);
      IEnderIoRecipe recipe = new EnderIoRecipe(IEnderIoRecipe.PAINTER_ID, DEFAULT_ENERGY_PER_TASK, is, is);
      return Collections.singletonList(recipe);
    }
  }
}
