package TravelerSTSMod.Relics;

import TravelerSTSMod.Powers.SentencePower;
import TravelerSTSMod.Powers.WhisperPower;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;

public class MobiusBand extends CustomRelic {
    // 遗物ID
    public static final String ID = "TravelerSTSMod:MobiusBand";
    // 图片路径
    private static final String IMG_PATH = "TravelerSTSModResources/img/relics/MobiusBand.png";
    // 遗物类型
    private static final RelicTier RELIC_TIER = RelicTier.BOSS;
    // 点击音效
    private static final LandingSound LANDING_SOUND = LandingSound.FLAT;

    public MobiusBand() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onPlayerEndTurn() {
        super.onPlayerEndTurn();

        AbstractPlayer p = AbstractDungeon.player;
        if (SentencePower.getSentence(p) >= 4) {
            flash();
            addToBot(new ApplyPowerAction(p, p, new EnergizedPower(p, 1), 1));
        }
    }

    public AbstractRelic makeCopy() {
        return new MobiusBand();
    }
}
