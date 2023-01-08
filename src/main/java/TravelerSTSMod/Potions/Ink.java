package TravelerSTSMod.Potions;

import TravelerSTSMod.Characters.Traveler;
import TravelerSTSMod.ModCore.TravelerMod;
import TravelerSTSMod.Relics.BookAndQuill;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class Ink extends AbstractPotion {
    public static final String POTION_ID = "TravelerSTSMod:Ink";

    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public Ink() {
        super(potionStrings.NAME, POTION_ID, PotionRarity.COMMON, PotionSize.H,
                AbstractPotion.PotionEffect.NONE, Color.GRAY, Color.DARK_GRAY, null);
        this.labOutlineColor = TravelerMod.MY_COLOR;
        this.isThrown = false;
    }

    public void initializeData() {
        this.potency = getPotency();
        this.description = potionStrings.DESCRIPTIONS[0] + this.potency + potionStrings.DESCRIPTIONS[1];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    public void use(AbstractCreature target) {
        BookAndQuill.increaseInk(AbstractDungeon.player, this.potency);
    }

    public boolean canUse() {
        if (AbstractDungeon.actionManager.turnHasEnded &&
                (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT)
            return false;
        if ((AbstractDungeon.getCurrRoom()).event != null &&
                (AbstractDungeon.getCurrRoom()).event instanceof com.megacrit.cardcrawl.events.shrines.WeMeetAgain)
            return false;
        return true;
    }

    public int getPotency(int ascensionLevel) {
        return 2;
    }

    public AbstractPotion makeCopy() {
        return new Ink();
    }
}
