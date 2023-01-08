package TravelerSTSMod.Potions;

import TravelerSTSMod.ModCore.TravelerMod;
import TravelerSTSMod.Powers.WhisperPower;
import TravelerSTSMod.Relics.BookAndQuill;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class Whisper extends AbstractPotion {
    public static final String POTION_ID = "TravelerSTSMod:Whisper";

    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public Whisper() {
        super(potionStrings.NAME, POTION_ID, AbstractPotion.PotionRarity.RARE, AbstractPotion.PotionSize.MOON, AbstractPotion.PotionColor.FEAR);

        this.labOutlineColor = TravelerMod.MY_COLOR;

        this.isThrown = true;
        this.targetRequired = true;
    }

    public void initializeData() {
        this.potency = getPotency();
        this.description = potionStrings.DESCRIPTIONS[0] + this.potency + potionStrings.DESCRIPTIONS[1];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    public void use(AbstractCreature target) {
        addToBot(new ApplyPowerAction(target, AbstractDungeon.player,
                new WhisperPower(target, AbstractDungeon.player, this.potency), this.potency));
    }

    public int getPotency(int ascensionLevel) {
        return 8;
    }

    public AbstractPotion makeCopy() {
        return new Whisper();
    }
}
