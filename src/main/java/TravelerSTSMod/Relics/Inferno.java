package TravelerSTSMod.Relics;

import TravelerSTSMod.Actions.RandomCardForFreeAction;
import TravelerSTSMod.Powers.WhisperPower;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;

public class Inferno extends CustomRelic {
    // 遗物ID
    public static final String ID = "TravelerSTSMod:Inferno";
    // 图片路径
    private static final String IMG_PATH = "TravelerSTSModResources/img/relics/Inferno.png";
    // 遗物类型
    private static final RelicTier RELIC_TIER = RelicTier.RARE;
    // 点击音效
    private static final LandingSound LANDING_SOUND = LandingSound.MAGICAL;

    private boolean activated;

    public Inferno() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
        activated = false;
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void atBattleStartPreDraw() {
        this.activated = false;
    }

    public void atTurnStartPostDraw() {
        if (!activated) {
            activated = true;
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new RandomCardForFreeAction(null, true, null));
        }
    }

    public AbstractRelic makeCopy() {
        return new Inferno();
    }
}
