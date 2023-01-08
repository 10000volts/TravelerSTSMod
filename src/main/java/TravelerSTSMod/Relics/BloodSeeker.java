package TravelerSTSMod.Relics;

import TravelerSTSMod.ModCore.TravelerMod;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.MasterReality;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;

public class BloodSeeker extends CustomRelic {
    // 遗物ID
    public static final String ID = "TravelerSTSMod:BloodSeeker";
    // 图片路径
    private static final String IMG_PATH = "TravelerSTSModResources/img/relics/BloodSeeker.png";
    // 遗物类型
    private static final RelicTier RELIC_TIER = RelicTier.COMMON;
    // 点击音效
    private static final LandingSound LANDING_SOUND = LandingSound.FLAT;

    public BloodSeeker() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
    }

    @Override
    public void onEquip() {
        super.onEquip();
        this.counter = 0;
    }

    public boolean canSpawn() {
        return (Settings.isEndless || AbstractDungeon.floorNum <= 49);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onVictory() {
        super.onVictory();
        if (this.counter == 2) {
            flash();
            BookAndQuill.increaseInk(AbstractDungeon.player, 1);
            this.counter = 0;
        } else {
            this.counter += 1;
        }
    }

    public AbstractRelic makeCopy() {
        return new BloodSeeker();
    }
}
